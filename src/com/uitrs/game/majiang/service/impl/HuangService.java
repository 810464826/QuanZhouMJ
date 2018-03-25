package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 荒庄业务逻辑处理
 * 
 * @author lucio
 *
 */
public class HuangService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		Room room = defaultDeal(session, action);
		// 设置下一把庄家
		room.setZhuangId(Long.parseLong(action.getM().toString()));
	}
}
