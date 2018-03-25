package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 出牌业务逻辑处理
 * 
 * @author lucio
 *
 */
public class OutCardService extends SupperService {

	/**
	 * 出牌
	 * 
	 * @param session
	 * @param action
	 */
	@Override
	public void doService(Session session, ReceiveMsg action) {

		// OutCardMsg ocm = JsonUtils.parse(JsonUtils.toJson(action.getM()),
		// OutCardMsg.class);
		// String card = ocm.getOc();
		// Set<String> tc = ocm.getTc();
		PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(action.getP());

		Room room = WSSessionKit.getRoom(session);
		String card = dealOutCardInfo(action, playerInfo, room);
		System.err.println("outCard ting: " + playerInfo.getUserInfo().getUserId() + ":::" + playerInfo.getTingCards());
		//缓存回放消息
		SendMsg replayMsg = new SendMsg(action.getP(), true, action.getT());
		replayMsg.setM(action);
		room.addReplayMsg(replayMsg);
		// this.sendMsgToSelf(room, session, result);
		// WebSocketUtil.sendAsyncMsg(result, session);
		// 出牌信息则保存下来,下次重连时发出
		// MJCache.getCache().addMsg(room.getRoomId(), result);

		// 检查其他人是否对出的牌有操作
		checkOtherOpt(action, card, room);
	}

	protected String dealOutCardInfo(ReceiveMsg action, PlayerInfo playerInfo, Room room) {
		// 取字符串的牌
		String card = action.getM().toString();
		playerInfo.outCard(card);
		// String goldCard = room.getMjRule().getGoldCard();
		// int jinIndex = Arrays.binarySearch(room.getMjRule().enablePais(),
		// goldCard);
		// playerInfo.getCounts()[jinIndex]--;
		// if (!card.equalsIgnoreCase(goldCard)) {
		// playerInfo.getNoJinCount()[jinIndex]--;
		// }

		// playerInfo.setTingCards(tc);

		room.setStatus(MJConst.OUT_CARD);// 出牌状态
		room.setBaseOrder(playerInfo.getOrder());
		room.setActiver(playerInfo.getOrder());
		// 重新计算听牌
		playerInfo.buildCount(room);
		// playerInfo.buildNoJinCount(room);
		playerInfo.buildTingCards(room);
		return card;
	}

}
