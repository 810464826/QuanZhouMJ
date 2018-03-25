package com.uitrs.game.majiang.common.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.Result;
import com.uitrs.game.majiang.common.kit.HuCheckKit;
import com.uitrs.game.majiang.common.kit.MJCheckKit;
import com.uitrs.game.majiang.common.kit.NRandom;
import com.uitrs.game.majiang.common.kit.PrintKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.room.Room;

/**
 * 抚州麻将规则
 * 
 * @author lucio
 *
 */
public class QZMJRule extends MJRule {

	private static final long serialVersionUID = -1727491163007049041L;
	/** 局数,8局 */
	public static final int J_EIGTH = 1;
	/** 局数,16局 */
	public static final int J_SIXTH = 2;
	/** 分数,100分 */
	public static final int F_ONE_HUNDRED = 3;
	/** 分数,200分 */
	public static final int F_TWO_HUNDRED = 4;
	/** 单金不能胡 */
	public static final int T_ONE = 1;
	/** 双金不能胡 */
	public static final int T_TWO = 2;
	/** 是否分数打法 */
	private boolean isFen;
	/** 是否单金不能胡 */
	private boolean isOneJin;
	/** 是否双金不能胡 */
	private boolean isTwoJin = true;
	private String[] roomCards;
	// private int jinCount;
	/** 几游中,0表示没有游金,1表示单游,2表示双游,3表示3游 */
	private int youJinNum;
	private int baseFen;
	/** 三金必须游金 */
	private boolean isThreeJin;
	/**
	 * 麻将规则名称
	 */
	public static final String RULE_NAME = "qzmj";
	/** 规则内所有基础的牌 */
	public static final String[] CARDS = new String[] { MJConst.ZHONG, MJConst.FA, MJConst.BAI, MJConst.DONG,
			MJConst.NAN, MJConst.XI, MJConst.BEI, MJConst.WAN1, MJConst.WAN2, MJConst.WAN3, MJConst.WAN4, MJConst.WAN5,
			MJConst.WAN6, MJConst.WAN7, MJConst.WAN8, MJConst.WAN9, MJConst.TIAO1, MJConst.TIAO2, MJConst.TIAO3,
			MJConst.TIAO4, MJConst.TIAO5, MJConst.TIAO6, MJConst.TIAO7, MJConst.TIAO8, MJConst.TIAO9, MJConst.TONG1,
			MJConst.TONG2, MJConst.TONG3, MJConst.TONG4, MJConst.TONG5, MJConst.TONG6, MJConst.TONG7, MJConst.TONG8,
			MJConst.TONG9 };
	/** 规则内所有花牌 */
	private static final String[] HUA = new String[] { MJConst.CHUN, MJConst.XIA, MJConst.QIU, MJConst.DON, MJConst.MEI,
			MJConst.LAN, MJConst.ZHU, MJConst.JU };

	/** 规则内所有可以打的牌 */
	public static final String[] ENABLE_CARDS = new String[] { MJConst.DONG, MJConst.NAN, MJConst.XI, MJConst.BEI,
			MJConst.CHUN, MJConst.XIA, MJConst.QIU, MJConst.DON, MJConst.MEI, MJConst.LAN, MJConst.ZHU, MJConst.JU,
			MJConst.ZHONG, MJConst.FA, MJConst.BAI, MJConst.TONG1, MJConst.TONG2, MJConst.TONG3, MJConst.TONG4,
			MJConst.TONG5, MJConst.TONG6, MJConst.TONG7, MJConst.TONG8, MJConst.TONG9, MJConst.TIAO1, MJConst.TIAO2,
			MJConst.TIAO3, MJConst.TIAO4, MJConst.TIAO5, MJConst.TIAO6, MJConst.TIAO7, MJConst.TIAO8, MJConst.TIAO9,
			MJConst.WAN1, MJConst.WAN2, MJConst.WAN3, MJConst.WAN4, MJConst.WAN5, MJConst.WAN6, MJConst.WAN7,
			MJConst.WAN8, MJConst.WAN9 };
	// 固定排序
	private static List<String> cardList = new ArrayList<String>();
	static {
		// 排序
		Arrays.sort(ENABLE_CARDS);
		Arrays.sort(CARDS);
		for (int i = 0; i < MJConst.TOTAL; i++) {
			for (int j = 0; j < CARDS.length; j++) {
				cardList.add(CARDS[j]);
			}
		}

		for (int i = 0; i < HUA.length; i++) {
			cardList.add(HUA[i]);
		}
		// Collections.sort(cardList);
	}

