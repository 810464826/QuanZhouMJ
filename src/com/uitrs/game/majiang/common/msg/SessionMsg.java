package com.uitrs.game.majiang.common.msg;

import javax.websocket.Session;

public class SessionMsg {

	private Session session;
	private SendMsg msg;

	public SessionMsg() {
		super();
	}

	public SessionMsg(Session session, SendMsg msg) {
		super();
		this.session = session;
		this.msg = msg;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public SendMsg getMsg() {
		return msg;
	}

	public void setMsg(SendMsg msg) {
		this.msg = msg;
	}

}
