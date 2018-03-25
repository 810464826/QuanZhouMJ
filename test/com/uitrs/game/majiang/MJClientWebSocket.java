/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.uitrs.game.majiang;

import java.util.Map;

import javax.swing.JOptionPane;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.client.service.CommonSevice;
import com.uitrs.game.majiang.client.service.IClientService;
import com.uitrs.game.majiang.client.service.SuperClientService;
import com.uitrs.game.majiang.common.kit.MJUtil;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;

/**
 *
 * @author lucio
 */
@ClientEndpoint
public class MJClientWebSocket {

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Connected to endpoint: " + session.getId());
	}

	@OnMessage
	public void onMessage(String message) {
		String string = "";
		SendMsg recevieMsg = buildMsg(message);
		UserInfo ui = CommonSevice.getInstance().getUi();
		if (null == ui) {
			string = "收到类型为登录的消息: " + message;

		} else if (recevieMsg.getT() == IMsg.LOGIN_TYPE) {
			ui = JsonUtils.parse(JsonUtils.toJson(recevieMsg.getM()), UserInfo.class);
			CommonSevice.getInstance().setUi(ui);
			string = "收到类型为登录的消息: " + message;
		} else {
			string = ui.getOpenId() + "::t:" + recevieMsg.getT() + ",收到类型为:" + MJUtil.convertOptType(recevieMsg.getT())
					+ " 的消息: " + message;
		}
		System.out.println(string);
		MainJFrame frame = CommonSevice.getInstance().getFrame();
		frame.getjEditorPaneReceive().setText(frame.getjEditorPaneReceive().getText() + "\n" + string);
		if (!recevieMsg.getS()) {
			JOptionPane.showMessageDialog(frame, recevieMsg.getE(), "出错了", JOptionPane.WARNING_MESSAGE);
		}

		IClientService clientService = SuperClientService.getClientService(recevieMsg.getT());
		if (null != clientService) {
			clientService.dealService(recevieMsg);
		}
	}

	private SendMsg buildMsg(String message) {
		@SuppressWarnings("unchecked")
		Map<String, Object> recevieMap = JsonUtils.parse(message, Map.class);
		SendMsg sendMsg = new SendMsg(Long.parseLong(recevieMap.get("p").toString()),
				Boolean.parseBoolean(recevieMap.get("s").toString()), Integer.parseInt(recevieMap.get("t").toString()));
		sendMsg.setE(null != recevieMap.get("e") ? recevieMap.get("e").toString() : null);
		sendMsg.setM(recevieMap.get("m"));
		return sendMsg;
	}

	@OnError
	public void onError(Throwable t) {
		t.printStackTrace();
	}
}
