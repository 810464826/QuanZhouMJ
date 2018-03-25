package com.uitrs.game.majiang.common.command;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.websockets.WebSocketUtil;

/**
 * 返回消息给消息发出者
 * 
 * @author lucio
 *
 */
public class ResponseCommand extends SendMsgCommand {
	private Session session;

	public ResponseCommand(int baseOrder, Session session, int level, int order) {
		super();
		this.session = session;
		this.level = level;
		this.order = order;
		this.baseOrder = baseOrder;
	}

	public ResponseCommand(int baseOrder, Session session, SendMsg msg, int level, int order) {
		super();
		this.session = session;
		this.msg = msg;
		this.level = level;
		this.order = order;
		this.baseOrder = baseOrder;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void execute() {
		WebSocketUtil.sendSyncMsg(msg, session);
	}

}
