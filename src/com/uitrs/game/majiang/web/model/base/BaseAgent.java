package com.uitrs.game.majiang.web.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 管理员列表
 */
@SuppressWarnings("serial")
public abstract class BaseAgent<M extends BaseAgent<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}
	
	public void setBili(java.lang.Double bili) {
		set("bili", bili);
	}

	public java.lang.Double getBili() {
		return get("bili");
	}
}
