package com.uitrs.game.majiang.websockets;

import javax.websocket.SendHandler;
import javax.websocket.SendResult;

import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.utils.JsonUtils;

/**
 * 消息发送Handler
 * 
 * @author lucio
 *
 */
public class MJSendHandler implements SendHandler {
	private static Log log = Log.getLog(MJSendHandler.class);
	private static final MJSendHandler sendHandler = new MJSendHandler();

	public static MJSendHandler getHandle() {
		return sendHandler;
	}

	@Override
	public void onResult(SendResult result) {
		System.err.println("SendResult:: " + JsonUtils.toJson(result));
		if (log.isInfoEnabled()) {
			log.info("End Send Async Msg result is:[" + result.isOK() + "]");
		}
		if(!result.isOK())
		{
			
		}
	}

}
