package com.uitrs.game.majiang.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.base.BaseAdminInfo;

@SuppressWarnings("serial")
public class AdminInfo extends BaseAdminInfo<AdminInfo> {
	public static final AdminInfo dao = new AdminInfo();

	/**
	 * 实现分页查询 这个方法要写在这个model里面的
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> paginate(int pageNumber, int pageSize) {
		List<Object> results = new ArrayList<Object>();
		Page<AdminInfo> page = paginate(pageNumber, pageSize, "select *", "from admininfo order by id asc");
		for (int i = 0; i < page.getList().size(); i++) {
			Map<String, Object> model = new HashMap<String, Object>();
			AdminInfo adminInfo = page.getList().get(i);
			model.put("id", adminInfo.getId());
			model.put("adminId", adminInfo.getAdminId());
			model.put("headIcon", adminInfo.getPassword());
			model.put("sysType", adminInfo.getSysType());
			model.put("password", adminInfo.getPassword());
			model.put("nickName", adminInfo.getNickName());
			model.put("telephone", adminInfo.getTelephone());
			model.put("totalCards", adminInfo.getTotalCards());
			model.put("surplusCards", adminInfo.getSurplusCards());
			model.put("loginTime", adminInfo.getLoginTime());
			results.add(Tools.mapToJson(model));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", results);
		map.put("curentPage", page.getPageNumber());
		map.put("totalPages", page.getTotalPage());
		return map;
	}
}
