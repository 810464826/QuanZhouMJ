package com.uitrs.game.majiang.common.kit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.websocket.Session;

import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.Result;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.BuHuaMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.common.room.RoomUserCallable;

public final class MJCheckKit {
	// private static MJCheckKit mjKit = new MJCheckKit();
	//
	// private MJCheckKit() {
	// }
	//
	// public static MJCheckKit getInstance() {
	// return mjKit;
	// }

	/**
	 * 检查手牌操作并发送消息给对应客户端
	 * 
	 * @param room
	 */
	public static void checkHand(Room room) {
		Iterator<Entry<Integer, Long>> it = room.getRoomUsers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> user = it.next();
			long userId = user.getValue();
			SendMsg result = new SendMsg(userId, true, IMsg.OPERATE_SHOW);
			PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(userId);
			String[] handCards = new String[playerInfo.getHandCards().size()];
			// Map<String, Object> optMap = new HashMap<String, Object>();
			// 处理补花
			List<BuHuaMsg> huaList = buHua(playerInfo, playerInfo.getHandCards().toArray(handCards), room);
			result.setM(huaList);
			// optMap.put(IMsg.FLAG_BU_HUA, huaList);
			// 如果是庄家则计算是否有杠和胡等起手牌信息
			if (userId == room.getZhuangId()) {
				List<OptCheckMsg> optList = new ArrayList<OptCheckMsg>();
				List<OptCheckMsg> anGang = MJCheckKit.checkAnGang(userId, playerInfo.getCounts(),
						room.getMjRule().enablePais());
				if (anGang.size() > 0) {
					optList.addAll(anGang);
				}
				result.setE(optList);
				// optMap.put(IMsg.FLAG_OPT_MSG, optList);
			}
			// 发送消息
			Session session = MJCache.getCache().getSession(userId);
			room.sendMsg(userId,result, session);
		}
	}

	/**
	 * 补花,添加补花信息和更新手牌信息
	 * 
	 * @param playerInfo
	 * @param cards
	 * @return List<BuHuaMsg>
	 */
	public static List<BuHuaMsg> buHua(PlayerInfo playerInfo, String[] cards, Room room) {
		List<BuHuaMsg> huaList = new ArrayList<BuHuaMsg>();
		String[] hua = MJCheckKit.checkHua(cards);
		for (int i = 0; i < hua.length; i++) {
			if (null != hua[i]) {
				dealHua(playerInfo, room, huaList, hua[i]);
			} else {// 如果是null,后面也不用去检查了,都是null
				return huaList;
			}
		}
		return huaList;
	}

	/**
	 * 使用递归的方式进行补花,补的牌如果还是话,则继续补
	 * 
	 * @param playerInfo
	 * @param room
	 * @param huaList
	 * @param hua
	 */
	public static void dealHua(PlayerInfo playerInfo, Room room, List<BuHuaMsg> huaList, String hua) {
		String pai = room.fetchPai();
		playerInfo.buHua(hua, pai);
		BuHuaMsg huaMsg = new BuHuaMsg(hua);
		huaMsg.setWho(playerInfo.getUserInfo().getUserId());
		huaMsg.setH(hua);
		huaMsg.setC(pai);
		huaList.add(huaMsg);
		// 补的牌是不是花
		if (MJCheckKit.isHua(pai)) {
			dealHua(playerInfo, room, huaList, pai);
		}
	}

	/**
	 * 检查手牌可操作消息,杠和胡
	 * 
	 * @param openId
	 * @param cards
	 * @param optCard
	 * @param enableCards
	 * @return List<OptCheckMsg>
	 */
	// public static List<OptCheckMsg> checkHandsOpts(long userId, String[]
	// cards, String optCard, String[] enableCards,
	// String goldCard) {
	// List<OptCheckMsg> optList = checkAnGang(userId, cards, enableCards);
	// OptCheckMsg hu = checkHu(userId, userId, optCard, cards, enableCards,
	// goldCard);
	// if (null != hu) {
	// optList.add(hu);
	// }
	// OptCheckMsg jin = checkJin(userId, userId, optCard, cards, enableCards,
	// goldCard);
	// if (null != jin) {
	// optList.add(jin);
	// }
	// return optList;
	// }

	/**
	 * 检查是否可以游金
	 * 
	 * @param userId
	 * @param userId2
	 * @param optCard
	 * @param cards
	 * @param enableCards
	 * @param goldCard
	 * @return OptCheckMsg
	 */
	// private static OptCheckMsg checkJin(long userId, long userId2, String
	// optCard, String[] cards, String[] enableCards,
	// String goldCard) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	/**
	 * 检查是否能碰
	 * 
	 * @param p
	 * @param from
	 * @param optCard
	 * @param cards
	 * @return OptCheckMsg
	 */
	// public static OptCheckMsg checkPeng(long p, long from, String optCard,
	// String[] cards, String[] enableCards) {
	// int index = Arrays.binarySearch(enableCards, optCard);
	// int[] counts = countCardNum(cards, enableCards);
	// if (counts[index] >= 2) {
	// return new OptCheckMsg(p, IMsg.PENG_TYPE, optCard, from, new String[] {
	// optCard, optCard });
	// }
	// return null;
	// }

	public static OptCheckMsg checkMingGang(long p, long from, String optCard, int[] counts, String[] enableCards) {
		int index = Arrays.binarySearch(enableCards, optCard);
		// int[] counts = countCardNum(cards, enableCards);
		if (index >= 0 && counts[index] >= 3) {
			return new OptCheckMsg(p, IMsg.MING_GANG, optCard, from, new String[] { optCard, optCard, optCard });
		}
		return null;
	}

	/**
	 * 检查手牌暗杠
	 * 
	 * @param openId
	 * @param cards
	 * @param optCard
	 * @param enableCards
	 * @return List<OptCheckMsg>
	 */
	public static List<OptCheckMsg> checkAnGang(long p, String[] cards, String[] enableCards) {
		int[] counts = countCardNum(cards, enableCards);
		return checkAnGang(p, counts, enableCards);
	}

	/**
	 * 检查拿牌后是否能杠,胡
	 * 
	 * @param room
	 * @param playerInfo
	 * @param faPai
	 * @param selfMsg
	 */
	public static void checkHandOpts(Room room, PlayerInfo playerInfo, String faPai, SendMsg selfMsg) {
		if (!MJCheckKit.isHua(faPai)) {
			// 检查暗杠
			List<OptCheckMsg> optList = MJCheckKit.checkAnGang(playerInfo.getUserInfo().getUserId(),
					playerInfo.getCounts(), room.getMjRule().enablePais());
			// 检查胡
			OptCheckMsg huMsg = MJCheckKit.checkHu(playerInfo, faPai);
			if (null != huMsg) {
				optList.add(huMsg);
			}
			if (optList.size() > 0) {
				selfMsg.setE(optList);
			}
		}
	}

	/**
	 * 检查手牌暗杠
	 * 
	 * @param openId
	 * @param counts
	 * @param enableCards
	 * @return List<OptCheckMsg>
	 */
	public static List<OptCheckMsg> checkAnGang(long p, int[] counts, String[] enableCards) {

		List<OptCheckMsg> optList = new ArrayList<OptCheckMsg>();
		// System.err.println("counts: " + Arrays.toString(counts));
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] >= 4) {
				optList.add(new OptCheckMsg(p, IMsg.AN_GANG, enableCards[i], p,
						new String[] { enableCards[i], enableCards[i], enableCards[i], enableCards[i] }));
			}
		}
		return optList;
	}

	/**
	 * 检查是否有暗杠
	 * 
	 * @param p
	 * @param from
	 * @param optCard
	 * @param cards
	 * @param enableCards
	 * @return OptCheckMsg
	 */
	public static OptCheckMsg checkAnGang(long p, String optCard, String[] cards, String[] enableCards) {
		int index = Arrays.binarySearch(enableCards, optCard);
		int[] counts = countCardNum(cards, enableCards);
		if (counts[index] >= 4) {
			return new OptCheckMsg(p, IMsg.AN_GANG, optCard, p, new String[] { optCard, optCard, optCard, optCard });
		}
		return null;
	}

	// public static OptCheckMsg checkHu(long p, long from, String optCard,
	// String[] cards, String[] enableCards,
	// String goldCard) {
	// String[] huCards = checkTingCards(cards, enableCards, goldCard);
	// if (huCards.length > 0) {
	// return new OptCheckMsg(p, IMsg.HU_TYPE, optCard, 0 == from ? p : from,
	// huCards);
	// }
	// return null;
	// }

	/**
	 * 找出花牌
	 * 
	 * @param cards
	 * @return String[8]
	 */
	public static String[] checkHua(String[] cards) {
		// 花牌最多8张
		String[] hua = new String[8];
		int j = 0;
		for (int i = 0; i < cards.length; i++) {
			if (isHua(cards[i])) {
				hua[j] = cards[i];
				j++;
			}
		}
		return hua;
	}

	/**
	 * 判断是否为花牌
	 * 
	 * @param card
	 * @return boolean
	 */
	public static boolean isHua(String card) {
		return StrKit.notBlank(card) && (card.startsWith(MJConst.HUA) || card.startsWith(MJConst.JI_JIE));
	}

	/**
	 * 计算每个牌的数量
	 * 
	 * @param hCards
	 * @param enableCards
	 * @return 数量数组int[]
	 */
	public static int[] countCardNum(List<String> hCards, String[] enableCards) {

		int[] count = new int[enableCards.length];
		// 首先将每个牌的个数置为0
		for (int i = 0; i < enableCards.length; i++) {
			count[i] = 0;
		}
		// 一定要sort过后的数组,否则下标会错误
		// Arrays.sort(enableCards);
		Collections.sort(hCards);
		// System.err.println(enableCards.length + " enableCards:" +
		// Arrays.toString(enableCards));
		// 循环手牌 计算每个牌的个数
		for (int i = 0; i < hCards.size(); i++) {
			int index = Arrays.binarySearch(enableCards, hCards.get(i));
			if (index >= 0) {
				count[index]++;
			}
		}
		// System.err.println(count.length + " count:" +
		// Arrays.toString(count));
		return count;
	}

	/**
	 * 计算每个牌的数量
	 * 
	 * @param hCards
	 * @param enableCards
	 * @return 数量数组int[]
	 */
	public static int[] countCardNum(String[] hCards, String[] enableCards) {

		int[] count = new int[enableCards.length];
		// 首先将每个牌的个数置为0
		for (int i = 0; i < enableCards.length; i++) {
			count[i] = 0;
		}
		// 一定要sort过后的数组,否则下标会错误
		// Arrays.sort(enableCards);
		// System.err.println(Arrays.toString(enableCards));
		// 循环手牌 计算每个牌的个数
		for (int i = 0; i < hCards.length; i++) {
			int index = Arrays.binarySearch(enableCards, hCards[i]);
			// System.err.println(hCards[i] + " index: " + index);
			count[index]++;
		}
		return count;
	}

	/**
	 * 检查能听的牌
	 * 
	 * @param hCards
	 *            所有手牌
	 * @param allCards
	 *            所有可用的牌
	 * @param goldCard
	 *            金牌(赖子)
	 * @return String[]
	 */
	public static String[] checkTingCards(String[] hCards, String[] allCards, String goldCard) {
		Set<String> rcs = checkTingSet(hCards, goldCard);
		String[] huCards = new String[rcs.size()];
		rcs.toArray(huCards);
		Arrays.sort(huCards);
		return huCards;
	}

	/**
	 * 检查能听的牌的集合
	 * 
	 * @param hCards
	 * @param goldCard
	 * @return Set<String>
	 */
	public static Set<String> checkTingSet(String[] hCards, String goldCard) {
		List<String> parameter = new ArrayList<String>();

		// 这里将金牌转为癞子
		for (String m : hCards) {
			if (m.equalsIgnoreCase(goldCard)) {
				parameter.add(MJConst.LAIZI);
			} else {
				parameter.add(m);
			}
		}

		HuCheckKit m1 = new HuCheckKit(parameter);
		// Collections.sort(m1.getCards());
		// m1.getCards().sort();
		PrintKit.out("原始牌: " + m1.toString());
		List<Result> solve = m1.solve();
		// 可以胡的牌去重
		Set<String> rcs = new HashSet<String>();
		for (Result result : solve) {
			Set<String> hpList = result.getHps();
			rcs.addAll(hpList);
			// for (String hp : hpList) {
			// rcs.add(hp);
			// }
		}

		String msg = ",可胡牌集合: ";
		for (String p : rcs) {
			msg += p + " , ";
		}
		PrintKit.out("可以胡牌的组合数: " + solve.size() + msg);

		// 这里对金牌组合求解 得到答案
		for (Result r : solve) {
			PrintKit.out(r.toString());
		}
		return rcs;
	}

	/**
	 * 根据听牌判断拿到或者别人出的牌是否能胡
	 * 
	 * @param tingCards
	 * @param optCard
	 * @return boolean
	 */
	public static boolean isCanHuOfCard(String[] tingCards, String optCard) {
		if (null != tingCards && tingCards.length > 0) {
			int index = Arrays.binarySearch(tingCards, optCard);
			return (index > -1);
		}
		return false;
	}

	/**
	 * 检查别人出的牌,自己能够进行哪些操作(吃,杠,碰)
	 * 
	 * @param p
	 * @param from
	 * @param optCard
	 * @param cards
	 * @param enableCards
	 * @return List<OptCheckMsg>
	 */
	public static List<OptCheckMsg> checkOutCardOpts(long p, long from, String optCard, int[] counts,
			String[] enableCards) {
		List<OptCheckMsg> optList = new ArrayList<OptCheckMsg>();
		if (p != from) {
			// 校验是否能碰
			OptCheckMsg peng = checkPeng(p, from, optCard, counts, enableCards);
			if (null != peng) {
				optList.add(peng);
			}
			// 校验是否能明杠
			OptCheckMsg minGang = checkMingGang(p, from, optCard, counts, enableCards);
			if (null != minGang) {
				optList.add(minGang);
			}
			// 校验是否吃
			List<OptCheckMsg> chiList = MJCheckKit.checkChi(p, from, optCard, enableCards);
			if (chiList.size() > 0) {
				optList.addAll(chiList);
			}
			// if (optList.size() > 0) {
			// 设置有操作的人,后面在判断是否回来所有操作的时候使用
			MJCache.getCache().getRoom(MJCache.getCache().getPlayerInfo(p).getRoomId()).getOptNumber().put(p, false);
			// }
		}
		return optList;
	}

	/**
	 * 该操作牌是否能胡牌
	 * 
	 * @param playerInfo
	 * @param optCard
	 * @param optList
	 */
	public static OptCheckMsg checkHu(PlayerInfo playerInfo, String optCard) {
		Set<String> tingSet = playerInfo.getTingCards();
		// 检查胡牌
		if (tingSet.size() > 0 && tingSet.contains(optCard)) {
			OptCheckMsg huMsg = new OptCheckMsg(playerInfo.getUserInfo().getUserId(), IMsg.HU_TYPE, optCard);
			return huMsg;
		}
		return null;
	}

	public static OptCheckMsg checkPeng(long p, long from, String optCard, int[] counts, String[] enableCards) {
		int index = Arrays.binarySearch(enableCards, optCard);
		if (index >= 0 && counts[index] >= 2) {
			return new OptCheckMsg(p, IMsg.PENG_TYPE, optCard, from, new String[] { optCard, optCard });
		}
		return null;
	}

	public static OptCheckMsg checkBuGang(long p, String optCard) {
		if (StrKit.notBlank(optCard)) {
			PlayerInfo player = MJCache.getCache().getPlayerInfo(p);
			List<OptCheckMsg> optMsgList = player.getOptMsgs();
			if (null != optMsgList && optMsgList.size() > 0) {
				for (OptCheckMsg optCheckMsg : optMsgList) {
					if (optCheckMsg.getT() == IMsg.PENG_TYPE && optCheckMsg.getC().equalsIgnoreCase(optCard)) {
						return new OptCheckMsg(p, IMsg.BU_GANG, optCard, p, new String[] { optCard, optCard, optCard });
					}
				}
			}
		}
		return null;
	}

	public static List<OptCheckMsg> checkChi(long me, long from, String optCard, String[] enablePais) {
		List<OptCheckMsg> optList = new ArrayList<OptCheckMsg>();
		int meOrder = MJCache.getCache().getPlayerInfo(me).getOrder();
		int fromOrder = MJCache.getCache().getPlayerInfo(from).getOrder();
		// 判断是不是下家
		if (fromOrder - meOrder == 1 || fromOrder - meOrder == -3) {
			// 筒,条,万才可能吃
			if (optCard.startsWith(MJConst.WAN) || optCard.startsWith(MJConst.TIAO)
					|| optCard.startsWith(MJConst.TONG)) {
				// 将操作牌加入进去重新计算个数
				int index = Arrays.binarySearch(enablePais, optCard);
				int[] counts = MJCache.getCache().getPlayerInfo(me).getNoJinCount();
				counts[index]++;
				findChi(me, from, optCard, counts, optList, enablePais, index);
				counts[index]--;
			}
		}
		return optList;
	}

	private static void findChi(long me, long from, String optCard, int[] count, List<OptCheckMsg> optList,
			String[] enablePais, int index) {
		findChiWan(me, from, optCard, count, enablePais, optList, index);
		findChiTiao(me, from, optCard, count, enablePais, optList, index);
		findChiTong(me, from, optCard, count, enablePais, optList, index);
	}

	private static void findChiTong(long me, long from, String optCard, int[] count, String[] enablePais,
			List<OptCheckMsg> optList, int index) {
		if (StrKit.isBlank(optCard) || !optCard.startsWith(MJConst.TONG)) {
			return;
		}
		int tongBeginIndex = Arrays.binarySearch(enablePais, MJConst.TONG1);
		buildChi(me, from, optCard, count, enablePais, index, optList, tongBeginIndex);
	}

	private static void findChiTiao(long me, long from, String optCard, int[] count, String[] enablePais,
			List<OptCheckMsg> optList, int index) {
		if (StrKit.isBlank(optCard) || !optCard.startsWith(MJConst.TIAO)) {
			return;
		}

		int tiaoBeginIndex = Arrays.binarySearch(enablePais, MJConst.TIAO1);
		buildChi(me, from, optCard, count, enablePais, index, optList, tiaoBeginIndex);
	}

	private static void findChiWan(long me, long from, String optCard, int[] count, String[] enablePais,
			List<OptCheckMsg> optList, int index) {
		if (StrKit.isBlank(optCard) || !optCard.startsWith(MJConst.WAN)) {
			return;
		}
		int wanBeginIndex = Arrays.binarySearch(enablePais, MJConst.WAN1);
		buildChi(me, from, optCard, count, enablePais, index, optList, wanBeginIndex);

	}

	private static void buildChi(long me, long from, String optCard, int[] count, String[] enablePais, int index,
			List<OptCheckMsg> optList, int tiaoBeginIndex) {
		for (int i = tiaoBeginIndex; i <= tiaoBeginIndex + 6; i++) {
			if (count[i] > 0 && count[i + 1] > 0 && count[i + 2] > 0 && (index >= i && index <= i + 2)) {
				optList.add(new OptCheckMsg(me, IMsg.CHI_TYPE, optCard, from,
						new String[] { enablePais[i], enablePais[i + 1], enablePais[i + 2] }));
			}
		}
	}

	/**
	 * 迭代房间用户信息
	 * 
	 * @param roomUsers
	 * @param callable
	 * @return List<V>
	 */
	public static <V extends IMsg> List<V> iteratorRoomUsers(Room room, RoomUserCallable<V> callable) {
		List<V> list = new ArrayList<V>();
		Iterator<Entry<Integer, Long>> it = room.getRoomUsers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> user = it.next();
			V v = null;
			try {
				v = callable.call(room, user);
			} catch (Exception e) {
				LogKit.error("iterator Room Users,userId: " + user.getKey() + " error, v = "
						+ (v == null ? null : v.toJson()), e);
			}
			if (null != v) {
				list.add(v);
			}
		}
		return list;
	}
}
