package com.uitrs.game.majiang.service.impl.quanzhou;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.impl.OutCardService;

/**
 * 选择单游,进入单游环节
 * 
 * @author lucio
 *
 */
public class OneYouZhongService extends OutCardService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		// OutCardMsg ocm = JsonUtils.parse(JsonUtils.toJson(action.getM()),
		// OutCardMsg.class);
		// String card = ocm.getOc();
		// Set<String> tc = ocm.getTc();
		Room room = WSSessionKit.getRoom(session);
		PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(action.getP());
		// 设置当前是单游中
		playerInfo.addHuInfo(MJConst.YOU_JINGING, 1);
		int youjin = room.getArg(MJConst.YOU_JINGING);
		room.addArg(MJConst.YOU_JINGING, youjin > 1 ? youjin : 1);
		String card = dealOutCardInfo(action, playerInfo, room);
		System.err.println(
				"OneYouZhong ting: " + playerInfo.getUserInfo().getUserId() + ":::" + playerInfo.getTingCards());
		// SendMsg result = new SendMsg(action.getP(), true, action.getT());
		// result.setM(action);
		// this.sendMsgToSelf(room, session, result);
		// WebSocketUtil.sendAsyncMsg(result, session);
		// 出牌信息则保存下来,下次重连时发出
		// MJCache.getCache().addMsg(room.getRoomId(), result);
		// 缓存回放消息
		SendMsg replayMsg = new SendMsg(action.getP(), true, action.getT());
		replayMsg.setM(action);
		room.addReplayMsg(replayMsg);
		// 检查其他人是否对出的牌有操作
		checkOtherOpt(action, card, room);
	}

}
