package com.uitrs.game.majiang.common.kit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.uitrs.game.majiang.common.command.Command;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.MsgType;
import com.uitrs.game.majiang.common.msg.SendMsg;

public final class MJUtil {

	/**
	 * 根据操作类型获取优先级
	 * 
	 * @param type
	 * @return 优先级
	 */
	public static int convertLevel(int type) {
		if (type == IMsg.HU_TYPE) {
			return Command.ONE_LEVEL;
		} else if (type == IMsg.PONG || type == IMsg.PENG_TYPE || type == IMsg.AN_GANG || type == IMsg.BU_GANG
				|| type == IMsg.GANG_TYPE || type == IMsg.MING_GANG) {
			return Command.TWO_LEVEL;
		} else if (type == IMsg.CHI_TYPE) {
			return Command.THREE_LEVEL;
		} else {
			return Command.NINE_LEVEL;
		}
	}

	/**
	 * 将操作类型转换成字符串
	 * 
	 * @param type
	 * @return String
	 */
	public static String convertOptType(int type) {
		for (MsgType msgType : MsgType.values()) {
			if (type == msgType.getCode()) {
				return msgType.getName();
			}
		}
		return "";
		// if (type == IMsg.AN_GANG) {
		// return "anGang";
		// } else if (type == IMsg.PONG) {
		// return "pong";
		// } else if (type == IMsg.CHI_TYPE) {
		// return "chi";
		// } else if (type == IMsg.MING_GANG) {
		// return "mingGang";
		// } else if (type == IMsg.BU_GANG) {
		// return "buGang";
		// } else if (type == IMsg.SPECIAL_GANG) {
		// return "specialGang";
		// } else if (type == IMsg.SPECIAL_JIA_GANG) {
		// return "specialBuGang";
		// } else {
		// return "";
		// }
	}

	/**
	 * 下载文件到本地
	 *
	 * @param urlString
	 *            被下载的文件地址
	 * @param filename
	 *            本地文件名
	 * @throws Exception
	 *             各种异常
	 */
	public static boolean download(String urlString, String filename) {
		InputStream is = null;
		OutputStream os = null;
		try {
			// 构造URL
			URL url = new URL(urlString);
			// 打开连接
			URLConnection con = url.openConnection();
			// 输入流
			is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			os = new FileOutputStream(filename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 完毕，关闭所有链接
				if (null != os) {
					os.close();
				}
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 判断是不是打牌操作类消息
	 * 
	 * @param msg
	 * @return boolean
	 */
	public static boolean isOptMsg(SendMsg msg) {
		if (msg.getT() == IMsg.OUT_CARD_TYPE || msg.getT() == IMsg.NA_TYPE || msg.getT() == IMsg.BU_HUA
				|| msg.getT() == IMsg.HU_TYPE || msg.getT() == IMsg.PENG_TYPE || msg.getT() == IMsg.MING_GANG
				|| msg.getT() == IMsg.AN_GANG || msg.getT() == IMsg.BU_GANG || msg.getT() == IMsg.THREE_GOLD_HU
				|| msg.getT() == IMsg.THREE_GOLD || msg.getT() == IMsg.TWO_GOLD || msg.getT() == IMsg.ONE_GOLD
				|| msg.getT() == IMsg.ZIMO_TYPE || msg.getT() == IMsg.DISMISSING_TYPE || msg.getT() == IMsg.DISMISS_TYPE
				|| msg.getT() == IMsg.JU_SCORE_TYPE || msg.getT() == IMsg.ROOM_SCORE_TYPE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是不是需要缓存的消息
	 * 
	 * @param msg
	 * @return boolean
	 */
	public static boolean isAddMsg(SendMsg msg) {
		if (msg.getT() == IMsg.START_TYPE || msg.getT() == IMsg.READY_TYPE || msg.getT() == IMsg.OUT_CARD_TYPE
				|| msg.getT() == IMsg.NA_TYPE || msg.getT() == IMsg.BU_HUA || msg.getT() == IMsg.HU_TYPE
				|| msg.getT() == IMsg.PENG_TYPE || msg.getT() == IMsg.MING_GANG || msg.getT() == IMsg.AN_GANG
				|| msg.getT() == IMsg.BU_GANG || msg.getT() == IMsg.THREE_GOLD_HU || msg.getT() == IMsg.THREE_GOLD
				|| msg.getT() == IMsg.TWO_GOLD || msg.getT() == IMsg.ONE_GOLD || msg.getT() == IMsg.ZIMO_TYPE
				|| msg.getT() == IMsg.DISMISSING_TYPE || msg.getT() == IMsg.DISMISS_TYPE
				|| msg.getT() == IMsg.JU_SCORE_TYPE || msg.getT() == IMsg.ROOM_SCORE_TYPE) {
			return true;
		} else {
			return false;
		}
	}
}
