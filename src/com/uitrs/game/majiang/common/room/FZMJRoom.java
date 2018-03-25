package com.uitrs.game.majiang.common.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.jfinal.log.Log;
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.HeartBeatTask;
import com.uitrs.game.majiang.common.kit.IdGen;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.MJCheckKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.BuHuaMsg;
import com.uitrs.game.majiang.common.msg.sub.CheckHuaMsg;
import com.uitrs.game.majiang.common.msg.sub.JoinMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.msg.sub.ToJoinMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.game.majiang.common.rule.QZMJRule;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.GamerInfo;
import com.uitrs.game.majiang.web.model.RoomInfo;

/**
 * 泉州麻将房间
 * 
 * @author lucio
 *
 */
public class FZMJRoom extends Room {

	private static final int FEN2 = 200;

	private static final int FEN1 = 100;

	private static Log log = Log.getLog(FZMJRoom.class);

	/** 是否需要减房卡 */
	private boolean isSurplusCards = false;

	public FZMJRoom() {
		this.roomId = IdGen.getInstance().getRoomId();
		init("fzmj");
		// 封顶分数,默认不封顶
		maxScore = 10000;
	}

	public FZMJRoom(long roomId) {
		this.roomId = roomId;
		init("fzmj");
		// 封顶分数,默认不封顶
		maxScore = 10000;
	}

	/**
	 * 人满了以后可以开始准备打牌
	 */
	@Override
	public void fullEvent() {
		readyEvent();
	}

	/**
	 * 所有人都准备好了，发牌
	 */
	@Override
	public synchronized void readyEvent() {
		if (log.isInfoEnabled()) {
			log.info("Room[" + roomId + "] is ready, Send start Message");
		}
		// isStarted = true;
		// this.reSetCards();
		// cardNum = 0;
		// 发牌并检查花牌
		List<CheckHuaMsg> huaList = MJCheckKit.iteratorRoomUsers(this, new RoomUserCallable<CheckHuaMsg>() {
			@Override
			public CheckHuaMsg call(Room room, Entry<Integer, Long> user) throws Exception {
				long userId = user.getValue();
				CheckHuaMsg buHuaMsg = new CheckHuaMsg(userId);
				buHuaMsg.setGc(room.getMjRule().getGoldCard());
				// 根据是否是庄家发送不同张数的牌
				boolean isZhuang = (userId == room.getZhuangId());
				String[] cards = room.faPais(cardNum, isZhuang);
				Arrays.sort(cards);
				buHuaMsg.setC(cards);
				PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(userId);
				// 初始化其他牌信息
				playerInfo.init(playerInfo.getRoomId(), playerInfo.getOrder());
				// 保存手牌
				playerInfo.setHandCards(new ArrayList<String>(Arrays.asList(cards)));
				// playerInfo.buildCount(room);
				// playerInfo.buildNoJinCount(room);
				// 补花数据
				List<BuHuaMsg> buHuaList = MJCheckKit.buHua(playerInfo, cards, room);
				if (buHuaList.size() > 0) {
					buHuaMsg.setH(buHuaList);
				}
				return buHuaMsg;
			}
		});

		this.startHandCheck(huaList);
		this.setStep(MJConst.STEP_START);

		// 缓存回放消息
		SendMsg replayMsg = new SendMsg();
		replayMsg.setT(IMsg.REBUILD_TYPE);
		replayMsg.setS(true);
		replayMsg.setM(this.buildReplayStartMsg());
		// replayMsg.setE(huaList);
		this.addReplayMsg(replayMsg);

		if (!isSurplusCards) {
			// 发牌成功了,则创建者减去相应的房卡数
			UserInfo creater = MJCache.getCache().getUserInfo(this.getCreateId());
			boolean result = GamerInfo.dao.surplusCards((int) creater.getUserId(), calcRoomCard());
			if (result) {
				creater.setCards(creater.getCards() - calcRoomCard());
			}
			saveRoom2DB();
			isSurplusCards = true;
			this.heartTask = new HeartBeatTask(this);
			this.getTimer().schedule(this.heartTask, 5000, 5000);
		}
	}

