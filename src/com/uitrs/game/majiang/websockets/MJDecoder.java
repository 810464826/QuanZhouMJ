package com.uitrs.game.majiang.websockets;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;

public class MJDecoder implements Decoder.Text<ReceiveMsg> {

	@Override
	public void destroy() {

	}

	@Override
	public void init(EndpointConfig config) {

	}

	@Override
	public ReceiveMsg decode(String message) throws DecodeException {
		System.err.println("----decode: "+message);
		try {
			// 解析消息,根据type类型判断具体动作
			return JsonUtils.parse(message, ReceiveMsg.class);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean willDecode(String arg0) {
		return true;
	}

}
