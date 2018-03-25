package com.uitrs.game.majiang.web.controller.mahjong.gameRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.web.common.MType;
import com.uitrs.game.majiang.web.model.GamerRecord;
import com.uitrs.game.majiang.web.model.SuperPage;
import com.uitrs.game.majiang.web.service.gameRecord.GamerRecordService;
import com.uitrs.web.common.controller.ControllerPath;
import com.uitrs.web.common.interceptor.SessionInterceptor;

/**
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:42:01
 */
@Before({ SessionInterceptor.class })
@ControllerPath(controllerKey = "/gamerRecord")
public class GamerRecordController extends Controller {
	/**
	 * 查询玩家的成绩表
	 */
	public void getById() {
		String gamId = getPara("gamerId");
		if (gamId != null && gamId.length() != 0) {
			SuperPage<GamerRecord> recordPage = GamerRecordService.getService().getRecordPage(getParaToInt("gamerId"));
			if (recordPage != null) {
				String json = JsonUtils.toJson(recordPage);
				renderJson(json);
			} else {
				renderJson("false");
			}
		} else {
			SuperPage<GamerRecord> recordPage = GamerRecordService.getService().getRecordPageByNull();
			if (recordPage != null) {
				String json = JsonUtils.toJson(recordPage);
				renderJson(json);
			} else {
				renderJson("false");
			}
		}

	}

	/**
	 * 查询所有玩家的成绩表 分页
	 */
	public void getAll() {
		// 前台传递当前页
		Map<String, Object> datas = new HashMap<String, Object>();
		Map<String, Object> gamerRecord = GamerRecordService.getService().paginate(getParaToInt("curentPage"), 10);
		datas.put("curentPage", gamerRecord.get("curentPage"));
		datas.put("totalPages", gamerRecord.get("totalPages"));
		datas.put("message", gamerRecord.get("list"));
		renderJson(datas);
	}

	/**
	 * 查询所有的成绩
	 */
	public void getPlayRecords() {
		List<GamerRecord> records = GamerRecordService.getService().getRecords();
		setAttr("records", records);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newGameRecord.html");
	}

	/**
	 * 查询指定的成绩
	 */
	public void getRecord() {
		int id = getParaToInt("name");
		GamerRecord record = GamerRecordService.getService().getOneRecord(id);
		setAttr("record", record);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newGameRecordInfo.html");
	}
}
