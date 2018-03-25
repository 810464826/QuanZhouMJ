package com.uitrs.game.majiang.common.msg.sub;

import java.util.Set;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 客户端出牌消息
 * 
 * @author lucio
 *
 */
public class OutCardMsg extends AbstractMsg {
	private static final long serialVersionUID = 2948078014299002476L;

	/** 出的牌 */
	private String oc;
	/** 听的牌集合 */
	private Set<String> tc;

	public OutCardMsg() {
		super();
	}

	public OutCardMsg(String oc) {
		super();
		this.oc = oc;
	}

	public String getOc() {
		return oc;
	}

	public void setOc(String oc) {
		this.oc = oc;
	}

	public Set<String> getTc() {
		return tc;
	}

	public void setTc(Set<String> tc) {
		this.tc = tc;
	}

}
