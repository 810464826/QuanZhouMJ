package com.uitrs.game.majiang.web.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 管理员列表
 */
@SuppressWarnings("serial")
public abstract class BaseAdminInfo<M extends BaseAdminInfo<M>> extends Model<M> implements IBean {

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

	public void setHeadIcon(java.lang.String headIcon) {
		set("headIcon", headIcon);
	}

	public java.lang.String getHeadIcon() {
		return get("headIcon");
	}

	public void setSysType(java.lang.Integer sysType) {
		set("sysType", sysType);
	}

	public java.lang.Integer getSysType() {
		return get("sysType");
	}

	public void setNickName(java.lang.String nickName) {
		set("nickName", nickName);
	}

	public java.lang.String getNickName() {
		return get("nickName");
	}

	public void setPassword(java.lang.String password) {
		set("password", password);
	}

	public java.lang.String getPassword() {
		return get("password");
	}

	public void setTelephone(java.lang.String telephone) {
		set("telephone", telephone);
	}

	public java.lang.String getTelephone() {
		return get("telephone");
	}

	public void setLoginTime(java.lang.String loginTime) {
		set("loginTime", loginTime);
	}

	public java.lang.String getLoginTime() {
		return get("loginTime");
	}

	public void setTotalCards(java.lang.Integer totalCards) {
		set("totalCards", totalCards);
	}

	public java.lang.Integer getTotalCards() {
		return get("totalCards");
	}

	public void setSurplusCards(java.lang.Integer surplusCards) {
		set("surplusCards", surplusCards);
	}

	public java.lang.Integer getSurplusCards() {
		return get("surplusCards");
	}
	
	public void setGamerId(java.lang.Integer gamerId) {
		set("gamerId", gamerId);
	}

	public java.lang.Integer getGamerId() {
		return get("gamerId");
	}
}
