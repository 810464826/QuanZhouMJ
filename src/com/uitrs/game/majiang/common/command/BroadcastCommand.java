package com.uitrs.game.majiang.common.command;

import java.util.List;

import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.msg.sub.OptSendMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.impl.NapaiService;

/**
 * 广播消息
 * 
 * @author lucio
 *
 */
public class BroadcastCommand extends SendMsgCommand {
	private static Log log = Log.getLog(BroadcastCommand.class);
	private Room room;
	private long who;

	public BroadcastCommand(long who, Room room, int level, int order) {
		this.room = room;
		this.who = who;
		this.level = level;
		this.order = order;
		this.baseOrder = room.getBaseOrder();
	}

	public BroadcastCommand(long who, Room room, int level, SendMsg msg, int order) {
		this.room = room;
		this.who = who;
		this.level = level;
		this.msg = msg;
		this.order = order;
		this.baseOrder = room.getBaseOrder();
	}

	@Override
	public void execute() {

		long fromId = this.room.getRoomUsers().get(this.room.getBaseOrder());
		PlayerInfo from = MJCache.getCache().getPlayerInfo(fromId);
		PlayerInfo opter = MJCache.getCache().getPlayerInfo(this.who);
		Object m = this.msg.getM();
		log.info("BroadcastCommand this.msg.getM(): " + this.msg.getM());
		if (null != m) {
			// 如果有则说明有操作
			OptCheckMsg optMsg = JsonUtils.parse(JsonUtils.toJson(m), OptCheckMsg.class);
			// 获取出牌信息
			String cardName = optMsg.getC();
			if (optMsg.getT() == IMsg.BU_GANG) {
				dealBuGang(opter, cardName);
				// NapaiService.fetchNewCard(this.who, room);
			} else if (optMsg.getT() == IMsg.SPECIAL_JIA_GANG) {
				dealSpecialJiaGang(opter, cardName);
				// NapaiService.fetchNewCard(this.who, room);
			} else {
				opter.addOptMsg(optMsg);
				// 处理手牌,如果是吃,则去掉吃的牌
				String[] cards = optMsg.getM();
				if (null != cards) {
					if (optMsg.getT() == IMsg.CHI_TYPE) {
						cards = dealChi(cardName, cards);
					}
					opter.optCards(cardName, fromId, cards);
				}
				// 胡牌,设置圈数,局数和庄家
				if (this.msg.getT() == IMsg.HU_TYPE) {
					dealHu(from, opter, optMsg);
				}
			}
		}

		// 如果没有操作则直接发牌
		if (this.msg.getT() == IMsg.OPT_PASS) {
			if (room.getActiver() != this.who) {
				NapaiService.fetchNewCard(room.nextUserId(), room);
				// room.setBaseOrder(this.room.nextOrder());
			}
		} else {
			// 缓存回放消息
			room.addReplayMsg(this.msg);

			this.room.setActiver(opter.getOrder());
			this.room.setBaseOrder(opter.getOrder());
			opter.buildCount(room);
			// opter.buildNoJinCount(room);
			opter.buildTingCards(room);
			from.buildCount(room);
			// from.buildNoJinCount(room);
			from.buildTingCards(room);
			// long opterId = opter.getUserInfo().getUserId();
			// 分发给其他人
			this.room.broadcast(this.msg);
			// 检查自己操作后可以有的操作
			// this.room.getMjRule().checkSelf(this.room, opter, "", this.msg);
			// this.room.sendMsg(opterId, msg,
			// MJCache.getCache().getSession(opterId));
		}
	}

	private void dealBuGang(PlayerInfo opter, String cardName) {
		List<OptCheckMsg> optList = opter.getOptMsgs();
		// 找到对应的碰,然后将牌添加到cards中
		for (OptCheckMsg cardsShow : optList) {
			if (cardsShow.getT() == IMsg.PENG_TYPE && cardsShow.getC().equalsIgnoreCase(cardName)) {
				cardsShow.setT(IMsg.BU_GANG);
				String[] cards = addCard(opter, cardName, opter.getUserInfo().getUserId(), cardsShow.getM());
				if (null != cards) {
					cardsShow.setM(cards);
				}
			}
		}
	}

