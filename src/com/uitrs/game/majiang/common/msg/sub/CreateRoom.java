package com.uitrs.game.majiang.common.msg.sub;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 创建房间信息,包括局数和玩法选项
 * 
 * @author lucio
 *
 */
public class CreateRoom extends AbstractMsg {
	private static final long serialVersionUID = -2319026446420615493L;
	/** 局数,8局,16局 */
	private int ju;
	/** 玩法选项,可多选,1:责任制;2:幺九杠;3:旋风杠;4:白八九杠;5:40算法 */
	private int[] opt;

	public CreateRoom() {
		super();
	}

	public CreateRoom(int ju) {
		super();
		this.ju = ju;
	}

	public CreateRoom(int ju, int[] opt) {
		super();
		this.ju = ju;
		this.opt = opt;
	}

	public int getJu() {
		return ju;
	}

	public void setJu(int ju) {
		this.ju = ju;
	}

	public int[] getOpt() {
		return opt;
	}

	public void setOpt(int[] opt) {
		this.opt = opt;
	}

}
