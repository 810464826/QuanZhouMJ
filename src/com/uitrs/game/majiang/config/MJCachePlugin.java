package com.uitrs.game.majiang.config;

import com.jfinal.plugin.IPlugin;
import com.uitrs.game.majiang.common.kit.DeamonThread;
import com.uitrs.game.majiang.common.kit.IdGen;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.service.impl.DefaultService;
import com.uitrs.game.majiang.service.impl.DismissService;
import com.uitrs.game.majiang.service.impl.HeartBeatService;
import com.uitrs.game.majiang.service.impl.HuangService;
import com.uitrs.game.majiang.service.impl.JoinService;
import com.uitrs.game.majiang.service.impl.LeaveService;
import com.uitrs.game.majiang.service.impl.LocationService;
import com.uitrs.game.majiang.service.impl.LoginService;
import com.uitrs.game.majiang.service.impl.NapaiService;
import com.uitrs.game.majiang.service.impl.OperatorService;
import com.uitrs.game.majiang.service.impl.OutCardService;
import com.uitrs.game.majiang.service.impl.QueryRoomService;
import com.uitrs.game.majiang.service.impl.QueryScoreService;
import com.uitrs.game.majiang.service.impl.ReadyService;
import com.uitrs.game.majiang.service.impl.RealVoiceService;
import com.uitrs.game.majiang.service.impl.ReconnectService;
import com.uitrs.game.majiang.service.impl.ReplayService;
import com.uitrs.game.majiang.service.impl.SendCheckService;
import com.uitrs.game.majiang.service.impl.ToDismissService;
import com.uitrs.game.majiang.service.impl.WXShareService;
import com.uitrs.game.majiang.service.impl.ZimoService;
import com.uitrs.game.majiang.service.impl.opt.AnGangService;
import com.uitrs.game.majiang.service.impl.opt.BuHuaService;
import com.uitrs.game.majiang.service.impl.opt.JiaGangService;
import com.uitrs.game.majiang.service.impl.opt.SpecialGangService;
import com.uitrs.game.majiang.service.impl.quanzhou.OneYouService;
import com.uitrs.game.majiang.service.impl.quanzhou.OneYouZhongService;
import com.uitrs.game.majiang.service.impl.quanzhou.QZCreateService;
import com.uitrs.game.majiang.service.impl.quanzhou.QZJuScoreService;
import com.uitrs.game.majiang.service.impl.quanzhou.SanJinDaoService;
import com.uitrs.game.majiang.service.impl.quanzhou.ThreeYouService;
import com.uitrs.game.majiang.service.impl.quanzhou.ThreeYouZhongService;
import com.uitrs.game.majiang.service.impl.quanzhou.TwoYouService;
import com.uitrs.game.majiang.service.impl.quanzhou.TwoYouZhongService;

public class MJCachePlugin implements IPlugin {
	@Override
	public boolean start() {
		// 初始化逻辑处理器
		MJCache.getCache().addServices(IMsg.DEFAULT_TYPE, new DefaultService());
		MJCache.getCache().addServices(IMsg.LOGIN_TYPE, new LoginService());
		MJCache.getCache().addServices(IMsg.CREATE_TYPE, new QZCreateService());
		MJCache.getCache().addServices(IMsg.OUT_CARD_TYPE, new OutCardService());
		MJCache.getCache().addServices(IMsg.JOIN_TYPE, new JoinService());
		MJCache.getCache().addServices(IMsg.READY_TYPE, new ReadyService());
		MJCache.getCache().addServices(IMsg.HEART_TYPE, new HeartBeatService());
		MJCache.getCache().addServices(IMsg.SEND_CHECK, new SendCheckService());
		OperatorService service = new OperatorService();
		MJCache.getCache().addServices(IMsg.PENG_TYPE, service);
		MJCache.getCache().addServices(IMsg.BU_GANG, service);
		MJCache.getCache().addServices(IMsg.MING_GANG, service);
		MJCache.getCache().addServices(IMsg.CHI_TYPE, service);
		MJCache.getCache().addServices(IMsg.OPT_PASS, service);
		MJCache.getCache().addServices(IMsg.HU_TYPE, service);
		MJCache.getCache().addServices(IMsg.ZIMO_TYPE, new ZimoService());
		MJCache.getCache().addServices(IMsg.ONE_GOLD, new OneYouService());
		MJCache.getCache().addServices(IMsg.TWO_GOLD, new TwoYouService());
		MJCache.getCache().addServices(IMsg.THREE_GOLD, new ThreeYouService());
		MJCache.getCache().addServices(IMsg.THREE_GOLD_HU, new SanJinDaoService());
		MJCache.getCache().addServices(IMsg.HUANG_TYPE, new HuangService());
		MJCache.getCache().addServices(IMsg.ONE_GOLDING, new OneYouZhongService());
		MJCache.getCache().addServices(IMsg.TWO_GOLDING, new TwoYouZhongService());
		MJCache.getCache().addServices(IMsg.THREE_GOLDING, new ThreeYouZhongService());
		MJCache.getCache().addServices(IMsg.AN_GANG, new AnGangService());
		MJCache.getCache().addServices(IMsg.BU_HUA, new BuHuaService());
		MJCache.getCache().addServices(IMsg.NA_TYPE, new NapaiService());
		MJCache.getCache().addServices(IMsg.JU_SCORE_TYPE, new QZJuScoreService());
		MJCache.getCache().addServices(IMsg.DISMISSING_TYPE, new ToDismissService());
		MJCache.getCache().addServices(IMsg.DISMISS_TYPE, new DismissService());
		MJCache.getCache().addServices(IMsg.RECORD_TYPE, new QueryScoreService());
		MJCache.getCache().addServices(IMsg.RECONNECT_TYPE, new ReconnectService());
		MJCache.getCache().addServices(IMsg.OPERATE_CHECK, service);
		MJCache.getCache().addServices(IMsg.SPECIAL_GANG, new SpecialGangService());
		MJCache.getCache().addServices(IMsg.SPECIAL_JIA_GANG, new JiaGangService());
		MJCache.getCache().addServices(IMsg.LEAVE_TYPE, new LeaveService());
		MJCache.getCache().addServices(IMsg.SHARE, new WXShareService());
		MJCache.getCache().addServices(IMsg.REPLAY_TYPE, new ReplayService());
		MJCache.getCache().addServices(IMsg.VOICE_TYPE, new RealVoiceService());
		MJCache.getCache().addServices(IMsg.LOCATION_TYPE, new LocationService());
		MJCache.getCache().addServices(IMsg.ROOM_QUERY, new QueryRoomService());
//		MJCache.getCache().addServices(IMsg.SHARE_GOLD, new WXShareGoldService());
		// 初始化数据库中自增ID
		IdGen.getInstance().init();
		// 启动监控线程
		DeamonThread deamonThread = new DeamonThread();
		Thread dThread = new Thread(deamonThread);
		dThread.setName("MJDeamonThread");
		// dThread.start();
		// 初始化用户数据
		return true;
	}

	@Override
	public boolean stop() {
		MJCache.resetCache();
		DeamonThread.isStop = true;
		return true;
	}

}
