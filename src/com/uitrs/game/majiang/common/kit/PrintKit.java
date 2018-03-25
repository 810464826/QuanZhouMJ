package com.uitrs.game.majiang.common.kit;

/**
 * 开发阶段打印信息用,一般用于非必要使用log文件记录的信息
 * 
 * @author lucio
 *
 */
public class PrintKit {

	/** 打印开关 */
	public static boolean isDebug = false;

	public static void red(Object err) {
		if (isDebug)
			System.err.println(err);
	}

	public static void red(Object err, Exception e) {
		if (isDebug)
			System.err.println(err);
		e.printStackTrace();
	}

	public static void out(Object msg) {
		if (isDebug)
			System.out.println(msg);
	}

	// public static void outln(String msg) {
	// if (isDebug)
	// System.out.println(msg);
	// }
}