	/**
	 * 处理特殊杠加杠
	 * 
	 * @param opter
	 * @param cardName
	 */
	private void dealSpecialJiaGang(PlayerInfo opter, String cardName) {
		List<OptCheckMsg> optList = opter.getOptMsgs();
		// 找到对应的特殊杠,然后将牌添加到cards中
		for (OptCheckMsg cardsShow : optList) {
			if (cardsShow.getT() == IMsg.SPECIAL_GANG) {
				String[] cards = addCard(opter, cardName, opter.getUserInfo().getUserId(), cardsShow.getM());
				if (null != cards) {
					cardsShow.setM(cards);
				}
			}
		}
	}

	/**
	 * 处理吃牌情况数据
	 * 
	 * @param cardName
	 * @param cards
	 * @return cards
	 */
	private String[] dealChi(String cardName, String[] cards) {
		String[] temp = new String[2];
		int j = 0;
		for (int i = 0; i < cards.length; i++) {
			if (!cards[i].equalsIgnoreCase(cardName)) {
				temp[j] = cards[i];
				j++;
			}
		}
		cards = temp;
		return cards;
	}

	/**
	 * 处理胡牌情况数据
	 * 
	 * @param from
	 * @param opter
	 * @param optMsg
	 */
	private void dealHu(PlayerInfo from, PlayerInfo opter, OptCheckMsg optMsg) {
		// 记录胡牌消息
		// MJCache.getCache().addMsg(room.getRoomId(), opter.getOrder(), msg);
		room.setStep(MJConst.STEP_END);// 胡牌状态
		// 如果连庄则累计连庄次数
		if (this.room.getZhuangId() == this.who && this.room.getPreZhuangId() == this.who) {
			Integer lzCount = opter.getHuInfo().get(MJConst.LIAN_ZHUANG_COUNT);
			opter.getHuInfo().put(MJConst.LIAN_ZHUANG_COUNT, (null == lzCount || 0 == lzCount) ? 1 : lzCount + 1);
		} else {
			opter.getHuInfo().put(MJConst.LIAN_ZHUANG_COUNT, 0);
		}

		// 胡牌次数
		Integer huCount = opter.getHuInfo().get(MJConst.HU_COUNT);
		opter.getHuInfo().put(MJConst.HU_COUNT, (null == huCount || 0 == huCount) ? 1 : huCount + 1);

		// 累加坐庄次数
		Integer zhuangCount = opter.getHuInfo().get(MJConst.ZHUANG_COUNT);
		opter.getHuInfo().put(MJConst.ZHUANG_COUNT, null == zhuangCount ? 1 : zhuangCount + 1);

		// 设置下一把庄家为胡牌者
		// this.room.setZhuangId(opter.getUserInfo().getUserId());

		// 累加放炮次数
		Integer paoCount = from.getHuInfo().get(MJConst.PAO_COUNT);
		from.getHuInfo().put(MJConst.PAO_COUNT, null == paoCount ? 1 : paoCount + 1);

		this.msg.setM(new OptSendMsg(optMsg.getC(), this.who, optMsg.getT(), from.getUserInfo().getUserId()));
	}

	private String[] addCard(PlayerInfo player, String operateCard, long userId, String[] oldCards) {
		for (int i = 0; i < oldCards.length; i++) {
			if (oldCards[i].equalsIgnoreCase(operateCard)) {
				// 处理手牌
				player.optCards(null, userId, new String[] { operateCard });
				String[] cards = addOne2Array(operateCard, oldCards);
				return cards;
			}
		}
		return null;
	}

	private String[] addOne2Array(String addCard, String[] oldCards) {
		String[] cards = new String[oldCards.length + 1];
		for (int i = 0; i < oldCards.length; i++) {
			cards[i] = oldCards[i];
		}
		cards[cards.length - 1] = addCard;
		return cards;
	}
}
