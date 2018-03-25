package com.uitrs.web.common.kit;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName: MakeOrderNum
 * @CreateTime 2015年9月13日 下午4:51:02
 * @author : mayi
 * @Description: 订单号生成工具，生成非重复订单号，理论上限1毫秒1000个，可扩展
 * 
 */
public class UUIDGen {
	private static final String FORMAT_STRING = "yyHHMMmmddss";
	/**
	 * 订单号生成计数器
	 */
	private static long orderNumCount = 0L;
	/**
	 * 每毫秒生成订单号数量最大值
	 */
	private static int maxPerMSECSize = 1000;

	private static SecureRandom random = new SecureRandom();

	// /**
	// * 生成非重复订单号，理论上限1秒1000个，可扩展
	// *
	// * @param tname
	// * 测试用
	// */
	// private static void makeOrderNum(String tname) {
	// try {
	// // 最终生成的订单号
	// String finOrderNum = "";
	// synchronized (lockObj) {
	// // 取系统当前时间作为订单号变量前半部分，精确到毫秒
	// long nowLong = Long.parseLong(new
	// SimpleDateFormat(FORMAT_STRING).format(new Date()));
	// // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
	// if (orderNumCount > maxPerMSECSize) {
	// orderNumCount = 0L;
	// }
	// // 组装订单号
	// String countStr = "" + (maxPerMSECSize + orderNumCount);
	// finOrderNum =tname+ nowLong + countStr.substring(1);
	// orderNumCount++;
	// System.out.println(Base64.byteArrayToBase64(("k" +
	// finOrderNum).getBytes()) + ":" + finOrderNum + "--"
	// + Thread.currentThread().getName() + "::" + tname);
	// // Thread.sleep(1000);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return random.nextLong();
	}

	/**
	 * 
	 * @param tname
	 *            前缀
	 * @return
	 */
	public synchronized static String genOrderNum(String tname) {// 最终生成的订单号
		String finOrderNum = "";
		try {
			// 取系统当前时间作为订单号变量前半部分，精确到毫秒
			long nowLong = Long.parseLong(new SimpleDateFormat(FORMAT_STRING).format(new Date()));
			// 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
			if (orderNumCount > maxPerMSECSize) {
				orderNumCount = 0L;
			}
			// 组装订单号
			String countStr = maxPerMSECSize + orderNumCount + "";
			finOrderNum = tname + nowLong + countStr.substring(1);
			orderNumCount++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finOrderNum;
	}

	public static void main(String[] args) {
		// 测试多线程调用订单号生成工具
		try {
			for (int i = 0; i < 200; i++) {
				Thread t1 = new Thread(new Runnable() {
					public void run() {
						// MakeOrderNum makeOrder = new MakeOrderNum();
						String finOrderNum = UUIDGen.genOrderNum("m");
						System.out.println(finOrderNum + "--" + Thread.currentThread().getName() + "::" + "m");
					}
				}, "at" + i);
				t1.start();

				Thread t2 = new Thread(new Runnable() {
					public void run() {
						// MakeOrderNum makeOrder = new MakeOrderNum();
						String finOrderNum = UUIDGen.genOrderNum("e");
						System.out.println(finOrderNum + "--" + Thread.currentThread().getName() + "::" + "e");
					}
				}, "bt" + i);
				t2.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
