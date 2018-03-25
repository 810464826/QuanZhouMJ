package com.uitrs.game.majiang.service.impl.opt;

import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 处理暗杠,自己直接摸了四张
 * 
 * @author lucio
 *
 */
public class AnGangService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		OptCheckMsg msg = JsonUtils.parse(JsonUtils.toJson(action.getM()), OptCheckMsg.class);
		PlayerInfo player = WSSessionKit.getPlayerInfo(session);
		Room room = WSSessionKit.getRoom(session);
		// String optType = MJUtil.convertOptType(action.getT());
		// 4张手牌
		String[] cards = new String[] { msg.getC(), msg.getC(), msg.getC(), msg.getC() };
		// 构造操作信息
		msg.setM(cards);
		// CardsShowMsg cmsg = new CardsShowMsg(action.getP(),
		// msg.getChildType(), cards);
		// JuCardsShow cardsShow = new JuCardsShow(msg.getOperateCard(),
		// optType);
		// cardsShow.setCardName(msg.getOperateCard());
		// cardsShow.setMsg(cmsg);
		// 加入操作信息
		player.addOptMsg(msg);
		// 处理手牌
		player.optCards(null, action.getP(), cards);
		// 暗杠后重新计算牌数
		player.buildCount(room);
		// player.buildNoJinCount(room);
		player.buildTingCards(room);
		// room.getMjRule().checkTingCards(room, player);
		// 默认处理,广播出去
		// defaultDeal(session, action);
		this.responseMsg(action.getP(), room, action.getT(), msg);

		// SendMsg result = new SendMsg(action.getP(), true, action.getT());
		// result.setM(action);
		// this.responseMsg(room, result, action.getP());
		// 拿牌
		// NapaiService.fetchNewCard(action.getP(), room);
	}

}
