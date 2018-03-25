package com.uitrs.game.majiang.service.impl.opt;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.command.BroadcastCommand;
import com.uitrs.game.majiang.common.command.Command;
import com.uitrs.game.majiang.common.command.SendMsgCommand;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 特殊杠加杠,在原来杠的基础上加一杠
 * 
 * @author lucio
 *
 */
public class JiaGangService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		// OptCheckMsg msg = JsonUtils.parse(JsonUtils.toJson(action.getM()),
		// OptCheckMsg.class);
		// PlayerInfo player = WSSessionKit.getPlayerInfo(session);
		// String operateCard = msg.getC();
		// String openId = action.getP();
		// List<JuCardsShow> csList = player.getCardsShow();
		// // 找到对应的特殊杠,然后将牌添加到cards中
		// for (JuCardsShow juCardsShow : csList) {
		// System.err.println(juCardsShow);
		// CardsShowMsg cardsShowMsg = juCardsShow.getMsg();
		// System.err.println(cardsShowMsg);
		// if (juCardsShow.getType().equalsIgnoreCase("specialGang")) {
		// String[] cards = addCard(player, operateCard, openId,
		// cardsShowMsg.getCards());
		// if (null != cards) {
		// cardsShowMsg.setCards(cards);
		// // msg.setCards(cards);
		// }
		// }
		// }

		// 将某人想加喜的消息构建出来放到消息队列中,等级为3,其他玩家可以抢碰,抢胡
		// defaultDeal(session, action);
		Room room = WSSessionKit.getRoom(session);
		// 玩家位子
		int order = MJCache.getCache().getPlayerInfo(action.getP()).getOrder();
		room.setBaseOrder(order);
		room.setActiver(order);
		// 添加补杠消息到队列中
		SendMsgCommand command = new BroadcastCommand(action.getP(), room, Command.THREE_LEVEL, order);
		SendMsg send = new SendMsg(action.getP(), true, action.getT());
		send.setM(action.getM());
		command.setMsg(send);
		room.addCommand(command);
		room.setStatus(MJConst.BU_CARD);// 补杠状态
		// 返回一条某某需要补杠的消息,看其他玩家能不能胡,碰,需不需要抢杠
		SendMsg result = new SendMsg(action.getP(), true, IMsg.WANT_BU_GANG);
		result.setM(action);
		// 添加WANT_BU_GANG消息至缓存,用户重连
		// MJCache.getCache().addMsg(room.getRoomId(), result);

		room.broadcast(result);
	}

	// private String[] addCard(PlayerInfo player, String operateCard, String
	// openId, String[] oldCards) {
	// for (int i = 0; i < oldCards.length; i++) {
	// if (oldCards[i].equalsIgnoreCase(operateCard)) {
	// // 处理手牌
	// player.optCards(null, openId, new String[] { operateCard });
	// String[] cards = addOne2Array(operateCard, oldCards);
	// return cards;
	// }
	// }
	// return null;
	// }
	//
	// private String[] addOne2Array(String addCard, String[] oldCards) {
	// String[] cards = new String[oldCards.length + 1];
	// for (int i = 0; i < oldCards.length; i++) {
	// cards[i] = oldCards[i];
	// }
	// cards[cards.length - 1] = addCard;
	// return cards;
	// }

}
