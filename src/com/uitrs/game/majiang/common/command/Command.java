package com.uitrs.game.majiang.common.command;

/**
 * 命令接口
 * 
 * @author lucio
 *
 */
public interface Command {

	/** 第1优先级 */
	int ONE_LEVEL = 1;
	/** 第二优先级 */
	int TWO_LEVEL = 2;
	/** 第三优先级 */
	int THREE_LEVEL = 3;
	/** 第四优先级 */
	int FOUR_LEVEL = 4;
	/** 第九优先级 */
	int NINE_LEVEL = 9;

	/**
	 * 命令等级,等级越高优先级越高
	 */
	int level();

	/**
	 * 执行命令
	 */
	void execute();
}
