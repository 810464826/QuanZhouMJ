package com.uitrs.game.majiang.common.command;

import com.uitrs.game.majiang.common.msg.SendMsg;

/**
 * 发送消息的抽象类
 * 
 * @author lucio
 *
 */
public abstract class SendMsgCommand implements Command {
	/** 要发送的消息 */
	protected SendMsg msg;
	/** 命令等级 */
	protected int level;
	/** 位置 */
	protected int order;

	/** 出牌人位置 */
	protected int baseOrder;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int level() {
		return this.level;
	}

	public SendMsg getMsg() {
		return msg;
	}

	public void setMsg(SendMsg msg) {
		this.msg = msg;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getBaseOrder() {
		return baseOrder;
	}

	public void setBaseOrder(int baseOrder) {
		this.baseOrder = baseOrder;
	}
}
