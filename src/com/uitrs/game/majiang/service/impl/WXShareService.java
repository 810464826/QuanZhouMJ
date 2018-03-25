package com.uitrs.game.majiang.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

import javax.websocket.Session;

import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.Share;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.websockets.WebSocketUtil;

public class WXShareService extends SupperService {
	// private static final String SHARE_URI =
	// "http://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa0c15c83d5c0f91f&redirect_uri=http://www.uitrs.com/Mahjong/tnmj&response_type=code&scope=snsapi_userinfo&state=wx&connect_redirect=1#wechat_redirect";
	private final Log log = Log.getLog(WXShareService.class);

	@Override
	public void doService(Session session, ReceiveMsg action) {
		// Room room = WSSessionKit.getRoom(session);
		if (PropKit.getBoolean("weixinMode")) {
			// 微信js需要的参数
			String appid = PropKit.get("appId");
			String secret = PropKit.get("appSecret");
			String tokenurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid
					+ "&secret=" + secret;
			String urlMsg = HttpKit.get(tokenurl);
			System.err.println("urlMsg:: "+urlMsg);
			String access_token = urlMsg.split(",")[0].substring(17).split("\"")[0];
			String xmlStr = HttpKit.get(
					"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi");
			String jsapi_ticket = xmlStr.split("\"")[9];
			// 排序
			String noncestr = getRandomString(15);
			String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
			// 这是分享的链接url 在这里加上分享的房间号
			String url = String.valueOf(action.getM());
			String signature2 = null;
			try {
				signature2 = signature(jsapi_ticket, timestamp, noncestr, url);
			} catch (UnsupportedEncodingException e) {
				log.error("signature failed!", e);
			}
			if (null != signature2) {
				String signature = signature2.toLowerCase();
				log.info("active/weixin jsapi_ticket:" + jsapi_ticket + "timestamp:" + timestamp + "noncestr:"
						+ noncestr + "url:" + url + "signature:" + signature);
				/* 将数据返回给前台 */
				Share share = new Share();
				share.setAppid(appid);
				share.setNoncestr(noncestr);
				share.setSignature(signature);
				share.setTimestamp(timestamp);
				share.setUrl(PropKit.get("httpPath"));
				SendMsg result = new SendMsg(action.getP(), true, action.getT());
				
				result.setM(JsonUtils.toJson(share));
				WebSocketUtil.sendAsyncMsg(result, session);
			}
		}
	}

	public static String getRandomString(int length) {
		return UUID.randomUUID().toString();
	}

	private String signature(String jsapi_ticket, String timestamp, String noncestr, String url)
			throws UnsupportedEncodingException {
		jsapi_ticket = "jsapi_ticket=" + jsapi_ticket;
		timestamp = "timestamp=" + timestamp;
		noncestr = "noncestr=" + noncestr;
		url = "url=" + url;
		String[] arr = new String[] { jsapi_ticket, timestamp, noncestr, url };
		// 将token、timestamp、nonce,url参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
			if (i != arr.length - 1) {
				content.append("&");
			}
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes("UTF-8"));
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		return tmpStr;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {

		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

}
