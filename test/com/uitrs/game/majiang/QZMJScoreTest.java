package com.uitrs.game.majiang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.result.CardsShowMsg;
import com.uitrs.game.majiang.common.msg.result.HupaiMsg;
import com.uitrs.game.majiang.common.msg.result.JuCardsShow;
import com.uitrs.game.majiang.common.msg.result.JuResult;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.game.majiang.common.room.QZMJRoom;
import com.uitrs.game.majiang.web.model._MappingKit;
import com.uitrs.web.common.config.BaseConfig;

public class QZMJScoreTest extends QZMJRoom {
	// 特殊杠
	public static final int[] OPT2 = new int[] { 2, 3, 4 };
	// 责任制
	public static final int[] OPT3 = new int[] { 1, 2, 3, 4 };

	// 责任制,40算法
	public static final int[] OPT5 = new int[] { 1, 2, 3, 4, 5 };
	// 40算法
	public static final int[] OPT6 = new int[] { 2, 3, 4, 5 };

	public static void main(String[] args) {

		// int ss = (int) Math.pow(2, 10);
		// System.err.println(ss*1);

		PropKit.use("develop.properties");
		DruidPlugin druidPlugin = BaseConfig.createDruidPlugin();
		druidPlugin.start();
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.start();
		//
		// // 所有配置在 MappingKit 中搞定
		_MappingKit.mapping(arp);

		// 缓存配置
		EhCachePlugin ecp = new EhCachePlugin(PathKit.getRootClassPath() + "/ehcache.xml");
		ecp.start();

		// testTianHu(ready(), 80);
		// testDiHu(ready(), 80);
		testpingHu(ready(), 80);
		testpingHu2(ready(), 80);
		testpingHu3(ready(), 80);
		testpingHu4(ready(), 80);
		testpingHu5(ready(), 80);
		testpingHu6(ready(), 80);
		testjiaHu(ready(), 80);
		testjiaHu2(ready(), 80);

		// testThirth(ready(), 80);
		// testSevenThirth(ready(), 80);
		// testSevenPair(ready(), 80);
		testPengPengHu(ready(), 80);
		// testSameColorReal(ready(), 80);
		// testSameColorFalse(ready(), 80);
		// testSameWordReal(ready(), 80);
		// testSameWordFalse(ready(), 80);

		// testSameColorSeven(ready(), 80);
		// testSameWordSeven(ready(), 80);
		// testSameColorPengPengHu(ready(), 80);
		// testSameWordPengPengHu(ready(), 80);

		// testSiGuiYi(ready(), 80);
		// testBaGuiYi(ready(), 80);

		// testSiGuiYiSameColorPingHu(ready(), 80);
		// testSiGuiYiSameColorPPHu(ready(), 80);
		//
		// testQianShao(ready(), 80);
		// testHouShao(ready(), 80);
		// testQianHouShao(ready(), 80);

		testpingHuSelfGang(ready(), 80);
		testpingHuAnGang(ready(), 80);
		testpingHuMingGang(ready(), 80);
		testpingHuSanGang(ready(), 80);

		// gang(ready().getJuResults().get(0), "sg", "openId-2");
		// gang(ready().getJuResults().get(1), "ag", "openId-2");
		// gang(ready().getJuResults().get(2), "mg", "openId-1");
	}

	/**
	 * 
	 * @param color
	 *            same_color | word_color | other_color 这三个值中的一种
	 * @param fourNum
	 *            四归一翻倍
	 * @param type
	 *            'thirty'|'sevenThirty'| 'pingHu' | 'sevenPair' | 'pengPengHu'
	 *            | 'jiaHu' 这六个值中的一种
	 * @param from
	 *            null或者点炮人
	 * @return HuType
	 */
	public static Map<String, Object> buildHuType(String color, String fourNum, String type, long from) {
		Map<String, Object> huType = new HashMap<String, Object>();
		huType.put("color", color);
		huType.put("fourNum", fourNum);
		huType.put("type", type);
		huType.put("from", from);
		return huType;
	}