	public String[] getRoomCards() {
		return roomCards;
	}

	public void setRoomCards(String[] roomCards) {
		this.roomCards = roomCards;
	}

	public int getYouJinNum() {
		return youJinNum;
	}

	public void setYouJinNum(int youJinNum) {
		this.youJinNum = youJinNum;
	}

	public boolean isFen() {
		return isFen;
	}

	public void setFen(boolean isFen) {
		this.isFen = isFen;
	}

	public boolean isOneJin() {
		return isOneJin;
	}

	public void setOneJin(boolean isOneJin) {
		this.isOneJin = isOneJin;
	}

	public boolean isTwoJin() {
		return isTwoJin;
	}

	public int getBaseFen() {
		return baseFen;
	}

	public void setBaseFen(int baseFen) {
		this.baseFen = baseFen;
	}

	@Override
	public String[] enablePais() {
		// for (int i = 0; i < ENABLE_CARDS.length; i++) {
		// System.err.println(i + " : " + ENABLE_CARDS[i]);
		// }
		return ENABLE_CARDS;
	}

	@Override
	public String[] createPais() {
		PrintKit.red("total cards: " + cardList.size());
		if (PropKit.getBoolean("devMode")) {
			String jinCard = PropKit.get("goldCard");
			if (StrKit.notBlank(jinCard)) {
				this.goldCard = jinCard;
			} else {
				this.goldCard = NRandom.randomString(CARDS);
			}
		} else {
			this.goldCard = NRandom.randomString(CARDS);// MJConst.WAN2;
		}
		PrintKit.red("goldCard: " + goldCard);
		// 删除一张金牌
		cardList.remove(goldCard);
		String[] result = new String[cardList.size()];
		if (PropKit.getBoolean("devMode", false) && !StringUtils.isEmpty(PropKit.get("cards"))) {
			roomCards = PropKit.get("cards").split(",");
		} else {
			roomCards = NRandom.sequence(cardList.toArray(result));
		}

		return roomCards;
	}

	@Override
	public Set<String> checkTingCards(Room room, PlayerInfo playerInfo) {
		List<String> hCards = playerInfo.getHandCards();
		Collections.sort(hCards);
		PrintKit.out(playerInfo.getUserInfo().getUserId() + "原始手牌: " + hCards + " goldCard: " + goldCard);

		// int[] counts = playerInfo.getCounts();
		// playerInfo.setCounts(counts);
		List<String> parameter = new ArrayList<String>();
		int jinCount = 0;
		// 这里将金牌转为癞子
		for (String m : hCards) {
			if (m.equalsIgnoreCase(goldCard)) {
				// parameter.add(MJConst.LAIZI);
				jinCount++;
			} else {
				parameter.add(m);
			}
		}

		// 可以胡的牌去重
		Set<String> rcs = new HashSet<String>();
		// if (3 <= jinCount) {
		// rcs.addAll(cardList);
		// PrintKit.out("三金倒!!!");
		// } else {
		Collections.sort(parameter);
		PrintKit.out(playerInfo.getUserInfo().getUserId() + "无金手牌: " + parameter);
		List<Result> solve = solve(parameter, jinCount);
		for (Result result : solve) {
			Set<String> hpList = result.getHps();
			rcs.addAll(hpList);
		}
		playerInfo.setTingCards(rcs);
		String msg = ",可胡牌集合: ";
		for (String p : rcs) {
			msg += p + " , ";
		}
		PrintKit.out("可以胡牌的组合数: " + solve.size() + msg);

		// 这里对金牌组合求解 得到答案
		for (Result r : solve) {
			PrintKit.out(r.toString());
		}
		// }s
		return rcs;
	}

