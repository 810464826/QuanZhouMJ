package com.uitrs.game.majiang.common.msg.sub;

import java.util.List;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 检查花牌消息,起手牌和拿牌时触发
 * 
 * @author lucio
 *
 */
public class CheckHuaMsg extends AbstractMsg {
	private static final long serialVersionUID = 5508569275992690069L;
	/** 玩家ID */
	private long p;
	/** 补花信息 */
	private List<BuHuaMsg> h;
	/** 起手牌或者拿的牌 */
	private String[] c;
	/** 金牌 */
	private String gc;

	public CheckHuaMsg() {
		super();
	}

	public CheckHuaMsg(long p) {
		super();
		this.p = p;
	}

	public CheckHuaMsg(long p, List<BuHuaMsg> h, String[] c) {
		super();
		this.p = p;
		this.h = h;
		this.c = c;
	}

	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}

	public List<BuHuaMsg> getH() {
		return h;
	}

	public void setH(List<BuHuaMsg> h) {
		this.h = h;
	}

	public String[] getC() {
		return c;
	}

	public void setC(String[] c) {
		this.c = c;
	}

	public String getGc() {
		return gc;
	}

	public void setGc(String gc) {
		this.gc = gc;
	}

}
