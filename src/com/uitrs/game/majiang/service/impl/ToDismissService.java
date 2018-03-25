package com.uitrs.game.majiang.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.PrintKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 申请解散房间业务处理
 * 
 * @author lucio
 *
 */
public class ToDismissService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		long roomId = Long.parseLong(session.getUserProperties().get(MJConst.ROOM_ID).toString());
		Room room = MJCache.getCache().getRoom(roomId);
		// 删除某用户缓存的解散申请消息
		long userId = action.getP();
		MJCache.getCache().removeMsg(room.getRoomId(), MJCache.getCache().getPlayerInfo(userId).getOrder());
		// 统计四个人都同意解散,则解散
		int result = room.toDismissRoom(userId, Boolean.parseBoolean(action.getM().toString()));
		PrintKit.red(roomId + " dismiss result: " + result);
		// 解散成功
		if (1 == result) {
			dismissing(action, room);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			room.dismissRoom();
		} else if (2 == result) {
			// 解散失败
			this.responseMsg(0, room, IMsg.DISMISS_TYPE, false);
		} else {
			// 广播谁申请退出
			dismissing(action, room);
		}
	}

	private void dismissing(ReceiveMsg action, Room room) {
		SendMsg sendMsg = new SendMsg(action.getP(), true, action.getT(), room.getMsgSeq().incrementAndGet());
		Map<String, Object> hashMap = new ConcurrentHashMap<String, Object>();
		hashMap.put("s", room.getToDismisser());
		hashMap.put("m", room.getDissmisNumber());
		sendMsg.setM(hashMap);
		room.broadcast(sendMsg);
	}
	// private void saveResult(Room room) {
	// List<PlayerInfo> list =
	// room.getRoomResults().get(room.getRoomResults().size() -
	// 1).getJuResults();
	// for (int i = 0; i < list.size(); i++) {
	// PlayerInfo juResult = list.get(i);
	// GamerRecord record = new GamerRecord();
	// // Map<String, Object> record = new HashMap<String, Object>();
	// record.setRoomId(room.getRoomId());
	// record.setEndTime(Tools.date2String(new Date()));
	// record.setGamerId(juResult.getUserInfo().getUserId());
	// // record.put("roomId", room.getRoomId());
	// record.setJuNum(room.getUseJuNum() - 1);
	// record.setWinPoints(juResult.getRoomScore());
	// record.setNickName(MJCache.getCache().getUserInfo(juResult.getUserInfo().getUserId()).getNickName());
	// record.setType("tnmj");
	// record.save();
	// }
	// }
	/**
	 * 保存本房间最终结果数据
	 * 
	 * @param room
	 */
	// private void saveResult(Room room) {
	// List<HupaiMsg> msgList = room.getRoomResults();
	// // 有记录则保存本房间最终结果数据
	// if (null != msgList && !msgList.isEmpty() &&
	// !msgList.get(0).getJuResults().isEmpty()) {
	// // 拿最后这局的数据即可
	// List<JuResult> juList = msgList.get(msgList.size() - 1).getJuResults();
	// Date endTime = new Date();
	// for (int i = 0; i < juList.size(); i++) {
	// JuResult juResult = juList.get(i);
	// UserInfo userInfo = MJCache.getCache().getUserInfo(juResult.getOpenId());
	// RoomRecord record = new RoomRecord();
	// record.setJuNum(room.getUseJuNum() - 1);
	// record.setRoomId(room.getRoomId());
	// record.setType("tnmj");
	// record.setGamerId(userInfo.getUserId());
	// record.setWinPoints(juResult.getRoomScore());
	// record.setNickName(userInfo.getNickName());
	// record.setEndTime(endTime);
	// record.save();
	// }
	// }
	// }
}