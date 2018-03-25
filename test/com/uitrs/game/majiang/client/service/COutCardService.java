package com.uitrs.game.majiang.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;

public class COutCardService implements IClientService {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void dealService(SendMsg recevieMsg) {
		if (null != recevieMsg.getM() && recevieMsg.getM().toString().length() > 2) {
			ReceiveMsg outCardMsg = JsonUtils.parse(JsonUtils.toJson(recevieMsg.getM()), ReceiveMsg.class);
			System.out.println("OutCard msg:" + outCardMsg);
			// MJClient.frame.getjTextFieldHua().setText(MJClient.frame.getjTextFieldHua().getText()+","+msg.getH());
		}
		if (null != recevieMsg.getE() && recevieMsg.getE().toString().length() > 2) {
			List<Map> optMsgList = JsonUtils.parse(JsonUtils.toJson(recevieMsg.getE()), ArrayList.class);
			System.out.println("操作:" + optMsgList);
			for (Map optMsg : optMsgList) {
				OptCheckMsg msg = JsonUtils.parse(JsonUtils.toJson(optMsg), OptCheckMsg.class);
				System.out.println("OptCheckMsg:" + msg);
				CommonSevice.getInstance().getFrame().getjComboBoxHand().addItem(msg.getC().trim());
				CommonSevice.getInstance().getFrame().getjTextFieldOpts()
						.setText(CommonSevice.getInstance().getFrame().getjTextFieldOpts().getText() + "," + msg.getC().trim());
			}
		}
	}
}
