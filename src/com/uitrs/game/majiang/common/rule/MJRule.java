package com.uitrs.game.majiang.common.rule;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.room.Room;

public abstract class MJRule implements Serializable {
	private static final long serialVersionUID = -8026278308500809307L;

	protected Room room;
	/** 金牌(赖子) */
	protected String goldCard;
	/** 剩余的牌 */
	protected int minCardNum;

	/**
	 * 允许出的牌
	 */
	public abstract String[] enablePais();

	/**
	 * 创建每局的新牌序
	 */
	public abstract String[] createPais();

	/**
	 * 设置创建参数
	 * @param juNum 
	 * @param opts
	 * @param createInfo
	 */
	public abstract void createRoom(int juNum, int[] opts, PlayerInfo createInfo);
	/**
	 * 计算圈数和局数
	 */
	// public abstract String[] calcJuNum();
	/**
	 * 检查拿牌后自己是否有杠和自摸
	 * 
	 * @param room
	 * @param playerInfo
	 * @param faPai
	 * @param selfMsg
	 */
	public abstract void checkSelf(Room room, PlayerInfo playerInfo, String faPai, SendMsg selfMsg);
	/**
	 * 检查听牌结果
	 */
	public abstract Set<String> checkTingCards(Room room, PlayerInfo playerInfo);

	/**
	 * 检查出牌操作
	 */
	public abstract List<OptCheckMsg> checkOutOpts(Room room, PlayerInfo operator, long from, String outCard);

	public String getGoldCard() {
		return goldCard;
	}

	public void setGoldCard(String goldCard) {
		this.goldCard = goldCard;
	}

	/**
	 * 根据名字获取麻将规则
	 * 
	 * @param name
	 * @return MJRule实例
	 */
	public static MJRule getRule(String name) {
		if (!StringUtils.isEmpty(name)) {
			if (FZMJRule.RULE_NAME.equalsIgnoreCase(name)) {
				return new FZMJRule();
			} else if (TNMJRule.RULE_NAME.equalsIgnoreCase(name)) {
				return new TNMJRule();
			} else if (QZMJRule.RULE_NAME.equalsIgnoreCase(name)) {
				return new QZMJRule();
			}
		}
		return null;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public int getMinCardNum() {
		return minCardNum;
	}

	public void setMinCardNum(int minCardNum) {
		this.minCardNum = minCardNum;
	}

}
