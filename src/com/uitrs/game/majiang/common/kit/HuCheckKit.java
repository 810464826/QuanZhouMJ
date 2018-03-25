package com.uitrs.game.majiang.common.kit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.Result;

/**
 * 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28
 * 29 30 31 32 33 34 35 36 37 38 39 40 41 t1 t2 t3 t4 t5 t6 t7 t8 t9 o1 o2 o3 o4
 * o5 o6 o7 o8 o9 w1 w2 w3 w4 w5 w6 w7 w8 w9 z f b e s x n c a q d m l u j 1条 2条
 * 3条 4条 5条 6条 7条 8条 9条 1筒 2筒 3筒 4筒 5筒 6筒 7筒 8筒 9筒 1万 2万 3万 4万 5万 6万 7万 8万 9万 中
 * 发 白 东 南 西 北 春 夏 秋 冬 梅 兰 竹 菊
 *
 * @author 小石潭记
 *         <p/>
 *         2017年5月6日 上午11:19:44
 * 
 */
public class HuCheckKit {
	private static final int ZI_BEGIN_INDEX = 15;
	/** 非癞子牌 手牌 */
	private List<String> cards = new ArrayList<String>();
	/** 金牌数量 */
	private int jinCount = 0;
	String[] allCards = new String[] { MJConst.TIAO1, MJConst.TIAO2, MJConst.TIAO3, MJConst.TIAO4, MJConst.TIAO5,
			MJConst.TIAO6, MJConst.TIAO7, MJConst.TIAO8, MJConst.TIAO9, MJConst.TONG1, MJConst.TONG2, MJConst.TONG3,
			MJConst.TONG4, MJConst.TONG5, MJConst.TONG6, MJConst.TONG7, MJConst.TONG8, MJConst.TONG9, MJConst.WAN1,
			MJConst.WAN2, MJConst.WAN3, MJConst.WAN4, MJConst.WAN5, MJConst.WAN6, MJConst.WAN7, MJConst.WAN8,
			MJConst.WAN9, MJConst.ZHONG, MJConst.FA, MJConst.BAI, MJConst.DONG, MJConst.NAN, MJConst.XI, MJConst.BEI,
			MJConst.CHUN, MJConst.XIA, MJConst.QIU, MJConst.DON, MJConst.MEI, MJConst.LAN, MJConst.ZHU, MJConst.JU };
	String[] noHuaCards = new String[] { MJConst.TIAO1, MJConst.TIAO2, MJConst.TIAO3, MJConst.TIAO4, MJConst.TIAO5,
			MJConst.TIAO6, MJConst.TIAO7, MJConst.TIAO8, MJConst.TIAO9, MJConst.TONG1, MJConst.TONG2, MJConst.TONG3,
			MJConst.TONG4, MJConst.TONG5, MJConst.TONG6, MJConst.TONG7, MJConst.TONG8, MJConst.TONG9, MJConst.WAN1,
			MJConst.WAN2, MJConst.WAN3, MJConst.WAN4, MJConst.WAN5, MJConst.WAN6, MJConst.WAN7, MJConst.WAN8,
			MJConst.WAN9, MJConst.ZHONG, MJConst.FA, MJConst.BAI, MJConst.DONG, MJConst.NAN, MJConst.XI, MJConst.BEI };
	/**
	 * 这是定义可以组合的牌
	 */
	Set<String> hasConfirmPs = getHasConfirmPs(noHuaCards);

	/**
	 * 这是将所有的存入一个set集合中
	 * 
	 * @param allPai
	 *            所有的牌
	 * @return
	 */
	public Set<String> getHasConfirmPs(String[] allPai) {
		Set<String> hasConfirmPs = new HashSet<String>();
		for (String p : allPai) {
			hasConfirmPs.add(p);
		}
		return hasConfirmPs;
	}

	// 构造函数
	public HuCheckKit(List<String> cards) {
		for (String p : cards) {
			// 这是没有金牌的时候
			if (p != MJConst.LAIZI) {
				// 这里将手牌加入cards中
				this.cards.add(p);
				// 这里操作麻将牌的对应的字符
			} else {
				// 金牌数量加加
				this.jinCount++;
			}
		}
		System.err.println("jinCount::::" + jinCount);
		Arrays.sort(this.noHuaCards);
		Arrays.sort(this.allCards);
		Collections.sort(this.cards);
	}

	@Override
	public String toString() {
		String msg = "";
		for (String p : cards) {
			msg += p + " , ";
		}
		for (int i = 0; i < jinCount; i++) {
			msg += MJConst.LAIZI + " , ";
		}
		return msg;
	}

