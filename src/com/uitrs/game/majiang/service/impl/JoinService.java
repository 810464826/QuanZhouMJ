package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.websockets.WebSocketUtil;

/**
 * 加入房间业务处理
 * 
 * @author lucio
 *
 */
public class JoinService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		// 读取userId
		long userId = action.getP();
		WSSessionKit.putUserId(session, userId);
		String roomId = action.getM().toString();
		// 加入房间
		if (log.isInfoEnabled()) {
			log.info("A new user join, userId is " + userId + ", RoomId is " + roomId);
		}
		SendMsg result = new SendMsg(action.getP(), true, action.getT());
		Room room = MJCache.getCache().getRoom(Long.parseLong(roomId));
		if (null != room) {
			result.setI(room.getMsgSeq().incrementAndGet());
			if (!room.enterRoom(session, userId)) {
				result.setS(false);
				result.setE("房间人数已满");
				WebSocketUtil.sendAsyncMsg(result, session);
			}
		} else {
			result.setS(false);
			result.setE("房间号不存在,或者已经解散");
			WebSocketUtil.sendAsyncMsg(result, session);
		}
	}
}