	/**
	 * 
	 * @param optType
	 *            normal | fangPao | zimo | jiePao
	 * @param huCardState
	 *            normal|othersGangOut(别人扛了，出的牌，扛上炮)| othersBuGang(别人补扛的牌，抢扛)
	 *            |selfGang(自已扛的牌，扛上花)
	 * @param isHasOutCard
	 *            是不是已经出过第一张牌，判断天胡地胡
	 * @param isQianShao
	 * @param isTing
	 * @param isZhuang
	 * @return Type
	 */
	public static Map<String, Object> buildType(String optType, String huCardState, String isHasOutCard,
			String isQianShao, String isTing, String isZhuang) {
		Map<String, Object> type = new HashMap<String, Object>();
		type.put("optType", optType);
		type.put("huCardState", huCardState);
		type.put("isHasOutCard", isHasOutCard);
		type.put("isQianShao", isQianShao);
		type.put("isTing", isTing);
		type.put("isZhuang", isZhuang);
		return type;
	}

	/**
	 * 构造杠数据
	 * 
	 * @param juResults1
	 * @param type
	 * @param from
	 */
	protected static void gang(JuResult juResults1, String type, long from) {
		List<JuCardsShow> cardsShow = new ArrayList<JuCardsShow>();
		JuCardsShow gang1 = new JuCardsShow();
		gang1.setCardName("e");
		gang1.setType(type);
		CardsShowMsg msg = new CardsShowMsg();
		msg.setFrom(from);
		gang1.setMsg(msg);
		cardsShow.add(gang1);
		juResults1.setCardsShow(cardsShow);
	}

	/**
	 * 构造杠数据
	 * 
	 * @param juResults1
	 * @param type
	 * @param openId
	 */
	protected static List<JuCardsShow> buildCardsShowMsg(String type, CardsShowMsg msg) {
		List<JuCardsShow> cardsShow = new ArrayList<JuCardsShow>();
		JuCardsShow gang1 = buildJuCardsShow(type, msg);
		cardsShow.add(gang1);
		return cardsShow;
	}

	public static JuCardsShow buildJuCardsShow(String type, CardsShowMsg msg) {
		JuCardsShow gang1 = new JuCardsShow();
		gang1.setCardName("e");
		gang1.setType(type);
		gang1.setMsg(msg);
		return gang1;
	}

	public static HupaiMsg ready() {

		long openId1 = 815001;
		long openId2 = 815002;
		long openId3 = 815003;
		long openId4 = 815004;

		HupaiMsg hupaiMsg = new HupaiMsg();
		JuResult juResults1 = new JuResult(openId1);
		JuResult juResults2 = new JuResult(openId2);
		JuResult juResults3 = new JuResult(openId3);
		JuResult juResults4 = new JuResult(openId4);

		PlayerInfo playerInfo1 = new PlayerInfo(new UserInfo(openId1));
		PlayerInfo playerInfo2 = new PlayerInfo(new UserInfo(openId2));
		PlayerInfo playerInfo3 = new PlayerInfo(new UserInfo(openId3));
		PlayerInfo playerInfo4 = new PlayerInfo(new UserInfo(openId4));

		hupaiMsg.addJuResult(juResults1);
		hupaiMsg.addJuResult(juResults2);
		hupaiMsg.addJuResult(juResults3);
		hupaiMsg.addJuResult(juResults4);

		MJCache.getCache().addPlayerInfo(playerInfo1);
		MJCache.getCache().addPlayerInfo(playerInfo2);
		MJCache.getCache().addPlayerInfo(playerInfo3);
		MJCache.getCache().addPlayerInfo(playerInfo4);
		return hupaiMsg;
	}

	public static boolean checkScore(List<JuResult> juResults, int oneScore, int twoScore, int threeScore,
			int fourScore) {
		return juResults.get(0).getCurrentScore() == oneScore && juResults.get(1).getCurrentScore() == twoScore
				&& juResults.get(2).getCurrentScore() == threeScore && juResults.get(3).getCurrentScore() == fourScore;
	}

	/**
	 * 校验掉炮分数是否正确
	 * 
	 * @param winer
	 * @param loser
	 * @param winScore
	 * @param loseScore
	 * @return boolean
	 */
	public static boolean checkPaoScore(List<JuResult> juResults, int winer, int loser, int winScore, int loseScore) {
		return juResults.get(winer).getCurrentScore() == winScore
				&& juResults.get(loser).getCurrentScore() == loseScore;
	}

