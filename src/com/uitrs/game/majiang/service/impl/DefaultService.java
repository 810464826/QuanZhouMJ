package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.service.SupperService;

public class DefaultService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		defaultDeal(session, action);
	}
}
