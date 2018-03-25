package com.uitrs.game.majiang.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.BuHuaMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;

public class COptShowService implements IClientService {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void dealService(SendMsg recevieMsg) {
		if (null != recevieMsg.getM() && recevieMsg.getM().toString().length() > 2) {
			List<Map> buHuaList = JsonUtils.parse(JsonUtils.toJson(recevieMsg.getM()), ArrayList.class);
			System.out.println("补花:" + buHuaList);
			for (Map buHuaMsg : buHuaList) {
				BuHuaMsg msg = JsonUtils.parse(JsonUtils.toJson(buHuaMsg), BuHuaMsg.class);
				System.out.println("花:" + msg);
				CommonSevice.getInstance().getFrame().getjTextFieldHua().setText(CommonSevice.getInstance().getFrame().getjTextFieldHua().getText()+","+msg.getH());
			}
		}
		if (null != recevieMsg.getE() && recevieMsg.getE().toString().length() > 2) {
			List<Map> optMsgList = JsonUtils.parse(JsonUtils.toJson(recevieMsg.getE()), ArrayList.class);
			System.out.println("操作:" + optMsgList);
			for (Map optMsg : optMsgList) {
				OptCheckMsg msg = JsonUtils.parse(JsonUtils.toJson(optMsg), OptCheckMsg.class);
				System.out.println("OptCheckMsg:" + msg);
				CommonSevice.getInstance().getFrame().getjComboBoxHand().addItem(msg.getC().trim());
				for (int i = 0; i < msg.getM().length; i++) {
					CommonSevice.getInstance().getFrame().getjTextFieldOpts().setText(CommonSevice.getInstance().getFrame().getjTextFieldOpts().getText()+","+msg.getM()[i].trim());
					CommonSevice.getInstance().getFrame().getjComboBoxHand().addItem(msg.getM()[i].trim());
				}
			}
		}
	}
}
