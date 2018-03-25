package com.uitrs.game.majiang.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.result.HupaiMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.service.SupperService;

/**
 * 一局结束了,统计得分情况
 * 
 * sendObj = {
 * 
 * cardsShow//碰、扛: [ {'name': 'e', /碰/type: 'p', from: openId}, {'name': 'w2',
 * /明杠/type: 'mg', from: openId }, //自己有三张，别人打一张 {'name': 't1', /暗杠/type: 'ag',
 * from: meOpenId}, //自己用四张扛 {'name': 't4', /自杠/type: 'sg', from: meOpenId},
 * //自己原来碰了，又摸到一张 ],
 * 
 * handCards//手中的牌 = [ 'e', 'w2', 't8', 'w4', 't8', 'w2', 't8', 'w2', 's', 'w2',
 * 't8', 'w2', 't8', 'w2', 'n']
 * 
 * huType//只与自己相关的胡牌类型(主要根据自己本身的信息判断): { color: 'same_color', //只要能胡牌，一定是
 * same_color | word_color | other_color 这三个值中的一种 fourNum: 0, //只要能胡牌，一定是
 * 0，1，2，3 这三个值中的一种(注意，这里只统计了还在手中的牌) type: 'pingHu', //只要能胡牌，一定是 'thirty'
 * |'sevenThirty'| 'pingHu' | 'sevenPair' | 'pengPengHu' | 'jiaHu' 这六个值中的一种
 * card: 'w2', //胡的牌 from: openId, //胡的是哪个人的牌 }
 * 
 * type: { optType: normal | fangPao | zimo | jiePao, //放炮 自摸 接炮
 * 没胡也没放炮（四个人都是normal,则是后烧） isHasOutCard: true, //是不是已经出过第一张牌，判断天胡地胡 isQianShao:
 * true, //是不是前烧 huCardState: normal|othersGangOut(别人扛了，出的牌，扛上炮)|
 * othersBuGang(别人补扛的牌，抢扛) |selfGang(自已扛的牌，扛上花) isTing: true, isZhuang: true, }
 * 
 * }
 * 
 * @author lucio
 *
 */
public class JuScoreService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		Room room = WSSessionKit.getRoom(session);
		// 玩家的得分情况
		PlayerInfo juUserResult = null;
		if (null != action.getM()) {
			juUserResult = JsonUtils.parse(JsonUtils.toJson(action.getM()), PlayerInfo.class);
		} else {
			juUserResult = new PlayerInfo();
		}

		if (room.JuScore(action, juUserResult)) {
			// 每局所有玩家得分消息
			HupaiMsg hupaiMsg = room.getRoomResults().get(room.getUseJuNum() - 2);
			if (log.isInfoEnabled()) {
				log.info("getJuResults hupaiMsg: " + hupaiMsg);
			}
			// 清除胡牌消息
			// MJCache.getCache().removeMsg(roomId, IMsg.HU_TYPE);
			// 房间所有人的准备制空
			// room.getReadyNumber().clear();
			// 重新生成牌
			// room.reSetCards();
			// 将单局汇总结果返回,展示结果
			this.responseMsg(action.getP(), room, action.getT(), hupaiMsg);
			// 保存胡牌结果到用户数据库
			saveResult(room, hupaiMsg);
		}
	}

	private void saveResult(Room room, HupaiMsg hupaiMsg) {
		List<PlayerInfo> list = hupaiMsg.getJuResults();
		for (int i = 0; i < list.size(); i++) {
			PlayerInfo juResult = list.get(i);
			Map<String, Object> record = new HashMap<String, Object>();
			record.put("roomId", room.getRoomId());
			record.put("juNum", room.getUseJuNum());
			record.put("type", "tnmj");
			record.put("gamerId", juResult.getUserInfo().getUserId());
			record.put("winPoints", juResult.getCurrentScore());
			record.put("nickName", MJCache.getCache().getUserInfo(juResult.getUserInfo().getUserId()).getNickName());
			record.put("endTime", new Date());
			// RoomRecord.dao.put(record).save();
		}
	}
}
