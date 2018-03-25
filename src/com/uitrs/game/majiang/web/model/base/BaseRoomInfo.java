package com.uitrs.game.majiang.web.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 管理员列表
 */
@SuppressWarnings("serial")
public abstract class BaseRoomInfo<M extends BaseRoomInfo<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setRoomId(java.lang.Integer roomId) {
		set("roomId", roomId);
	}

	public java.lang.Integer getRoomId() {
		return get("roomId");
	}

	public void setGamerOneId(java.lang.Integer gamerOneId) {
		set("gamerOneId", gamerOneId);
	}

	public java.lang.Integer getGamerOneId() {
		return get("gamerOneId");
	}

	public void setGamerTwoId(java.lang.Integer gamerTwoId) {
		set("gamerTwoId", gamerTwoId);
	}

	public java.lang.Integer getGamerTwoId() {
		return get("gamerTwoId");
	}

	public void setGamerThreeId(java.lang.Integer gamerThreeId) {
		set("gamerThreeId", gamerThreeId);
	}

	public java.lang.Integer getGamerThreeId() {
		return get("gamerThreeId");
	}

	public void setGamerFourId(java.lang.Integer gamerFourId) {
		set("gamerFourId", gamerFourId);
	}

	public java.lang.Integer getGamerFourId() {
		return get("gamerFourId");
	}

	public void setCreateTime(java.lang.String createTime) {
		set("createTime", createTime);
	}

	public java.lang.String getCreateTime() {
		return get("createTime");
	}

	public void setEndTime(java.lang.String endTime) {
		set("endTime", endTime);
	}

	public java.lang.String getEndTime() {
		return get("endTime");
	}

	public void setUseRounds(java.lang.Integer useRounds) {
		set("useRounds", useRounds);
	}

	public java.lang.Integer getUseRounds() {
		return get("useRounds");
	}

	public void setTotalRounds(java.lang.Integer totalRounds) {
		set("totalRounds", totalRounds);
	}

	public java.lang.Integer getTotalRounds() {
		return get("totalRounds");
	}
}
