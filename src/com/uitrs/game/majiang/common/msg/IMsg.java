package com.uitrs.game.majiang.common.msg;

/**
 * 消息接口和消息常量定义
 * 
 * @author lucio
 *
 */
public interface IMsg {
	/** 类型为异常 */
	int ERROR_TYPE = 99;
	/** 消息发送确认 */
	int SEND_CHECK = 98;
	/** 类型为默认 */
	int DEFAULT_TYPE = 0;
	/** 类型为开始 */
	int START_TYPE = 1;
	/** 类型为房间人员都准备好了 */
	int READY_TYPE = 2;
	/** 类型为加入房间 */
	int JOIN_TYPE = 3;
	/** 类型为登录 */
	int LOGIN_TYPE = 4;
	/** 类型为创建房间 */
	int CREATE_TYPE = 5;
	/** 类型心跳确认 */
	int HEART_TYPE = 6;
	/** 类型为出牌 */
	int OUT_CARD_TYPE = 7;
	/** 类型为胡牌 */
	int HU_TYPE = 8;
	/** 类型为拿牌 */
	int NA_TYPE = 9;
	/** 类型为荒庄 */
	int HUANG_TYPE = 10;
	/** 类型为碰 */
	int PENG_TYPE = 11;
	/** 类型为杠 */
	int GANG_TYPE = 12;
	/** 类型为吃 */
	int CHI_TYPE = 13;
	/** 客户端展示可操作信息 */
	int OPERATE_SHOW = 14;
	/** 类型为自摸 */
	int ZIMO_TYPE = 15;
	/** 三金倒 */
	int THREE_GOLD_HU = 16;
	/** 单游金 */
	int ONE_GOLD = 17;
	/** 双游金 */
	int TWO_GOLD = 18;
	/** 三游金 */
	int THREE_GOLD = 19;
	/** 类型为每局结束得分统计 */
	int JU_SCORE_TYPE = 20;
	/** 类型为房间结束得分统计 */
	int ROOM_SCORE_TYPE = 21;
	/** 天胡 */
	int TIAN_HU = 22;
	/** 单游金中 */
	int ONE_GOLDING = 23;
	/** 双游金中 */
	int TWO_GOLDING = 24;
	/** 三游金中 */
	int THREE_GOLDING = 25;
	/** 类型为解散房间 */
	int DISMISS_TYPE = 30;
	/** 类型为申请解散房间 */
	int DISMISSING_TYPE = 31;
	/** 类型查看记录 */
	int RECORD_TYPE = 32;
	/** 类型玩家离线 */
	int DISCONNECT_TYPE = 33;
	/** 类型重新建立连接 */
	int RECONNECT_TYPE = 34;
	/** 类型重新建立连接并进入打牌游戏 */
	int REBUILD_TYPE = 35;
	/** 个人离开房间,房间不解散 */
	int LEAVE_TYPE = 36;
	/** 分享 */
	int SHARE = 37;
	/** 补花消息 */
	int BU_HUA = 38;
	/** 回放消息 */
	int REPLAY_TYPE = 39;
	/** 实时语音消息 */
	int VOICE_TYPE = 40;
	/** 地理位置 */
	int LOCATION_TYPE = 41;
	/** 查看某房间所有记录 */
	int ROOM_QUERY = 42;
	/** 分享奖励 */
	int SHARE_GOLD = 43;
	/** 对出的牌没有人有吃碰胡等操作 */
	int OPT_PASS = 50;
	/** 选择补扛后要广播出去 */
	int BU_GANG = 51;
	/** 选择暗扛后要广播出去 */
	int AN_GANG = 52;
	/** 选择听后要广播出去 */
	int TING = 53;
	/** 碰成功时发的消息 */
	int PONG = 54;
	/** 表情 */
	int FACE = 55;
	/** 文字 */
	int WORD = 56;
	/** 每个用户点击选择了一个后发送(针对别人出的牌) */
	int OPERATE_CHECK = 57;
	/** 明扛成功发的消息 */
	int MING_GANG = 58;
	/** 发送想要补扛的消息 */
	int WANT_BU_GANG = 59;
	/** 回应对别人要补扛的牌能不能胡 */
	int BU_GANG_CHECK = 60;
	/** 每个人的准备按钮 */
	int ME_READY = 61;
	/** 选择4种特殊杠后要广播出去 */
	int SPECIAL_GANG = 62;
	/** 特殊杠,加杠 ,如摸了中发白杠,手上还有一张中,这时可以加杠,算2杠 */
	int SPECIAL_JIA_GANG = 63;

	/** 可操作消息标识 */
	String FLAG_OPT_MSG = "opt";
	/** 补花消息标识 */
	String FLAG_BU_HUA = "buh";

	public String toJson();
}