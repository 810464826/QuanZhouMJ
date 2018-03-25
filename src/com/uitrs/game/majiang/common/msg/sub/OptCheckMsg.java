package com.uitrs.game.majiang.common.msg.sub;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 吃碰杠胡等操作检查消息,判断优先级,胡 > 碰/杠 > 吃
 * 
 * message: {t:57, m: '{t:52, c: 'w3'}', 'p': '10120'}
 * 
 * 如果是吃： {t:'chi', c: 'w3', m: ['w3', 'w4', 'w5']}
 * 
 * @author lucio
 *
 */
public class OptCheckMsg extends AbstractMsg {
	private static final long serialVersionUID = -734536319657799664L;
	/** 操作者 */
	private long p;
	/** 操作类型 */
	private int t;
	/** 操作牌 */
	private String c;
	/** 触发者 */
	private long from;
	/** 涉及牌 */
	private String[] m;

	public OptCheckMsg() {
		super();
	}

	public OptCheckMsg(int t) {
		super();
		this.t = t;
	}

	public OptCheckMsg(int t, String c) {
		super();
		this.t = t;
		this.c = c;
	}

	public OptCheckMsg(long p, int t, String c) {
		super();
		this.p = p;
		this.t = t;
		this.c = c;
	}

	public OptCheckMsg(long p, int t, String c, String[] m) {
		super();
		this.p = p;
		this.t = t;
		this.c = c;
		this.m = m;
	}

	public OptCheckMsg(int t, String c, String[] m) {
		super();
		this.t = t;
		this.c = c;
		this.m = m;
	}

	public OptCheckMsg(long p, int t, String c, long from) {
		super();
		this.p = p;
		this.t = t;
		this.c = c;
		this.from = from;
	}

	public OptCheckMsg(long p, int t, String c, long from, String[] m) {
		super();
		this.p = p;
		this.t = t;
		this.c = c;
		this.from = from;
		this.m = m;
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

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String[] getM() {
		return m;
	}

	public void setM(String[] m) {
		this.m = m;
	}

	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}
}
