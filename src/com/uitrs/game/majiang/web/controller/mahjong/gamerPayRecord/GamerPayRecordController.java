package com.uitrs.game.majiang.web.controller.mahjong.gamerPayRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.web.common.MType;
import com.uitrs.game.majiang.web.model.AdminInfo;
import com.uitrs.game.majiang.web.model.GamerInfo;
import com.uitrs.game.majiang.web.model.GamerPayRecord;
import com.uitrs.game.majiang.web.model.SuperPage;
import com.uitrs.game.majiang.web.service.adminInfo.AdminInfoService;
import com.uitrs.game.majiang.web.service.cardType.CardTypeService;
import com.uitrs.game.majiang.web.service.gamerInfo.GamerInfoService;
import com.uitrs.game.majiang.web.service.gamerPayRecord.GamerPayService;
import com.uitrs.web.common.controller.ControllerPath;
import com.uitrs.web.common.interceptor.SessionInterceptor;

/**
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:42:09
 */
@Before({ SessionInterceptor.class })
@ControllerPath(controllerKey = "/gamerPay")
public class GamerPayRecordController extends Controller {
	/**
	 * 查询所有充值记录
	 */
	public void getAll() {
		int adminId = (int) getSession().getAttribute("adminId");
		Map<String, Object> payRecord = GamerPayService.getService().getAdminAllPayRecord(getParaToInt("curentPage"),
				10, adminId);
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("curentPage", payRecord.get("curentPage"));
		datas.put("totalPages", payRecord.get("totalPages"));
		datas.put("message", payRecord.get("list"));
		renderJson(datas);
	}

	/**
	 * 查询指定代理商得充值记录
	 */
	public void getByGamerId() {
		String admd = getPara("gamerId");
		if (admd != null && admd.length() != 0) {
			SuperPage<GamerPayRecord> page = GamerPayService.getService().getGamerPayRecordPage(Integer.parseInt(admd));
			if (page != null) {
				String json = JsonUtils.toJson(page);
				renderJson(json);
			} else {
				renderJson("false");
			}
		} else {
			SuperPage<GamerPayRecord> page = GamerPayService.getService().getGamerPayRecordPageByNoId();
			if (page != null) {
				String json = JsonUtils.toJson(page);
				renderJson(json);
			} else {
				renderJson("false");
			}
		}

	}

	/**
	 * 给管理员代理商充值的页面
	 */
	public void toAddCard() {
		String device = MType.getType().getDevice();
		List<Object> types = CardTypeService.getService().getAllTypes();
		setAttr("types", types);
		render("/WEB-INF/" + device + "/default/mahjong/add_room_gamer.html");
	}

	/**
	 * 给玩家充值
	 */
	public void payForGamer() {
		int adminId = (int) getSession().getAttribute("adminId");
		Integer gamerId = getParaToInt("gamerId");
		GamerInfo gamerById = GamerInfoService.getService().getGamerById(gamerId);
		if (gamerById == null) {
			renderJson("result", false);
			return;
		}
		String payType = getPara("payType");
		double payMoney = Double.parseDouble(getPara("payMoney"));
		boolean payCardForGamer = GamerPayService.getService().payCardForGamer(payMoney, adminId, gamerId, payType);
		renderJson("result", payCardForGamer);
	}

	/**
	 * 玩家充值得页面
	 */
	public void getAllRecords() {
		List<GamerPayRecord> records = new ArrayList<>();
		String username = (String) getSession().getAttribute("username");
		AdminInfo admin = AdminInfoService.getService().getAdminByUserName(username);
		if (admin.getSysType() == 0) {
			records = GamerPayService.getService().getRecords();
		} else {
			records = GamerPayService.getService().getSameRecordsByAdminId(admin.getAdminId());
		}
		setAttr("records", records);
		setAttr("person", admin);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newPayForGamer.html");
	}

	/**
	 * 这是获取某一条具体得充值记录
	 */
	public void getOneRecord() {
		int id = getParaToInt("name");
		Integer type = getParaToInt("type");
		GamerPayRecord record = GamerPayService.getService().getOneRecords(id);
		String username = (String) getSession().getAttribute("username");
		AdminInfo admin = AdminInfoService.getService().getAdminByUserName(username);
		setAttr("record", record);
		setAttr("type", type);
		setAttr("type", admin.getSysType());
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newPayInfoForGamer.html");
	}

	/**
	 * 根据id删除
	 */
	public void deleteRecord() {
		int id = getParaToInt("id");
		boolean deleteRecord = GamerPayService.getService().deleteRecord(id);
		renderJson("result", deleteRecord);
	}

	/**
	 * 去向充值页面 手机
	 */
	public void goToGamerPayMobile() {
		String device = MType.getType().getDevice();
		List<Object> types = CardTypeService.getService().getAllTypes();
		setAttr("types", types);
		render("/WEB-INF/" + device + "/default/mahjong/newAddPayInfoForGamer.html");
	}

	/**
	 * 查询输入的玩家ID的充值记录
	 */
	public void getSameRecords() {
		String gamid = getPara("gamerId");
		if (gamid != null && gamid.length() != 0) {
			List<GamerPayRecord> gamerPayRecord = GamerPayService.getService()
					.getSameRecordsByGamerId(Integer.parseInt(getPara("gamerId")));
			String username = (String) getSession().getAttribute("username");
			AdminInfo admin = AdminInfoService.getService().getAdminByUserName(username);
			setAttr("person", admin);
			setAttr("records", gamerPayRecord);
			String device = MType.getType().getDevice();
			render("/WEB-INF/" + device + "/default/mahjong/newPayForGamer.html");
		} else {
			String device = MType.getType().getDevice();
			render("/WEB-INF/" + device + "/default/mahjong/error.html");
		}

	}
}
