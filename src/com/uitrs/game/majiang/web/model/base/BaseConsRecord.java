package com.uitrs.game.majiang.web.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 消费记录表
 */
@SuppressWarnings("serial")
public abstract class BaseConsRecord<M extends BaseConsRecord<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setConsType(java.lang.String consType) {
		set("consType", consType);
	}

	public java.lang.String getConsType() {
		return get("consType");
	}

	public void setConsTime(java.lang.String consTime) {
		set("consTime", consTime);
	}

	public java.lang.String getConsTime() {
		return get("consTime");
	}

	public void setConsMoney(java.lang.Double consMoney) {
		set("consMoney", consMoney);
	}

	public java.lang.Double getConsMoney() {
		return get("consMoney");
	}

}
