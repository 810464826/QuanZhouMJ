package com.uitrs.game.majiang.web.controller.mahjong.adminPayRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.web.common.MType;
import com.uitrs.game.majiang.web.model.AdminInfo;
import com.uitrs.game.majiang.web.model.AdminPayRecord;
import com.uitrs.game.majiang.web.model.SuperPage;
import com.uitrs.game.majiang.web.service.adminInfo.AdminInfoService;
import com.uitrs.game.majiang.web.service.adminPayRecord.AdminPayRecordService;
import com.uitrs.game.majiang.web.service.cardType.CardTypeService;
import com.uitrs.web.common.controller.ControllerPath;
import com.uitrs.web.common.interceptor.SessionInterceptor;

/**
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:41:46
 */
@Before({ SessionInterceptor.class })
@ControllerPath(controllerKey = "/adminPay")
public class AdminPayRecordController extends Controller {
	/**
	 * 查询所有充值记录
	 */
	public void getAll() {
		Map<String, Object> payRecord = AdminPayRecordService.getService()
				.getAdminAllPayRecord(getParaToInt("curentPage"), 10);
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("curentPage", payRecord.get("curentPage"));
		datas.put("totalPages", payRecord.get("totalPages"));
		datas.put("message", payRecord.get("list"));
		renderJson(datas);
	}

	/**
	 * 查询指定代理商得充值记录
	 */
	public void getByAdminId() {
		String admd = getPara("adminId");
		if (admd != null && admd.length() != 0) {
			SuperPage<AdminPayRecord> page = AdminPayRecordService.getService()
					.getAdminPayRecordPage(Integer.parseInt(admd));
			if (page != null) {
				String json = JsonUtils.toJson(page);
				renderJson(json);
			} else {
				renderJson("false");
			}
		} else {
			SuperPage<AdminPayRecord> page = AdminPayRecordService.getService().getNoAdminIdRecordPage();
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
	public void goToAdminPay() {
		String device = MType.getType().getDevice();
		List<Object> types = CardTypeService.getService().getAllTypes();
		setAttr("types", types);
		render("/WEB-INF/" + device + "/default/mahjong/add_room_admin.html");
	}

	/**
	 * 给代理商充值
	 */
	public void payForAdmin() {
		Integer adminId = getParaToInt("adminId");
		AdminInfo adminByAdminId = AdminInfoService.getService().getAdminByAdminId(adminId);
		if (adminByAdminId == null) {
			renderJson("result", false);
			return;
		}
		String payType = getPara("payType");
		double payMoney = Double.parseDouble(getPara("payMoney"));
		boolean payCardForAdmin = AdminPayRecordService.getService().payForAdmin(payMoney, adminId, payType);
		renderJson("result", payCardForAdmin);
	}

	/**
	 * 所有给代理商充值得记录
	 */
	public void getAllPayForAdmin() {
		List<AdminPayRecord> records = AdminPayRecordService.getService().getRecords();
		String username = (String) getSession().getAttribute("username");
		AdminInfo admin = AdminInfoService.getService().getAdminByUserName(username);
		setAttr("records", records);
		setAttr("person", admin);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newPay.html");
	}

	/**
	 * 这是获取某一条具体得充值记录
	 */
	public void getOneRecord() {
		int id = getParaToInt("name");
		Integer type = getParaToInt("type");
		AdminPayRecord record = AdminPayRecordService.getService().getOneRecords(id);
		String username = (String) getSession().getAttribute("username");
		AdminInfo admin = AdminInfoService.getService().getAdminByUserName(username);
		setAttr("record", record);
		setAttr("type", type);
		setAttr("type", admin.getSysType());
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newPayInfo.html");
	}

	/*
	 * 根据id删除
	 */
	public void deleteRecord() {
		int id = getParaToInt("id");
		boolean deleteRecord = AdminPayRecordService.getService().deleteRecord(id);
		renderJson("result", deleteRecord);
	}

	/**
	 * 去向充值页面 手机
	 */
	public void goToAdminPayMobile() {
		String device = MType.getType().getDevice();
		List<Object> types = CardTypeService.getService().getAllTypes();
		setAttr("types", types);
		render("/WEB-INF/" + device + "/default/mahjong/newAddPayInfo.html");
	}

	/**
	 * 给代理商充值得处理
	 */
	public void payForAdminMobile() {
		Integer adminId = getParaToInt("adminId");
		AdminInfo admin = AdminInfoService.getService().getAdminByAdminId(adminId);
		if (admin == null) {
			renderJson("result", false);
			return;
		}
		String payType = getPara("payType");
		double payMoney = Double.parseDouble(getPara("payMoney"));
		boolean payForAdmin = AdminPayRecordService.getService().payForAdmin(payMoney, adminId, payType);
		renderJson("result", payForAdmin);
	}

	/**
	 * 查询输入的代理商ID的充值记录
	 */
	public void getSameRecords() {
		String gamid = getPara("adminId");
		if (gamid != null && gamid.length() != 0) {
			List<AdminPayRecord> gamerPayRecord = AdminPayRecordService.getService()
					.getRecordsByAdminId(Integer.parseInt(getPara("adminId")));
			String username = (String) getSession().getAttribute("username");
			AdminInfo admin = AdminInfoService.getService().getAdminByUserName(username);
			setAttr("person", admin);
			setAttr("records", gamerPayRecord);
			String device = MType.getType().getDevice();
			render("/WEB-INF/" + device + "/default/mahjong/newPay.html");
		} else {
			String device = MType.getType().getDevice();
			render("/WEB-INF/" + device + "/default/mahjong/error.html");
		}

	}
}