	/**
	 * 核心求解方法
	 * 
	 * @return
	 */
	public List<Result> solve() {
		System.err.println("begin calc hu..." + (Calendar.getInstance().getTimeInMillis() / 1000));
		List<Result> resultList = new ArrayList<Result>();
		if (this.jinCount == 3) {
			Set<String> hf = new HashSet<String>();
			hf.addAll(cards);
			resultList.add(new Result(cards, hf));
		} else {
			for (List<String> e : combine()) {
				Collections.sort(e);
				Set<String> hf = checkNormalTing(hCards2Array(e), e.size());
				if (hf != null && hf.size() > 0) {
					resultList.add(new Result(e, hf));
				}
			}
		}
		System.err.println("end  calc hu..." + (Calendar.getInstance().getTimeInMillis() / 1000));
		return resultList;
	}

	public List<String> getCards() {
		return cards;
	}

	/**
	 * 求癞子组合牌
	 *
	 * @return
	 */
	private List<List<String>> combine() {
		// 这是有金牌的时候
		List<List<String>> results = new ArrayList<List<String>>();
		// 没有金牌的时候
		if (this.jinCount == 0) {
			// 没有金牌直接返回当前的手牌
			results.add(cards);
		} else {
			for (int i = 0; i < jinCount; i++) {
				if (i == 0) {
					results.addAll(descartes(this.cards, hasConfirmPs));
				} else {
					List<List<String>> source = new ArrayList<List<String>>();
					source.addAll(results);
					results.clear();
					for (List<String> e : source) {
						results.addAll(descartes(e, hasConfirmPs));
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
	private List<List<String>> descartes(List<String> source1, Set<String> source2) {
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
		int[] tmp = null;
		Set<String> huPai = new HashSet<String>(); // 可胡的牌
		for (int i = 0; i < count.length; i++) {
			if (count[i] < 4) {
				count[i]++;
				tmp = Arrays.copyOf(count, count.length);
				if (isHU(tmp, size)) {
					huPai.add(translate(i));
				}
				count[i]--;
			}
		}
		return huPai;
	}

	/**
	 * 将手牌转为数组
	 * 
	 * @param hCards
	 * @return
	 */
	private int[] hCards2Array(List<String> hCards) {
		return MJCheckKit.countCardNum(hCards, allCards);
	}

	/**
	 *
	 * @param i
	 *            通过牌的编码返回具体的牌
	 * @return
	 */
	public String translate(int i) {
		return this.allCards[i];
	}

	/**
	 * 通过传入上边处理排好序的数组count去判断是否能否胡牌
	 *
	 * @param count
	 * @param hCdlength
	 *            这是传的手牌的长度 原来的是14
	 * @return
	 */
	public static boolean isHU(int[] count, int hCdlength) {
		boolean result = tryHU(count, hCdlength + 1);
		return result;
	}

	/**
	 * 这是真正处理业务逻辑的 判断是否能胡牌的
	 *
	 * @param count
	 *            上边处理的数组count
	 * @param len
	 *            传入的牌的长度
	 */
	public static boolean tryHU(int[] count, int len) {
		// System.err.println("count:"+Arrays.toString(count));
		// 如果牌的长度为0时，则返回true 代表可以胡牌了
		if (len == 0) {
			return true;
		}
		// 对子 len % 3 == 2 这是判断是否有对子的情况
		if (len % 3 == 2) {
			for (int i = 0; i < count.length; i++) {
				// count[i] >= 2 这是说明该牌至少有两个是对子或者三个的
				if (count[i] >= 2) {
					// 这里将该牌的数量-2
					count[i] -= 2;
					// 继续循环判断是否能胡牌
					if (tryHU(count, len - 2)) {
						return true;
					}
					// 如果这里不能胡牌将数量加回去
					count[i] += 2;
				}
			}
		} else {
			// 三个一样的 刻子 这是判断30位上的count的每一位上的牌是否有3张以上的
			for (int i = 0; i < count.length; i++) {
				if (count[i] >= 3) {
					count[i] -= 3;
					if (tryHU(count, len - 3)) {
						return true;
					}
					count[i] += 3;
				}
			}

			// 数字牌起始位置,不同的麻将类型需要注意排序后第一张数字牌位置不一样
			int tongBeginIndex = ZI_BEGIN_INDEX;
			// 是否是顺子 不能从0遍历到25而应该是0~6,9~15,18~24
			for (int i = tongBeginIndex; i <= tongBeginIndex + 6; i++) {
				// 这里判断相邻的三位上是否有牌 如果有 则将该位的数字减1
				if (count[i] > 0 && count[i + 1] > 0 && count[i + 2] > 0) {
					count[i] -= 1;
					count[i + 1] -= 1;
					count[i + 2] -= 1;
					if (tryHU(count, len - 3)) {
						return true;
					}
					count[i] += 1;
					count[i + 1] += 1;
					count[i + 2] += 1;
				}
			}
			// 是否是顺子 不能从0遍历到25而应该是0~6,9~15,18~24
			for (int i = tongBeginIndex + 9; i <= tongBeginIndex + 9 + 6; i++) {
				if (count[i] > 0 && count[i + 1] > 0 && count[i + 2] > 0) {
					count[i] -= 1;
					count[i + 1] -= 1;
					count[i + 2] -= 1;
					if (tryHU(count, len - 3)) {
						return true;
					}
					count[i] += 1;
					count[i + 1] += 1;
					count[i + 2] += 1;
				}
			}
			// 是否是顺子 不能从0遍历到25而应该是0~6,9~15,18~24
			for (int i = tongBeginIndex + 18; i <= tongBeginIndex + 18 + 6; i++) {
				if (count[i] > 0 && count[i + 1] > 0 && count[i + 2] > 0) {
					count[i] -= 1;
					count[i + 1] -= 1;
					count[i + 2] -= 1;
					if (tryHU(count, len - 3)) {
						return true;
					}
					count[i] += 1;
					count[i + 1] += 1;
					count[i + 2] += 1;
				}
			}
		}
		return false;
	}

	public static boolean tryThree(int[] count, int len) {
		// 如果牌的长度为0时，则返回true 代表可以胡牌了
		if (len == 0) {
			return true;
		}
		// 对子 len % 3 == 2 这是判断是否有对子的情况
		if (len % 3 != 0) {
			return false;
		} else {
			// 三个一样的 刻子 这是判断30位上的count的每一位上的牌是否有3张以上的
			for (int i = 0; i < count.length; i++) {
				if (count[i] >= 3) {
					count[i] -= 3;
					if (tryThree(count, len - 3)) {
						return true;
					}
					count[i] += 3;
				}
			}

			// 数字牌起始位置,不同的麻将类型需要注意排序后第一张数字牌位置不一样
			int tongBeginIndex = ZI_BEGIN_INDEX;
			// 是否是顺子 不能从0遍历到25而应该是0~6,9~15,18~24
			for (int i = tongBeginIndex; i <= tongBeginIndex + 6; i++) {
				// 这里判断相邻的三位上是否有牌 如果有 则将该位的数字减1
				if (count[i] > 0 && count[i + 1] > 0 && count[i + 2] > 0) {
					count[i] -= 1;
					count[i + 1] -= 1;
					count[i + 2] -= 1;
					if (tryThree(count, len - 3)) {
						return true;
					}
					count[i] += 1;
					count[i + 1] += 1;
					count[i + 2] += 1;
				}
			}
			// 是否是顺子 不能从0遍历到25而应该是0~6,9~15,18~24
			for (int i = tongBeginIndex + 9; i <= tongBeginIndex + 9 + 6; i++) {
				if (count[i] > 0 && count[i + 1] > 0 && count[i + 2] > 0) {
					count[i] -= 1;
					count[i + 1] -= 1;
					count[i + 2] -= 1;
					if (tryThree(count, len - 3)) {
						return true;
					}
					count[i] += 1;
					count[i + 1] += 1;
					count[i + 2] += 1;
				}
			}
			// 是否是顺子 不能从0遍历到25而应该是0~6,9~15,18~24
			for (int i = tongBeginIndex + 18; i <= tongBeginIndex + 18 + 6; i++) {
				if (count[i] > 0 && count[i + 1] > 0 && count[i + 2] > 0) {
					count[i] -= 1;
					count[i + 1] -= 1;
					count[i + 2] -= 1;
					if (tryThree(count, len - 3)) {
						return true;
					}
					count[i] += 1;
					count[i + 1] += 1;
					count[i + 2] += 1;
				}
			}
		}
		return false;
	}

	/**
	 * 是否包含中发白,东南西北风以及花牌
	 * 
	 * @param noJinCount
	 * @return
	 */
	public static boolean isHasSpecialCard(int[] noJinCount) {
		for (int i = 0; i < ZI_BEGIN_INDEX; i++) {
			if (noJinCount[i] > 0) {
				return true;
			}
		}
		return false;
	}
}
