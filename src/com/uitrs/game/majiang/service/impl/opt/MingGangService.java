package com.uitrs.game.majiang.service.impl.opt;

import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.service.impl.NapaiService;

/**
 * 处理明杠,自己直接摸了3张
 * 
 * @author lucio
 *
 */
public class MingGangService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		OptCheckMsg msg = JsonUtils.parse(JsonUtils.toJson(action.getM()), OptCheckMsg.class);
		PlayerInfo player = WSSessionKit.getPlayerInfo(session);
		// String optType = MJUtil.convertOptType(action.getT());
		// 4张手牌
		String[] cards = new String[] { msg.getC(), msg.getC(), msg.getC() };
		// 构造操作信息
		msg.setM(cards);
		// 加入操作信息
		player.addOptMsg(msg);
		// 处理手牌
		player.optCards(null, action.getP(), cards);
		// 默认处理,广播出去
		defaultDeal(session, action);
		// 拿牌
		Room room = WSSessionKit.getRoom(session);
		NapaiService.fetchNewCard(action.getP(), room);
	}

}
