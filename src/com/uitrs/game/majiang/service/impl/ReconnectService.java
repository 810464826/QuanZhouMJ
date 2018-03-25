package com.uitrs.game.majiang.service.impl;

import java.util.List;

import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.websockets.WebSocketUtil;

/**
 * 重新链接,设置新的session
 * 
 * @author lucio
 *
 */
public class ReconnectService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		long userId = action.getP();
		// 房间号,是否存在,存在则添加,否则不添加
		Object m = action.getM();
		if (null != m && !StringUtils.isEmpty(m.toString())) {
			long roomId = Integer.parseInt(m.toString());
			WSSessionKit.putRoomId(session, roomId);
		}

		dealReconnect(session, userId);
	}

	protected void dealReconnect(Session session, long userId) {
		PlayerInfo player = MJCache.getCache().getPlayerInfo(userId);
		WSSessionKit.putUserId(session, userId);
		MJCache.getCache().addSession(userId, session);
		player.getUserInfo().setState(MJConst.ONLINE);
		long roomId = WSSessionKit.getRoomId(session);
		roomId = (roomId == 0) ? player.getRoomId() : roomId;
		WSSessionKit.putRoomId(session, roomId);
		Room room = WSSessionKit.getRoom(session);
		System.err.println(userId + " ReconnectService room: " + room);
		SendMsg result = new SendMsg(userId, true, IMsg.RECONNECT_TYPE);
		result.setM(userId);
		if (null != room) {
			// 通知其他人某某上线了
			room.broadcast(result, userId);
			// 个人未处理的消息群发出去
			List<SendMsg> checkMsgs = MJCache.getCache().getSendCheckMsgs(userId);

			if (!checkMsgs.isEmpty()) {
				System.err.println(userId + " ReconnectService checkMsgs: " + checkMsgs.size());
				result.setE(checkMsgs);
			}
			// 发给自己
			room.sendMsg(userId, result, session);
			// WebSocketUtil.sendAsyncMsg(result, session);
		} else {
			WebSocketUtil.sendAsyncMsg(result, session);
		}
	}
}
