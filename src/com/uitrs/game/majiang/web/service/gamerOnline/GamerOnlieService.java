package com.uitrs.game.majiang.web.service.gamerOnline;

import java.util.Date;
import java.util.List;

import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.GamerInfo;
import com.uitrs.game.majiang.web.model.GamerOnline;
import com.uitrs.game.majiang.web.service.gamerInfo.GamerInfoService;

/**
 * 玩家在线充值列表
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:56:02
 */
public class GamerOnlieService {
	private static GamerOnlieService gamerOnlieService = new GamerOnlieService();

	private GamerOnlieService() {

	}

	public static GamerOnlieService getService() {
		return gamerOnlieService;
	}

	/**
	 * 根据id查询当前的充值
	 */
	public GamerOnline getOnlinePayById(int id) {
		return GamerOnline.dao.findById(id);
	}

	/**
	 * 根据id查询当前的充值
	 */
	public GamerOnline getOnlinePayByGamerId(int gamerId) {
		return GamerOnline.dao.findFirst(
				"select * from onlinepay where gamerId = ? and state = 0 order by payTime desc limit 1 ", gamerId);
	}

	/**
	 * 根据id查询当前的充值
	 */
	public boolean deleteOnlinePayById(int id) {
		return GamerOnline.dao.deleteById(id);
	}

	/**
	 * 保存充值的信息
	 */
	public boolean addOnlinePay(int gamerId, String payType, Double money) {
		GamerOnline gamerOnline = new GamerOnline();
		GamerInfo gamerInfo = GamerInfoService.getService().getGamerById(gamerId);
		return gamerOnline.set("gamerId", gamerId).set("headFace", gamerInfo.getHeadFace()).set("payType", payType).set("payMoney", money).set("state", 0)
				.set("payTime", Tools.date2String(new Date())).save();
	}
	
	/**
	 * 获取所有的在线充值记录
	 */
	public List<GamerOnline> getAllOnlinePay(){
		return GamerOnline.dao.find(
				"select * from onlinepay order by payTime desc limit 6 ");
	}
	
	/**
	 * 获取指定玩家的在线充值的记录
	 */
	public List<GamerOnline> getGamerPayRecord(int gamerId){
		return GamerOnline.dao.find("select * from onlinepay where gamerId = ? order by payTime desc limit 6 ",gamerId);
	}
}
