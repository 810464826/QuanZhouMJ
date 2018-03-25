package com.uitrs.game.majiang.web.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 玩家列表
 */
@SuppressWarnings("serial")
public abstract class BaseGamerInfo<M extends BaseGamerInfo<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setOpenId(java.lang.String openId) {
		set("openId", openId);
	}

	public java.lang.String getOpenId() {
		return get("openId");
	}

	public void setNickName(java.lang.String nickName) {
		set("nickName", nickName);
	}

	public java.lang.String getNickName() {
		return get("nickName");
	}

	public void setHeadFace(java.lang.String headFace) {
		set("headFace", headFace);
	}

	public java.lang.String getHeadFace() {
		return get("headFace");
	}

	public void setSex(java.lang.Integer sex) {
		set("sex", sex);
	}

	public java.lang.String getIp() {
		return get("ip");
	}

	public void setIp(java.lang.String ip) {
		set("ip", ip);
	}

	public java.lang.Integer getSex() {
		return get("sex");
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

	public void setTelephone(java.lang.String telephone) {
		set("telephone", telephone);
	}

	public java.lang.String getTelephone() {
		return get("telephone");
	}

	public void setQqNum(java.lang.String qqNum) {
		set("qqNum", qqNum);
	}

	public java.lang.String getQqNum() {
		return get("qqNum");
	}

	public void setEnableLogin(java.lang.Integer enableLogin) {
		set("enableLogin", enableLogin);
	}

	public java.lang.Integer getEnableLogin() {
		return get("enableLogin");
	}

	public void setPassword(java.lang.String password) {
		set("password", password);
	}

	public java.lang.String getPassword() {
		return get("password");
	}

	public void setParentId(java.lang.Integer parentId) {
		set("parentId", parentId);
	}

	public java.lang.Integer getParentId() {
		return get("parentId");
	}

	public void setShareNum(java.lang.Integer shareNum) {
		set("shareNum", shareNum);
	}

	public java.lang.Integer getShareNum() {
		return get("shareNum");
	}

	public void setShareTime(java.lang.String shareTime) {
		set("shareTime", shareTime);
	}

	public java.lang.String getShareTime() {
		return get("shareTime");
	}
}
