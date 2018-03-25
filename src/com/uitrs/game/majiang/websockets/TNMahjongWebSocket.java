package com.uitrs.game.majiang.websockets;

import java.io.IOException;
import java.net.SocketException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.exception.EConst;
import com.uitrs.game.majiang.common.exception.MJException;
import com.uitrs.game.majiang.common.kit.IdGen;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;

/**
 * 洮南麻将websocket
 * 
 * @author lucio
 *
 */
@ServerEndpoint("/ws/tnmj")
public class TNMahjongWebSocket {

	private static Log log = Log.getLog(TNMahjongWebSocket.class);

	/**
	 * join:加入房间;create:创建房间;card:出牌
	 * 
	 * @param message
	 * @param session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		if (log.isInfoEnabled()) {
			log.info("receive user: " + session.getId() + " msg: " + message);
		}
		try {
			// 解析消息,根据type类型判断具体动作
			ReceiveMsg action = JsonUtils.parse(message, ReceiveMsg.class);
			if (null != action && 0 != action.getT()) {
				MJCache.getCache().getService(action.getT()).dealService(session, action);
			}
		} catch (Throwable e) {
			log.error("WebSocket onMessage error! msg:" + message + ",session:" + session, e);
		}
	}

	/**
	 * 打开websocket连接
	 * 
	 * @param session
	 */
	@OnOpen
	public void onOpen(Session session) {
		// System.err.println("getMaxIdleTimeout::::"+session.getMaxIdleTimeout());
		// session.setMaxIdleTimeout(1000 * 1 * 30);
		if (log.isInfoEnabled()) {
			log.info("A Client has connected " + session.getId());
		}
	}

	/**
	 * websocket连接关闭
	 * 
	 * @param session
	 */
	@OnClose
	public void onClose(Session session) {
		long roomId = WSSessionKit.getRoomId(session);
		long userId = WSSessionKit.getUserId(session);
		if (0 != userId) {
			if (log.isInfoEnabled()) {
				log.info("A user close connection, roomId: " + roomId + ", user: " + userId);
			}
			IdGen.getInstance().addTestUserId(userId);
			if (0 != roomId) {
				Room room = MJCache.getCache().getRoom(roomId);
				if (room != null) {
					PlayerInfo playInfo = MJCache.getCache().getPlayerInfo(userId);
					// 清除申请解散的数据
					room.getDissmisNumber().clear();
					// 设置该用户离线
					playInfo.getUserInfo().setState(0);
					if (room.isStarted() || playInfo.getUserInfo().isReady() || userId == (room.getCreateId())) {
						// 通知其他玩家某个人离线了
						SendMsg result = new SendMsg(userId, true, IMsg.DISCONNECT_TYPE);
						result.setM(userId);
						room.broadcast(result, userId);
					} else {
						room.leaveRoom(session);
						// 通知其他玩家某个没有准备的人退出了
						SendMsg result = new SendMsg(userId, true, IMsg.LEAVE_TYPE);
						result.setM(userId);
						room.broadcast(result, userId);
					}
				}
			}
			// 连接关闭清除session缓存
			MJCache.getCache().removeSession(userId);
		}
		if (log.isInfoEnabled()) {
			log.info("A Client Connection closed, user: " + userId);
		}
	}

	/**
	 * websocket出现异常
	 * 
	 * @param session
	 * @param e
	 */
	@OnError
	public void onError(Session session, Throwable e) {
		long roomId = WSSessionKit.getRoomId(session);
		String openId = WSSessionKit.getOpenId(session);
		int full = -1;
		if (e instanceof IllegalStateException) {
			full = e.getMessage().indexOf("TEXT_FULL_WRITING");
			log.error("TEXT_FULL_WRITING:" + full);
		}
		// IdGen.getInstance().addTestUserId(openId.toString());
		if (0 != roomId) {
			Room room = MJCache.getCache().getRoom(roomId);
			if (room != null) {
				MJException mje = null;
				// 如果是socket异常,说明用户的链接出现问题,比如掉线等
				if (e instanceof SocketException) {
					log.error("web socket error,openId is " + openId, e);
					// 删除该用户的session信息
					// MJCache.getCache().removeSession(openId);
					// room.leaveRoom(session);
					mje = new MJException(EConst.SOCKET_EXCEPTION, e);
				} else if (e instanceof MJException) {
					log.error("system error! roomId is " + roomId, e);
					mje = (MJException) e;
				} else {
					log.error("other error! roomId is " + roomId, e);
					mje = new MJException(EConst.OTHER_EXCEPTION, e);
				}
				if (full > 0) {
					broadException(session, room, mje);
				}
				// broadException(session, room, mje);
			}
		} else {
			// MJCache.getCache().removeSession(openId);
			log.error("other error and roomId is null! openId is " + openId, e);
		}
	}

	/**
	 * 异常信息广播
	 * 
	 * @param session
	 * @param exception
	 */
	private void broadException(Session session, Room room, MJException e) {
		// 如果用户存在,则标识谁出现异常
		long userId = WSSessionKit.getUserId(session);
		// 构造异常消息进行广播
		SendMsg msg = new SendMsg(userId, false, IMsg.ERROR_TYPE);
		String errorMsg = e.getErrorTip();
		// 如果用户存在,则标识谁出现异常
		if (0 != userId) {
			errorMsg = MJCache.getCache().getUserInfo(userId).getNickName() + e.getErrorTip();
		}
		msg.setE(errorMsg);
		room.broadcast(msg);
	}
}
