package com.uitrs.game.majiang.service.impl;

import java.util.List;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.result.HupaiMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 房间打满结束了,统计得分情况
 * 
 * @author lucio
 *
 */
public class RoomScoreService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		// 将房间得分汇总结果返回,展示结果
		this.responseMsg(0, room, action.getT(), buildScore(room));
		// 解散房间
		if (room.dismissRoom(session)) {
			// 保存胡牌结果到用户数据库
			// saveResult(room);
		}
	}

	private List<HupaiMsg> buildScore(Room room) {
		return room.getRoomResults();
	}

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
