package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.kit.IdGen;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.JoinMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.game.majiang.common.room.QZMJRoom;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.web.model.GamerInfo;
import com.uitrs.game.majiang.websockets.WebSocketUtil;

/**
 * 登录游戏业务处理
 * 
 * @author lucio
 *
 */
public class LoginService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		// 返回创建成功
		SendMsg result = new SendMsg(action.getP(), true, action.getT());
		String json = JsonUtils.toJson(action.getM());
		UserInfo userInfo = JsonUtils.parse(json, UserInfo.class);
		String roomId = userInfo.getRoomId();
		log.info("LoginService,userInfo: " + userInfo);
		// 测试环境,构造用户

		if (PropKit.getBoolean("devMode", false) && "10101".equalsIgnoreCase(userInfo.getOpenId())) {
			long userId = IdGen.getInstance().getTestUserId();
			// if (StringUtils.isEmpty(userId)) {
			// userId = IdGen.getInstance().getUserId() + "";
			// }

			UserInfo playerInfo = MJCache.getCache().getUserInfo(userId);
			if (null == playerInfo) {
				userInfo.setOpenId(userId + "");
				userInfo.setUserId(userId);
				userInfo.setNickName("玩家" + userId);
				userInfo.setSex(1);
				userInfo.setFaceIcon(PropKit.get("httpPath") + "/img/single-man.png");
				userInfo.setIp("127.0.0.1");
				// userInfo.setState(1);// 在线
			} else {
				userInfo = playerInfo;
			}

			userInfo = queryUserInfo(userInfo);
			if (!StringUtils.isEmpty(roomId)) {// 创建房间
				long parseLong = Long.parseLong(roomId);
				if (null == MJCache.getCache().getRoom(parseLong)) {
					QZMJRoom qzmjRoom = new QZMJRoom(parseLong);
					MJCache.getCache().addRoom(qzmjRoom);
					MJCache.getCache().addPlayerInfo(new PlayerInfo(userInfo));
					qzmjRoom.setOpt(new int[] { 1, 1 });
					qzmjRoom.createRoom(2, userInfo.getUserId());
				}
			}
			// 检查是否为重连并构造返回消息
			checkInfo(session, result, userInfo);
		} else {
			// userInfo = JsonUtils.parse(JsonUtils.toJson(action.getM()),
			// UserInfo.class);
			// 获取数据库中的数据
			log.info("weixin,userInfo: " + userInfo);
			userInfo = queryUserInfo(userInfo);
			checkInfo(session, result, userInfo);
		}

		// 如果房间号不为空且之前没有进入房间则加入房间
		if (!StringUtils.isEmpty(roomId) && 0 == WSSessionKit.getRoomId(session)) {
			long parseLong = Long.parseLong(roomId);
			SendMsg joinMsg = new SendMsg(action.getP(), true, IMsg.JOIN_TYPE);
			Room room = MJCache.getCache().getRoom(parseLong);
			if (null != room) {
				joinMsg.setI(room.getMsgSeq().incrementAndGet());
				if (!room.enterRoom(session, userInfo.getUserId())) {
					joinMsg.setS(false);
					joinMsg.setE("房间人数已满");
					WebSocketUtil.sendAsyncMsg(result, session);
				}
			} else {
				joinMsg.setS(false);
				joinMsg.setE("房间号不存在,或者已经解散");
				WebSocketUtil.sendAsyncMsg(result, session);
			}
		}

	}

	/**
	 * 检查是否存在游戏中退出情况, 存在则构造数据由客户端生成场景
	 * 
	 * @param session
	 * @param result
	 * @param userInfo
	 */
	private void checkInfo(Session session, SendMsg result, UserInfo userInfo) {
		userInfo.setState(1);// 在线
		WSSessionKit.putOpenId(session, userInfo.getOpenId());
		WSSessionKit.putUserId(session, userInfo.getUserId());
		PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(userInfo.getUserId());
		if (null == playerInfo) {
			playerInfo = new PlayerInfo(userInfo);
		} else {
			// 设置之前的准备状态
			userInfo.setReady(playerInfo.getUserInfo().isReady());
			userInfo.setRoomId(playerInfo.getRoomId() + "");
			playerInfo.setUserInfo(userInfo);
		}
		if (MJCache.getCache().addPlayerInfo(playerInfo)) {
			log.info("checkInfo,playerInfo: " + playerInfo);
			// 保存session信息到缓存中
			MJCache.getCache().addSession(session);
			long roomId = playerInfo.getRoomId();
			Room room = MJCache.getCache().getRoom(roomId);
			log.info("checkInfo,room: " + room);
			// 如果有玩家打牌信息则说明是重连,发送重连时需要的玩家个人信息和打牌数据,否则只发送用户基本信息
			if (null == room || 0 == playerInfo.getRoomId()) {
				result.setM(userInfo);
			} else {
				// 设置roomId到新的session中
				log.info("ReBuildMsg,userInfo: " + userInfo);
				WSSessionKit.putRoomId(session, room.getRoomId());
				JoinMsg reMsg = room.buildReBuildMsg(userInfo.getUserId());
				result.setT(IMsg.REBUILD_TYPE);
				reMsg.setMsg(MJCache.getCache().getMsg(session));
				result.setM(reMsg);
				log.info(userInfo.getOpenId() + " reMsg:" + reMsg);
				// 通知其他用户某人上线了
				SendMsg reConnectMsg = new SendMsg(userInfo.getUserId(), true, IMsg.RECONNECT_TYPE);
				reConnectMsg.setM(userInfo.getUserId());
				room.broadcast(reConnectMsg);
			}
			if (log.isInfoEnabled()) {
				log.info("a user logined " + result);
			}
			// 给自己回消息
			WebSocketUtil.sendAsyncMsg(result, session);
			// 如果是重连用户将未发送成功的消息再次发送给改用户
			// if (result.getT() == IMsg.REBUILD_TYPE) {
			// try {
			// Thread.sleep(500);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// MJCache.getCache().popMsgs(session);
			//
			// }
		} else {
			result.setS(false);
			result.setE("在线用户数达到最大,请稍后再登录!");
		}

	}

	/**
	 * 根据openId查询用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	private UserInfo queryUserInfo(UserInfo userInfo) {
		GamerInfo gamer = GamerInfo.dao.findFirst("select * from gamerinfo where openid = ?", userInfo.getOpenId());
		if (null == gamer) {
			gamer = new GamerInfo();
			if (!PropKit.getBoolean("weixinMode", false)) {
				gamer.setId(Integer.parseInt(userInfo.getOpenId()));
				userInfo.setUserId(gamer.getId());
			} else {
				gamer.setId((int) IdGen.getInstance().getUserId());
				userInfo.setUserId(gamer.getId());
			}
			log.info("---queryUserInfo,userInfo: " + userInfo);
			gamer.setNickName(userInfo.getNickName());
			gamer.setOpenId(userInfo.getOpenId());
			gamer.setHeadFace(userInfo.getFaceIcon());
			gamer.setSex(userInfo.getSex());
			gamer.setIp(userInfo.getIp());
			gamer.setParentId(userInfo.getParentId());
			// 数据库设置默认值
			gamer.setTotalCards(10);
			gamer.setSurplusCards(10);
			gamer.save();
		}
		// 判断原来有没有邀请者ID, 如果有忽略,如果没有则使用userInfo传过来的parentId设置,并增加parentId的房卡5张
		if (0 != gamer.getParentId()) {
			userInfo.setParentId(gamer.getParentId());
		} else {
			gamer.setParentId(userInfo.getParentId());
			gamer.update();
		}
		
		userInfo.setFaceIcon(gamer.getHeadFace());
		userInfo.setCards(gamer.getSurplusCards());
		userInfo.setUserId(gamer.getId());
		log.info("---exit queryUserInfo,userInfo: " + userInfo);
		return userInfo;
	}
}
