package com.uitrs.game.majiang.common.msg.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 个人战绩查询消息
 * 
 * @author lucio
 *
 */
public class ScoreRecords extends AbstractMsg implements Comparable<ScoreRecords> {
	public static final String SCORE = "score";
	public static final String NICK_NAME = "nickName";
	public static final String GAMER_ID = "gamerId";
	public static final String REPLAY_ID = "repId";
	public static final String JU_NUM = "juNum";
	private static final long serialVersionUID = -3716440002175992093L;

	private long roomId;

	private String time;
	private int juNum;
	private String repId;

	private List<Map<String, Object>> usersInfo;

	public ScoreRecords(long roomId) {
		super();
		this.roomId = roomId;
		this.usersInfo = new ArrayList<Map<String, Object>>();
	}

	public ScoreRecords(long roomId, String time) {
		super();
		this.roomId = roomId;
		this.time = time;
		this.usersInfo = new ArrayList<Map<String, Object>>();
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getJuNum() {
		return juNum;
	}

	public void setJuNum(int juNum) {
		this.juNum = juNum;
	}

	public String getRepId() {
		return repId;
	}

	public void setRepId(String repId) {
		this.repId = repId;
	}

	public List<Map<String, Object>> getUsersInfo() {
		return usersInfo;
	}

	public void setUsersInfo(List<Map<String, Object>> usersInfo) {
		this.usersInfo = usersInfo;
	}

	public void addUsersInfo(Map<String, Object> usersInfo) {
		this.usersInfo.add(usersInfo);
	}

	/**
	 * 根据gamerId查找对应的用户
	 * 
	 * @param gamerId
	 * @return Map<String, Object>
	 */
	public Map<String, Object> findUsersInfo(long gamerId) {
		for (Map<String, Object> user : usersInfo) {
			if (user.get(GAMER_ID).toString().equalsIgnoreCase(gamerId + "")) {
				return user;
			}
		}
		return null;
	}

	/**
	 * 根据gamerId查找对应的用户的分数
	 * 
	 * @param gamerId
	 * @return score
	 */
	public int findUsersInfoScore(long gamerId) {
		Map<String, Object> user = findUsersInfo(gamerId);
		if (null == user) {
			return 0;
		}
		return Integer.parseInt(user.get(SCORE).toString());
	}

	@Override
	public int compareTo(ScoreRecords other) {
		return (int) (other.getRoomId() - this.getRoomId());
	}
}