	/**
	 * 核心求解方法
	 * 
	 * @param jinCount
	 * 
	 * @param playerInfo
	 * 
	 * @param playerInfo
	 * 
	 * @return
	 */
	public List<Result> solve(List<String> parameter, int jinCount) {
		List<Result> resultList = new ArrayList<Result>();
		List<List<String>> combine = combine(parameter, jinCount);
		for (List<String> e : combine) {
			Collections.sort(e);
			Set<String> hf = checkNormalTing(MJCheckKit.countCardNum(e, this.enablePais()), e.size());
			if (hf != null && hf.size() > 0) {
				resultList.add(new Result(e, hf));
			}
		}
		return resultList;
	}

	/**
	 * 求癞子组合牌
	 * 
	 * @param jinCount
	 * 
	 * @param playerInfo
	 *
	 * @return
	 */
	private List<List<String>> combine(List<String> parameter, int jinCount) {
		// 这是有金牌的时候
		List<List<String>> results = new ArrayList<List<String>>();
		// 没有金牌的时候
		if (jinCount == 0) {
			// 没有金牌直接返回当前的手牌
			results.add(parameter);
		} else {
			for (int i = 0; i < jinCount; i++) {
				if (i == 0) {
					results.addAll(descartes(parameter, CARDS));
				} else {
					List<List<String>> source = new ArrayList<List<String>>();
					source.addAll(results);
					results = new ArrayList<List<String>>();
					for (List<String> e : source) {
						results.addAll(descartes(e, CARDS));
					}
				}
			}
		}
		return results;
	}

	/**
	 * 求笛卡尔组合
	 * 
	 * @param source1
	 *            目标组合 手牌
	 * @param source2
	 *            癞子可以充当的牌
	 * @return
	 */
	private List<List<String>> descartes(List<String> source1, String[] source2) {
		List<List<String>> r = new ArrayList<List<String>>();
		// 循环source2 每次都是添加一个组合 手牌 加上 source2里的一张牌
		for (String card : source2) {
			List<String> ps = new ArrayList<String>();
			ps.addAll(source1);
			ps.add(card);
			r.add(ps);
		}
		return r;
	}

	/**
	 * 求某一个组合具体的解
	 * 
	 * @param count
	 * @param size
	 * @return
	 */
	private Set<String> checkNormalTing(int[] count, int size) {
		int[] tmp = new int[this.enablePais().length];
		tmp = Arrays.copyOf(count, count.length);
		Set<String> huPai = new HashSet<String>(); // 可胡的牌
		for (int i = 0; i < this.enablePais().length; i++) {
			if (tmp[i] < 4 && !MJCheckKit.isHua(this.enablePais()[i])) {
				tmp[i]++;
				if (HuCheckKit.isHU(tmp, size)) {
					huPai.add(this.enablePais()[i]);
				}
				tmp[i]--;
			}
		}
		return huPai;
	}

	/**
	 * 求某一个组合是否有叫
	 * 
	 * @param count
	 * @param size
	 * @return boolean
	 */
	public boolean checkHasTing(int[] count, int size) {
		int[] tmp = new int[this.enablePais().length];
		tmp = Arrays.copyOf(count, count.length);
		for (int i = 0; i < this.enablePais().length; i++) {
			tmp[i]++;
			if (HuCheckKit.isHU(tmp, size)) {
				return true;
			}
			tmp[i]--;
		}
		return false;
	}

	public Set<String> getHasConfirmPs(String[] allPai) {
		Set<String> hasConfirmPs = new HashSet<String>();
		for (String p : allPai) {
			hasConfirmPs.add(p);
		}
		return hasConfirmPs;
	}

