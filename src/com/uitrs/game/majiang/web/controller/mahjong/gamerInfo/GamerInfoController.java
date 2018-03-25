package com.uitrs.game.majiang.web.controller.mahjong.gamerInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.web.common.MType;
import com.uitrs.game.majiang.web.model.GamerInfo;
import com.uitrs.game.majiang.web.model.SuperPage;
import com.uitrs.game.majiang.web.service.adminInfo.AdminInfoService;
import com.uitrs.game.majiang.web.service.gamerInfo.GamerInfoService;
import com.uitrs.web.common.controller.ControllerPath;
import com.uitrs.web.common.interceptor.SessionInterceptor;

/**
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:42:05
 */
@Before({ SessionInterceptor.class })
@ControllerPath(controllerKey = "/gamerInfo")
public class GamerInfoController extends Controller {
	/**
	 * 查询所有玩家信息
	 */
	public void getAll() {
		// 前台传递当前页
		Map<String, Object> datas = new HashMap<String, Object>();
		Map<String, Object> gamerInfo = GamerInfoService.getService().getAllGamer(getParaToInt("curentPage"), 10);
		datas.put("curentPage", gamerInfo.get("curentPage"));
		datas.put("totalPages", gamerInfo.get("totalPages"));
		datas.put("message", gamerInfo.get("list"));
		renderJson(datas);
	}

	/**
	 * 查询所有的玩家 返回list
	 */
	public void getPlayers() {
		List<GamerInfo> players = GamerInfoService.getService().getPlayers();
		setAttr("players", players);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newPlayer.html");
	}

	/**
	 * 模糊查询指定的玩家列表
	 */
	public void getSamePlayers() {
		String para = getPara("gamerId");
		if (para == null || para.length() == 0) {
			String device = MType.getType().getDevice();
			render("/WEB-INF/" + device + "/default/mahjong/error.html");
			return;
		}
		List<GamerInfo> players = GamerInfoService.getService().getMHPlayersById(Integer.parseInt(para));
		setAttr("players", players);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newPlayer.html");
	}

	/**
	 * 查询指定信息的玩家
	 */
	public void getGamer() {
		String openId = getPara("openId");
		String nickName = getPara("nickName");
		SuperPage<GamerInfo> gamerPage = GamerInfoService.getService().getGamerPage(openId, nickName);
		if (gamerPage != null) {
			String json = JsonUtils.toJson(gamerPage);
			renderJson(json);
		} else {
			renderJson("false");
		}
	}

	/**
	 * 查询指定的玩家
	 */
	public void getPlayer() {
		String nickName = getPara("name");
		GamerInfo player = GamerInfoService.getService().getPlayer(nickName);
		setAttr("player", player);
		setAttr("person",
				AdminInfoService.getService().getAdminByUserName((String) getSession().getAttribute("username")));
		if (player != null) {
			String device = MType.getType().getDevice();
			render("/WEB-INF/" + device + "/default/mahjong/newPlayerInfo.html");
		}
	}

	/**
	 * 修改玩家信息
	 */
	public void editGammer() {
		int id = getParaToInt("id");
		String openId = getPara("openId");
		int sex = getParaToInt("sex");
		String pic = getPara("pic");
		String name = getPara("name");
		int nums = getParaToInt("nums");
		int num = getParaToInt("num");
		String phone = getPara("phone");
		String QQ = getPara("QQ");
		boolean b = GamerInfoService.getService().editGammer(id, openId, sex, name, pic, nums, num, phone, QQ);
		if (b) {
			getPlayers();
		}
	}

}
