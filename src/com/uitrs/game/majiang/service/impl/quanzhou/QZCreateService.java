package com.uitrs.game.majiang.service.impl.quanzhou;

import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.CreateRoom;
import com.uitrs.game.majiang.common.msg.sub.JoinMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.game.majiang.common.room.QZMJRoom;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.common.rule.QZMJRule;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.web.model.GamerInfo;
import com.uitrs.game.majiang.websockets.WebSocketUtil;

/**
 * 创建房间业务处理
 * 
 * @author lucio
 *
 */
public class QZCreateService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		Room room = new QZMJRoom();
		String json = JsonUtils.toJson(action.getM());
		CreateRoom cr = JsonUtils.parse(json, CreateRoom.class);
		PlayerInfo p = MJCache.getCache().getPlayerInfo(action.getP());
		int juNum = cr.getJu();
		int card = calcUseDiamond(juNum);
		// 如果房卡不够则返回错误消息
		if (p.getUserInfo().getCards() < card) {
			// 返回创建成功
			SendMsg result = new SendMsg(action.getP(), false, action.getT());
			result.setE("房卡不够,无法创建房间!");
			WebSocketUtil.sendAsyncMsg(result, session);
		} else {
			setJuNum(room, juNum);
			room.setOpt(cr.getOpt());
			WSSessionKit.putRoomId(session, room.getRoomId());
			// 创建房间的局数是选项的8倍
			room.createRoom(juNum, action.getP());
			// 是否是给别人开房
			if (cr.getOpt().length > 2 && cr.getOpt()[2] == 1) {
				// 庄家去掉
				room.setZhuangId(0);
				// 不占位置
				room.getRoomUsers().put(1, 0l);
				// 个人信息不在房间中
				p.clear();
				// 添加定时任务
				/*room.getTimer().schedule(new RoomCheckTask(room), 60000, 60000);*/
				// 创建成功了,则创建者减去相应的房卡数
				UserInfo creater = MJCache.getCache().getUserInfo(room.getCreateId());
				boolean result = GamerInfo.dao.surplusCards((int) creater.getUserId(), calcRoomCard(room));
				if (result) {
					creater.setCards(creater.getCards() - calcRoomCard(room));
				}
				// saveRoom2DB(room);
			} else {
				room.setZhuangId(action.getP());
				// 默认准备
				//p.getUserInfo().setReady(true);
			}
			MJCache.getCache().addRoom(room);

			// 返回创建成功
			SendMsg result = new SendMsg(action.getP(), true, action.getT(), room.getMsgSeq().incrementAndGet());
			JoinMsg toJoinMsg = room.buildReBuildMsg(action.getP());
			result.setM(toJoinMsg);
			WebSocketUtil.sendAsyncMsg(result, session);
		}
	}

	/**
	 * 计算需要消耗的房卡/钻石数
	 * 
	 * @return
	 */
	private int calcRoomCard(Room room) {
		switch (room.getTotalJuNum()) {
		case 8:
			return 5;
		case 16:
			return 10;
		case 100:
			return 5;
		case 200:
			return 10;
		default:
			return 10000;
		}
	}

	/**
	 * 保存房间信息到数据库
	 */
	// private void saveRoom2DB(Room room) {
	// RoomInfo roomInfo = new RoomInfo();
	// roomInfo.setRoomId((int) room.getRoomId());
	// roomInfo.setCreateTime(Tools.date2String(new Date()));
	// roomInfo.setGamerOneId((int)
	// MJCache.getCache().getUserInfo(room.getRoomUsers().get(1)).getUserId());
	// roomInfo.setGamerTwoId((int)
	// MJCache.getCache().getUserInfo(room.getRoomUsers().get(2)).getUserId());
	// roomInfo.setGamerThreeId((int)
	// MJCache.getCache().getUserInfo(room.getRoomUsers().get(3)).getUserId());
	// roomInfo.setGamerFourId((int)
	// MJCache.getCache().getUserInfo(room.getRoomUsers().get(4)).getUserId());
	// roomInfo.setTotalRounds(room.getTotalJuNum());
	// roomInfo.setUseRounds(1);
	// roomInfo.save();
	// }

	/**
	 * 设置局数或者分数
	 * 
	 * @param room
	 * @param juNum
	 */
	private void setJuNum(Room room, int juNum) {
		switch (juNum) {
		case QZMJRule.J_EIGTH:
			room.setTotalJuNum(8);
			break;
		case QZMJRule.J_SIXTH:
			room.setTotalJuNum(16);
			break;
		case QZMJRule.F_ONE_HUNDRED:
			room.setTotalJuNum(100);
			break;
		case QZMJRule.F_TWO_HUNDRED:
			room.setTotalJuNum(200);
			break;
		default:
			room.setTotalJuNum(0);
			break;
		}
	}

	/**
	 * 根据选项计算需要消耗的房卡数
	 * 
	 * @param juNum
	 * @return 房卡数
	 */
	private int calcUseDiamond(int juNum) {
		switch (juNum) {
		case QZMJRule.J_EIGTH:
			return 5;
		case QZMJRule.J_SIXTH:
			return 10;
		case QZMJRule.F_ONE_HUNDRED:
			return 5;
		case QZMJRule.F_TWO_HUNDRED:
			return 10;
		default:
			return 100000;
		}
	}
}