	@Override
	public List<OptCheckMsg> checkOutOpts(Room room, PlayerInfo playerInfo, long from, String outCard) {
		// 几游中
		this.youJinNum = room.getArg(MJConst.YOU_JINGING);
		long userId = playerInfo.getUserInfo().getUserId();
		List<OptCheckMsg> optList = new ArrayList<OptCheckMsg>();
		// 如果是金牌则不检查
		if (!outCard.equalsIgnoreCase(this.getGoldCard())) {
			String[] enablePais = this.enablePais();
			optList = MJCheckKit.checkOutCardOpts(userId, from, outCard, playerInfo.getNoJinCount(), enablePais);
			int jinIndex = Arrays.binarySearch(this.enablePais(), this.getGoldCard());
			int jinNum = playerInfo.getCounts()[jinIndex];
			// 双游中/三游中/单金/双金不能胡的判断
			if (this.youJinNum < 2 && jinNum < 2) {
				if (!isOneJin || (isOneJin && jinNum == 0)) {// 单金不能平胡
					boolean isCanHu = playerInfo.getTingCards().contains(outCard);
					if (isCanHu) {
						OptCheckMsg huMsg = new OptCheckMsg(userId, IMsg.HU_TYPE, outCard, from);
						optList.add(huMsg);
					}
				}
			}
		}
		return optList;
	}

	@Override
	public void createRoom(int juNum, int[] opts, PlayerInfo creater) {
		this.isFen = (juNum == F_ONE_HUNDRED || juNum == F_TWO_HUNDRED);
		this.baseFen = (int) Math.pow(2, opts[0]);
		this.isOneJin = (opts[1] == T_ONE);
		this.isTwoJin = true;
		this.isThreeJin = (opts[1] == T_TWO);
		if (opts[1] == 3) {
			this.isOneJin = true;
			this.isThreeJin = true;
		}
		creater.getHuInfo().put(MJConst.ZHUANG_COUNT, 1);
	}

	/**
	 * 检查拿牌后自己是否有杠和自摸
	 * 
	 * @param room
	 * @param playerInfo
	 * @param faPai
	 * @param selfMsg
	 */
	@Override
	public void checkSelf(Room room, PlayerInfo playerInfo, String faPai, SendMsg selfMsg) {
		if (!MJCheckKit.isHua(faPai)) {
			playerInfo.buildCount(room);
			playerInfo.buildTingCards(room);
			// 检查暗杠
			long userId = playerInfo.getUserInfo().getUserId();
			List<OptCheckMsg> optList = MJCheckKit.checkAnGang(userId, playerInfo.getCounts(),
					room.getMjRule().enablePais());
			// 检查补杠
			OptCheckMsg buGang = MJCheckKit.checkBuGang(userId, faPai);
			if (null != buGang) {
				optList.add(buGang);
			}

			// 三金倒,游金检查
			List<OptCheckMsg> jinList = checkJinOpts(room, playerInfo, faPai);
			optList.addAll(jinList);
			if (optList.size() > 0) {
				selfMsg.setE(optList);
			}
		}
	}

	public List<OptCheckMsg> checkJinOpts(Room room, PlayerInfo playerInfo, String faPai) {
		List<OptCheckMsg> jinOpts = new ArrayList<OptCheckMsg>();

		// 没有金牌的统计
		int[] noJinCount = playerInfo.getNoJinCount();
		int[] counts = playerInfo.getCounts();
		long userId = playerInfo.getUserInfo().getUserId();
		// 是否游金中
		Integer youNum = playerInfo.getHuInfo().get(MJConst.YOU_JINGING);
		youNum = null == youNum ? 0 : youNum.intValue();
		// 判断是否游金成功
		if (youNum > 0) {
			jinOpts.add(new OptCheckMsg(userId,
					youNum == 3 ? IMsg.THREE_GOLD : (youNum == 2 ? IMsg.TWO_GOLD : IMsg.ONE_GOLD), ""));
		} else if (StrKit.notBlank(faPai) && playerInfo.getTingCards().contains(faPai)) {
			// 没有游金的情况下判断是否能自摸
			OptCheckMsg zimo = new OptCheckMsg(userId, IMsg.ZIMO_TYPE, faPai);
			zimo.setFrom(userId);
			jinOpts.add(zimo);
		}

		List<String> handCards = playerInfo.getHandCards();
		int jinIndex = Arrays.binarySearch(this.enablePais(), this.getGoldCard());
		// 金的数量
		int jinCount = counts[jinIndex];
		// if (playerInfo.getTingCards().contains(faPai)) {
		// OptCheckMsg hu = new OptCheckMsg(userId, IMsg.HU_TYPE, faPai);
		// hu.setFrom(userId);
		// jinOpts.add(hu);
		// }
		// 没有金牌时直接判断是否能胡牌,然后直接返回
		if (jinCount > 0 || youNum > 0) {
			// 构造没有金牌的手牌
			List<String> noJinHands = new ArrayList<String>(handCards);
			for (int i = 0; i < jinCount; i++) {
				noJinHands.remove(goldCard);
			}

			if (jinCount == 3 || youNum == 2) {
				checkThreeJin(jinOpts, noJinCount, userId, noJinHands);
			} else if (jinCount == 2 || youNum == 1) {
				checkTwoJin(noJinHands, jinOpts, noJinCount, userId, jinCount);
			} else if (jinCount == 1 && youNum == 0) {
				checkOneJin(jinOpts, noJinCount, userId, noJinHands);
			}
		}
		return jinOpts;
	}

