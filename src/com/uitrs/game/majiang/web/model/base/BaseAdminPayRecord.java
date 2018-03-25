package com.uitrs.game.majiang.web.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 代理商购买记录列表
 */
@SuppressWarnings("serial")
public abstract class BaseAdminPayRecord<M extends BaseAdminPayRecord<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setAdminId(java.lang.Integer adminId) {
		set("adminId", adminId);
	}

	public java.lang.Integer getAdminId() {
		return get("adminId");
	}

	public void setPayTime(java.lang.String payTime) {
		set("payTime", payTime);
	}

	public java.lang.String getPayTime() {
		return get("payTime");
	}

	public void setPayMoney(java.lang.Double payMoney) {
		set("payMoney", payMoney);
	}

	public java.lang.Double getPayMoney() {
		return get("payMoney");
	}

	public void setState(java.lang.Integer state) {
		set("state", state);
	}

	public java.lang.Integer getState() {
		return get("state");
	}

	public void setPayType(java.lang.String payType) {
		set("payType", payType);
	}

	public java.lang.String getPayType() {
		return get("payType");
	}
}
