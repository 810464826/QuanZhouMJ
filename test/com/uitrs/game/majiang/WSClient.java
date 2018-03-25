package com.uitrs.game.majiang;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.jfinal.config.Plugins;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.kit.IdGen;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.CreateRoom;
import com.uitrs.game.majiang.common.msg.sub.JoinMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.game.majiang.web.model._MappingKit;

@ClientEndpoint
public class WSClient {
	private static long roomId;
	private static boolean flag;

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Connected to endpoint: " + session.getId());
	}

	@OnMessage
	public void onMessage(String message) {
		
		System.out.println(message);
		SendMsg recevieMsg = buildMsg(message);
		System.out.println(recevieMsg.getT()+" 收到消息: " + recevieMsg);
		
		if (recevieMsg.getT() == IMsg.CREATE_TYPE) {
			JoinMsg joinMsg = JsonUtils.parse(JsonUtils.toJson(recevieMsg.getM()), JoinMsg.class);
			roomId = joinMsg.getRoomId();
			flag = true;
		}
	}

	private SendMsg buildMsg(String message) {
		@SuppressWarnings("unchecked")
		Map<String, Object> recevieMap = JsonUtils.parse(message, Map.class);
		SendMsg sendMsg = new SendMsg(Long.parseLong(recevieMap.get("p").toString()),Boolean.parseBoolean(recevieMap.get("s").toString()),
				Integer.parseInt(recevieMap.get("t").toString()));
		sendMsg.setE(null != recevieMap.get("e") ? recevieMap.get("e").toString() : null);
		sendMsg.setM(recevieMap.get("m"));
		return sendMsg;
	}

	@OnError
	public void onError(Throwable t) {
		t.printStackTrace();
	}

	public static void main(String[] args) {
		init();
		List<UserInfo> users = new ArrayList<UserInfo>();
		users.add(buildBaseUser());
		users.add(buildBaseUser());
		users.add(buildBaseUser());
		users.add(buildBaseUser());

		String uri = "ws://192.168.0.103:8080/quanzhou/ws/qzmj";
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		Session session1 = null;
		Session session2 = null;
		Session session3 = null;
		Session session4 = null;
		try {
			session1 = container.connectToServer(WSClient.class, new URI(uri));
			session2 = container.connectToServer(WSClient.class, new URI(uri));
			session3 = container.connectToServer(WSClient.class, new URI(uri));
			session4 = container.connectToServer(WSClient.class, new URI(uri));

			ReceiveMsg sendMsg = new ReceiveMsg(IMsg.LOGIN_TYPE, users.get(0), users.get(0).getUserId());
			session1.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息
			sendMsg = new ReceiveMsg(IMsg.LOGIN_TYPE, users.get(1), users.get(1).getUserId());
			session2.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息
			sendMsg = new ReceiveMsg(IMsg.LOGIN_TYPE, users.get(2), users.get(2).getUserId());
			session3.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息
			sendMsg = new ReceiveMsg(IMsg.LOGIN_TYPE, users.get(3), users.get(3).getUserId());
			session4.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息
			flag = false;
			CreateRoom createMsg = new CreateRoom(1, new int[] { 2, 3, 4 });
			sendMsg = new ReceiveMsg(IMsg.CREATE_TYPE, createMsg, users.get(0).getUserId());
			System.err.println("CREATE_TYPE: "+ sendMsg.toJson());
			session1.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息

			while (!flag) {
				Thread.sleep(500); // 等待一小会
			}
			sendMsg = new ReceiveMsg(IMsg.JOIN_TYPE, roomId, users.get(1).getUserId());
			session2.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息

			sendMsg = new ReceiveMsg(IMsg.JOIN_TYPE, roomId, users.get(2).getUserId());
			session3.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息

			sendMsg = new ReceiveMsg(IMsg.JOIN_TYPE, roomId, users.get(3).getUserId());
			session4.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息
			while (!flag) {
				Thread.sleep(500); // 等待一小会
			}
			sendMsg = new ReceiveMsg(IMsg.READY_TYPE, roomId, users.get(0).getUserId());
			session1.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息
			
			sendMsg = new ReceiveMsg(IMsg.READY_TYPE, roomId, users.get(1).getUserId());
			session2.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息

			sendMsg = new ReceiveMsg(IMsg.READY_TYPE, roomId, users.get(2).getUserId());
			session3.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息

			sendMsg = new ReceiveMsg(IMsg.READY_TYPE, roomId, users.get(3).getUserId());
			session4.getBasicRemote().sendText(sendMsg.toJson()); // 发送消息
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Thread.sleep(5000);
				// session1.close(); // 关闭连接
				// session2.close(); // 关闭连接
				// session3.close(); // 关闭连接
				// session4.close(); // 关闭连接
			} catch (Exception e) {
				e.printStackTrace();
			} // 等待一小会

		}
	}

	private static void init() {
		PropKit.use("develop.properties");
		DruidPlugin druidPlugin = createDruidPlugin();
		druidPlugin.start();
		Plugins me = new Plugins();
		me.add(druidPlugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.start();
		me.add(arp);
		//
		// // 所有配置在 MappingKit 中搞定
		_MappingKit.mapping(arp);

		// 缓存配置
		EhCachePlugin ecp = new EhCachePlugin(PathKit.getRootClassPath() + "/ehcache.xml");
		ecp.start();
		me.add(ecp);
		IdGen.getInstance().init();
	}

	private static UserInfo buildBaseUser() {
		UserInfo userInfo = new UserInfo();
		// Long userId = IdGen.getInstance().getUserId();
		String userId = IdGen.getInstance().getUserId() + "";
		// if (StringUtils.isEmpty(userId)) {
		// userId = IdGen.getInstance().getUserId() + "";
		// }
		userInfo.setOpenId(userId);
		userInfo.setUserId(Long.parseLong(userId));
		userInfo.setNickName("玩家" + userId);
		userInfo.setSex(1);
		userInfo.setFaceIcon("5350b36d56b6e054.jpg");
		userInfo.setIp("127.0.0.1");
		userInfo.setState(1);// 在线
		userInfo.setCards(3);
		return userInfo;
	}

	public static DruidPlugin createDruidPlugin() {
		// System.out.println(ToolIDEA.encrypt("root"));
		// System.out.println(ToolIDEA.encrypt("123456"));
		// 非开发模式下,用户名和密码必须加密设置
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user").trim(), PropKit.get("password").trim());
	}
}
