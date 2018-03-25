package com.uitrs.game.majiang.client.service;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;

public class CLoginService implements IClientService {

	@Override
	public void dealService(SendMsg recevieMsg) {
		UserInfo ui = JsonUtils.parse(JsonUtils.toJson(recevieMsg.getM()), UserInfo.class);
		CommonSevice.getInstance().getFrame().setTitle("麻将玩家:"+ ui.getOpenId());
	}
}