	/**
	 * 计算需要消耗的房卡/钻石数
	 * 
	 * @return
	 */
	private int calcRoomCard() {
		switch (this.totalJuNum) {
		case 8:
			return 5;
		case 16:
			return 10;
		case FEN1:
			return 5;
		case FEN2:
			return 10;
		default:
			return 10000;
		}
	}

	private void startHandCheck(List<CheckHuaMsg> huaList) {
		// 检查手牌消息
		List<SendMsg> sendList = MJCheckKit.iteratorRoomUsers(this, new RoomUserCallable<SendMsg>() {
			@Override
			public SendMsg call(Room room, Entry<Integer, Long> user) throws Exception {
				long userId = user.getValue();
				SendMsg result = new SendMsg(userId, true, IMsg.START_TYPE, room.getMsgSeq().incrementAndGet());
				// result.setM(huaList);
				PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(userId);
				// 如果是分数打法则设置最高分
				if (room.getTotalJuNum() == FEN1) {
					playerInfo.setCurrentScore(FEN1);
					playerInfo.setRoomScore(FEN1);
				} else if (room.getTotalJuNum() == FEN2) {
					playerInfo.setCurrentScore(FEN2);
					playerInfo.setRoomScore(FEN2);
				}
				// String[] enablePais = room.getMjRule().enablePais();
				playerInfo.buildCount(room);
				// playerInfo.buildNoJinCount(room);
				// 根据是否是庄家发送不同张数的牌
				boolean isZhuang = (userId == room.getZhuangId());
				if (isZhuang) {
					room.getMjRule().checkSelf(room, playerInfo, "", result);
					// List<OptCheckMsg> optList =
					// MJCheckKit.checkAnGang(userId,
					// playerInfo.getNoJinCount(), enablePais);
					// // 胡牌,三金倒,游金检查
					// List<OptCheckMsg> jinList = MJCheckKit.checkJinOpts(room,
					// playerInfo);
					// optList.addAll(jinList);
					// if (optList.size() > 0) {
					// result.setE(optList);
					// }

				} else {
					// 其他玩家起手牌后查叫
					playerInfo.buildTingCards(room);
					System.err.println(
							"start ting: " + playerInfo.getUserInfo().getUserId() + ":::" + playerInfo.getTingCards());
				}
				return result;
			}
		});
		// 剩余牌数
		int cardNum = this.getCards().length - this.getCardNum();

		// 发送消息
		for (SendMsg sendMsg : sendList) {
			// 根据不同的玩家展示不同的牌
			sendMsg.setM(buildNoCardsHuaMsg(cardNum, huaList, sendMsg.getP()));
			this.sendMsg2Client(sendMsg.getP(),sendMsg);
		}

	}

	private List<CheckHuaMsg> buildNoCardsHuaMsg(int cardNum, List<CheckHuaMsg> huaList, long selfId) {
		List<CheckHuaMsg> otherHuaList = new ArrayList<CheckHuaMsg>(huaList.size());
		for (CheckHuaMsg checkHuaMsg : huaList) {
			CheckHuaMsg newHuaMsg = new CheckHuaMsg(checkHuaMsg.getP());
			newHuaMsg.setGc(checkHuaMsg.getGc());
			newHuaMsg.setC(Arrays.copyOf(checkHuaMsg.getC(), checkHuaMsg.getC().length));
			if (selfId != checkHuaMsg.getP()) {
				Arrays.fill(newHuaMsg.getC(), "");
			}
			List<BuHuaMsg> buHuaList = checkHuaMsg.getH();
			if (null != buHuaList && buHuaList.size() > 0) {
				List<BuHuaMsg> newBuHuaList = new ArrayList<BuHuaMsg>(buHuaList.size());
				for (BuHuaMsg buHuaMsg : buHuaList) {
					BuHuaMsg newBuHua = new BuHuaMsg(buHuaMsg.getWho(), buHuaMsg.getH());
					newBuHua.setC((selfId != buHuaMsg.getWho()) ? "" : buHuaMsg.getC());
					newBuHua.setCardNum(cardNum);
					newBuHuaList.add(newBuHua);
				}
				newHuaMsg.setH(newBuHuaList);
			}
			otherHuaList.add(newHuaMsg);
		}
		return otherHuaList;
	}

