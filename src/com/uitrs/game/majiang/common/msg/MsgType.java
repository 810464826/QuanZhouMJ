package com.uitrs.game.majiang.common.msg;

/**
 * 消息类型
 * 
 * @author lucio
 *
 */
public enum MsgType {
	AN_GANG("anGang", IMsg.AN_GANG), 
	BU_GANG("buGang", IMsg.BU_GANG), 
	BU_HUA("buHua", IMsg.BU_HUA), 
	CREATE_TYPE( "create", IMsg.CREATE_TYPE), 
	DEFAULT_TYPE("default", IMsg.DEFAULT_TYPE), 
	DISCONNECT_TYPE("disconnect", IMsg.DISCONNECT_TYPE), 
	DISMISS_TYPE("dismiss", IMsg.DISMISS_TYPE), 
	DISMISSING_TYPE("dismissing", IMsg.DISMISSING_TYPE), 
	ERROR_TYPE("error", IMsg.ERROR_TYPE), 
	FACE("face", IMsg.FACE), 
	FULL_TYPE("heart", IMsg.HEART_TYPE), 
	GANG_TYPE("gang", IMsg.GANG_TYPE), 
	HU_TYPE("hu", IMsg.HU_TYPE), 
	HUANG_TYPE("huang", IMsg.HUANG_TYPE), 
	JOIN_TYPE("join", IMsg.JOIN_TYPE), 
	JU_SCORE_TYPE( "juScore", IMsg.JU_SCORE_TYPE), 
	LEAVE_TYPE("leave", IMsg.LEAVE_TYPE), 
	LOGIN_TYPE("login", IMsg.LOGIN_TYPE), 
	ME_READY("meReady", IMsg.ME_READY), 
	MING_GANG("mingGang", IMsg.MING_GANG), 
	NA_TYPE("naPai", IMsg.NA_TYPE), 
	OPERATE_CHECK("operateCheck", IMsg.OPERATE_CHECK), 
	OPERATE_SHOW("operateShow", IMsg.OPERATE_SHOW), 
	OPT_PASS("pass", IMsg.OPT_PASS), 
	OUT_CARD_TYPE("outCard", IMsg.OUT_CARD_TYPE), 
	PENG_TYPE("peng", IMsg.PENG_TYPE), 
	READY_TYPE("ready", IMsg.READY_TYPE), 
	REBUILD_TYPE("rebuild", IMsg.REBUILD_TYPE), 
	RECONNECT_TYPE("reconnect", IMsg.RECONNECT_TYPE), 
	RECORD_TYPE("record", IMsg.RECORD_TYPE), 
	ROOM_SCORE_TYPE("roomScore", IMsg.ROOM_SCORE_TYPE), 
	START_TYPE("start", IMsg.START_TYPE), 
	SHARE("share", IMsg.SHARE), 
	SPECIAL_GANG("specialGang", IMsg.SPECIAL_GANG), 
	SPECIAL_JIA_GANG("specialBuGang", IMsg.SPECIAL_JIA_GANG), 
	TING("ting", IMsg.TING), 
	WANT_BU_GANG("want_bu_gang", IMsg.WANT_BU_GANG), 
	WORD("word", IMsg.WORD);

	// 类型名称
	private String name;
	// 类型编码
	private int code;

	private MsgType(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
