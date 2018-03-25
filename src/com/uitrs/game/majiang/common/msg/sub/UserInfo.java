package com.uitrs.game.majiang.common.msg.sub;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 用户基本信息
 * 
 * @author lucio
 *
 */
public class UserInfo extends AbstractMsg {

	private static final long serialVersionUID = -5142585206132452336L;
	/** 系统用户ID */
	protected long userId;
	/** 用户昵称,可以来自微信或其他第三方登录 */
	protected String nickName;
	/** 第三方登录的ID,如微信的openid */
	protected String openId;
	/** 头像地址 */
	protected String faceIcon;
	/** IP地址 */
	protected String ip;
	/** 纬度 */
	protected double lat;
	/** 经度 */
	protected double lon;
	/** 房间ID */
	protected String roomId;
	/** 邀请者ID */
	protected int parentId;
	/** 性别 */
	protected int sex;
	/** 现有房卡数 */
	protected int cards;
	/** 状态,在线1,离线 0 */
	protected int state;
	/** 是否准备好了 */
	protected boolean ready;

	public UserInfo() {
		super();
	}

	public UserInfo(String openId) {
		super();
		this.openId = openId;
	}

	public UserInfo(long userId) {
		super();
		this.userId = userId;
	}

	public UserInfo(long userId, String openId) {
		super();
		this.userId = userId;
		this.openId = openId;
	}

	public UserInfo(String nickName, String openId, String faceIcon) {
		super();
		this.nickName = nickName;
		this.openId = openId;
		this.faceIcon = faceIcon;
	}

	public UserInfo(long userId, String nickName, String openId, String faceIcon, int cards) {
		super();
		this.userId = userId;
		this.nickName = nickName;
		this.openId = openId;
		this.faceIcon = faceIcon;
		this.cards = cards;
	}

	public int getCards() {
		return cards;
	}

	public void setCards(int cards) {
		this.cards = cards;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getFaceIcon() {
		return faceIcon;
	}

	public void setFaceIcon(String faceIcon) {
		this.faceIcon = faceIcon;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
}
