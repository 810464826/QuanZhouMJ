package com.uitrs.game.majiang.web.controller.mahjong.roomInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.web.common.MType;
import com.uitrs.game.majiang.web.model.RoomInfo;
import com.uitrs.game.majiang.web.model.SuperPage;
import com.uitrs.game.majiang.web.service.roomInfo.RoomInfoService;
import com.uitrs.web.common.controller.ControllerPath;
import com.uitrs.web.common.interceptor.SessionInterceptor;

/**
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:42:15
 */
@Before({ SessionInterceptor.class })
@ControllerPath(controllerKey = "/roomInfo")
public class RoomInfoController extends Controller {
	/**
	 * 查询所有房间
	 */
	public void getAll() {
		Map<String, Object> allRooms = RoomInfoService.getService().getAllRooms(getParaToInt("curentPage"), 10);
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("curentPage", allRooms.get("curentPage"));
		datas.put("totalPages", allRooms.get("totalPages"));
		datas.put("message", allRooms.get("list"));
		renderJson(datas);
	}

	/**
	 * 查询指定的房间
	 */
	public void getById() {
		String romId = getPara("roomId");
		if (romId != null && romId.length() != 0) {
			SuperPage<RoomInfo> room = RoomInfoService.getService().getRoomPage(getParaToInt("roomId"));
			if (room != null) {
				String json = JsonUtils.toJson(room);
				renderJson(json);
			} else {
				renderJson("false");
			}
		} else {
			SuperPage<RoomInfo> room = RoomInfoService.getService().getRoomPageByNull();
			if (room != null) {
				String json = JsonUtils.toJson(room);
				renderJson(json);
			} else {
				renderJson("false");
			}
		}
	}

	/**
	 * 查询所有的房间
	 */
	public void getAllRooms() {
		List<RoomInfo> rooms = RoomInfoService.getService().getAllRooms();
		setAttr("rooms", rooms);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newRoom.html");
	}

	/**
	 * 模糊查询制定的房间
	 */
	public void getSameRooms() {
		List<RoomInfo> rooms = RoomInfoService.getService().getMHRoom(getParaToInt("roomId"));
		setAttr("rooms", rooms);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newRoom.html");
	}

	/**
	 * 获取指定房间
	 */
	public void getOneRoom() {
		int roomId = getParaToInt("name");
		RoomInfo room = RoomInfoService.getService().getRoom(roomId);
		setAttr("room", room);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newRoomInfo.html");
	}
}
