package com.uitrs.game.majiang.websockets;

import java.io.IOException;

import javax.websocket.Session;

import com.jfinal.log.Log;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.IMsg;

/**
 * webSocket工具类
 * 
 * @author lucio
 *
 */
public final class WebSocketUtil {
	private static Log log = Log.getLog(WebSocketUtil.class);

	/**
	 * 发送异步消息,不会缓存消息
	 * 
	 * @param String
	 * @param session
	 */
	public static void sendAsyncMsg(String msg, Session session) {
		if (null != session && null != msg && session.isOpen()) {
			String openId = WSSessionKit.getOpenId(session);
			if (log.isInfoEnabled()) {
				log.info("Begin Send Async Msg:[" + msg + "],sessionId:[" + session.getId() + "],openId:" + openId);
			}
			session.getAsyncRemote().sendText(msg);
			if (log.isInfoEnabled()) {
				log.info("End Send Async Msg:[" + msg + "],sessionId:[" + session.getId() + "],openId:" + openId);
			}
		}
	}

	/**
	 * 发送同步消息,不会缓存消息
	 * 
	 * @param msg
	 * @param session
	 * @return boolean
	 */
	public static boolean sendSyncMsg(String msg, Session session) {
		if (null != session) {
			if (null != msg) {
				String openId = WSSessionKit.getOpenId(session);
				if (session.isOpen()) {
					try {
						if (log.isInfoEnabled()) {
							log.info("Begin Send Sync Msg:[" + msg + "],sessionId:[" + session.getId() + "],openId:"
									+ openId);
						}
						session.getBasicRemote().sendText(msg);
						if (log.isInfoEnabled()) {
							log.info("End Send Sync Msg:[" + msg + "],sessionId:[" + session.getId() + "],openId:"
									+ openId);
						}
						return true;
					} catch (IOException e) {
						log.error("Failed Send Sync Msg:[" + msg + "],sessionId:[" + session.getId() + "],openId:"
								+ openId, e);
						// MJCache.getCache().addMsg(session, msg);
					}
				} else {
					log.error("Failed Send Sync Msg:[" + msg + "],openId:" + openId + ", webSocekt closed!");
					// MJCache.getCache().addMsg(session, msg);
				}
			}
		}

		return false;
	}

	/**
	 * 发送异步消息,不会缓存消息
	 * 
	 * @param IMsg
	 * @param session
	 */
	public static void sendAsyncMsg(IMsg msg, Session session) {
		if (null != msg) {
			sendAsyncMsg(msg.toJson(), session);
		}
	}

	/**
	 * 发送同步消息,不会缓存消息
	 * 
	 * @param IMsg
	 * @param session
	 */
	public static boolean sendSyncMsg(IMsg msg, Session session) {
		if (null != msg) {
			return sendSyncMsg(msg.toJson(), session);
		}
		return false;
	}
}
