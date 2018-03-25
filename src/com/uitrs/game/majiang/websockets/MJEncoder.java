package com.uitrs.game.majiang.websockets;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;

import com.uitrs.game.majiang.common.msg.SendMsg;

public class MJEncoder implements javax.websocket.Encoder.Text<SendMsg> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String encode(SendMsg msg) throws EncodeException {
		System.err.println("----encode: "+msg);
		if (null == msg) {
			return "";
		}
		return msg.toJson();
	}
}
