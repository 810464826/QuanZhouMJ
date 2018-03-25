package com.uitrs.game.majiang.service.impl.opt;

import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 吃牌
 * 
 * @author lucio
 *
 */
public class ChiService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		OptCheckMsg msg = JsonUtils.parse(JsonUtils.toJson(action.getM()), OptCheckMsg.class);
		PlayerInfo player = WSSessionKit.getPlayerInfo(session);
		String[] cards = new String[2];
		int j = 0;
		for (int i = 0; i < msg.getM().length; i++) {
			if (!msg.getM()[i].equalsIgnoreCase(msg.getC())) {
				cards[j] = msg.getM()[i];
				j++;
			}
		}
		// 加入操作信息
		player.addOptMsg(msg);
		// 处理手牌
		player.optCards(msg.getC(), action.getP(), cards);
		// 默认处理,广播出去
		defaultDeal(session, action);
	}
}
