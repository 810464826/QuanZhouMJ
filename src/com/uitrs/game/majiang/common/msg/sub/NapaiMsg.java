package com.uitrs.game.majiang.common.msg.sub;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 拿牌消息
 * 
 * @author lucio
 *
 */
public class NapaiMsg extends AbstractMsg {

	private static final long serialVersionUID = 4915468906965437368L;
	/** 谁拿牌 */
	private long who;
	/** 发的什么牌 */
	private String c;
	/** 剩余多少牌 */
	private int cardNum;

	public NapaiMsg() {
		super();
	}

	public NapaiMsg(long who) {
		super();
		this.who = who;
	}

	public NapaiMsg(long who, String card) {
		super();
		this.who = who;
		this.c = card;
	}

	public NapaiMsg(long who, int cardNum) {
		super();
		this.who = who;
		this.cardNum = cardNum;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public long getWho() {
		return who;
	}

	public void setWho(long who) {
		this.who = who;
	}

	public String getC() {
		return c;
	}

	public void setC(String card) {
		this.c = card;
	}
}
