package com.uitrs.game.majiang.common.msg.sub;

import java.util.List;
import java.util.Map;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 一局完成后的结果,统计分数等
 * 
 * @author lucio
 *
 */
public class JuResult extends AbstractMsg {

	private static final long serialVersionUID = -7425721308074365711L;
	/** 用户ID */
	private String openId;
	/** 已经操作的类型,碰、扛等 */
	private List<Map<String, String>> cardsShow;
	/** 手中的牌 */
	private String[] handCards;
	/** 只与自己相关的胡牌类型(主要根据自己本身的信息判断) */
	private Map<String, Object> huType;
	/** 与别人相关的胡牌类型 */
	private Map<String, Object> type;
	/** 本局得分 */
	private int currentScore;
	/** 本房间目前得分 */
	private int roomScore;

	public JuResult() {
		super();
	}

	public JuResult(String openId) {
		super();
		this.openId = openId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public List<Map<String, String>> getCardsShow() {
		return cardsShow;
	}

	public void setCardsShow(List<Map<String, String>> cardsShow) {
		this.cardsShow = cardsShow;
	}

	public String[] getHandCards() {
		return handCards;
	}

	public void setHandCards(String[] handCards) {
		this.handCards = handCards;
	}

	public Map<String, Object> getHuType() {
		return huType;
	}

	public void setHuType(Map<String, Object> huType) {
		this.huType = huType;
	}

	public Map<String, Object> getType() {
		return type;
	}

	public void setType(Map<String, Object> type) {
		this.type = type;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int getRoomScore() {
		return roomScore;
	}

	public void setRoomScore(int roomScore) {
		this.roomScore = roomScore;
	}
}
