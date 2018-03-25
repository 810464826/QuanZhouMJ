package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.web.model.GamerInfo;
import com.uitrs.game.majiang.websockets.WebSocketUtil;

/**
 * 解散自己创建的房间业务处理
 * 
 * @author lucio
 *
 */
public class DismissService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {

		long roomId = Long.parseLong(action.getM().toString());
		Room room = MJCache.getCache().getRoom(roomId);
		SendMsg result = new SendMsg(action.getP(), true, action.getT());
		result.setM(false);
		if (null != room && room.getCreateId() == action.getP()) {
			// 创建成功了,则创建者增加相应的房卡数
			UserInfo creater = MJCache.getCache().getUserInfo(room.getCreateId());
			boolean isUpdate = GamerInfo.dao.addCards((int) room.getCreateId(), calcRoomCard(room));
			if (isUpdate) {
				creater.setCards(creater.getCards() - calcRoomCard(room));
				result.setM(true);
			}
		}
		WebSocketUtil.sendAsyncMsg(result, session);
	}

	/**
	 * 计算需要消耗的房卡/钻石数
	 * 
	 * @return
	 */
	private int calcRoomCard(Room room) {
		switch (room.getTotalJuNum()) {
		case 8:
			return 5;
		case 16:
			return 10;
		case 100:
			return 5;
		case 200:
			return 10;
		default:
			return 0;
		}
	}
}