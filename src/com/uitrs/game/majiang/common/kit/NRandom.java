package com.uitrs.game.majiang.common.kit;

import java.util.Random;

/**
 * 数组乱序
 * 
 * @author lucio
 *
 */
public class NRandom {
	/**
	 * 对给定数目的自0开始步长为1的数字序列进行乱序
	 * 
	 * @param no
	 *            给定数目
	 * @return 乱序后的数组
	 */
	public static int[] getSequence(int no) {
		int[] sequence = new int[no];
		for (int i = 0; i < no; i++) {
			sequence[i] = i;
		}
		Random random = new Random();
		for (int i = 0; i < no; i++) {
			int p = random.nextInt(no);
			int tmp = sequence[i];
			sequence[i] = sequence[p];
			sequence[p] = tmp;
		}
		random = null;
		return sequence;
	}

	/**
	 * 对给定的String数组进行乱序
	 * 
	 * @param no
	 *            给定String数组
	 * @return 乱序后的String数组
	 */
	public static String[] sequence(String[] no) {
		String[] sequence = new String[no.length];
		for (int i = 0; i < no.length; i++) {
			sequence[i] = no[i];
		}
		Random random = new Random();
		for (int i = 0; i < no.length; i++) {
			int p = random.nextInt(no.length);
			String tmp = sequence[i];
			sequence[i] = sequence[p];
			sequence[p] = tmp;
		}
		random = null;
		return sequence;
	}

	/**
	 * 获取数组中随机的一个
	 * 
	 * @param ss
	 * @return String
	 */
	public static String randomString(String[] ss) {
		Random random = new Random();
		return ss[random.nextInt(ss.length)];
	}

	public static void main(String[] args) {
		String[] news = NRandom.sequence(new String[] { "a", "b", "c", "d", "e", "f", "i", "j", "k" });
		for (int i = 0; i < news.length; i++) {
			System.err.print(news[i] + ", ");
		}
	}
}
