package com.uitrs.game.majiang.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Plugins;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.uitrs.game.majiang.common.kit.PrintKit;
import com.uitrs.web.common.config.BaseConfig;

public class MahjongConfig extends BaseConfig {

	@Override
	public void configConstant(Constants me) {
		super.configConstant(me);
		PrintKit.isDebug = me.getDevMode();
		// 启动监控线程
		// DeamonThread deamonThread = new DeamonThread();
		// Thread dThread = new Thread(deamonThread);
		// dThread.setName("MJDeamonThread");
		// dThread.start();
	}

	@Override
	public void configHandler(Handlers me) {
		super.configHandler(me);
		me.add(new UrlSkipHandler("^/ws/.+", false));
	}

	@Override
	public void configPlugin(Plugins me) {
		super.configPlugin(me);
		// 初始化麻将游戏缓存
		me.add(new MJCachePlugin());
	}
}
