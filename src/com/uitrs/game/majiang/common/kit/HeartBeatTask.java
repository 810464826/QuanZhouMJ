package com.uitrs.game.majiang.common.kit;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TimerTask;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;

/**
 * 解散房间定时器
 * 
 * @author lucio
 *
 */
public class HeartBeatTask extends TimerTask {

	/** 允许间隔的最长时间为5分钟 */
	// private static SendMsg heartMsg = new SendMsg(true, IMsg.HEART_TYPE);
	private Room room;

	/** 开始申请解散时间 */
	// private long startTime;

	public HeartBeatTask(Room room) {
		this.room = room;
	}

	@Override
	public void run() {
		Iterator<Entry<Integer, Long>> it = this.room.getRoomUsers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> entry = it.next();
			long userId = entry.getValue();
			PlayerInfo player = MJCache.getCache().getPlayerInfo(userId);
			if (null != player) {
				// 如果时间超过10秒则说明离线了
				Session session = MJCache.getCache().getSession(userId);
				long mills = System.currentTimeMillis() - player.getAliveTime();
				if (player.getUserInfo().getState() == MJConst.ONLINE && mills > 16000) {
					System.err.println("Send disconnect Msg, userId: " + userId);
					player.getUserInfo().setState(MJConst.OFFLINE);
					SendMsg msg = new SendMsg(userId, true, IMsg.DISCONNECT_TYPE);
					msg.setM(userId);
					//this.room.broadcast(msg, userId);
				} else if (mills > 5000 && mills < 1800000) {
					SendMsg heartMsg = new SendMsg(userId, true, IMsg.HEART_TYPE);
					//this.room.sendMsg(userId,heartMsg, session);
				}
			}
		}
	}
}
