package com.uitrs.game.majiang.service.impl.opt;

import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.command.BroadcastCommand;
import com.uitrs.game.majiang.common.command.Command;
import com.uitrs.game.majiang.common.command.SendMsgCommand;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 处理补杠,之前碰了,又摸了一张
 * 
 * @author lucio
 *
 */
public class BuGangService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		// 玩家位子
		int order = MJCache.getCache().getPlayerInfo(action.getP()).getOrder();
		room.setBaseOrder(order);
		room.setActiver(order);
		// 添加补杠消息到队列中
		SendMsgCommand command = new BroadcastCommand(action.getP(), room, Command.TWO_LEVEL, order);
		SendMsg send = new SendMsg(action.getP(), true, action.getT());
		send.setM(action.getM());
		command.setMsg(send);
		room.addCommand(command);
		room.setStatus(MJConst.BU_CARD);// 补杠状态
		// room.addOptNumber(action.getP());
		// 添加BU_GANG消息至缓存,用户重连
		// MJCache.getCache().addMsg(room.getRoomId(), send);

		// 检查其他人是否有操作
		Object m = action.getM();
		// 如果不为空则说明有操作
		if (null != m && m.toString().length() > 0) {
			if (log.isInfoEnabled()) {
				log.info("optCheckMsg: " + m);
			}
			String jsonString = JsonUtils.toJson(m);
			OptCheckMsg optMsg = JsonUtils.parse(jsonString, OptCheckMsg.class);
			optMsg.setFrom(action.getP());
			// 检查其他人是否对出的牌有操作
			checkOtherHu(action, optMsg.getC(), room);
		}
	}

}
