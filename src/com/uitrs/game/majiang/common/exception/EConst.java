package com.uitrs.game.majiang.common.exception;

/**
 * 异常常量信息,异常码定义等
 * 
 * @author lucio
 *
 */
public interface EConst {
	/** 系统信息读取前缀 */
	String PREFIX = "error.";
	/** 系统异常 */
	int SYSTEM_EXCEPTION = 1000;

	/** 其他异常 */
	int OTHER_EXCEPTION = 9999;

	/** socket异常 */
	int SOCKET_EXCEPTION = 1002;
}
