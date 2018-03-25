package com.uitrs.game.majiang.common.kit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.jfinal.plugin.activerecord.Db;

/**
 * ID自增工具类
 * 
 * @author lucio
 *
 */
public class IdGen {
	private static IdGen idGen = new IdGen();
	/** 用户自增id */
	private AtomicLong userIdSeq;
	/** 房间号自增id */
	private AtomicLong roomIdSeq;
	/** 回放自增id */
	private AtomicLong replayIdSeq;
	private List<Long> testUsers;

	/** 房间号自增id */
	// private List<AtomicLong> roomIdSeqs = new ArrayList<AtomicLong>();
	// private Random random;

	private IdGen() {
		// this.random = new Random();
		// if (PropKit.getBoolean("devMode", false)) {
		// userIdSeq = new AtomicLong(518000);
		// roomIdSeqs.add(new AtomicLong(200000));
		// roomIdSeqs.add(new AtomicLong(400000));
		// roomIdSeqs.add(new AtomicLong(600000));
		// roomIdSeqs.add(new AtomicLong(800000));
		// } else {
		// init();
		// }
	}

	/**
	 * 初始化用户ID和房间ID值,从数据库中读取当前最大值
	 */
	public void init() {
		// GamerInfo.dao.findFirst("select max(id) from gamerInfo");
		Integer maxGamerId = Db.queryColumn("select max(id) from gamerinfo");
		Integer maxRoomId = Db.queryColumn("select max(roomId) from roominfo");
		Integer maxReplayId = Db.queryColumn("select max(replayId) from replayinfo");
		this.userIdSeq = new AtomicLong(null == maxGamerId ? 51800000l : maxGamerId.intValue());
		this.roomIdSeq = new AtomicLong(null == maxRoomId ? 800000l : maxRoomId.intValue());
		this.replayIdSeq = new AtomicLong(null == maxReplayId ? 11800000l : maxReplayId.intValue());
		testUsers = new ArrayList<Long>();
	}

	public static synchronized IdGen getInstance() {
		return idGen;
	}

	/**
	 * 自增用户ID
	 * 
	 * @return
	 */
	public long getUserId() {
		return userIdSeq.incrementAndGet();
	}

	public synchronized long getTestUserId() {
		if (testUsers.size() > 0) {
			long userId = testUsers.get(testUsers.size() - 1);
			this.removeTestUserId(userId);
			return userId;
		}
		return idGen.getUserId();
	}

	public synchronized void addTestUserId(long userId) {
		testUsers.add(userId);
	}

	public void removeTestUserId(long userId) {
		testUsers.remove(userId);
	}

	/**
	 * 随机自增房间ID
	 * 
	 * @return
	 */
	public long getRoomId() {
		return roomIdSeq.incrementAndGet();
	}

	/**
	 * 随机自增房间ID
	 * 
	 * @return
	 */
	public long getReplayId() {
		return replayIdSeq.incrementAndGet();
	}

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}
	
	public static void main(String[] args) {
		System.err.println(generateShortUuid());
	}
	// public static void main(String[] args) {
	// for (int i = 0; i < 100; i++) {
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// System.err.println(IdGen.getRoomId());
	// }
	// }).start();
	// }
	//
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// System.err.println(IdGen.getRoomId());
	// }
	// }).start();
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// System.err.println(IdGen.getRoomId());
	// }
	// }).start();
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// System.err.println(IdGen.getRoomId());
	// }
	// }).start();
	// }
}