	private void checkThreeJin(List<OptCheckMsg> jinOpts, int[] noJinCount, long userId, List<String> noJinHands) {
		if (!isThreeJin) {
			// 不是三金必须游金则显示三金倒
			OptCheckMsg sanJinDao = new OptCheckMsg(userId, IMsg.THREE_GOLD_HU, goldCard, userId);
			jinOpts.add(sanJinDao);
		}
		// 双游判断
		List<String> twoYouList = checkAddCardYou(noJinHands.size() + 1, noJinCount, this.enablePais());
		if (twoYouList.size() > 0) {
			buildTwoYouMsg(jinOpts, userId);
		} else {
			// 单游判断
			Set<String> checkCards = check3Jin1You(noJinHands.size() + 1, noJinCount, this.enablePais());
			// 手牌中存在的牌才能单游
			Set<String> youJinCards = new HashSet<String>();
			for (String card : noJinHands) {
				if (checkCards.contains(card)) {
					youJinCards.add(card);
				}
			}
			if (youJinCards.size() > 0) {
				buildOneYouMsg(jinOpts, userId, youJinCards);
			}
		}
	}

	private void checkOneJin(List<OptCheckMsg> jinOpts, int[] noJinCount, long userId, List<String> noJinHands) {
		// 排除金牌后, 每一张牌作为将牌,其他的牌全是刻子或者顺子则可单游
		Set<String> youJinCards = checkReduceCardYou(noJinHands.size() - 1, noJinCount, this.enablePais());
		if (youJinCards.size() > 0) {
			buildOneYouMsg(jinOpts, userId, youJinCards);
		}
	}

	private boolean checkTwoJin(List<String> noJinHands, List<OptCheckMsg> jinOpts, int[] noJinCount, long userId,
			int jinCount) {
		// 排除掉金牌后是否全是刻子或者顺子
		boolean isTwoYou = HuCheckKit.tryThree(noJinCount, noJinHands.size());
		if (isTwoYou) {
			// 双游金
			buildTwoYouMsg(jinOpts, userId);
		} else {
			// 单游金
			Set<String> checkCards = check2Jin1You(noJinHands, noJinCount);
			// 手牌中存在的牌才能单游
			Set<String> youJinCards = new HashSet<String>();
			for (String card : noJinHands) {
				if (checkCards.contains(card)) {
					youJinCards.add(card);
				}
			}
			if (youJinCards.size() > 0) {
				buildOneYouMsg(jinOpts, userId, youJinCards);
			}
		}
		return isTwoYou;
	}

