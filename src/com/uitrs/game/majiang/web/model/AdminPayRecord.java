package com.uitrs.game.majiang.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.base.BaseAdminPayRecord;

@SuppressWarnings("serial")
public class AdminPayRecord extends BaseAdminPayRecord<AdminPayRecord> {
	public static final AdminPayRecord dao = new AdminPayRecord();

	/**
	 * 实现分页查询 代理商的充值
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> paginateAdmin(int pageNumber, int pageSize) {
		List<Object> results = new ArrayList<Object>();
		Page<AdminPayRecord> page = paginate(pageNumber, pageSize, "select *", "from adminpayrecord order by id asc");
		for (int i = 0; i < page.getList().size(); i++) {
			Map<String, Object> model = new HashMap<String, Object>();
			AdminPayRecord payRecord = page.getList().get(i);
			model.put("id", payRecord.getId());
			model.put("adminId", payRecord.getAdminId());
			model.put("payTime", payRecord.getPayTime());
			model.put("payMoney", payRecord.getPayMoney());
			model.put("state", payRecord.getState());
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
