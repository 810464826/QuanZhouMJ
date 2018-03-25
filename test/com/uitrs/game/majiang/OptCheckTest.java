package com.uitrs.game.majiang;

import java.util.Arrays;
import java.util.List;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.MJCheckKit;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.game.majiang.common.rule.QZMJRule;

public class OptCheckTest {
	public static void main(String[] args) {

		String[] hCards = new String[] { MJConst.WAN9, MJConst.WAN9, MJConst.WAN9, MJConst.WAN4, MJConst.WAN2,
				MJConst.WAN3, MJConst.WAN2, MJConst.WAN3, MJConst.WAN4, MJConst.TIAO7, MJConst.TIAO7, MJConst.XI,
				MJConst.TIAO6, MJConst.DONG, MJConst.DONG, MJConst.XI };
		String outCard = MJConst.TIAO6;
		String goldCard = MJConst.XI;

		PlayerInfo playerInfo = new PlayerInfo(new UserInfo(518001l));
		playerInfo.init(800001, 4);
		MJCache.getCache().addPlayerInfo(playerInfo);
		PlayerInfo playerInfo2 = new PlayerInfo(new UserInfo(518002l));
		playerInfo2.init(800002, 1);
		MJCache.getCache().addPlayerInfo(playerInfo2);

		boolean isHu = testHu(hCards, outCard, goldCard);
		System.out.println("胡牌:" + isHu + " :" + goldCard);
		// 测试暗杠
		hCards = new String[] { MJConst.WAN9, MJConst.WAN9, MJConst.WAN9, MJConst.WAN9, MJConst.WAN2, MJConst.WAN3,
				MJConst.WAN3, MJConst.WAN3, MJConst.WAN3, MJConst.TIAO7, MJConst.TIAO7, MJConst.XI, MJConst.TIAO6,
				MJConst.DONG, MJConst.DONG, MJConst.XI };
		List<OptCheckMsg> anGangs = MJCheckKit.checkAnGang(518001, hCards, QZMJRule.ENABLE_CARDS);
		System.out.println("暗杠:" + (anGangs.size() == 2) + " :" + anGangs.toString());
		playerInfo.addOptMsgs(anGangs);
		// 测试明杠
		hCards = new String[] { MJConst.TIAO6, MJConst.TIAO6, MJConst.TIAO6, MJConst.WAN9, MJConst.WAN2, MJConst.WAN3,
				MJConst.WAN3, MJConst.WAN3, MJConst.WAN3, MJConst.TIAO7, MJConst.TIAO7, MJConst.XI, MJConst.TIAO6,
				MJConst.DONG, MJConst.DONG, MJConst.XI };
		OptCheckMsg mingGang = MJCheckKit.checkMingGang(518001l, 518002l, outCard, hCards, QZMJRule.ENABLE_CARDS);
		System.out.println("明杠:" + (null != mingGang) + " :" + mingGang.toString());
		playerInfo.addOptMsgs(mingGang);

		// 测试碰
		hCards = new String[] { MJConst.TIAO6, MJConst.TIAO6, MJConst.WAN9, MJConst.WAN9, MJConst.WAN2, MJConst.WAN2,
				MJConst.WAN3, MJConst.WAN3, MJConst.WAN3, MJConst.TIAO7, MJConst.TIAO7, MJConst.XI, MJConst.TIAO6,
				MJConst.DONG, MJConst.DONG, MJConst.XI };
		OptCheckMsg peng = MJCheckKit.checkPeng(518001l, 518002l, outCard, hCards, QZMJRule.ENABLE_CARDS);
		System.out.println("碰牌:" + (null != peng) + " :" + peng.toString());
		playerInfo.addOptMsgs(peng);

		// 测试补杠
		OptCheckMsg buGang = MJCheckKit.checkBuGang(518001l, 518002l, outCard);
		System.out.println("补杠:" + (null != buGang) + " :" + buGang.toString());

		// 测试吃牌
		hCards = new String[] { MJConst.TIAO5, MJConst.TIAO8, MJConst.WAN9, MJConst.WAN9, MJConst.WAN2, MJConst.WAN2,
				MJConst.WAN3, MJConst.WAN3, MJConst.WAN3, MJConst.TIAO7, MJConst.TIAO7, MJConst.XI, MJConst.TIAO6,
				MJConst.DONG, MJConst.DONG, MJConst.XI };
		List<OptCheckMsg> chiList = MJCheckKit.checkChi(518001l, 518002l, outCard, hCards, QZMJRule.ENABLE_CARDS);
		System.out.println("吃牌:" + (chiList.size() == 2) + " :" + chiList.toString());
	}

	private static boolean testHu(String[] hCards, String outCard, String goldCard) {
		String[] canTingCards = MJCheckKit.checkTingCards(hCards, QZMJRule.ENABLE_CARDS, goldCard);
		System.out.println("能胡de牌:" + Arrays.toString(canTingCards));

		boolean isCanHuOfCard = MJCheckKit.isCanHuOfCard(canTingCards, outCard);
		System.out.println(outCard + " 是否能胡牌:" + isCanHuOfCard);
		return isCanHuOfCard;
	}
}
