package com.uitrs.game.majiang.client.service;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.JoinMsg;

public class CCreateService implements IClientService {

	@Override
	public void dealService(SendMsg recevieMsg) {
		JoinMsg joinMsg = JsonUtils.parse(JsonUtils.toJson(recevieMsg.getM()), JoinMsg.class);
		CommonSevice.getInstance().getPlayer().init(joinMsg.getRoomId(), joinMsg.getMeOrder());
	}
}
