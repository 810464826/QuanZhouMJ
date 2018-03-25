package com.uitrs.game.majiang.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.base.BaseRoomInfo;

@SuppressWarnings("serial")
public class RoomInfo extends BaseRoomInfo<RoomInfo> {
	public static final RoomInfo dao = new RoomInfo();

	/**
	 * 实现分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> paginate(int pageNumber, int pageSize) {
		List<Object> results = new ArrayList<Object>();
		Page<RoomInfo> page = paginate(pageNumber, pageSize, "select *", "from roominfo order by id asc");
		for (int i = 0; i < page.getList().size(); i++) {
			Map<String, Object> model = new HashMap<String, Object>();
			RoomInfo roomInfo = page.getList().get(i);
			model.put("id", roomInfo.getId());
			model.put("roomId", roomInfo.getRoomId());
			model.put("createTime", roomInfo.getCreateTime());
			model.put("endTime", roomInfo.getEndTime());
			model.put("useRounds", roomInfo.getUseRounds());
			model.put("totalRounds", roomInfo.getTotalRounds());
			model.put("gamerOneId", roomInfo.getGamerOneId());
			model.put("gamerTwoId", roomInfo.getGamerTwoId());
			model.put("gamerThreeId", roomInfo.getGamerThreeId());
			model.put("gamerFourId", roomInfo.getGamerFourId());
			results.add(Tools.mapToJson(model));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", results);
		map.put("curentPage", page.getPageNumber());
		map.put("totalPages", page.getTotalPage());
		return map;
	}

	public RoomInfo findByRoomId(long roomId) {
		return dao.findFirst("select * from roominfo where roomid=?", roomId);
	}
}
