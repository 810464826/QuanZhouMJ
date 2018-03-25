package com.uitrs.game.majiang.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.base.BaseGamerPayRecord;
import com.uitrs.game.majiang.web.service.adminInfo.AdminInfoService;

@SuppressWarnings("serial")
public class GamerPayRecord extends BaseGamerPayRecord<GamerPayRecord> {
	public static final GamerPayRecord dao = new GamerPayRecord();

	/**
	 * 实现分页查询 代理商的充值
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> paginateGamer(int pageNumber, int pageSize, int adminId) {
		List<Object> results = new ArrayList<Object>();
		AdminInfo adminByAdminId = AdminInfoService.getService().getAdminByAdminId(adminId);
		Page<GamerPayRecord> page = null;
		if (adminByAdminId.getSysType() == 0) {
			page = paginate(pageNumber, pageSize, "select *", "from gamerpayrecord order by id asc");
		} else {
			page = paginate(pageNumber, pageSize, "select *",
					"from gamerpayrecord where adminId = " + adminId + " order by id asc");
		}

		for (int i = 0; i < page.getList().size(); i++) {
			Map<String, Object> model = new HashMap<String, Object>();
			GamerPayRecord payRecord = page.getList().get(i);
			model.put("id", payRecord.getId());
			model.put("adminId", payRecord.getAdminId());
			model.put("payTime", payRecord.getPayTime());
			model.put("payMoney", payRecord.getPayMoney());
			model.put("gamerId", payRecord.getGamerId());
			model.put("payType", payRecord.getPayType());
			results.add(Tools.mapToJson(model));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", results);
		map.put("curentPage", page.getPageNumber());
		map.put("totalPages", page.getTotalPage());
		return map;
	}
}
