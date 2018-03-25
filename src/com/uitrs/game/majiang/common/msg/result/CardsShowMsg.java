package com.uitrs.game.majiang.common.msg.result;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 每局胡牌的时候包含的类型,如旋风杠,白八九杠等
 * 
 * @author lucio
 *
 */
public class CardsShowMsg extends AbstractMsg {
	private static final long serialVersionUID = -4724571448240648668L;
	/** 谁点的 */
	private long from;
	/** 子类型,如旋风杠,白八九杠等 */
	private String childType;
	/** 具体的牌 */
	private String[] cards;

	public CardsShowMsg() {
		super();
	}

	public CardsShowMsg(long from) {
		super();
		this.from = from;
	}

	public CardsShowMsg(long from, String childType, String[] cards) {
		super();
		this.from = from;
		this.childType = childType;
		this.cards = cards;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public String getChildType() {
		return childType;
	}

	public void setChildType(String childType) {
		this.childType = childType;
	}

	public String[] getCards() {
		return cards;
	}

	public void setCards(String[] cards) {
		this.cards = cards;
	}

}
