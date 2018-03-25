package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 自摸胡牌业务逻辑处理
 * 
 * @author lucio
 *
 */
public class ZimoService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		room.setStep(MJConst.STEP_END);
		long userId = action.getP();

		countZhuang(room, userId);

		PlayerInfo opter = MJCache.getCache().getPlayerInfo(userId);
		OptCheckMsg zimo = JsonUtils.parse(JsonUtils.toJson(action.getM()), OptCheckMsg.class);
		// OptCheckMsg zimo = new OptCheckMsg(userId, IMsg.ZIMO_TYPE,
		// m.getHuCard());
		zimo.setFrom(userId);
		opter.addOptMsg(zimo);

		SendMsg result = new SendMsg(userId, true, action.getT(), room.getMsgSeq().incrementAndGet());
		result.setM(zimo);

		room.broadcast(result);

	}

	/**
	 * 统计坐庄次数和连庄次数
	 * 
	 * @param room
	 * @param huId
	 */
	protected void countZhuang(Room room, long huId) {
		PlayerInfo opter = MJCache.getCache().getPlayerInfo(huId);
		// 如果连庄则累计连庄次数
		if (room.getZhuangId() == huId && room.getPreZhuangId() == huId) {
			Integer lzCount = opter.getHuInfo().get(MJConst.LIAN_ZHUANG_COUNT);
			opter.getHuInfo().put(MJConst.LIAN_ZHUANG_COUNT, (null == lzCount || 0 == lzCount) ? 1 : lzCount + 1);
		} else {
			opter.getHuInfo().put(MJConst.LIAN_ZHUANG_COUNT, 0);
		}
		//胡牌次数
		Integer huCount = opter.getHuInfo().get(MJConst.HU_COUNT);
		opter.getHuInfo().put(MJConst.HU_COUNT, (null == huCount || 0 == huCount) ? 1 : huCount + 1);
		// 累加坐庄次数
		Integer zhuangCount = opter.getHuInfo().get(MJConst.ZHUANG_COUNT);
		opter.getHuInfo().put(MJConst.ZHUANG_COUNT, null == zhuangCount ? 1 : zhuangCount + 1);
		// 设置下一把庄家为胡牌的人
		// room.setZhuangId(huId);
	}
}
