package com.uitrs.game.majiang.common.kit;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.room.Room;

/**
 * web Socket Session 工具类
 * 
 * @author lucio
 *
 */
public final class WSSessionKit {
	/**
	 * 将openId存入session中
	 * 
	 * @param session
	 * @param openId
	 */
	public static void putOpenId(Session session, String openId) {
		if (null != session) {
			session.getUserProperties().put(MJConst.OPEN_ID, openId);
		}
	}

	/**
	 * 将userId存入session中
	 * 
	 * @param session
	 * @param userId
	 */
	public static void putUserId(Session session, long userId) {
		if (null != session) {
			session.getUserProperties().put(MJConst.USER_ID, userId);
		}
	}

	/**
	 * 将roomId存入session中
	 * 
	 * @param session
	 * @param roomId
	 */
	public static void putRoomId(Session session, long roomId) {
		if (null != session) {
			session.getUserProperties().put(MJConst.ROOM_ID, roomId);
		}
	}

	/**
	 * 获取session中的openId,不存在则返回null
	 * 
	 * @param session
	 * @return String
	 */
	public static String getOpenId(Session session) {
		if (null != session) {
			Object openId = session.getUserProperties().get(MJConst.OPEN_ID);
			if (null != openId) {
				return openId.toString();
			}
		}
		return null;
	}

	/**
	 * 获取session中的userId,不存在则返回0
	 * 
	 * @param session
	 * @return long
	 */
	public static long getUserId(Session session) {
		if (null != session) {
			Object userId = session.getUserProperties().get(MJConst.USER_ID);
			if (null != userId) {
				return Long.parseLong(userId.toString());
			}
		}
		return 0;
	}

	/**
	 * 获取session中的roomId,不存在则返回0
	 * 
	 * @param session
	 * @return long
	 */
	public static long getRoomId(Session session) {
		if (null != session) {
			Object roomId = session.getUserProperties().get(MJConst.ROOM_ID);
			if (null != roomId) {
				return Long.parseLong(roomId.toString());
			}
		}
		return 0;
	}

	public static Room getRoom(Session session) {
		return MJCache.getCache().getRoom(getRoomId(session));
	}

	public static PlayerInfo getPlayerInfo(Session session) {
		return MJCache.getCache().getPlayerInfo(WSSessionKit.getUserId(session));
	}
}
