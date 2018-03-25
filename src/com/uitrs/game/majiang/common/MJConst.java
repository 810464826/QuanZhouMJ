package com.uitrs.game.majiang.common;

public interface MJConst {
	/** 用户缓存 标识 */
	String CATCH_USERS = "users";
	/** 房间缓存 标识 */
	String CATCH_ROOMS = "rooms";
	/** 微信 id 标识 */
	String OPEN_ID = "openId";
	/** 用户 id 标识 */
	String USER_ID = "userId";
	/** Room id 标识 */
	String ROOM_ID = "roomId";
	/** 默认南头像图片路径 */
	String IMG_DEFAULT_MEN_JPG = "/img/single-man.png";
	/** 默认女头像图片路径 */
	String IMG_DEFAULT_CUTE_JPG = "/img/single-cute.png";
	/** 房间位置 */
	int[] ORDERS = { 1, 2, 3, 4 };
	/** 玩家在线 */
	int ONLINE = 1;
	/** 玩家离线 */
	int OFFLINE = 0;
	/** 每张牌总共四张 */
	int TOTAL = 4;

	/** 赖子 */
	String LAIZI = "y";

	/** 花牌标识 */
	String HUA = "h";
	/** 季节牌标识 */
	String JI_JIE = "i";
	/** 箭牌标识 */
	String JIAN = "j";
	/** 风牌标识 */
	String FENG = "f";
	/** 万牌标识 */
	String WAN = "w";
	/** 条牌标识 */
	String TIAO = "t";
	/** 筒牌标识 */
	String TONG = "o";

	/** 中 */
	String ZHONG = JIAN + "1";
	/** 發 */
	String FA = JIAN + "2";
	/** 白 */
	String BAI = JIAN + "3";

	/** 东 */
	String DONG = FENG + "1";
	/** 南 */
	String NAN = FENG + "2";
	/** 西 */
	String XI = FENG + "3";
	/** 北 */
	String BEI = FENG + "4";

	/** 梅 */
	String MEI = HUA + "1";
	/** 兰 */
	String LAN = HUA + "2";
	/** 竹 */
	String ZHU = HUA + "3";
	/** 菊 */
	String JU = HUA + "4";

	/** 春 */
	String CHUN = JI_JIE + "1";
	/** 夏 */
	String XIA = JI_JIE + "2";
	/** 秋 */
	String QIU = JI_JIE + "3";
	/** 冬 */
	String DON = JI_JIE + "4";

	/** 万1 */
	String WAN1 = WAN + "1";
	/** 万2 */
	String WAN2 = WAN + "2";
	/** 万3 */
	String WAN3 = WAN + "3";
	/** 万4 */
	String WAN4 = WAN + "4";
	/** 万5 */
	String WAN5 = WAN + "5";
	/** 万6 */
	String WAN6 = WAN + "6";
	/** 万7 */
	String WAN7 = WAN + "7";
	/** 万8 */
	String WAN8 = WAN + "8";
	/** 万9 */
	String WAN9 = WAN + "9";

	/** 条1 */
	String TIAO1 = TIAO + "1";
	/** 条2 */
	String TIAO2 = TIAO + "2";
	/** 条3 */
	String TIAO3 = TIAO + "3";
	/** 条4 */
	String TIAO4 = TIAO + "4";
	/** 条5 */
	String TIAO5 = TIAO + "5";
	/** 条6 */
	String TIAO6 = TIAO + "6";
	/** 条7 */
	String TIAO7 = TIAO + "7";
	/** 条8 */
	String TIAO8 = TIAO + "8";
	/** 条9 */
	String TIAO9 = TIAO + "9";

	/** 筒1 */
	String TONG1 = TONG + "1";
	/** 筒2 */
	String TONG2 = TONG + "2";
	/** 筒3 */
	String TONG3 = TONG + "3";
	/** 筒4 */
	String TONG4 = TONG + "4";
	/** 筒5 */
	String TONG5 = TONG + "5";
	/** 筒6 */
	String TONG6 = TONG + "6";
	/** 筒7 */
	String TONG7 = TONG + "7";
	/** 筒8 */
	String TONG8 = TONG + "8";
	/** 筒9 */
	String TONG9 = TONG + "9";

	/********** 玩家打牌状态 *************************/
	/**
	 * 1-创建或加入房间(刚进入房间, 还没发牌) 2-开始打牌(已经发牌了--某个人胡牌之前) 3-单局结束(某个人胡了后--下一局发牌之前)
	 * 4-申请解散的过程中
	 */
	/** 创建房间 */
	int STEP_CREATE = 1;
	/** 加入房间 */
	int STEP_JOIN = 1;
	/** 开始打牌 */
	int STEP_START = 2;
	/** 打牌结束 */
	int STEP_END = 3;
	/** 申请解散 ? */
	int STEP_DISMISS = 4;

	/********** 出牌操作状态 *************************/
	/** 拿牌 */
	int NA_CARD = 1;
	/** 出牌 */
	int OUT_CARD = 2;
	/** 胡牌 */
	int HU_CARD = 3;
	/** 补杠 */
	int BU_CARD = 4;

	/** 胡牌次数 */
	String HU_COUNT = "huCount";
	/** 连庄次数 */
	String LIAN_ZHUANG_COUNT = "lZCount";
	/** 庄家次数 */
	String ZHUANG_COUNT = "zhuangCount";
	/** 点炮次数 */
	String PAO_COUNT = "paoCount";
	/** 胡牌分数 */
	String HU_FEN = "huFen";
	/** 金牌分数 */
	String JING_FEN = "jingFen";
	/** 杠分数 */
	String GANG_FEN = "gangFen";
	
	/** 游金中 */
	String YOU_JINGING = "youJinZhong";
}
