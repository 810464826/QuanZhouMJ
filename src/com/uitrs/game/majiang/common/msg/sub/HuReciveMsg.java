package com.uitrs.game.majiang.common.msg.sub;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 收到客户端发来的胡牌消息格式
 * 
 * @author lucio
 *
 */
public class HuReciveMsg extends AbstractMsg {
	private static final long serialVersionUID = -6557593990069565642L;
	private String huCard;
	private long from;
	private int type;

	public HuReciveMsg() {
		super();
	}

	public HuReciveMsg(long from, int type) {
		super();
		this.from = from;
		this.type = type;
	}

	public HuReciveMsg(String huCard, long from, int type) {
		super();
		this.huCard = huCard;
		this.from = from;
		this.type = type;
	}

	public String getHuCard() {
		return huCard;
	}

	public void setHuCard(String huCard) {
		this.huCard = huCard;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
