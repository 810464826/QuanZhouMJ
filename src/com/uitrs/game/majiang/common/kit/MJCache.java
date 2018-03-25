package com.uitrs.game.majiang.common.kit;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import com.jfinal.log.Log;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.MJService;
import com.uitrs.game.majiang.websockets.WebSocketUtil;
import com.uitrs.web.common.kit.SystemUtil;

/**
 * 麻将的缓存,缓存房间信息和用户信息,使用ehcache第三方缓存工具缓存用户信息,房间信息和session信息
 * 
 * @author lucio
 *
 */
public class MJCache {
	private static Log log = Log.getLog(MJCache.class);
	/** 默认最大缓存用户数 */
	private static final int USER_MAX = 45000;
	/** 默认最大缓存房间数 */
	private static final int ROOM_MAX = 10000;
	/** 单例,方便使用 */
	private static MJCache cache = new MJCache();
	/** 最大缓存用户数 */
	private int userMax;
	/** 最大缓存房间数 */
	private int roomMax;
	/** 存储所有的房间 */
	private ConcurrentHashMap<Long, Room> rooms;
	/** 存储在线用户 */
	private ConcurrentHashMap<Long, Session> sessions;
	/** 存储玩家打牌信息,key为openId */
	private ConcurrentHashMap<Long, PlayerInfo> playerInfos;
	/** 业务处理实例 */
	private ConcurrentHashMap<Integer, MJService> services;
	/** 存储发送信息,key为openId */
	private ConcurrentHashMap<Long, Vector<SendMsg>> sendCheck;

	/** 失败消息 ，key=房间号,value={key=消息类型,value=sendMsg} */
	private ConcurrentHashMap<Long, ConcurrentHashMap<Integer, SendMsg>> msgs;

	private MJCache() {
		this.userMax = USER_MAX;
		this.roomMax = ROOM_MAX;
		this.rooms = new ConcurrentHashMap<Long, Room>();
		this.sessions = new ConcurrentHashMap<Long, Session>();
		this.playerInfos = new ConcurrentHashMap<Long, PlayerInfo>();
		this.services = new ConcurrentHashMap<Integer, MJService>();
		this.msgs = new ConcurrentHashMap<Long, ConcurrentHashMap<Integer, SendMsg>>();
		this.sendCheck = new ConcurrentHashMap<Long, Vector<SendMsg>>();
	}

	public static MJCache getCache() {
		return cache;
	}

	public ConcurrentHashMap<Long, Room> getRooms() {
		// return CacheKit.getKeys("rooms");
		return this.rooms;
	}

	// public ConcurrentHashMap<String, UserInfo> getUsers() {
	// return this.users;
	// }
	public ConcurrentHashMap<Long, PlayerInfo> getPlayerInfos() {
		// return CacheKit.getKeys("playerInfos");
		return this.playerInfos;
	}

	public Room getRoom(long roomId) {
		return this.rooms.get(roomId);
		// return CacheKit.get("rooms", roomId);
	}

	public Session getSession(long userId) {
		return this.sessions.get(userId);
		// return CacheKit.get("sessions", openId);
	}

	public PlayerInfo getPlayerInfo(long userId) {
		// return CacheKit.get("playerInfos", openId);
		return this.playerInfos.get(userId);
	}

	public UserInfo getUserInfo(long userId) {
		PlayerInfo playerInfo = this.getPlayerInfo(userId);
		if (null != playerInfo) {
			return playerInfo.getUserInfo();
		}
		return null;
	}

	public Map<Integer, SendMsg> getMsgs(long roomId) {
		ConcurrentHashMap<Integer, SendMsg> map = this.msgs.get(roomId);
		if (null == map) {
			this.msgs.putIfAbsent(roomId, new ConcurrentHashMap<Integer, SendMsg>());
		}
		return this.msgs.get(roomId);
	}

	public Map<Integer, SendMsg> getMsgs(Session session) {
		long roomId = WSSessionKit.getRoomId(session);
		return this.getMsgs(roomId);
	}

