package com.uitrs.game.majiang.common.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.PropKit;
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.HuCheckKit;
import com.uitrs.game.majiang.common.kit.MJCheckKit;
import com.uitrs.game.majiang.common.kit.NRandom;
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
public class FZMJRule extends MJRule {
	private static final long serialVersionUID = -5214380073004682927L;
	private String[] roomCards;
	/** 麻将规则名称 */
	public static final String RULE_NAME = "fzmj";
	/** 规则内所有可以打的牌 */
	private static final String[] CARDS = new String[] { MJConst.ZHONG, MJConst.FA, MJConst.BAI, MJConst.DONG,
			MJConst.NAN, MJConst.XI, MJConst.BEI, MJConst.WAN1, MJConst.WAN2, MJConst.WAN3, MJConst.WAN4, MJConst.WAN5,
			MJConst.WAN6, MJConst.WAN7, MJConst.WAN8, MJConst.WAN9, MJConst.TIAO1, MJConst.TIAO2, MJConst.TIAO3,
			MJConst.TIAO4, MJConst.TIAO5, MJConst.TIAO6, MJConst.TIAO7, MJConst.TIAO8, MJConst.TIAO9, MJConst.TONG1,
			MJConst.TONG2, MJConst.TONG3, MJConst.TONG4, MJConst.TONG5, MJConst.TONG6, MJConst.TONG7, MJConst.TONG8,
			MJConst.TONG9 };
	/** 所有可以打的牌 */
	public static String[] allCards = new String[CARDS.length * 4];
	// 固定排序
	private static List<String> cardList = new ArrayList<String>(CARDS.length * 4);
	private static int tongIndex;
	private static int jianIndex;
	private static int fengIndex;
	private static int tiaoIndex;
	private static int wanIndex;

	static {
		Arrays.sort(CARDS);
		tongIndex = Arrays.binarySearch(CARDS, MJConst.TONG1);
		tiaoIndex = Arrays.binarySearch(CARDS, MJConst.TIAO1);
		wanIndex = Arrays.binarySearch(CARDS, MJConst.WAN1);
		fengIndex = Arrays.binarySearch(CARDS, MJConst.FENG);
		jianIndex = Arrays.binarySearch(CARDS, MJConst.JIAN);

		for (int i = 0; i < MJConst.TOTAL; i++) {
			for (int j = 0; j < CARDS.length; j++) {
				cardList.add(CARDS[j]);
			}
		}
		cardList.toArray(allCards);
		Arrays.sort(allCards);
	}

	@Override
	public String[] enablePais() {
		return CARDS;
	}

	@Override
	public String[] createPais() {
		if (PropKit.getBoolean("devMode", false) && !StringUtils.isEmpty(PropKit.get("cards"))) {
			roomCards = PropKit.get("cards").split(",");
		} else {
			roomCards = NRandom.sequence(allCards);
		}
		return roomCards;
	}

