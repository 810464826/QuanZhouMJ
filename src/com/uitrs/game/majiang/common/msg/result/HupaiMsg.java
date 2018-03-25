package com.uitrs.game.majiang.common.msg.result;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.AbstractMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;

/**
 * 每局胡牌4个人的结果消息
 * 
 * @author lucio
 *
 */
public class HupaiMsg extends AbstractMsg {

	private static final long serialVersionUID = -6196870391947354616L;

	// 当前总局数
	private int totalJuNum;
	// 已经使用的局数
	private int useJuNum;
	// 当前总圈数
	private int totalQuanNum;
	// 已经使用的圈数
	private int useQuanNum;
	// 庄家userId
	private long zhuangId;

	/** 每局胡牌4个人的结果 */
	private List<PlayerInfo> juResults = new ArrayList<PlayerInfo>();

	public List<PlayerInfo> getJuResults() {
		return juResults;
	}

	public void addJuResult(PlayerInfo juResults) {
		this.juResults.add(juResults);
	}

	public int getTotalJuNum() {
		return totalJuNum;
	}

	public void setTotalJuNum(int totalJuNum) {
		this.totalJuNum = totalJuNum;
	}

	public int getUseJuNum() {
		return useJuNum;
	}

	public void setUseJuNum(int useJuNum) {
		this.useJuNum = useJuNum;
	}

	public int getTotalQuanNum() {
		return totalQuanNum;
	}

	public void setTotalQuanNum(int totalQuanNum) {
		this.totalQuanNum = totalQuanNum;
	}

	public int getUseQuanNum() {
		return useQuanNum;
	}

	public void setUseQuanNum(int useQuanNum) {
		this.useQuanNum = useQuanNum;
	}

	public long getZhuangId() {
		return zhuangId;
	}

	public void setZhuangId(long zhuangId) {
		this.zhuangId = zhuangId;
	}

	@Override
	public String toJson() {
		return JsonUtils.toJson(this.juResults);
	}
}
