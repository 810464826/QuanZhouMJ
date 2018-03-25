package com.uitrs.game.majiang.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.websocket.Session;

import com.jfinal.log.Log;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.MJCheckKit;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.common.room.RoomUserCallable;

/**
 * 业务处理父类
 * 
 * @author lucio
 *
 */
public abstract class SupperService implements MJService {

	protected static Log log = Log.getLog(SupperService.class);

	public SupperService() {
		super();
	}

	@Override
	public void dealService(Session session, ReceiveMsg action) {
		if (action.getT() != IMsg.LOGIN_TYPE) {
			WSSessionKit.putUserId(session, action.getP());
			PlayerInfo player = MJCache.getCache().getPlayerInfo(action.getP());
			if (null != player) {
				// 设置通讯时间
				player.setAliveTime(System.currentTimeMillis());
			}
		}

		doService(session, action);
	}

	public abstract void doService(Session session, ReceiveMsg action);

	/**
	 * 默认业务处理,直接广播action内容
	 * 
	 * @param session
	 * @param ReceiveMsg
	 * @return Room
	 */
	protected Room defaultDeal(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		long userId = WSSessionKit.getUserId(session);
		SendMsg result = new SendMsg(userId, true, action.getT());
		result.setM(action);
		responseMsg(room, result);
		return room;
	}

	/**
	 * 直接返回收到的消息
	 * 
	 * @param Room
	 * @param ReceiveMsg
	 */
	protected void responseMsg(Room room, ReceiveMsg action) {
		SendMsg result = new SendMsg(action.getP(), true, action.getT(), room.getMsgSeq().incrementAndGet());
		result.setM(action);
		responseMsg(room, result);
	}

	/**
	 * 广播model数据
	 * 
	 * @param p
	 * @param room
	 * @param type
	 * @param model
	 */
	protected void responseMsg(long p, Room room, int type, Object model) {
		// 保存胡牌结果到用户数据库
		SendMsg result = new SendMsg(p, true, type, room.getMsgSeq().incrementAndGet());
		result.setM(model);
		responseMsg(room, result);
	}

	/**
	 * 直接返回收到的消息
	 * 
	 * @param Room
	 * @param SendMsg
	 */
	protected void responseMsg(Room room, SendMsg msg) {
		room.broadcast(msg);
	}

	/**
	 * 直接返回收到的消息
	 * 
	 * @param Room
	 * @param SendMsg
	 * @param excludeUserId
	 */
	protected void responseMsg(Room room, SendMsg msg, long excludeUserId) {
		room.broadcast(msg, excludeUserId);
	}

	/**
	 * 给自己回消息,会缓存消息
	 * 
	 * @param room
	 * @param session
	 * @param msg
	 */
	protected void sendMsgToSelf(long userId,Room room, Session session, SendMsg msg) {
		room.sendMsg(userId,msg, session);
	}

	/**
	 * 检查别人对出的牌有的操作(吃,碰,胡,杠)
	 * 
	 * @param action
	 * @param card
	 * @param room
	 */
	protected void checkOtherOpt(final ReceiveMsg action, final String card, Room room) {
		final long msgId = room.getMsgSeq().incrementAndGet();
		List<SendMsg> sendList = MJCheckKit.iteratorRoomUsers(room, new RoomUserCallable<SendMsg>() {

			@Override
			public SendMsg call(Room room, Entry<Integer, Long> user) throws Exception {

				PlayerInfo player = MJCache.getCache().getPlayerInfo(user.getValue());
				SendMsg send = new SendMsg(user.getValue(), true, action.getT(), msgId);
				send.setM(action);
				// 自己出的牌自己不检测
				if (user.getValue() != action.getP()) {
					List<OptCheckMsg> optList = player.checkOutOpts(action.getP(), card);
					if (null != optList && optList.size() > 0) {
						send.setE(optList);
					}
				}

				return send;
			}
		});

		// 发送消息
		for (SendMsg sendMsg : sendList) {
			//缓存回放消息
//			SendMsg replayMsg = new SendMsg(action.getP(), true, action.getT());
//			replayMsg.setM(action);
//			room.addReplayMsg(replayMsg);
			room.sendMsg(sendMsg.getP(),sendMsg, MJCache.getCache().getSession(sendMsg.getP()));
		}

		// Iterator<Entry<Integer, Long>> it =
		// room.getRoomUsers().entrySet().iterator();
		// while (it.hasNext()) {
		// Entry<Integer, Long> user = it.next();
		// long userId = user.getValue();
		// // 排除自己
		// long from = action.getP();
		// if (userId != from) {
		// // room.addOptNumber(userId);
		// PlayerInfo player = MJCache.getCache().getPlayerInfo(userId);
		//
		// // String[] cards = player.handCards2Array();
		// // String[] enablePais = room.getMjRule().enablePais();
		// SendMsg send = new SendMsg(userId, true, action.getT());
		// send.setM(action);
		// // List<OptCheckMsg> optList =
		// // MJCheckKit.checkOutCardOpts(userId, from, card, cards,
		// // enablePais);
		// OptCheckMsg outMsg = JsonUtils.parse(JsonUtils.toJson(action.getM()),
		// OptCheckMsg.class);
		// List<OptCheckMsg> optCheckList = player.checkOutOpts(outMsg);
		// if (null != optCheckList && optCheckList.size() > 0) {
		// // isHasOpt = true;
		// send.setE(optCheckList);
		// }
		// // 发送消息
		// room.sendMsg(send, MJCache.getCache().getSession(userId));
		// }
		// }
	}

	/**
	 * 检查其他玩家能不能胡
	 * 
	 * @param action
	 * @param card
	 * @param room
	 */
	protected void checkOtherHu(ReceiveMsg action, String card, Room room) {
		Iterator<Entry<Integer, Long>> it = room.getRoomUsers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> user = it.next();
			long userId = user.getValue();
			// 排除自己
			long from = action.getP();
			if (userId != from) {
				room.addOptNumber(userId);
				PlayerInfo player = MJCache.getCache().getPlayerInfo(userId);
				SendMsg send = new SendMsg(userId, true, action.getT());
				send.setM(action.getM());

				List<OptCheckMsg> listOpt = new ArrayList<OptCheckMsg>();
				OptCheckMsg huMsg = MJCheckKit.checkHu(player, card);
				if (null != huMsg) {
					listOpt.add(huMsg);
					send.setE(listOpt);
				}
				// 发送消息
				room.sendMsg(userId,send, MJCache.getCache().getSession(userId));
			}
		}
	}

	/**
	 * 保存胡牌分数到数据库
	 * 
	 * @param room
	 * @param hupaiMsg
	 * @param type
	 */
	// protected void saveGamerRecord(Room room, HupaiMsg hupaiMsg, String type)
	// {
	// List<PlayerInfo> list = hupaiMsg.getJuResults();
	// for (int i = 0; i < list.size(); i++) {
	// PlayerInfo juResult = list.get(i);
	// GamerRecord record = new GamerRecord();
	// record.setRoomId(room.getRoomId());
	// record.setEndTime(Tools.date2String(new Date()));
	// record.setGamerId(MJCache.getCache().getUserInfo(juResult.getUserInfo().getUserId()).getUserId());
	// record.setJuNum(room.getUseJuNum() - 1);
	// record.setWinPoints(juResult.getRoomScore());
	// record.setNickName(MJCache.getCache().getUserInfo(juResult.getUserInfo().getUserId()).getNickName());
	// record.setType(type);
	// record.save();
	// }
	// }

}