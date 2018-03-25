package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.NapaiMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 拿牌业务逻辑处理
 * 
 * @author lucio
 *
 */
public class NapaiService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		fetchNewCard(action.getP(), room);
	}

	public static void fetchNewCard(long selfId, Room room) {
		// 缓存用户的出牌记录
		PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(selfId);
		String faPai = room.fetchPai();
		// 设置最新拿的牌
		playerInfo.fetchCard(faPai);

		// String goldCard = room.getMjRule().getGoldCard();
		// int jinIndex = Arrays.binarySearch(room.getMjRule().enablePais(),
		// goldCard);
		// playerInfo.getCounts()[jinIndex]++;
		// if (!faPai.equalsIgnoreCase(goldCard)) {
		// playerInfo.getNoJinCount()[jinIndex]++;
		// }
		// 拿牌状态
		room.setStatus(MJConst.NA_CARD);
		// 设置当前活动用户
		room.setBaseOrder(playerInfo.getOrder());
		room.setActiver(playerInfo.getOrder());
		long msgId = room.getMsgSeq().incrementAndGet();
		// 剩余牌数
		int cardNum = room.getCards().length - room.getCardNum();
		// 是否荒庄
		if (isHuang(room, faPai, cardNum)) {
			return;
		}
		// 发给其他三家的消息
		SendMsg otherMsg = buildNaPaiMsg(selfId, "", cardNum, msgId);
		room.broadcast(otherMsg, selfId);

		// 构建发给自己的消息
		SendMsg selfMsg = buildNaPaiMsg(selfId, faPai, cardNum, msgId);
		room.getMjRule().checkSelf(room, playerInfo, faPai, selfMsg);
		room.sendMsg(selfId,selfMsg, MJCache.getCache().getSession(selfId));

		// 缓存回放消息
		room.addReplayMsg(selfMsg);
	}

	// /**
	// * 检查拿牌后自己是否有杠和自摸
	// *
	// * @param room
	// * @param playerInfo
	// * @param faPai
	// * @param selfMsg
	// */
	// protected static void checkSelf(Room room, PlayerInfo playerInfo, String
	// faPai, SendMsg selfMsg) {
	// if (!MJCheckKit.isHua(faPai)) {
	// playerInfo.buildCount(room);
	// // playerInfo.buildNoJinCount(room);
	// // 检查暗杠
	// long userId = playerInfo.getUserInfo().getUserId();
	// List<OptCheckMsg> optList = MJCheckKit.checkAnGang(userId,
	// playerInfo.getCounts(),
	// room.getMjRule().enablePais());
	// // 检查补杠
	// OptCheckMsg buGang = MJCheckKit.checkBuGang(userId, faPai);
	// if (null != buGang) {
	// optList.add(buGang);
	// }
	//
	// // 三金倒,游金检查
	// List<OptCheckMsg> jinList = MJCheckKit.checkJinOpts(room, playerInfo);
	// optList.addAll(jinList);
	// if (optList.size() > 0) {
	// selfMsg.setE(optList);
	// }
	// }
	// }

	/**
	 * 是否荒庄
	 * 
	 * @param room
	 * @param fetchPai
	 * @param cardNum
	 * @return boolean
	 */
	protected static boolean isHuang(Room room, String fetchPai, int cardNum) {
		if (null == fetchPai || cardNum < room.getMjRule().getMinCardNum()) {
			room.setZhuangId(room.nextUserId());
			SendMsg huang = new SendMsg(IMsg.HUANG_TYPE);
			room.broadcast(huang);
			return true;
		}
		return false;
	}

	private static SendMsg buildNaPaiMsg(long selfId, String faPai, int cardNum, long msgId) {
		SendMsg result = new SendMsg(selfId, true, IMsg.NA_TYPE, msgId);
		NapaiMsg msg = new NapaiMsg(selfId);
		msg.setCardNum(cardNum);
		msg.setC(faPai);

		result.setM(msg);
		return result;
	}
}
