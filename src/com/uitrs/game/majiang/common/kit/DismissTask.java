package com.uitrs.game.majiang.common.kit;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.TimerTask;

import com.jfinal.kit.PropKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;

/**
 * 解散房间定时器
 * 
 * @author lucio
 *
 */
public class DismissTask extends TimerTask {
	/** 允许间隔的最长时间为5分钟 */
	// public static final int LIMIT_TIME = 5 * 60 * 1000;
	private Room room;
	/** 开始申请解散时间 */
	private long startTime;

	public DismissTask(Room room) {
		this.room = room;
		this.startTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		if (this.room.getRoomUsers().isEmpty() && System.currentTimeMillis() - startTime >= 24 * 60 * 60 * 1000) {

		}
		Map<Long, Boolean> dissmisNumber = this.room.getDissmisNumber();
		Iterator<Entry<Integer, Long>> it = this.room.getRoomUsers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> entry = it.next();
			long userId = entry.getValue();
			// 如果超过5分钟则自动表示同意
			if ((null == dissmisNumber.get(userId))
					&& (System.currentTimeMillis() - startTime) >= PropKit.getLong("dismissTime")) {
				dissmisNumber.put(userId, true);
				SendMsg sendMsg = new SendMsg(userId, true, IMsg.DISMISSING_TYPE, room.getMsgSeq().incrementAndGet());
				Map<String, Object> hashMap = new ConcurrentHashMap<String, Object>();
				hashMap.put("s", room.getToDismisser());
				hashMap.put("m", room.getDissmisNumber());
				sendMsg.setM(hashMap);
				room.broadcast(sendMsg);
				// 检查是否解散成功
				int result = room.toDismissRoom(userId, true);
				if (result == 1) {
					room.dismissRoom();
					this.cancel();
					return;
				}
			}
		}
	}
}
