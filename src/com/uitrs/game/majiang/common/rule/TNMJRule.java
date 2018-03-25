package com.uitrs.game.majiang.common.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.PropKit;
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.NRandom;
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
public class TNMJRule extends MJRule {

	private static final long serialVersionUID = -1727491163007049041L;
	private String[] roomCards;
	/**
	 * 麻将规则名称
	 */
	public static final String RULE_NAME = "tnmj";
	/** 规则内所有可以打的牌 */
	private static final String[] CARDS = new String[] { MJConst.ZHONG, MJConst.FA, MJConst.BAI, MJConst.WAN1,
			MJConst.WAN2, MJConst.WAN3, MJConst.WAN4, MJConst.WAN5, MJConst.WAN6, MJConst.WAN7, MJConst.WAN8,
			MJConst.WAN9, MJConst.TIAO1, MJConst.TIAO2, MJConst.TIAO3, MJConst.TIAO4, MJConst.TIAO5, MJConst.TIAO6,
			MJConst.TIAO7, MJConst.TIAO8, MJConst.TIAO9, MJConst.TONG1, MJConst.TONG2, MJConst.TONG3, MJConst.TONG4,
			MJConst.TONG5, MJConst.TONG6, MJConst.TONG7, MJConst.TONG8, MJConst.TONG9 };
	// 固定排序
	private static List<String> cardList = new ArrayList<String>(CARDS.length * 4);

	static {
		for (int i = 0; i < MJConst.TOTAL; i++) {
			for (int j = 0; j < CARDS.length; j++) {
				cardList.add(CARDS[j]);
			}
		}
	}

	@Override
	public String[] enablePais() {
		return CARDS;
	}

	@Override
	public String[] createPais() {
		String[] result = new String[CARDS.length * 4];
		if (PropKit.getBoolean("devMode", false) && !StringUtils.isEmpty(PropKit.get("cards"))) {
			roomCards = PropKit.get("cards").split(",");
		} else {
			roomCards = NRandom.sequence(cardList.toArray(result));
		}
		return roomCards;
	}

	@Override
	public Set<String> checkTingCards(Room room, PlayerInfo playerInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OptCheckMsg> checkOutOpts(Room room, PlayerInfo operator, long from, String outCard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createRoom(int juNum,int[] opts, PlayerInfo createInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkSelf(Room room, PlayerInfo playerInfo, String faPai, SendMsg selfMsg) {
		// TODO Auto-generated method stub
		
	}
}
