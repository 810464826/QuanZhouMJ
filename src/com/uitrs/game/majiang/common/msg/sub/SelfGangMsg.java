package com.uitrs.game.majiang.common.msg.sub;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 特殊杠消息体 {operateName:'specialGang', from: '88989', operateCard: 'w3', cards:
 * ['w3', 'w4', 'w5'],childType: 'yaoGang',index:3}
 * 
 * @author lucio
 *
 */
public class SelfGangMsg extends AbstractMsg {
	private static final long serialVersionUID = -734536319657799664L;
	/** 特殊杠 */
	private String operateName;
	/** 索引 */
	private int index;
	/** 操作牌 */
	private String operateCard;
	/** 触发者 */
	private String from;
	/** 涉及牌 */
	private String[] cards;
	/** 子类型 */
	private String childType;

	public SelfGangMsg() {
		super();
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getOperateCard() {
		return operateCard;
	}

	public void setOperateCard(String operateCard) {
		this.operateCard = operateCard;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getCards() {
		return cards;
	}

	public void setCards(String[] cards) {
		this.cards = cards;
	}

	public String getChildType() {
		return childType;
	}

	public void setChildType(String childType) {
		this.childType = childType;
	}
}
