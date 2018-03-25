package com.uitrs.game.majiang.web.service.roomInfo;

import java.util.List;
import java.util.Map;

import com.uitrs.game.majiang.web.model.RoomInfo;
import com.uitrs.game.majiang.web.model.SuperPage;

/**
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:56:07
 */
public class RoomInfoService {
	private static RoomInfoService roomInfoService = new RoomInfoService();

	private RoomInfoService() {

	}

	public static RoomInfoService getService() {
		return roomInfoService;
	}

	/**
	 * 查询指定的房间
	 */
	public RoomInfo getRoom(int roomId) {
		return RoomInfo.dao.findFirst("select * from roominfo where roomId = ? ", roomId);
	}

	/**
	 * 查询所有的房间
	 */
	public List<RoomInfo> getAllRooms() {
		return RoomInfo.dao.find("select * from roominfo limit 6");
	}

	/**
	 * 查询指定的房间 模糊查询
	 */
	public List<RoomInfo> getMHRoom(int roomId) {
		return RoomInfo.dao.find("select * from roominfo where roomId like '%" + roomId + "%' limit 6");
	}

	/**
	 * 查询指定的房间 模糊查询
	 */
	public List<RoomInfo> getMHRoomByNull() {
		return RoomInfo.dao.find("select * from roominfo ");
	}

	/**
	 * 查询扩展类
	 */
	public SuperPage<RoomInfo> getRoomPageByNull() {
		List<RoomInfo> room = getMHRoomByNull();
		if (room.size() > 0) {
			SuperPage<RoomInfo> page = new SuperPage<RoomInfo>();
			for (RoomInfo r : room) {
				page.getList().add(r);
			}
			page.setCurrentPage(1);
			page.setTotalPages(1);
			return page;
		} else {
			return null;
		}

	}

	/**
	 * 查询扩展类
	 */
	public SuperPage<RoomInfo> getRoomPage(int roomId) {
		List<RoomInfo> room = getMHRoom(roomId);
		if (room.size() > 0) {
			SuperPage<RoomInfo> page = new SuperPage<RoomInfo>();
			for (RoomInfo r : room) {
				page.getList().add(r);
			}
			page.setCurrentPage(1);
			page.setTotalPages(1);
			return page;
		} else {
			return null;
		}

	}

	/**
	 * 查询所有房间
	 */
	public Map<String, Object> getAllRooms(int pageNumber, int pageSize) {
		return RoomInfo.dao.paginate(pageNumber, pageSize);
	}
}
