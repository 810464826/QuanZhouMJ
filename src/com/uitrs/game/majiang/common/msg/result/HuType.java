package com.uitrs.game.majiang.common.msg.result;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 胡牌类型
 * 
 * @author lucio
 *
 */
public class HuType extends AbstractMsg {
	private static final long serialVersionUID = 8840546585179573409L;
	/** 只要能胡牌，一定是 same_color | word_color | other_color 这三个值中的一种 */
	private String color;
	/** 只要能胡牌，一定是 0，1，2，3 这三个值中的一种(注意，这里只统计了还在手中的牌) */
	private int fourNum;
	/**
	 * 只要能胡牌，一定是 'thirty' | 'pingHu' | 'sevenPair' | 'pengPengHu' | 'jiaHu'
	 * 这五个值中的一种
	 */
	private String type;
	/** 胡的牌 */
	private String card;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getFourNum() {
		return fourNum;
	}

	public void setFourNum(int fourNum) {
		this.fourNum = fourNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}
}
