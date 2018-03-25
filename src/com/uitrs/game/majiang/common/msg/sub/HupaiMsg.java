package com.uitrs.game.majiang.common.msg.sub;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 每局胡牌4个人的结果消息
 * 
 * @author lucio
 *
 */
public class HupaiMsg extends AbstractMsg {

	private static final long serialVersionUID = -6196870391947354616L;

	private int normalNum;

	public int getNormalNum() {
		return normalNum;
	}

	public void setNormalNum(int normalNum) {
		this.normalNum = normalNum;
	}

	/** 每局胡牌4个人的结果 */
	private List<JuResult> juResults = new ArrayList<JuResult>();

	public List<JuResult> getJuResults() {
		return juResults;
	}

	public void addJuResult(JuResult juResults) {
		this.juResults.add(juResults);
	}

	@Override
	public String toJson() {
		return JsonUtils.toJson(this.juResults);
	}
}