	// public boolean addMsg(long room, SendMsg msg) {
	// this.getMsgs(room).put(1, msg);
	// this.getMsgs(room).put(2, msg);
	// this.getMsgs(room).put(3, msg);
	// this.getMsgs(room).put(4, msg);
	// return true;
	// }

	/**
	 * 获取用户重构时要发送的未成功消息
	 * 
	 * @param session
	 * @return IMsg
	 */
	public SendMsg getMsg(Session session) {
		long userId = WSSessionKit.getUserId(session);
		PlayerInfo info = MJCache.getCache().getPlayerInfo(userId);
		return this.getMsgs(session).get(info.getOrder());
	}

	public SendMsg getMsg(Room room, PlayerInfo player) {
		return this.getMsgs(room.getRoomId()).get(player.getOrder());
	}

	public boolean addMsg(long room, int order, SendMsg msg) {
		this.getMsgs(room).put(order, msg);
		return true;
	}

	/**
	 * 缓存消息:创建房间,准备,开始,出牌,拿牌/补花,吃/碰/明杠/补杠/暗杠,胡/自摸/三金倒,游金/双游/三游,单局结算/房间结算
	 * 
	 * @param session
	 * @param msg
	 * @return boolean
	 */
	public boolean addMsg(Session session, SendMsg msg) {
		// 缓存消息:开始,出牌,拿牌/补花,吃/碰/明杠/补杠/暗杠,胡/自摸/三金倒,游金/双游/三游,单局结算/房间结算
		if (MJUtil.isOptMsg(msg)) {
			long userId = WSSessionKit.getUserId(session);
			PrintKit.red("Begin Add Rebuild Msg Type:" + msg.getT() + " ,userID: " + userId);
			PlayerInfo info = MJCache.getCache().getPlayerInfo(userId);
			this.getMsgs(session).put(info.getOrder(), msg);
			PrintKit.red("End Add Rebuild Msg Type:" + msg.getT() + " ,userID: " + userId);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeMsg(long room, int order) {
		IMsg msg = this.getMsgs(room).remove(order);
		if (null == msg) {
			return false;
		}
		return true;
	}

	public boolean removeMsg(Session session) {
		long userId = WSSessionKit.getUserId(session);
		PlayerInfo info = MJCache.getCache().getPlayerInfo(userId);
		this.getMsgs(session).get(info.getOrder());
		IMsg msg = this.getMsgs(session).remove(info.getOrder());
		if (null == msg) {
			return false;
		}
		return true;
	}

	/**
	 * 执行某个用户失败的消息
	 * 
	 * @param session
	 */
	public void popMsgs(Session session) {
		IMsg msg = this.getMsg(session);
		if (null != msg) {
			WebSocketUtil.sendAsyncMsg(msg, session);
		}
	}

	public ConcurrentHashMap<Long, Vector<SendMsg>> getSendCheck() {
		return sendCheck;
	}

	public synchronized Vector<SendMsg> getSendCheckMsgs(long userId) {
		Vector<SendMsg> listMsg = this.sendCheck.get(userId);
		if (null == listMsg) {
			this.sendCheck.put(userId, new Vector<SendMsg>());
			listMsg = this.sendCheck.get(userId);
		}
		return listMsg;
	}

	public SendMsg getSendCheckMsg(long msgId, long userId) {
		Vector<SendMsg> list = this.getSendCheckMsgs(userId);
		for (SendMsg sendMsg : list) {
			if (sendMsg.getI() == msgId) {
				return sendMsg;
			}
		}
		return null;
	}

	public void addSendCheckMsg(long userId, SendMsg msg) {
		if (null != msg && msg.getI() != 0) {
			System.err.println(userId + " begin add msg: " + msg.getI() + " type: " + msg.getT());
			this.getSendCheckMsgs(userId).add(msg);
			System.err.println(userId + " end   add msg: " + msg.getI() + " type: " + msg.getT());
		}
	}

	/**
	 * 删除某个用户的某个ID的信息
	 * 
	 * @param msgId
	 * @param userId
	 * @return boolean
	 */
	public boolean removeSendCheckMsg(long msgId, long userId) {
		System.err.println(userId + " begin remove msg: " + msgId);
		SendMsg checkMsg = getSendCheckMsg(msgId, userId);
		if (null != checkMsg) {
			System.err.println(userId + " end   remove msg: " + msgId + " type: " + checkMsg.getT());
			return this.getSendCheckMsgs(userId).remove(checkMsg);
		}
		System.err.println(userId + " end   remove msg: " + msgId);
		return false;
	}

	public boolean addRoom(Room room) {
		// if (CacheKit.getKeys("rooms").size() >= this.roomMax ||
		// SystemUtil.getFreeMemory() <= 10) {
		// return false;
		// }
		// CacheKit.put("rooms", room.getRoomId(), room);
		if (this.rooms.size() >= this.roomMax || SystemUtil.getFreeMemory() <= 10) {
			return false;
		}
		this.rooms.put(room.getRoomId(), room);
		return true;
	}

	public boolean addSession(long userId, Session session) {
		// if (CacheKit.getKeys("sessions").size() >= this.userMax ||
		// SystemUtil.getFreeMemory() <= 10) {
		// return false;
		// }
		// CacheKit.put("sessions", WSSessionKit.getOpenId(session), session);
		if (this.sessions.size() >= this.userMax || SystemUtil.getFreeMemory() <= 10) {
			return false;
		}
		this.sessions.put(userId, session);
		return true;
	}

	public boolean addSession(Session session) {
		// if (CacheKit.getKeys("sessions").size() >= this.userMax ||
		// SystemUtil.getFreeMemory() <= 10) {
		// return false;
		// }
		// CacheKit.put("sessions", WSSessionKit.getOpenId(session), session);
		if (this.sessions.size() >= this.userMax || SystemUtil.getFreeMemory() <= 10) {
			return false;
		}
		this.sessions.put(WSSessionKit.getUserId(session), session);
		return true;
	}

	/**
	 * 缓存玩家打牌信息
	 * 
	 * @param playerInfo
	 * @return true
	 */
	public boolean addPlayerInfo(PlayerInfo playerInfo) {
		if (this.playerInfos.size() >= this.userMax || SystemUtil.getFreeMemory() <= 10) {
			return false;
		}
		this.playerInfos.put(playerInfo.getUserInfo().getUserId(), playerInfo);
		return true;
	}

	public void removePlayerInfo(PlayerInfo playerInfo) {
		this.playerInfos.remove(playerInfo.getUserInfo().getUserId());
	}

	public void removeRoom(Room room) {
		this.rooms.remove(room.getRoomId());
		// CacheKit.remove("rooms", room.getRoomId());
	}

	public void removeRoom(long roomId) {
		this.rooms.remove(roomId);
		// CacheKit.remove("rooms", room.getRoomId());
	}

	public void removeSession(long userId) {
		try {
			Session session = this.getSession(userId);
			if (null != session) {
				this.sessions.remove(userId);
				session.close();
			}
		} catch (Exception e) {
			log.error("close session failed! userId=" + userId, e);
		}
		// CacheKit.remove("sessions", openId);
	}

	public ConcurrentHashMap<Integer, MJService> getServices() {
		return this.services;
	}

	/**
	 * 根据类型获取业务实体类,没有则调用默认处理方法
	 * 
	 * @param type
	 * @return MJService
	 */
	public MJService getService(int type) {
		MJService service = this.services.get(type);
		if (null == service) {
			return this.services.get(IMsg.DEFAULT_TYPE);
		}
		return service;

	}

	public ConcurrentHashMap<Integer, MJService> addServices(Integer type, MJService service) {
		this.services.put(type, service);
		return this.services;
	}

	public ConcurrentHashMap<Integer, MJService> removeServices(Integer type) {
		this.services.remove(type);
		return this.services;
	}

	public int getUserMax() {
		return userMax;
	}

	public void setUserMax(int userMax) {
		this.userMax = userMax;
	}

	public int getRoomMax() {
		return roomMax;
	}

	public void setRoomMax(int roomMax) {
		this.roomMax = roomMax;
	}

	public ConcurrentHashMap<Long, Session> getSessions() {
		return sessions;
	}

	/**
	 * 重置缓存内数据
	 */
	public static void resetCache() {
		cache = new MJCache();
	}
}
