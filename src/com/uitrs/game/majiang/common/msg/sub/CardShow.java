package com.uitrs.game.majiang.common.msg.sub;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 触发的操作信息(吃,碰,杠)
 * 
 * @author lucio
 *
 */
public class CardShow extends AbstractMsg {
	private static final long serialVersionUID = -1690032466148434885L;
	/** 触发牌 */
	private String c;
	/** 类型,碰,明杠,暗杠,自杠 */
	private int t;
	/** 谁的 */
	private String f;
	/** 相关的牌 */
	private String[] m;

	public CardShow() {
		super();
	}

	public CardShow(String c, int t) {
		super();
		this.c = c;
		this.t = t;
	}

	public CardShow(String c, int t, String f) {
		super();
		this.c = c;
		this.t = t;
		this.f = f;
	}

	public CardShow(String c, int t, String f, String[] m) {
		super();
		this.c = c;
		this.t = t;
		this.f = f;
		this.m = m;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String[] getM() {
		return m;
	}

	public void setM(String[] m) {
		this.m = m;
	}

}
