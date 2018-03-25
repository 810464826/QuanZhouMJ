package com.uitrs.game.majiang.common.msg.sub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 玩家加入消息
 * 
 * @author lucio
 *
 */
public class ToJoinMsg extends AbstractMsg {

	private static final long serialVersionUID = -7980104562176995270L;

	private long roomId;

	private int totalJuNum;
	private int totalQuanNum;
	private long creator;
	/** 我的位置号 */
	private int meOrder;

	private int[] opt;

	private List<UserInfo> roomUsers;

	public ToJoinMsg() {
		super();
	}

	public ToJoinMsg(long roomId, int totalJuNum, int totalQuanNum, Map<Integer, Long> openIds, int[] opt, int order) {
		super();
		this.roomId = roomId;
		this.totalJuNum = totalJuNum;
		this.totalQuanNum = totalQuanNum;
		this.opt = opt;
		this.meOrder = order;
		this.roomUsers = new ArrayList<UserInfo>();
		// 构造userInfo消息给客户端显示
		for (int i = 0; i < openIds.size(); i++) {
			long value = openIds.get(i + 1);
			if (0 != value) {
				UserInfo userInfo = MJCache.getCache().getUserInfo(value);
				if (null != userInfo) {
					this.roomUsers.add(userInfo);
				}
			}
		}
	}

	public ToJoinMsg(long roomId, int totalJuNum, int totalQuanNum, long creator, Map<Integer, Long> roomUsers, int[] opt,
			int order) {
		this(roomId, totalJuNum, totalQuanNum, roomUsers, opt, order);
		this.creator = creator;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public int getTotalJuNum() {
		return totalJuNum;
	}

	public void setTotalJuNum(int totalJuNum) {
		this.totalJuNum = totalJuNum;
	}

	public List<UserInfo> getRoomUsers() {
		return roomUsers;
	}

	public void setRoomUsers(List<UserInfo> roomUsers) {
		this.roomUsers = roomUsers;
	}

	public int[] getOpt() {
		return opt;
	}

	public void setOpt(int[] opt) {
		this.opt = opt;
	}

	public int getMeOrder() {
		return meOrder;
	}

	public void setMeOrder(int meOrder) {
		this.meOrder = meOrder;
	}

	public int getTotalQuanNum() {
		return totalQuanNum;
	}

	public void setTotalQuanNum(int totalQuanNum) {
		this.totalQuanNum = totalQuanNum;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}
}
