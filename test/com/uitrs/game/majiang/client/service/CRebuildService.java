package com.uitrs.game.majiang.client.service;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReBuildMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;

public class CRebuildService implements IClientService {

	@Override
	public void dealService(SendMsg recevieMsg) {
		
		ReBuildMsg msg = JsonUtils.parse(JsonUtils.toJson(recevieMsg.getM()), ReBuildMsg.class);
		
		String p = msg.getP();
		System.err.println("p:"+p);
		for (int i = 0; i < msg.getPlayers().size(); i++) {
			PlayerInfo playerInfo = msg.getPlayers().get(i);
			
			System.err.println("playerInfo:"+playerInfo.getUserInfo());
			if (playerInfo.getUserInfo().getOpenId().equalsIgnoreCase(p)) {
				System.err.println("equalsIgnoreCase"+p);
				CommonSevice.getInstance().setPlayer(playerInfo);
				CommonSevice.getInstance().refreshCards(playerInfo);
				CommonSevice.getInstance().getFrame().setTitle("麻将玩家:"+ playerInfo.getUserInfo().getOpenId());
			}
		}
	}
}
