package com.uitrs.game.majiang.common.msg.sub;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 要发送给客户端的操作消息
 * 
 * @author lucio
 *
 */
public class OptSendMsg extends AbstractMsg {
	private static final long serialVersionUID = -1078473182795806256L;
	/** 触发操作的牌 */
	private String c;
	/** 操作的用户 */
	private long p;
	/** 操作类型 */
	private int t;
	/** 触发者 */
	private long from;

	public OptSendMsg() {
		super();
	}

	public OptSendMsg(long p, int t) {
		super();
		this.p = p;
		this.t = t;
	}

	public OptSendMsg(String c, long p, int t, long from) {
		super();
		this.c = c;
		this.p = p;
		this.t = t;
		this.from = from;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

}
