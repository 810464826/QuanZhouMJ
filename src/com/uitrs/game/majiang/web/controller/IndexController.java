package com.uitrs.game.majiang.web.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.PrintKit;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.web.common.MType;
import com.uitrs.web.common.controller.ControllerPath;

@ControllerPath(controllerKey = "/")
public class IndexController extends Controller {
	public void index() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/login.html");
	}

	/**
	 * 更新缓存
	 */
	public void refreshConfig() {
		String type = this.getPara("t");
		if (StrKit.notBlank(type)) {
			PrintKit.red(PropKit.get("version"));
			PropKit.useless("develop.properties");
			PropKit.use("develop.properties");
			PrintKit.red(PropKit.get("version"));
		}
		renderText("Refresh Config Successfull!");
	}

	/**
	 * 打印用户缓存
	 */
	public void printPlayer() {
		String userId = this.getPara("c");
		if (StrKit.notBlank(userId)) {
			renderText("Print Player Cache:\n " + MJCache.getCache().getPlayerInfo(Long.parseLong(userId)));
		} else {
			renderText("Print Player Cache:\n userId is null!");
		}
	}

	/**
	 * 打印用户缓存
	 */
	public void printRoom() {
		String roomId = this.getPara("r");
		if (StrKit.notBlank(roomId)) {
			Room room = MJCache.getCache().getRoom(Long.parseLong(roomId));
			renderText("Print Room Cache:\n " + (null == room ? "" : room.buildReplayStartMsg().toJson()));
		} else {
			renderText("Print Room Cache:\n roomId is null!");
		}
	}
	
	/**
	 * 打印用户缓存
	 */
	public void printSendMsg() {
		String userId = this.getPara("c");
		if (StrKit.notBlank(userId)) {
			List<SendMsg> sendMsgs = MJCache.getCache().getSendCheckMsgs(Long.parseLong(userId));
			renderText("Print SendMsg Cache:\n " + (null == sendMsgs ? "" : sendMsgs));
		} else {
			renderText("Print SendMsg Cache:\n userId is null!");
		}
	}
}
