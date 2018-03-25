package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;

/**
 * 心跳业务
 * 
 * @author lucio
 *
 */
public class HeartBeatService extends ReconnectService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		long userId = action.getP();

		PlayerInfo player = MJCache.getCache().getPlayerInfo(userId);
		// 如果在房间中则判断是不是上线操作
		if (player.getUserInfo().getState() == MJConst.OFFLINE) {
			dealReconnect(session, userId);
		}
	}
}
