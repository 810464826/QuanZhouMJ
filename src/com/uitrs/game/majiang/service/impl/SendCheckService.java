package com.uitrs.game.majiang.service.impl;

import java.util.List;

import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 处理发出去的消息收到回复消息
 * 
 * @author lucio
 *
 */
public class SendCheckService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		long userId = action.getP();
		List<?> msgId = JsonUtils.parse(JsonUtils.toJson(action.getM()), List.class);
		for (Object object : msgId) {
			MJCache.getCache().removeSendCheckMsg(Long.parseLong(object.toString()), userId);
		}
	}
}
