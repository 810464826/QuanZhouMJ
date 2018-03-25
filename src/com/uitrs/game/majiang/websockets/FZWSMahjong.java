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
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.exception.EConst;
import com.uitrs.game.majiang.common.exception.MJException;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;

/**
 * 抚州麻将websocket
 * 
 * @author lucio
 *
 */
@ServerEndpoint("/ws/fzmj")
public class FZWSMahjong {

	private static Log log = Log.getLog(FZWSMahjong.class);

	/**
	 * join:加入房间;create:创建房间;card:出牌
	 * 
	 * @param message
	 * @param session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		if (log.isInfoEnabled()) {
			log.info("receive user " + session.getId() + " msg: " + message);
		}
		// 解析消息,根据type类型判断具体动作
		ReceiveMsg action = JsonUtils.parse(message, ReceiveMsg.class);
		if (null != action && 0 != action.getT()) {
			MJCache.getCache().getService(action.getT()).dealService(session, action);
		}
	}

	/**
	 * 打开websocket连接
	 * 
	 * @param session
	 */
	@OnOpen
	public void onOpen(Session session) {
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
		// IdGen.getInstance().addTestUserId(openId.toString());
		if (log.isInfoEnabled()) {
			log.info("A user close connection, roomId: " + roomId + ", user: " + userId);
		}
		if (0 != roomId) {
			Room room = MJCache.getCache().getRoom(roomId);
			if (room != null) {
				MJCache.getCache().removeSession(userId);
				// 如果是加入房间或者创建房间的时候断了,则删除某个用户信息,不发送消息
				if (room.getStep() == MJConst.STEP_CREATE) {
					room.leaveRoom(session);
				} else {
					// 通知其他玩家某个人离线了
					SendMsg result = new SendMsg(userId, true, IMsg.DISCONNECT_TYPE);
					result.setM(userId);
					room.broadcast(result);
				}

				// room.leaveRoom(session);
				// // 如果房间没有人了,则直接解散,释放缓存
				// if (room.getNowNumber() <= 0) {
				// MJCache.getCache().removeRoom(room);
				// if (log.isInfoEnabled()) {
				// log.info("a user leave Room ,roomid: " + room.getRoomId());
				// }
				// }
			}
		}

		// if (null != openId) {
		// MJCache.getCache().removeUser(openId.toString());
		// if (log.isInfoEnabled()) {
		// log.info("remove user:" + openId);
		// }
		// }
		if (log.isInfoEnabled()) {
			log.info("A Client Connection closed");
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
		Object roomId = session.getUserProperties().get(MJConst.ROOM_ID);
		if (null != roomId) {
			Room room = MJCache.getCache().getRoom(Long.parseLong(roomId.toString()));
			if (room != null) {
				MJException mje = null;
				// 如果是socket异常,说明用户的链接出现问题,比如掉线等
				if (e instanceof SocketException) {
					log.error("web socket error,session id is " + session.getId(), e);
					// 房间中删除该用户的session信息
					// room.leaveRoom(session);
					mje = new MJException(EConst.SOCKET_EXCEPTION, e);
				} else if (e instanceof MJException) {
					log.error("system error! roomId is " + roomId, e);
					mje = (MJException) e;
				} else {
					log.error("other error! roomId is " + roomId, e);
					mje = new MJException(EConst.OTHER_EXCEPTION, e);
				}
				broadException(session, room, mje);
			}
		} else {
			log.error("other error and roomId is null!", e);
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
		long openId = WSSessionKit.getUserId(session);
		if (0 != openId) {
			errorMsg = MJCache.getCache().getUserInfo(openId).getNickName() + e.getErrorTip();
		}
		msg.setE(errorMsg);
		room.broadcast(msg);
	}
}