	/**
	 * 检查2金单游情况
	 * 
	 * @param noJinHands
	 * @param noJinCount
	 * @return Set<String>
	 */
	private Set<String> check2Jin1You(List<String> noJinHands, int[] noJinCount) {
		Set<String> youJinCards = new HashSet<String>();
		int[] tempNoJinCount = Arrays.copyOf(noJinCount, noJinCount.length);
		for (int i = 0; i < tempNoJinCount.length; i++) {
			// 添加一张手牌,如果手里的牌检查能成111,123组合说明是單游
			if (tempNoJinCount[i] < 4) {
				tempNoJinCount[i]++;
				Set<String> tmpList = checkReduceCardYou(noJinHands.size(), tempNoJinCount, this.enablePais());
				youJinCards.addAll(tmpList);
				tempNoJinCount[i]--;
			}
		}
		return youJinCards;
	}

	private void buildTwoYouMsg(List<OptCheckMsg> jinOpts, long userId) {
		OptCheckMsg twoYou = new OptCheckMsg(userId, IMsg.TWO_GOLDING, goldCard, userId);
		twoYou.setM(new String[] { goldCard });
		jinOpts.add(twoYou);
	}

	private void buildOneYouMsg(List<OptCheckMsg> jinOpts, long userId, Set<String> youJinCards) {
		String[] jinCard = new String[youJinCards.size()];
		youJinCards.toArray(jinCard);
		OptCheckMsg oneYou = new OptCheckMsg(userId, IMsg.ONE_GOLDING, goldCard, userId);
		oneYou.setM(jinCard);
		jinOpts.add(oneYou);
	}

	/**
	 * 减少一张牌来判断是否能组成111,123的组合
	 * 
	 * @param noJinHands
	 * @param noJinCount
	 * @param enablePais
	 * @return List<String>
	 */
	private Set<String> checkReduceCardYou(int len, int[] noJinCount, String[] enablePais) {
		Set<String> youJinCards = new HashSet<String>();
		for (int i = 0; i < noJinCount.length; i++) {
			if (noJinCount[i] > 0) {
				noJinCount[i]--;
				boolean isThree = HuCheckKit.tryThree(noJinCount, len);
				noJinCount[i]++;
				if (isThree) {
					youJinCards.add(enablePais[i]);
				}
			}
		}
		return youJinCards;
	}

	/**
	 * 检查增加一张牌是否能凑成111,123的组合
	 * 
	 * @param noJinHands
	 * @param noJinCount
	 * @param enablePais
	 * @return List<String>
	 */
	private List<String> checkAddCardYou(int len, int[] noJinCount, String[] enablePais) {
		List<String> youJinCards = new ArrayList<String>();
		for (int i = 0; i < noJinCount.length; i++) {
			if (noJinCount[i] < 4) {
				noJinCount[i]++;
				boolean isThree = HuCheckKit.tryThree(noJinCount, len);
				noJinCount[i]--;
				if (isThree) {
					youJinCards.add(enablePais[i]);
				}
			}
		}
		return youJinCards;
	}

	/**
	 * 检查增加2张牌在减去1张牌后是否能凑成111,123的组合
	 * 
	 * @param noJinHands
	 * @param noJinCount
	 * @param enablePais
	 * @return List<String>
	 */
	private Set<String> check3Jin1You(int len, int[] noJinCount, String[] enablePais) {
		// 单游金的牌
		Set<String> youJinCards = new HashSet<String>();
		int[] tempNoJinCount = Arrays.copyOf(noJinCount, noJinCount.length);
		for (int m = 0; m < tempNoJinCount.length; m++) {
			// 添加一张手里有的牌,如果手里没有的牌检查能成111,123组合说明是双游,此处没有意义
			if (tempNoJinCount[m] > 0) {
				tempNoJinCount[m]--;
				for (int i = 0; i < tempNoJinCount.length; i++) {
					// 添加一张手里有的牌,如果手里没有的牌检查能成111,123组合说明是双游,此处没有意义
					if (tempNoJinCount[i] > 0) {
						tempNoJinCount[i]--;
						boolean isThree = HuCheckKit.tryThree(tempNoJinCount, len - 3);
						tempNoJinCount[i]++;
						if (isThree) {
							youJinCards.add(enablePais[i]);
							youJinCards.add(enablePais[m]);
						}
					}
				}
				tempNoJinCount[m]++;
			}
		}
		return youJinCards;
	}
}
