package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 游戏未开始前玩家离开房间
 * 
 * @author lucio
 *
 */
public class LeaveService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		long userId = WSSessionKit.getUserId(session);
		SendMsg result = new SendMsg(action.getP(), true, action.getT());
		result.setM(action.getP());
		if (null != room) {
			result.setI(room.getMsgSeq().incrementAndGet());
			if (userId == room.getCreateId()) {
				// 庄家退出则直接解散房间, 把人都踢出去
				room.dismissRoom();
			} else {
				room.leaveRoom(session);
			}
			this.responseMsg(room, result);
		}
	}
}
