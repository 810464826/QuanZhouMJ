package com.uitrs.game.majiang.web.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import com.jfinal.plugin.activerecord.Model;

public class Tools {
	/**
	 * 
	 * map转换json. <br>
	 * 详细说明
	 * 
	 * @param map
	 *            集合
	 * @return
	 * @return String json字符串
	 * @throws @author
	 *             slj
	 */
	public static String mapToJson(Map<String, Object> map) {
		Set<String> keys = map.keySet();
		String key = "";
		Object value;
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("{");
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			key = (String) it.next();
			value = map.get(key);
			if (value instanceof java.util.List) {
				jsonBuffer.append("\"" + key + "\":" + value);
			} else {
				jsonBuffer.append("\"" + key + "\":" + "\"" + value + "\"");
			}

			if (it.hasNext()) {
				jsonBuffer.append(",");
			}
		}
		jsonBuffer.append("}");
		return jsonBuffer.toString();
	}

	/**
	 * 计算总页数
	 */
	public static int getSize(int size) {
		// 这是能被10整除
		if (size % 10 == 0) {
			return size / 10;
		} else {
			return size / 10 + 1;
		}
	}

	/**
	 * 将时间转为String 时分秒
	 */
	public static String date2String(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		return str;
	}
	/**
	 * 将时间转为String 时分
	 */
	public static String date2StringSecond(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = sdf.format(date);
		return str;
	}
	/**
	 * 将时间转为String 时分秒
	 */
	public static String dateNoSFM2String(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(date);
		return str;
	}

	/**
	 * 将时间转为String 没有时分秒
	 */
	public static Date StringNoSFM2Date(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/** 保留n位小数 */
	public static <T> String formatN(T val, int n) {
		char[] zero = new char[n];
		for (int i = 0; i < n; i++) {
			zero[i] = '0';
		}
		String str = String.valueOf(val);
		int pos = str.lastIndexOf('.');
		if (pos == -1) {
			pos = str.length();
			str += "." + new String(zero);
		} else {
			str += new String(zero);
		}
		return str.substring(0, pos + n + 1);
	}

	/** 将Model类的子类对象转换成JSON格式 */
	public static <T extends Model<T>> String toJson(@SuppressWarnings("rawtypes") List list) {
		int len = list.size();
		List<String> resultArr = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			@SuppressWarnings("unchecked")
			T m = (T) list.get(i);
			resultArr.add(m.toJson());
		}
		return resultArr.toString();
	}

	/** float格式计算*加 */
	public static float Floatadd(float f1, float f2) {
		BigDecimal re1 = new BigDecimal(String.valueOf(f1));
		BigDecimal re2 = new BigDecimal(String.valueOf(f2));
		float f3 = re1.add(re2).floatValue();
		return f3;
	}

	/** float格式计算*减 */
	public static float Floatsub(float f1, float f2) {
		BigDecimal re1 = new BigDecimal(String.valueOf(f1));
		BigDecimal re2 = new BigDecimal(String.valueOf(f2));
		float f3 = re1.subtract(re2).floatValue();
		return f3;
	}

	/** float格式计算*乘 */
	public static float Floatmultiply(float f1, float f2) {
		BigDecimal re1 = new BigDecimal(String.valueOf(f1));
		BigDecimal re2 = new BigDecimal(String.valueOf(f2));
		float f3 = re1.multiply(re2).floatValue();
		return f3;
	}

	public static void main(String[] args) {
		System.err.println(Floatmultiply(0f, 100f));
	}

	/** float格式计算*除 */
	public static float Floatdiv(float f1, float f2) {
		BigDecimal re1 = new BigDecimal(String.valueOf(f1));
		BigDecimal re2 = new BigDecimal(String.valueOf(f2));
		float f3 = re1.divide(re2).floatValue();
		return f3;
	}

	/** 数组转成以a隔开的字符串 */
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append("a");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	/** 以隔开a的字符串转成数组 */
	@SuppressWarnings("unchecked")
	public static List<String> StringToList(String str) {
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();
		list = Arrays.asList(str.split("a"));
		return list;
	}

	/**
	 * 去到指定的网页，获取网页数据。js生成数据使用HttpAnalyzerStdV7抓包
	 */
	public static String demo(String url) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		String html = null;
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = getMethod.getResponseBody();
			// 处理内容
			html = new String(responseBody, "UTF-8");
			System.out.println(html);
		} catch (Exception e) {
			System.err.println("页面无法访问");
		} finally {
			getMethod.releaseConnection();
		}
		return html;
	}
}
