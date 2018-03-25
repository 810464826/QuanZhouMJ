package com.uitrs.game.majiang.common.msg.sub;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 补花数据
 * 
 * @author lucio
 *
 */
public class BuHuaMsg extends AbstractMsg {
	private static final long serialVersionUID = 5084099014303944073L;
	/** 谁补花 */
	private long who;
	/** 需要补花的花牌 */
	private String h;
	/** 补到的牌 */
	private String c;
	/** 剩余多少牌 */
	private int cardNum;

	public BuHuaMsg() {
		super();
	}

	public BuHuaMsg(long who, String h) {
		super();
		this.who = who;
		this.h = h;
	}

	public BuHuaMsg(String h) {
		super();
		this.h = h;
	}

	public BuHuaMsg(String h, String c) {
		super();
		this.h = h;
		this.c = c;
	}

	public long getWho() {
		return who;
	}

	public void setWho(long who) {
		this.who = who;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

}
