package com.uitrs.game.majiang.service;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.msg.ReceiveMsg;

/**
 * 麻将业务处理接口
 * 
 * @author lucio
 *
 */
public interface MJService {

	/**
	 * 处理业务
	 * 
	 * @param session
	 * @param action
	 */
	public void dealService(Session session, ReceiveMsg action);
}
