package com.uitrs.game.majiang.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.base.BaseGamerRecord;

@SuppressWarnings("serial")
public class GamerRecord extends BaseGamerRecord<GamerRecord> {
	public static final GamerRecord dao = new GamerRecord();

	/**
	 * 实现分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> paginate(int pageNumber, int pageSize) {
		List<Object> results = new ArrayList<Object>();
		Page<GamerRecord> page = paginate(pageNumber, pageSize, "select *", "from gamerecord order by id asc");
		for (int i = 0; i < page.getList().size(); i++) {
			Map<String, Object> model = new HashMap<String, Object>();
			GamerRecord gamerRecord = page.getList().get(i);
			model.put("id", gamerRecord.getId());
			model.put("gamerId", gamerRecord.getGamerId());
			model.put("roomId", gamerRecord.getRoomId());
			model.put("juNum", gamerRecord.getJuNum());
			model.put("type", gamerRecord.getType());
			model.put("winPoints", gamerRecord.getWinPoints());
			model.put("nickName", gamerRecord.getNickName());
			model.put("endTime", gamerRecord.getEndTime());
			results.add(Tools.mapToJson(model));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", results);
		map.put("curentPage", page.getPageNumber());
		map.put("totalPages", page.getTotalPage());
		return map;
	}
}