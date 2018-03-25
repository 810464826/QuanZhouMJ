package com.uitrs.game.majiang.common.msg;

/**
 * 回执消息
 * 
 * @author lucio
 *
 */
public class SendMsg extends AbstractMsg {
	private static final long serialVersionUID = 3448327503559691798L;
	private long p;
	/** 错误信息或者附带消息,必须是可序列化的 */
	private Object e;
	/** 返回实体,必须是可序列化的 */
	private Object m;
	/** 是否成功 */
	private Boolean s;
	/** 类型 */
	private int t;
	/** 消息编号 */
	private long i;

	public SendMsg() {
		super();
	}

	public SendMsg(long p) {
		super();
		this.p = p;
	}

	public SendMsg(long p, Boolean s) {
		super();
		this.p = p;
		this.s = s;
	}

	public SendMsg(long p, Boolean s, int t) {
		super();
		this.p = p;
		this.s = s;
		this.t = t;
	}

	public SendMsg(long p, Boolean s, int t, long i) {
		super();
		this.p = p;
		this.s = s;
		this.t = t;
		this.i = i;
	}

	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}

	public Object getE() {
		return e;
	}

	public void setE(Object e) {
		this.e = e;
	}

	public Object getM() {
		return m;
	}

	public void setM(Object m) {
		this.m = m;
	}

	public Boolean getS() {
		return s;
	}

	public void setS(Boolean s) {
		this.s = s;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public long getI() {
		return i;
	}

	public void setI(long i) {
		this.i = i;
	}

}