	/**
	 * 校验自摸分数是否正确
	 * 
	 * @param juResults
	 * @param winer
	 * @param winScore
	 * @param loseScore
	 * @return boolean
	 */
	public static boolean checkZimoScore(List<JuResult> juResults, int winer, int winScore, int loseScore) {
		for (int i = 0; i < juResults.size(); i++) {
			// 如果赢家分数不对,直接返回false
			if (i == winer) {
				if (juResults.get(i).getCurrentScore() != winScore) {
					return false;
				}
			} else {
				if (juResults.get(i).getCurrentScore() != loseScore) {
					return false;
				}
			}
		}
		return true;
	}

	public static void testTianHu(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0).setHuType(buildHuType("other_color", "0", "piHu", 0));
		hupaiMsg.getJuResults().get(0).setType(buildType("zimo", "normal", "false", "false", "false", "true"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("normal", "normal", "false", "false", "false", "false"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "false", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "false", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(0).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkZimoScore(hupaiMsg.getJuResults(), 0, 12, -4) + "---天胡,josn:" + hupaiMsg.toJson());
	}

	public static void testDiHu(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "piHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "false", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "false", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "false", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "false", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 4, -4) + "---地胡,josn:" + hupaiMsg.toJson());
	}

	public static void testpingHu(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "piHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));
		hupaiMsg.getJuResults().get(0)
				.setCardsShow(buildCardsShowMsg("pong", new CardsShowMsg(hupaiMsg.getJuResults().get(2).getUserId())));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkScore(hupaiMsg.getJuResults(), 6, -4, -1, -1) + "---屁胡1+庄家放炮,josn:" + hupaiMsg.toJson());
	}

