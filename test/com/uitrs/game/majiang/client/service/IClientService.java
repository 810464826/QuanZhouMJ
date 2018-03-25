package com.uitrs.game.majiang.client.service;

import com.uitrs.game.majiang.common.msg.SendMsg;

public interface IClientService {
	/**
	 * 处理业务
	 * 
	 * @param session
	 * @param action
	 */
	public void dealService(SendMsg recevieMsg);
}
