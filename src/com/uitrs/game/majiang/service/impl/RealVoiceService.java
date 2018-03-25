package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 实时语音,直接转发serverId
 * 
 * @author lucio
 *
 */
public class RealVoiceService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		long userId = WSSessionKit.getUserId(session);
		SendMsg result = new SendMsg(userId, true, action.getT());
		result.setM(action);
		room.broadcast(result, userId);
	}
}
