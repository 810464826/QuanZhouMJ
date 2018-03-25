package com.uitrs.game.majiang.web.controller;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.SnsAccessToken;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import com.jfinal.weixin.sdk.utils.HttpUtils;
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.IdGen;
import com.uitrs.game.majiang.common.kit.MJUtil;
import com.uitrs.game.majiang.common.kit.PrintKit;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.web.common.controller.ControllerPath;

/**
 * 游戏首页
 * 
 * @author lucio
 *
 */
@ControllerPath(controllerKey = "/qzmj")
public class FrontController extends Controller {
	private static final String SHARE_URI = "http://open.weixin.qq.com/connect/oauth2/authorize?appid=appid&redirect_uri=uri&response_type=code&scope=snsapi_userinfo&state=wx&connect_redirect=1#wechat_redirect";

	// 外部首页
	public void index() {
		System.err.println("index url: " + PropKit.get("httpPath") + this.getRequest().getRequestURI() + "?"
				+ this.getRequest().getQueryString());

		String roomId = this.getPara("roomId");
		if (StrKit.isBlank(roomId)) {
			roomId = this.getPara("state");
		}
		UserInfo userInfo = getOpenId();
		if (null != userInfo) {
			String state = this.getPara("state");
			if (StrKit.notBlank(state) && !state.startsWith("wx")) {
				userInfo.setRoomId(roomId);
			}
			// 构建微信分享的配置信息
			// Share wxShare = buildShareInfo(userInfo.getUserId());
			// if (null != wxShare) {
			// this.setAttr("wxShare", wxShare);
			// }

			render("/index.html");
		} else {
			try {
				String redirectUri = SHARE_URI.replaceAll("appid=appid", "appid=" + PropKit.get("appId")).replaceAll(
						"redirect_uri=uri", "redirect_uri=" + PropKit.get("httpPath") + "/" + PropKit.get("ruleType"));
				System.err.println("redirectUri:: " + redirectUri);
				if (StrKit.isBlank(roomId)) {
					getResponse().sendRedirect(redirectUri);
				} else {
					getResponse().sendRedirect(redirectUri.replaceAll("state=wx", "state=" + roomId));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			render("/reindex.html");
		}
	}

	/**
	 * 更新缓存
	 */
	public void refreshConfig() {
		String type = this.getPara("t");
		if (StrKit.notBlank(type)) {
			PrintKit.red(PropKit.get("version"));
			PropKit.useless("develop.properties");
			PropKit.use("develop.properties");
			PrintKit.red(PropKit.get("version"));
		}
		renderText("Refresh Config Successfull!");
	}

	// 获取微信ID,没有则使用ip
	public UserInfo getOpenId() {
		String openId = this.getPara("openId");
		// 测试环境
		if (!PropKit.getBoolean("weixinMode", false)) {
			// 构造玩家信息
			if (StringUtils.isEmpty(openId)) {
				openId = IdGen.getInstance().getUserId() + "";
			}
			UserInfo ui = new UserInfo(openId);
			ui.setUserId(Long.parseLong(openId));
			ui.setNickName("昵称" + openId);
			ui.setFaceIcon(PropKit.get("httpPath") + MJConst.IMG_DEFAULT_MEN_JPG);
			ui.setSex(2);
			ui.setIp(this.getRequest().getRemoteAddr());
			// 更新用户头像
			updateFaceIcon(ui);
			this.setAttr("userInfo", ui);
			return ui;
		} else {
			String code = getPara("code");
			if (!StringUtils.isEmpty(code)) {
				SnsAccessToken token = SnsAccessTokenApi.getSnsAccessToken(PropKit.get("appId"),
						PropKit.get("appSecret"), code);
				openId = token.getOpenid();
				String atoken = token.getAccessToken();
				String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + atoken + "&openid=" + openId
						+ "&lang=zh_CN ";
				if (!StringUtils.isEmpty(openId)) {
					try {
						String jsonStr = HttpUtils.get(url);
						ApiResult apiResult = new ApiResult(jsonStr);
						return fetchUserInfo(apiResult, openId);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	private UserInfo fetchUserInfo(ApiResult apiResult, String openId) {
		UserInfo ui = new UserInfo(openId);
		this.setAttr("userInfo", ui);
		ui.setIp(this.getRequest().getRemoteAddr());
		ui.setFaceIcon(apiResult.getStr("headimgurl"));
		ui.setNickName(apiResult.getStr("nickname"));
		ui.setSex(apiResult.getInt("sex"));
		// 设置邀请者ID
		String para = this.getPara("parentId");
		if (StrKit.notBlank(para)) {
			ui.setParentId(Integer.parseInt(para.toString()));
		}

		// 更新用户头像
		updateFaceIcon(ui);

		return ui;
	}

	private void updateFaceIcon(UserInfo ui) {
		// 下载用户头像到本地
		String headFace = ui.getFaceIcon();
		String facePath = PropKit.get("facePath");
		String imgPath = "/img/faceicons/" + ui.getOpenId() + ".jpg";
		String httpPath = PropKit.get("httpPath");
		if (MJUtil.download(headFace,
				(StringUtils.isEmpty(facePath) ? PathKit.getWebRootPath() : facePath) + imgPath)) {
			ui.setFaceIcon(httpPath + imgPath);
		} else {
			if (ui.getSex() == 1) {
				ui.setFaceIcon(httpPath + MJConst.IMG_DEFAULT_MEN_JPG);
			} else {
				ui.setFaceIcon(httpPath + MJConst.IMG_DEFAULT_CUTE_JPG);
			}
		}
	}

	public static String getRandomString(int length) {
		return UUID.randomUUID().toString();
	}

}
