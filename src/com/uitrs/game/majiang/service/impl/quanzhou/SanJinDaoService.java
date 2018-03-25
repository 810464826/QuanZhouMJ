package com.uitrs.game.majiang.service.impl.quanzhou;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;

public class SanJinDaoService extends OneYouService {

	@Override
	public void dealService(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		long userId = action.getP();

		countZhuang(room, userId);
		// 缓存回放消息
		SendMsg replayMsg = new SendMsg(action.getP(), true, action.getT());
		replayMsg.setM(action);
		room.addReplayMsg(replayMsg);
		SendMsg result = new SendMsg(userId, true, action.getT(), room.getMsgSeq().incrementAndGet());
		result.setM(action.getM());
		responseMsg(room, result);
	}
}
