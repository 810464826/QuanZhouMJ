package com.uitrs.game.majiang.web.controller.mahjong.onlinePay;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.uitrs.game.majiang.web.common.MType;
import com.uitrs.game.majiang.web.model.GamerOnline;
import com.uitrs.game.majiang.web.service.gamerOnline.GamerOnlieService;
import com.uitrs.web.common.controller.ControllerPath;
import com.uitrs.web.common.interceptor.SessionInterceptor;

@Before({ SessionInterceptor.class })
@ControllerPath(controllerKey = "/onlinePay")
public class OnLinePayController extends Controller {
	/**
	 * 查询所有玩家在线充值的记录
	 */
	public void getAll() {
		List<GamerOnline> onlinePays = GamerOnlieService.getService().getAllOnlinePay();
		setAttr("onlinePay", onlinePays);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newOnlinePay.html");
	}
	
	/**
	 * 查询指定的充值记录
	 */
	public void getOneRecord(){
		Integer id = getParaToInt("id");
		GamerOnline gamerOnline = GamerOnlieService.getService().getOnlinePayById(id);
		setAttr("gamerOnline", gamerOnline);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newOnlinePayInfo.html");
	}
	
	/**
	 * 查询指定玩家的充值记录
	 */
	public void getGamerPayRecord(){
		String para = getPara("gamerId");
		if(!StringUtils.isEmpty(para)){
			Integer gamerId = getParaToInt("gamerId");
			List<GamerOnline> gamerPayRecord = GamerOnlieService.getService().getGamerPayRecord(gamerId);
			setAttr("onlinePay", gamerPayRecord);
			String device = MType.getType().getDevice();
			render("/WEB-INF/" + device + "/default/mahjong/newOnlinePay.html");
		}else{
			String device = MType.getType().getDevice();
			render("/WEB-INF/" + device + "/default/mahjong/error.html");
		}
	}
}