	// @Override
	// public synchronized void handCheckEvent() {
	// Iterator<Entry<Integer, Long>> it =
	// this.getRoomUsers().entrySet().iterator();
	// while (it.hasNext()) {
	// Entry<Integer, Long> user = it.next();
	// long userId = user.getValue();
	// SendMsg result = new SendMsg(userId, true, IMsg.OPERATE_SHOW);
	// PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(userId);
	// String[] handCards = new String[playerInfo.getHandCards().size()];
	// playerInfo.getHandCards().toArray(handCards);
	// System.err.println(playerInfo.getUserInfo().getOpenId() + "
	// handCards: " + Arrays.toString(handCards));
	// // 处理补花
	// List<BuHuaMsg> huaList = MJCheckKit.buHua(playerInfo, handCards,
	// this);
	// result.setM(huaList);
	// // 如果是庄家则计算是否有杠和胡等起手牌信息
	// if (userId == (this.getZhuangId())) {
	// List<OptCheckMsg> optList = MJCheckKit.checkHandsOpts(userId,
	// handCards, null,
	// this.getMjRule().enablePais(), this.getMjRule().getGoldCard());
	// if (optList.size() > 0) {
	// result.setE(optList);
	// }
	// }
	// // 发送消息
	// Session session = MJCache.getCache().getSession(userId);
	// this.sendMsg(result, session);
	// }
	// }

	/**
	 * 玩家加入
	 */
	@Override
	public void joinEvent(PlayerInfo playerInfo) {
		if (log.isInfoEnabled()) {
			log.info("Room[" + roomId + "] has user join, Send join Message,userinfo: " + playerInfo.getUserInfo());
		}

		SendMsg result = new SendMsg(playerInfo.getUserInfo().getUserId(), true, IMsg.JOIN_TYPE,
				this.getMsgSeq().incrementAndGet());
		playerInfo.getUserInfo().setReady(true);
		JoinMsg joinMsg = this.buildReBuildMsg(playerInfo.getUserInfo().getUserId());
		result.setM(joinMsg);
		this.broadcast(result);
	}

	/**
	 * 牌计分如下：
	 * 
	 * 1、平胡：1翻
	 * 
	 * 2、自摸：2翻
	 * 
	 * 3、单游 ：3翻+半个底分
	 * 
	 * 4、三金倒 ：2.5番
	 * 
	 * 5、双游：4翻+半个底分
	 * 
	 * 6、三游：5翻+半个底分
	 * 
	 * 7、天胡：3番
	 * 
	 * 注：胡牌后，除去胡牌的人，其余未胡的人也要算番数，手中的番数相加减。
	 * 
	 * 庄家翻倍:不管是不是庄家点炮还是胡牌输赢都要双倍
	 * 
	 * 杠: 补杠和明杠、金都是一个底分, 暗杠底分*2, 胡的人杠,三家都要给,没胡人的杠,没胡的人给（2个人）
	 * 
	 * 花杠: 花牌不是金牌牌局结束前有4个及以上花就可以当一个杠，有8个及以上花就可以当两个杠
	 * 
	 * 局数打法中如果连庄,则底分依次翻倍。但在分数打法中，如果连庄底分不翻
	 * 
	 */
	@Override
	public void juScoreEvent(int maxScore) {

	}

