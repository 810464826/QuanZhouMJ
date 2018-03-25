package com.uitrs.game.majiang.common.msg;

/**
 * socket接收消息
 * 
 * @author lucio
 *
 */
public class ReceiveMsg extends AbstractMsg {
	private static final long serialVersionUID = -9141578273016337556L;
	/** 消息类型 */
	private int t;
	/** 消息实体 */
	private Object m;
	/** 玩家ID */
	private long p;

	public ReceiveMsg() {
		super();
	}

	public ReceiveMsg(int t) {
		super();
		this.t = t;
	}

	public ReceiveMsg(int t, long p) {
		super();
		this.t = t;
		this.p = p;
	}

	public ReceiveMsg(int t, Object m, long p) {
		super();
		this.t = t;
		this.m = m;
		this.p = p;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public Object getM() {
		return m;
	}

	public void setM(Object m) {
		this.m = m;
	}

	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}
}
