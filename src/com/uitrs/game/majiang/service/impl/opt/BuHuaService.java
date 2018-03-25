package com.uitrs.game.majiang.service.impl.opt;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.BuHuaMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.impl.NapaiService;

public class BuHuaService extends NapaiService {

	@Override
	public void dealService(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(action.getP());
		String hua = action.getM().toString();
		// 补花后的牌
		String fetchPai = room.fetchPai();
		// 拿牌状态
		room.setStatus(MJConst.NA_CARD);
		// 设置当前活动用户
		room.setActiver(playerInfo.getOrder());
		room.setBaseOrder(playerInfo.getOrder());
		// 剩余牌数
		int cardNum = room.getCards().length - room.getCardNum();
		// 是否荒庄
		if (isHuang(room, fetchPai, cardNum)) {
			return;
		}

		long i = room.getMsgSeq().incrementAndGet();
		// 发送补花消息(其他三家)
		SendMsg otherMsg = buildBuHuaMsg(action, hua, cardNum, "");
		otherMsg.setI(i);
		room.broadcast(otherMsg, action.getP());

		// 发给自己的消息
		SendMsg selfMsg = buildBuHuaMsg(action, hua, cardNum, fetchPai);
		selfMsg.setI(i);
		// 更新补花数据
		playerInfo.buHua(hua, fetchPai);
		// 如果补花的牌不是花牌,则校验是否可以对拿的牌进行操作
		room.getMjRule().checkSelf(room, playerInfo, fetchPai, selfMsg);
		room.sendMsg(action.getP(), selfMsg, session);
	}

	private SendMsg buildBuHuaMsg(ReceiveMsg action, String hua, int cardNum, String card) {
		SendMsg otherMsg = new SendMsg(action.getP(), true, action.getT());
		BuHuaMsg otherBuHua = new BuHuaMsg();
		// 设置补花人
		otherBuHua.setWho(action.getP());
		// 设置花牌
		otherBuHua.setH(hua);
		// 设置剩余牌数
		otherBuHua.setCardNum(cardNum);
		// 设置补牌
		otherBuHua.setC(card);

		otherMsg.setM(otherBuHua);
		return otherMsg;
	}
}