	private void clearCurrentScore() {
		Iterator<Entry<Integer, Long>> it = this.getRoomUsers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> user = it.next();
			long userId = user.getValue();
			PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(userId);
			playerInfo.setCurrentScore(0);
		}
	}

	private void calcRoomScore() {
		Iterator<Entry<Integer, Long>> it = this.getRoomUsers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> user = it.next();
			long userId = user.getValue();
			PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(userId);
			playerInfo.setRoomScore(playerInfo.getRoomScore() + playerInfo.getCurrentScore());
		}
	}

	private int calcBaseHuScore(int baseType, int diFen) {
		int result = 0;
		if (baseType == IMsg.HU_TYPE) {// 平胡
			result = diFen;
		} else if (baseType == IMsg.ZIMO_TYPE) {// 自摸
			result = diFen * 2;
		} else if (baseType == IMsg.THREE_GOLD_HU) {// 三金倒
			result = (int) (diFen * Math.pow(2, 1.5));
		} else if (baseType == IMsg.ONE_GOLD) {// 单游
			result = (int) (diFen * Math.pow(2, 2));
		} else if (baseType == IMsg.TWO_GOLD) {// 双游
			result = (int) (diFen * Math.pow(2, 3));
		} else if (baseType == IMsg.THREE_GOLD) {// 三游
			result = (int) (diFen * Math.pow(2, 4));
		}
		return result;
	}

	/**
	 * 计算本轮得分
	 * 
	 * @param hupaiMsg
	 * @param openId
	 * @param otherOpenId
	 * @param selfScore
	 * @param otherScore
	 */
	private void calcCurrentScore(long userId, long otherUserId, int selfScore, int otherScore) {
		Iterator<Entry<Integer, Long>> it = this.getRoomUsers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> user = it.next();
			long myUserId = user.getValue();
			PlayerInfo juResult = MJCache.getCache().getPlayerInfo(myUserId);
			Integer huFen = juResult.getHuInfo().get(MJConst.HU_FEN);
			int currentHuScore = null == huFen ? 0 : huFen.intValue();
			juResult.getHuInfo().put(MJConst.HU_FEN, currentHuScore);
			if (myUserId == userId) {// 自己得分
				// 保存胡玩家信息中
				juResult.setCurrentScore(juResult.getCurrentScore() + selfScore);
				juResult.getHuInfo().put(MJConst.HU_FEN, currentHuScore + selfScore);
				// juResult.setCurrentScore(playInfo.getCurrentScore());
			} else if (0 == otherUserId || userId == otherUserId) {// 其他三家(自摸,暗杠)
				// 保存胡玩家信息中
				juResult.setCurrentScore(juResult.getCurrentScore() + otherScore);
				juResult.getHuInfo().put(MJConst.HU_FEN, currentHuScore + otherScore);
				// juResult.setCurrentScore(playInfo.getCurrentScore());
			} else if (otherUserId == myUserId) {// 别人得分
				// 保存胡玩家信息中
				juResult.setCurrentScore(juResult.getCurrentScore() + otherScore);
				juResult.getHuInfo().put(MJConst.HU_FEN, currentHuScore + otherScore);
				// juResult.setCurrentScore(playInfo.getCurrentScore());
			}
		}
	}

	@Override
	public void creatEvent(PlayerInfo playerInfo) {
		this.baseFen = this.opt[0] * 2;
		this.mjRule.createRoom(this.getTotalJuNum(),this.opt, playerInfo);
	}

	public boolean isSurplusCards() {
		return isSurplusCards;
	}

	@Override
	public SendMsg operateEvent() {
		return null;
	}

	@Override
	public void juScore(PlayerInfo huPlayer, OptCheckMsg huMsg) {
		if (log.isInfoEnabled()) {
			log.info("QZJuScore room: " + this);
			log.info("QZJuScore huPlayer: " + huPlayer);
			log.info("QZJuScore OptCheckMsg: " + huMsg);
		}

		long huId = huMsg.getP();
		long fromId = huMsg.getFrom();
		int huType = huMsg.getT();
		int diFen = this.baseFen;
		// 非分数打法,连庄底分翻翻
		if (this.opt[0] != QZMJRule.F_ONE_HUNDRED && this.opt[0] != QZMJRule.F_TWO_HUNDRED) {
			Integer lzCount = huPlayer.getHuInfo().get(MJConst.LIAN_ZHUANG_COUNT);
			if (null == lzCount || lzCount < 1) {
				lzCount = 0;
			}
			// 连庄底分*2
			diFen = this.baseFen * (int) Math.pow(this.baseFen, lzCount);
		}

		// 胡牌基础得分
		int baseHuScore = calcBaseHuScore(huType, diFen);
		// 清空上局的
		clearCurrentScore();
		// 计算本局胡牌得分
		calcCurrentScore(huId, fromId, baseHuScore * ((fromId == 0 || fromId == huId) ? 3 : 1), -baseHuScore);

		// 庄家翻倍: 不管是不是庄家点炮还是到牌输赢都要双倍
		if (this.zhuangId == huId) {
			calcCurrentScore(huId, fromId, baseHuScore * ((fromId == 0 || fromId == huId) ? 3 : 1), -baseHuScore);
		} else if (huType == IMsg.ZIMO_TYPE || this.zhuangId == fromId) {
			calcCurrentScore(huId, this.zhuangId, baseHuScore, -baseHuScore);
		}

		// 计算杠分: 补杠和明杠、金都是一个底分, 暗杠底分*2, 到的人杠,三家都要给, 没到人的杠,没到的人给（2个人）;
		// 花杠: 花牌不是金牌牌局结束前有4个及以上花就可以当一个杠，有8个及以上花就可以当两个杠
		calcAllGangScore(huId);
		// 计算当前房间所有人的总得分
		calcRoomScore();
	}

	/**
	 * 补杠和明杠、手上的金都是一个底分, 暗杠底分*2, 到的人杠,三家都要给, 没到人的杠,没到的人给（2个人）
	 * 
	 * @param huId
	 */
	private void calcAllGangScore(long huId) {
		Iterator<Entry<Integer, Long>> it = this.getRoomUsers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> user = it.next();
			long userId = user.getValue();
			PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(userId);
			int jinCount = calcJinCount(playerInfo);
			calcJinScore(userId, 0, jinCount * this.baseFen * 3, -jinCount * this.baseFen);
			List<OptCheckMsg> optList = playerInfo.getOptMsgs();
			for (OptCheckMsg optCheckMsg : optList) {
				int gangFen = 0;
				if (optCheckMsg.getT() == IMsg.AN_GANG) {// 暗杠得分*2
					gangFen = this.baseFen * 2;
				} else if (optCheckMsg.getT() == IMsg.MING_GANG || optCheckMsg.getT() == IMsg.BU_GANG) {
					gangFen = this.baseFen;
				}

				if (0 != gangFen) {
					if (huId == userId) {
						// 到的人杠,三家都要给
						calcGangScore(userId, 0, gangFen * 3, -gangFen);
					} else {
						// 没到人的杠,没到的人给（2个人）
						calcGangScore(userId, huId, gangFen * 2, -gangFen);
					}
				}
			}
		}
	}

	/**
	 * 计算手牌中的金数量
	 * 
	 * @param playerInfo
	 * @return
	 */
	private int calcJinCount(PlayerInfo playerInfo) {
		// playerInfo.getHuInfo().put("jingFen", 0);
		List<String> outCardList = playerInfo.getHandCards();
		String goldCard = this.getMjRule().getGoldCard();
		int jinCount = 0;
		for (String oc : outCardList) {
			if (oc.equalsIgnoreCase(goldCard)) {
				jinCount++;
			}
		}
		return jinCount;
	}

	private void calcGangScore(long userId, long huId, int selfScore, int otherScore) {
		Iterator<Entry<Integer, Long>> it = this.getRoomUsers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> user = it.next();
			long myUserId = user.getValue();
			PlayerInfo juResult = MJCache.getCache().getPlayerInfo(myUserId);
			Integer gangFen = juResult.getHuInfo().get(MJConst.GANG_FEN);
			int currentGangScore = (null == gangFen ? 0 : gangFen.intValue());
			juResult.getHuInfo().put(MJConst.GANG_FEN, currentGangScore);
			if (myUserId == userId) {// 自己得分
				juResult.setCurrentScore(juResult.getCurrentScore() + selfScore);
				juResult.getHuInfo().put(MJConst.GANG_FEN, currentGangScore + selfScore);
			} else if (0 == huId || userId == huId) {// 其他三家(暗杠)
				juResult.setCurrentScore(juResult.getCurrentScore() + otherScore);
				juResult.getHuInfo().put(MJConst.GANG_FEN, currentGangScore + otherScore);
			} else if (myUserId != huId) {
				// 没胡的出分
				juResult.setCurrentScore(juResult.getCurrentScore() + otherScore);
				juResult.getHuInfo().put(MJConst.GANG_FEN, currentGangScore + otherScore);
			}
		}
	}

	private void calcJinScore(long userId, long otherId, int selfScore, int otherScore) {
		Iterator<Entry<Integer, Long>> it = this.getRoomUsers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> user = it.next();
			long myUserId = user.getValue();
			PlayerInfo juResult = MJCache.getCache().getPlayerInfo(myUserId);
			Integer jinFen = juResult.getHuInfo().get(MJConst.JING_FEN);
			int currentJinScore = (null == jinFen ? 0 : jinFen.intValue());
			juResult.getHuInfo().put(MJConst.JING_FEN, currentJinScore);
			if (myUserId == userId) {// 自己得分
				juResult.setCurrentScore(juResult.getCurrentScore() + selfScore);
				juResult.getHuInfo().put(MJConst.JING_FEN, currentJinScore + selfScore);
			} else if (0 == otherId || userId == otherId) {// 其他三家(金)
				juResult.setCurrentScore(juResult.getCurrentScore() + otherScore);
				juResult.getHuInfo().put(MJConst.JING_FEN, currentJinScore + otherScore);
			} 
		}
	}

	/**
	 * 庄家发17张牌,闲家发16张牌
	 * 
	 * @param from
	 * @param isZhuang
	 * @return String[] 发牌
	 */
	@Override
	public synchronized String[] faPais(int from, boolean isZhuang) {
		if (isZhuang) {
			cardNum += 17;
			return Arrays.copyOfRange(cards, from, from + 17);
		}
		cardNum += 16;
		return Arrays.copyOfRange(cards, from, from + 16);
	}

	@Override
	public ToJoinMsg toJoinMsg(int order) {
		return new ToJoinMsg(this.roomId, this.totalJuNum, this.totalQuanNum, this.createId, this.roomUsers, this.opt,
				order);
	}

	/**
	 * 保存房间信息到数据库
	 */
	private void saveRoom2DB() {
		RoomInfo roomInfo = new RoomInfo();
		roomInfo.setRoomId((int) this.roomId);
		roomInfo.setCreateTime(Tools.date2String(new Date()));
		roomInfo.setGamerOneId((int) MJCache.getCache().getUserInfo(this.getRoomUsers().get(1)).getUserId());
		roomInfo.setGamerTwoId((int) MJCache.getCache().getUserInfo(this.getRoomUsers().get(2)).getUserId());
		roomInfo.setGamerThreeId((int) MJCache.getCache().getUserInfo(this.getRoomUsers().get(3)).getUserId());
		roomInfo.setGamerFourId((int) MJCache.getCache().getUserInfo(this.getRoomUsers().get(4)).getUserId());
		roomInfo.setTotalRounds(this.totalJuNum);
		roomInfo.setUseRounds(1);
		roomInfo.save();
	}

}
