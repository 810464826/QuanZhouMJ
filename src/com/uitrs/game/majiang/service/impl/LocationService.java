package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.LocationMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.websockets.WebSocketUtil;

/**
 * 上报客户端位置
 * @author lucio
 *
 */
public class LocationService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		long userId = action.getP();
		LocationMsg location = JsonUtils.parse(JsonUtils.toJson(action.getM()), LocationMsg.class);
		UserInfo userInfo = MJCache.getCache().getPlayerInfo(userId).getUserInfo();
		userInfo.setLat(location.getLat());
		userInfo.setLon(location.getLon());
		
		//回消息
		SendMsg result = new SendMsg(userId, true, action.getT());
		result.setM(action);
		WebSocketUtil.sendAsyncMsg(result, session);
	}

}
