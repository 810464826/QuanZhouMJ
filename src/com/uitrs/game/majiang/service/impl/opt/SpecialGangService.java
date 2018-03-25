package com.uitrs.game.majiang.service.impl.opt;

import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 处理特殊杠:白八九杠,白板+8条+9万;九杠/九喜,9万+9筒+9条在洮南麻将里面也算杠;幺杠/幺喜,1万+1筒+1条在洮南麻将里面也算杠;中发白杠/
 * 旋风杠,中+ 发+白板在洮南麻将里面也算杠
 * 
 * {operateName:'specialGang', from: '88989', operateCard: 'w3', cards: ['w3',
 * 'w4', 'w5'],childType: 'yaoGang',index:3}
 * 
 * @author lucio
 *
 */
public class SpecialGangService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		OptCheckMsg msg = JsonUtils.parse(JsonUtils.toJson(action.getM()), OptCheckMsg.class);
		PlayerInfo player = WSSessionKit.getPlayerInfo(session);
		// String optType = MJUtil.convertOptType(action.getT());
		// // 构造操作信息
		// CardsShowMsg cmsg = new CardsShowMsg(action.getP(),
		// msg.getChildType(), msg.getCards());
		// JuCardsShow cardsShow = new JuCardsShow(msg.getOperateCard(),
		// optType);
		// cardsShow.setCardName(msg.getOperateCard());
		// cardsShow.setMsg(cmsg);
		// 加入操作信息
		player.addOptMsg(msg);
		// 处理手牌
		player.optCards(null, action.getP(), msg.getM());
		// 默认处理,广播出去
		defaultDeal(session, action);
	}
}
