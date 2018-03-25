package com.uitrs.game.majiang.client.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.BuHuaMsg;
import com.uitrs.game.majiang.common.msg.sub.CheckHuaMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;

public class CStartService implements IClientService {

	@SuppressWarnings("unchecked")
	@Override
	public void dealService(SendMsg recevieMsg) {
		System.err.println("recevieMsg.getM: " + recevieMsg.getM());
		List<Map<?, ?>> list = JsonUtils.parse(JsonUtils.toJson(recevieMsg.getM()), ArrayList.class);
		for (Map<?, ?> map : list) {
			String json = JsonUtils.toJson(map);
			CheckHuaMsg checkHuaMsg = JsonUtils.parse(json, CheckHuaMsg.class);
			System.err.println("checkHuaMsg:" + checkHuaMsg.toJson());
			if (checkHuaMsg.getP() == CommonSevice.getInstance().getUi().getUserId()) {
				JTextField textFieldHand = CommonSevice.getInstance().getFrame().getjTextFieldHand();
				String sss = Arrays.toString(checkHuaMsg.getC());
				textFieldHand.setText(sss.substring(1, sss.length() - 1));
				List<BuHuaMsg> huaList = checkHuaMsg.getH();
				String huaStr = "";
				for (int i = 0; i < huaList.size(); i++) {
					huaStr = huaStr + (i == 0 ? "" : ",") + huaList.get(i).getH();
				}
				CommonSevice.getInstance().getFrame().getjTextFieldHua().setText(huaStr);

				Object opts = recevieMsg.getE();
				if (null != opts) {
					String optStr = "";
					List<OptCheckMsg> optList = JsonUtils.parse(JsonUtils.toJson(opts), List.class);
					for (int i = 0; i < optList.size(); i++) {
						optStr = optStr + (i == 0 ? "" : ",") + optList.get(i).getC() + optList.get(i).getM();
					}
					CommonSevice.getInstance().getFrame().getjTextFieldOpts().setText(optStr);
				}
				// Arrays.sort(hands);
				List<String> handCards = new ArrayList<String>(Arrays.asList(checkHuaMsg.getC()));
				// Collections.sort(handCards);
				CommonSevice.getInstance().getPlayer().setHandCards(handCards);
				CommonSevice.getInstance().getFrame().getjComboBoxHand()
						.setModel(new DefaultComboBoxModel<String>(checkHuaMsg.getC()));
				return;
			}
		}
		// Object cards = m.get("cards");
		// Object hua = m.get("hua");
		// Object opts = recevieMsg.getE();
		//
		// JTextField textFieldHand =
		// CommonSevice.getInstance().getFrame().getjTextFieldHand();
		// textFieldHand.setText(cards.toString().substring(1,
		// cards.toString().length() - 1));
		// String[] hands = textFieldHand.getText().split(",");
		// for (int j = 0; j < hands.length; j++) {
		// hands[j] = hands[j].trim();
		// }
		//
		// if (null != hua) {
		// String huaStr = "";
		// @SuppressWarnings("unchecked")
		// List<BuHuaMsg> huaList = JsonUtils.parse(JsonUtils.toJson(hua),
		// List.class);
		// for (int i = 0; i < huaList.size(); i++) {
		// huaStr = huaStr + (i == 0 ? "" : ",") + huaList.get(i).getH();
		// }
		// CommonSevice.getInstance().getFrame().getjTextFieldHua().setText(huaStr);
		// }
		//
		// if (null != opts) {
		// String optStr = "";
		// @SuppressWarnings("unchecked")
		// List<OptCheckMsg> optList = JsonUtils.parse(JsonUtils.toJson(opts),
		// List.class);
		// for (int i = 0; i < optList.size(); i++) {
		// optStr = optStr + (i == 0 ? "" : ",") + optList.get(i).getC() +
		// optList.get(i).getM();
		// }
		// CommonSevice.getInstance().getFrame().getjTextFieldOpts().setText(optStr);
		// }
		// // Arrays.sort(hands);
		// List<String> handCards = new ArrayList<String>(Arrays.asList(hands));
		// // Collections.sort(handCards);
		// CommonSevice.getInstance().getPlayer().setHandCards(handCards);
		// CommonSevice.getInstance().getFrame().getjComboBoxHand().setModel(new
		// DefaultComboBoxModel<String>(hands));
	}
}
