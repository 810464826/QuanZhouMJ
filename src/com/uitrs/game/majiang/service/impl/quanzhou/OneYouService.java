package com.uitrs.game.majiang.service.impl.quanzhou;

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
import com.uitrs.game.majiang.service.impl.ZimoService;

/**
 * 单游成功业务逻辑处理
 * 
 * @author lucio
 *
 */
public class OneYouService extends ZimoService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		room.setStep(MJConst.STEP_END);
		long userId = action.getP();

		OptCheckMsg optMsg = JsonUtils.parse(JsonUtils.toJson(action.getM()), OptCheckMsg.class);
		optMsg.setFrom(userId);
		PlayerInfo me = MJCache.getCache().getPlayerInfo(userId);
		me.addOptMsg(optMsg);

		super.countZhuang(room, userId);
		// 缓存回放消息
		SendMsg replayMsg = new SendMsg(action.getP(), true, action.getT());
		replayMsg.setM(action);
		room.addReplayMsg(replayMsg);
		
		SendMsg result = new SendMsg(userId, true, action.getT(), room.getMsgSeq().incrementAndGet());
		result.setM(optMsg);
		room.broadcast(result);
	}
}
