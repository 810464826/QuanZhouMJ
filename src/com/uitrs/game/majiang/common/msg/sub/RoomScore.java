package com.uitrs.game.majiang.common.msg.sub;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.AbstractMsg;

public class RoomScore extends AbstractMsg {
	private static final long serialVersionUID = 7361123469230094651L;
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
