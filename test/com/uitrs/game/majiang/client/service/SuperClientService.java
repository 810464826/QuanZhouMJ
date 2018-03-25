package com.uitrs.game.majiang.client.service;

import java.util.concurrent.ConcurrentHashMap;

import com.uitrs.game.majiang.common.msg.IMsg;

public abstract class SuperClientService implements IClientService {
	/** 业务处理实例 */
	private static ConcurrentHashMap<Integer, IClientService> services;

	public static void init() {
		services = new ConcurrentHashMap<Integer, IClientService>();
		CCreateService cCreateService = new CCreateService();
		services.put(IMsg.CREATE_TYPE, cCreateService);
		services.put(IMsg.JOIN_TYPE, cCreateService);
		services.put(IMsg.REBUILD_TYPE, new CRebuildService());
		services.put(IMsg.START_TYPE, new CStartService());
		services.put(IMsg.OPERATE_SHOW, new COptShowService());
		services.put(IMsg.LOGIN_TYPE, new CLoginService());
	}

	public static IClientService getClientService(int t) {
		return services.get(t);
	}
}
