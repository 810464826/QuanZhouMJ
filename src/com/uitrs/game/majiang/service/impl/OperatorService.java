package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.command.SendMsgCommand;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 吃碰杠胡等操作检查,判断优先级,胡>碰/杠>吃
 * 
 * @author lucio
 *
 */
public class OperatorService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		long userId = WSSessionKit.getUserId(session);
		// 删除某用户缓存的出牌消息
		MJCache.getCache().removeMsg(room.getRoomId(), MJCache.getCache().getPlayerInfo(userId).getOrder());
		// 操作类型汇总判断, 汇总后优先级最高的执行,优先级相同则按位置顺序优先级执行
		if (room.operateCheck(session, action)) {
			// 取出优先级最高的命令
			SendMsgCommand sendMsgCommand = room.getCommands().poll();
			// 设置成活动用户
			room.setActiver(sendMsgCommand.getOrder());
			if (log.isInfoEnabled()) {
				log.info(room.getCommands().size() + " OperatorService sendMsgCommand,order is " + sendMsgCommand.getOrder()
						+ " ,msg is " + sendMsgCommand.getMsg().toJson());
			}
			// 执行优先级和位置最高的命令
			sendMsgCommand.execute();
			// 清除本次操作命令,便于下次重新统计
			room.clearCommand();
			// 看看之前房间中有没有没发出的消息,有则发送
			//HeartBeatService.reSendMsg(room);
		}
	}
}