	public static void testpingHu2(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "piHu", hupaiMsg.getJuResults().get(2).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));
		hupaiMsg.getJuResults().get(0)
				.setCardsShow(buildCardsShowMsg("pong", new CardsShowMsg(hupaiMsg.getJuResults().get(2).getUserId())));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("normal", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("fangPao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkScore(hupaiMsg.getJuResults(), 5, -2, -2, -1) + "---屁胡2+非庄家放炮,josn:" + hupaiMsg.toJson());
	}

	public static void testpingHu3(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "piHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));
		// hupaiMsg.getJuResults().get(0)
		// .setCardsShow(buildCardsShowMsg("pong", new
		// CardsShowMsg(hupaiMsg.getJuResults().get(2).getUserId())));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println("---" + checkScore(hupaiMsg.getJuResults(), 12, -8, -2, -2) + "---屁胡3+庄家放炮+站立胡,josn:"
				+ hupaiMsg.toJson());
	}

	public static void testpingHu4(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "piHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));
		hupaiMsg.getJuResults().get(0)
				.setCardsShow(buildCardsShowMsg("pong", new CardsShowMsg(hupaiMsg.getJuResults().get(2).getUserId())));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT3);
		mjRoom.creatEvent();
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkScore(hupaiMsg.getJuResults(), 6, -6, 0, 0) + "---屁胡4+庄家放炮+责任制,josn:" + hupaiMsg.toJson());
	}

	public static void testpingHu5(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "piHu", hupaiMsg.getJuResults().get(2).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));
		hupaiMsg.getJuResults().get(0)
				.setCardsShow(buildCardsShowMsg("pong", new CardsShowMsg(hupaiMsg.getJuResults().get(2).getUserId())));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("normal", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("fangPao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT3);
		mjRoom.creatEvent();
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkScore(hupaiMsg.getJuResults(), 5, 0, -5, 0) + "---屁胡5+非庄家放炮+责任制,josn:" + hupaiMsg.toJson());
	}

	public static void testpingHu6(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "piHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));
		// hupaiMsg.getJuResults().get(0)
		// .setCardsShow(buildCardsShowMsg("pong", new
		// CardsShowMsg(hupaiMsg.getJuResults().get(2).getUserId())));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT3);
		mjRoom.creatEvent();
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println("---" + checkScore(hupaiMsg.getJuResults(), 12, -12, 0, 0) + "---屁胡6+庄家放炮+站立胡+责任制,josn:"
				+ hupaiMsg.toJson());
	}

	public static void testjiaHu(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "jHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));
		hupaiMsg.getJuResults().get(0)
				.setCardsShow(buildCardsShowMsg("pong", new CardsShowMsg(hupaiMsg.getJuResults().get(2).getUserId())));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkScore(hupaiMsg.getJuResults(), 12, -8, -2, -2) + "---夹胡+庄家放炮,josn:" + hupaiMsg.toJson());
	}

	public static void testjiaHu2(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "jHu", hupaiMsg.getJuResults().get(2).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));
		hupaiMsg.getJuResults().get(0)
				.setCardsShow(buildCardsShowMsg("pong", new CardsShowMsg(hupaiMsg.getJuResults().get(2).getUserId())));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("normal", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("fangPao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkScore(hupaiMsg.getJuResults(), 10, -4, -4, -2) + "---夹胡+非庄家放炮,josn:" + hupaiMsg.toJson());
	}

	public static void testThirth(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "thirty", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 0, -0) + "---十三烂,josn:" + hupaiMsg.toJson());
	}

	public static void testSevenThirth(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "sevenThirty", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 0, -0) + "---七星十三烂,josn:" + hupaiMsg.toJson());
	}

	public static void testSevenPair(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "pengpenghu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 12, -12) + "---七对,josn:" + hupaiMsg.toJson());
	}

	public static void testPengPengHu(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "pengPengHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println("---" + checkScore(hupaiMsg.getJuResults(), 32 + 16, -4 * 2 * 2 * 2, -4 * 2, -4 * 2)
				+ "---碰碰胡+站立胡+庄家点炮,josn:" + hupaiMsg.toJson());
	}

	public static void testSameColorReal(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("same_color", "0", "piHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 4, -4) + "---清一色真胡,josn:" + hupaiMsg.toJson());
	}

	public static void testSameColorFalse(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("same_color ", "0", "jHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 5, -5) + "---清一色假胡,josn:" + hupaiMsg.toJson());
	}

	public static void testSameWordReal(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("word_color", "0", "pingHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 20, -20) + "---字一色真胡,josn:" + hupaiMsg.toJson());
	}

	public static void testSameWordFalse(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("word_color", "0", "jiaHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 10, -10) + "---字一色假胡,josn:" + hupaiMsg.toJson());
	}

	public static void testSameColorSeven(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("same_color", "0", "sevenPair", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 20, -20) + "---七对清一色,josn:" + hupaiMsg.toJson());
	}

	public static void testSameWordSeven(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("word_color", "0", "sevenPair", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 40, -40) + "---七对字一色,josn:" + hupaiMsg.toJson());
	}

	public static void testSameColorPengPengHu(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("same_color", "0", "pengPengHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 20, -20) + "---碰碰胡清一色,josn:" + hupaiMsg.toJson());
	}

	public static void testSameWordPengPengHu(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("word_color", "0", "pengPengHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 40, -40) + "---碰碰胡字一色,josn:" + hupaiMsg.toJson());
	}

	public static void testSiGuiYi(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "1", "pingHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 4, -4) + "---四归一,josn:" + hupaiMsg.toJson());
	}

	public static void testBaGuiYi(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "2", "pingHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 8, -8) + "---八归一,josn:" + hupaiMsg.toJson());
	}

	public static void testSiGuiYiSameColorPingHu(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("same_color", "1", "pingHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println("---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 20, -20) + "---清一色平胡四归一,josn:"
				+ hupaiMsg.toJson());
	}

	public static void testSiGuiYiSameColorPPHu(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("same_color", "1", "pengPengHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println("---" + checkPaoScore(hupaiMsg.getJuResults(), 0, 1, 40, -40) + "---清一色碰碰胡四归一,josn:"
				+ hupaiMsg.toJson());
	}

	public static void testQianShao(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0).setHuType(buildHuType("other_color", "0", "pingHu", 0));
		hupaiMsg.getJuResults().get(0).setType(buildType("zimo", "normal", "true", "true", "false", "true"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "true", "false", "false"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "true", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "true", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(0).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println(
				"---" + checkZimoScore(hupaiMsg.getJuResults(), 0, 3, -1) + "---前烧自摸,josn:" + hupaiMsg.toJson());
	}

	public static void testHouShao(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(0).setType(buildType("normal", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(0).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err
				.println("---" + checkZimoScore(hupaiMsg.getJuResults(), 0, 3, -1) + "---后烧,josn:" + hupaiMsg.toJson());
	}

	public static void testQianHouShao(HupaiMsg hupaiMsg, int max) {
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(0).setType(buildType("normal", "normal", "true", "true", "false", "true"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("normal", "normal", "true", "true", "false", "false"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "true", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "true", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(0).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err
				.println("---" + checkZimoScore(hupaiMsg.getJuResults(), 0, 0, 0) + "---前后烧,josn:" + hupaiMsg.toJson());
	}

	public static void testpingHuSelfGang(HupaiMsg hupaiMsg, int max) {
		gang(hupaiMsg.getJuResults().get(0), "buGang", hupaiMsg.getJuResults().get(1).getUserId());
		// gang(hupaiMsg.getJuResults().get(1), "ag",
		// hupaiMsg.getJuResults().get(1).getUserId());
		// gang(hupaiMsg.getJuResults().get(2), "mg", "openId-1");
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "piHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println("---" + checkScore(hupaiMsg.getJuResults(), 6 + 9, -4 - 3, -1 - 3, -1 - 3)
				+ "---平胡+庄家点炮+补杠,josn:" + hupaiMsg.toJson());
	}

	public static void testpingHuAnGang(HupaiMsg hupaiMsg, int max) {
		// gang(hupaiMsg.getJuResults().get(0), "sg",
		// hupaiMsg.getJuResults().get(1).getUserId());
		gang(hupaiMsg.getJuResults().get(0), "anGang", hupaiMsg.getJuResults().get(0).getUserId());
		// gang(hupaiMsg.getJuResults().get(2), "mg", "openId-1");
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(0).setType(buildType("fangPao", "normal", "false", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("normal", "normal", "false", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2)
				.setHuType(buildHuType("other_color", "0", "piHu", hupaiMsg.getJuResults().get(0).getUserId()));
		hupaiMsg.getJuResults().get(2).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println("---" + checkScore(hupaiMsg.getJuResults(), 15 - 4, -5 - 4, -5 + 10, -5 - 2)
				+ "---平胡+站立胡+暗杠,josn:" + hupaiMsg.toJson());
	}

	public static void testpingHuMingGang(HupaiMsg hupaiMsg, int max) {
		// gang(hupaiMsg.getJuResults().get(0), "sg",
		// hupaiMsg.getJuResults().get(1).getUserId());
		// gang(hupaiMsg.getJuResults().get(1), "ag",
		// hupaiMsg.getJuResults().get(1).getUserId());
		gang(hupaiMsg.getJuResults().get(2), "mingGang", hupaiMsg.getJuResults().get(1).getUserId());
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "piHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println("---" + checkScore(hupaiMsg.getJuResults(), -3 + 12, -3 - 8, 9 - 2, -3 - 2)
				+ "---平胡+站立胡+庄家点炮+明杠,josn:" + hupaiMsg.toJson());
	}

	public static void testpingHuSanGang(HupaiMsg hupaiMsg, int max) {
		gang(hupaiMsg.getJuResults().get(0), "buGang", hupaiMsg.getJuResults().get(1).getUserId());
		gang(hupaiMsg.getJuResults().get(1), "anGang", hupaiMsg.getJuResults().get(1).getUserId());
		gang(hupaiMsg.getJuResults().get(2), "mingGang", hupaiMsg.getJuResults().get(0).getUserId());
		// 是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)
		hupaiMsg.getJuResults().get(0)
				.setHuType(buildHuType("other_color", "0", "piHu", hupaiMsg.getJuResults().get(1).getUserId()));
		hupaiMsg.getJuResults().get(0).setType(buildType("jiePao", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(1).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(1).setType(buildType("fangPao", "normal", "true", "false", "false", "true"));

		hupaiMsg.getJuResults().get(2).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(2).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		hupaiMsg.getJuResults().get(3).setHuType(buildHuType("other_color", "0", null, 0));
		hupaiMsg.getJuResults().get(3).setType(buildType("normal", "normal", "true", "false", "false", "false"));

		QZMJRoom mjRoom = new QZMJRoom(800100);
		mjRoom.setOpt(OPT2);
		mjRoom.setZhuangId(hupaiMsg.getJuResults().get(1).getUserId());
		mjRoom.juScoreEvent(hupaiMsg, max);
		System.err.println("---"
				+ checkScore(hupaiMsg.getJuResults(), 6 + 9 - 5 - 3, -4 - 3 + 15 - 3, -1 - 3 - 5 + 9, -1 - 3 - 5 - 3)
				+ "---平胡+庄家点炮+三杠,josn:" + hupaiMsg.toJson());
	}
}
