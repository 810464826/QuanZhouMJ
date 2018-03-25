package com.uitrs.game.majiang.common.msg.sub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.AbstractMsg;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;

/**
 * 加入房间/重连重构消息
 * 
 * @author lucio
 *
 */
public class JoinMsg extends AbstractMsg {
	private static final long serialVersionUID = 4558409041764615449L;
	private Map<String, String> room;
	/** 房间所有玩家信息 */
	private List<PlayerInfo> players;
	/** 重连者userId */
	private long p;
	/** 重连者未处理消息 */
	private SendMsg msg;

	public JoinMsg() {
		super();
		this.players = new ArrayList<PlayerInfo>();
		this.room = new HashMap<String, String>();
	}

	public JoinMsg(Room room, long p) {
		super();
		this.p = p;
		players = new ArrayList<PlayerInfo>();
		this.room = new HashMap<String, String>();
		buildRoom(room);
	}

	public IMsg getMsg() {
		return msg;
	}

	public void setMsg(SendMsg msg) {
		this.msg = msg;
	}

	/**
	 * 构建客户端需要的房间信息
	 * 
	 * @param Room
	 */
	public void buildRoom(Room room) {
		this.room.put("roomId", room.getRoomId() + "");
		this.room.put("totalJuNum", room.getTotalJuNum() + "");
		this.room.put("useJuNum", room.getUseJuNum() + "");
		this.room.put("totalQuanNum", room.getTotalQuanNum() + "");
		this.room.put("useQuanNum", room.getUseQuanNum() + "");
		this.room.put("opt", JsonUtils.toJson(room.getOpt()));
		this.room.put("cardNum", room.getCardNum() + "");
		this.room.put("zhuangId", room.getZhuangId() + "");
		this.room.put("creator", room.getCreateId() + "");
		this.room.put("goldCard", room.getMjRule().getGoldCard());
		this.room.put("activer", room.getActiver() + "");
		this.room.put("step", room.getStep() + "");
		this.room.put("status", room.getStatus() + "");
	}

	public Map<String, String> getRoom() {
		return room;
	}

	public void setRoom(Map<String, String> room) {
		this.room = room;
	}

	public List<PlayerInfo> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerInfo> players) {
		this.players = players;
	}

	public void addPlayers(PlayerInfo player) {
		this.players.add(player);
	}

	public void removePlayers(PlayerInfo player) {
		this.players.remove(player);
	}

	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}
}
