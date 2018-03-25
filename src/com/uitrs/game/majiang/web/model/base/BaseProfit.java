package com.uitrs.game.majiang.web.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 管理员列表
 */
@SuppressWarnings("serial")
public abstract class BaseProfit<M extends BaseProfit<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}
	
	public void setWinnerId(java.lang.Integer winnerId) {
		set("winnerId", winnerId);
	}

	public java.lang.Integer getWinnerId() {
		return get("winnerId");
	}
	
	public void setProfit(java.lang.Double profit) {
		set("profit", profit);
	}

	public java.lang.Double getProfit() {
		return get("profit");
	}
}
