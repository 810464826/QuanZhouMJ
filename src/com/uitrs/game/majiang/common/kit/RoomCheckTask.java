package com.uitrs.game.majiang.common.kit;

import java.util.TimerTask;

import com.uitrs.game.majiang.common.room.Room;

/**
 * 解散房间定时器
 * 
 * @author lucio
 *
 */
public class RoomCheckTask extends TimerTask {
	/** 允许间隔的最长时间为24小时 */
	public static final int LIMIT_TIME = 24 * 60 * 60 * 1000;
	private Room room;
	/** 开始申请解散时间 */
	private long startTime;

	public RoomCheckTask(Room room) {
		this.room = room;
		this.startTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		if (this.room.getRoomUsers().isEmpty() && System.currentTimeMillis() - startTime >= LIMIT_TIME) {
			room.dismissRoom();
			this.cancel();
			return;
		}
	}
}
