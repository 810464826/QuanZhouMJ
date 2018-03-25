package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 准备业务处理
 * 
 * @author lucio
 *
 */
public class ReadyService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		long roomId = WSSessionKit.getRoomId(session);
		Room room = MJCache.getCache().getRoom(roomId);

		// 如果打完了所有局数或者分数则将房间得分汇总结果返回,展示结果
		if (room.getUseJuNum() > room.getTotalJuNum()) {
			SendMsg result = new SendMsg(action.getP(), true, action.getT(), room.getMsgSeq().incrementAndGet());
			result.setM(room.getRoomResults());
			this.sendMsgToSelf(action.getP(), room, session, result);
		} else if (!room.readyRoom(session)) {// 如果房间内还有人没准备好,则广播某某准备好了,否则直接发牌
			SendMsg readyMsg = new SendMsg(action.getP(), true, action.getT(), room.getMsgSeq().incrementAndGet());
			// List<Long> m = new ArrayList<Long>();
			// Map<Integer, Long> roomUsers = room.getRoomUsers();
			// for (int i = 0; i < roomUsers.size(); i++) {
			// long value = roomUsers.get(i + 1);
			// if (0 != value) {
			// UserInfo userInfo = MJCache.getCache().getUserInfo(value);
			// if (null != userInfo && userInfo.isReady()) {
			// m.add(userInfo.getUserId());
			// }
			// }
			// }
			readyMsg.setM(room.getReadyNumber());
			this.responseMsg(room, readyMsg);
		}
	}
}
