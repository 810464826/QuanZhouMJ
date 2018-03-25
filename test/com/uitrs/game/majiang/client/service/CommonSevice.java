package com.uitrs.game.majiang.client.service;

import java.net.URI;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.MJClientWebSocket;
import com.uitrs.game.majiang.MainJFrame;
import com.uitrs.game.majiang.WeixinJDialog;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.sub.CreateRoom;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;

public class CommonSevice {
	private UserInfo ui;
	private PlayerInfo player;
	private Session session;
	public MainJFrame frame;
	public WeixinJDialog weixin;
	
	private static CommonSevice service = new CommonSevice();
	
	public static CommonSevice getInstance()
	{
		return service;
	}

	public Session connectToServer(MainJFrame mainJFrame, WeixinJDialog weixin, String uri) {
		this.frame = mainJFrame;
		this.weixin = weixin;
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		try {
			this.session = container.connectToServer(MJClientWebSocket.class, new URI(uri));
			return this.session;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			} // 等待一小会

		}
		return null;
	}

	public boolean sendMsg(String text) {
		ReceiveMsg sendMsg = JsonUtils.parse(text, ReceiveMsg.class);
		boolean result = sendMsg(sendMsg);
		if (!result) {
			return false;
		}
		return true;
	}

	private boolean sendMsg(ReceiveMsg sendMsg) {
		try {
			// 发送消息
			session.getBasicRemote().sendText(sendMsg.toJson());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			frame.getjEditorPaneReceive().setText(e.getMessage());
		}
		return false;
	}

	public void refreshCards(PlayerInfo playerInfo) {
		if (null != playerInfo.getHandCards()) {
			String[] hands = new String[playerInfo.getHandCards().size()];
			playerInfo.getHandCards().toArray(hands);
			Arrays.sort(hands);

			// String[] hands = frame.getjTextFieldHand().getText().split(",");
			frame.getjTextFieldHand().setText("");
			for (int j = 0; j < hands.length; j++) {
				hands[j] = hands[j].trim();
				frame.getjTextFieldHand().setText(frame.getjTextFieldHand().getText()
						+ (StrKit.isBlank(frame.getjTextFieldHand().getText()) ? "" : ",") + hands[j]);
			}
			frame.getjComboBoxHand().setModel(new DefaultComboBoxModel<String>(hands));
		}
		// 花
		if (null != playerInfo.getHuaCards()) {
			frame.getjTextFieldHua().setText("");
			frame.getjTextFieldHua().setText(
					playerInfo.getHuaCards().toString().substring(1, playerInfo.getHuaCards().toString().length() - 1));
			// String[] huas = frame.getjTextFieldHua().getText().split(",");
			// Arrays.sort(huas);
			// for (int j = 0; j < huas.length; j++) {
			// frame.getjComboBoxHand().addItem(huas[j].trim());
			// }
		}
		// 操作牌
		if (null != playerInfo.getOptMsgs()) {
			frame.getjTextFieldOpts().setText("");
			for (OptCheckMsg optMsg : playerInfo.getOptMsgs()) {
				frame.getjTextFieldOpts()
						.setText(frame.getjTextFieldOpts().getText()
								+ (StrKit.isBlank(frame.getjTextFieldOpts().getText()) ? "" : ",")
								+ optMsg.getC().trim() + "," + Arrays.toString(optMsg.getM()));
				frame.getjComboBoxHand().addItem(optMsg.getC().trim());
			}
		}
		// frame.getjTextFieldOpts().setText(
		// playerInfo.getOptMsgs().toString().substring(1,
		// playerInfo.getOptMsgs().toString().length() - 1));
		// String[] opts = frame.getjTextFieldOpts().getText().split(",");
		// Arrays.sort(opts);
		// for (int j = 0; j < opts.length; j++) {
		// frame.getjComboBoxHand().addItem(opts[j].trim());
		// }
	}
	public UserInfo login(UserInfo ui) {
		this.ui = ui;
		this.ui.setNickName(ui.getNickName() + ui.getOpenId());
		this.ui.setCards(100);
		this.ui.setState(1);
		ReceiveMsg sendMsg = new ReceiveMsg(IMsg.LOGIN_TYPE, this.ui, this.ui.getUserId());
		try {
			// 发送消息
			session.getBasicRemote().sendText(sendMsg.toJson());
			player = new PlayerInfo(ui);
			player.init(0, 0);
			return ui;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void create(String input) {
		ReceiveMsg msg = new ReceiveMsg(IMsg.CREATE_TYPE, ui.getUserId());
		CreateRoom m = new CreateRoom();
		if (StrKit.isBlank(input)) {
			m.setJu(1);
			m.setOpt(new int[] { 1, 1, 1 });
		} else {
			String[] items = input.split(";");
			m.setJu(Integer.parseInt(items[0]));
			String[] optstr = items[1].split(",");
			int[] opts = new int[optstr.length];
			for (int i = 0; i < opts.length; i++) {
				opts[i] = Integer.parseInt(optstr[i]);
			}
			m.setOpt(opts);
		}
		msg.setM(m);
		sendMsg(msg);
	}

	public void join(String input) {
		ReceiveMsg msg = new ReceiveMsg(IMsg.JOIN_TYPE, ui.getUserId());
		if (StrKit.isBlank(input)) {
			JOptionPane.showMessageDialog(frame, "请输入房间号");
		} else {
			msg.setM(input);
			sendMsg(msg);
		}
	}

	public void ready(String input) {
		ReceiveMsg msg = new ReceiveMsg(IMsg.READY_TYPE, ui.getUserId());
		sendMsg(msg);
	}

	public void outCard(String card) {
		card = frame.getjComboBoxHand().getSelectedItem().toString().trim();
		ReceiveMsg msg = new ReceiveMsg(IMsg.OUT_CARD_TYPE, ui.getUserId());
		msg.setM(card);
		sendMsg(msg);

		// refreshCards(player);
	}

	public void buHua(String string) {
		// TODO Auto-generated method stub

	}

	public void peng(String card) {
		card = frame.getjComboBoxHand().getSelectedItem().toString().trim();
		ReceiveMsg msg = new ReceiveMsg(IMsg.PENG_TYPE, ui.getUserId());
		OptCheckMsg optMsg = new OptCheckMsg(ui.getUserId(), IMsg.PENG_TYPE, card, new String[] { card, card });
		msg.setM(optMsg);
		sendMsg(msg);
	}

	public void fetch(String string) {
		// TODO Auto-generated method stub
	}

	public void anGang(String string) {
		// TODO Auto-generated method stub
	}

	public void mingGang(String string) {
		// TODO Auto-generated method stub
	}

	public void buGang(String string) {
		// TODO Auto-generated method stub
	}

	public void hu(String string) {
		// TODO Auto-generated method stub
	}

	public UserInfo getUi() {
		return ui;
	}

	public void setUi(UserInfo ui) {
		this.ui = ui;
	}

	public PlayerInfo getPlayer() {
		return player;
	}

	public void setPlayer(PlayerInfo player) {
		this.player = player;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public MainJFrame getFrame() {
		return frame;
	}

	public void setFrame(MainJFrame frame) {
		this.frame = frame;
	}

	public WeixinJDialog getWeixin() {
		return weixin;
	}

	public void setWeixin(WeixinJDialog weixin) {
		this.weixin = weixin;
	}
	
}