	@Override
	public Set<String> checkTingCards(Room room, PlayerInfo playerInfo) {
		playerInfo.buildCount(room);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OptCheckMsg> checkOutOpts(Room room, PlayerInfo operator, long from, String outCard) {
		long userId = operator.getUserInfo().getUserId();
		List<OptCheckMsg> optList = new ArrayList<OptCheckMsg>();
		// 如果是金牌则不检查
		String[] enablePais = this.enablePais();
		optList = MJCheckKit.checkOutCardOpts(userId, from, outCard, operator.getCounts(), enablePais);
		// 是否能胡
		boolean isCanHu = HuCheckKit.tryHU(operator.getCounts(), operator.getHandCards().size());
		// boolean isCanHu = operator.getTingCards().contains(outCard);
		if (isCanHu) {
			OptCheckMsg huMsg = new OptCheckMsg(userId, IMsg.HU_TYPE, outCard, from);
			optList.add(huMsg);
		}
		int outCardIndex = Arrays.binarySearch(this.enablePais(), outCard);
		int[] counts = operator.getCounts();
		boolean isSameColor = checkSameColor(operator, outCard);
		int xingNum = checkThirteenCard(operator, outCardIndex);
		boolean isPair = checkPair(counts, outCardIndex);
		boolean isSevenPair = isPair && operator.getHandCards().size() == 13;
		int fourCount = checkFour(counts, outCardIndex);
		
		return optList;
	}

	private int checkFour(int[] counts, int index) {
		counts[index]++;
		int fourCount = 0;
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] == 4) {
				fourCount++;
			}
		}
		counts[index]--;
		return fourCount;
	}

	private boolean checkPair(int[] counts, int index) {
		if (counts[index] < 4) {
			counts[index]++;
			for (int i = 0; i < counts.length; i++) {
				if (counts[i] % 2 != 0) {
					counts[index]--;
					return false;
				}
			}
			counts[index]--;
			return true;
		}
		return false;
	}

	/**
	 * 不能有对子, 不能碰, 不能缺非字牌(中发白,东南西北风),字牌之间必须间隔2张及以上,如1,4,7;2,5,9等
	 * 
	 * @param operator
	 * @param outCard
	 * @return 0非十三烂,>0表示几星烂
	 */
	private int checkThirteenCard(PlayerInfo operator, int index) {

		// 不能有碰或吃等操作,手牌数必须是13张
		if (operator.getHandCards().size() != 13) {
			return 0;
		}
		int[] counts = operator.getCounts();
		if (counts[index] == 0) {
			counts[index]++;
			// 几星烂
			int xingNum = 0;
			// 手上不能有对子
			for (int i = 0; i < counts.length; i++) {
				if (counts[i] > 1) {
					counts[index]--;
					return 0;
				}
			}
			// 风牌和箭牌不能重复,且至少有5张
			xingNum = checkFengAndJianCount(counts);
			if (xingNum < 5) {
				counts[index]--;
				return 0;
			}
			// 同一种字牌最多有三张,且间隔>2
			if (!isZiPaiLan(counts, tongIndex) || !isZiPaiLan(counts, tiaoIndex) || !isZiPaiLan(counts, wanIndex)) {
				counts[index]--;
				return 0;
			}

			counts[index]--;
			return xingNum;
		}
		return 0;
	}

	private boolean isZiPaiLan(int[] counts, int index) {
		int preTmp = -1;
		int ziCount = 0;
		for (int i = index; i < index + 9; i++) {
			if (counts[i] > 0) {
				if (counts[i] > 1) {
					return false;
				} else {
					ziCount++;
					if (ziCount > 3) {
						return false;
					}
					if (preTmp != -1) {
						if (counts[i] - preTmp < 3) {
							return false;
						}
					}
					preTmp = counts[i];
				}
			}
		}
		return true;
	}

	private int checkFengAndJianCount(int[] counts) {
		int fengCount = 0;
		for (int i = fengIndex; i < fengIndex + 4; i++) {
			if (counts[i] == 1) {
				fengCount++;
			}
		}

		int jianCount = 0;
		for (int i = jianIndex; i < jianIndex + 4; i++) {
			if (counts[i] == 1) {
				jianCount++;
			}
		}
		return jianCount + fengCount;
	}

	/**
	 * 检查是否清一色
	 * 
	 * @param operator
	 * @param outCard
	 * @return
	 */
	private boolean checkSameColor(PlayerInfo operator, String outCard) {
		String cardType = outCard.substring(0, 1);
		if (cardType.equalsIgnoreCase(MJConst.TONG) || cardType.equalsIgnoreCase(MJConst.TIAO)
				|| cardType.equalsIgnoreCase(MJConst.WAN)) {
			for (String card : operator.getHandCards()) {
				if (!card.startsWith(cardType)) {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public void createRoom(int juNum, int[] opts, PlayerInfo createInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkSelf(Room room, PlayerInfo playerInfo, String card, SendMsg selfMsg) {
		// TODO Auto-generated method stub

	}
}
