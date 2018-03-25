package com.uitrs.game.majiang.service.impl.quanzhou;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.Session;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.sub.JoinMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.common.rule.QZMJRule;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.GamerRecord;
import com.uitrs.game.majiang.web.model.ReplayInfo;

/**
 * 每一局结束 data: { e | errMsg: null, m | message: {juResults: [//每一局的信息，包括4个人的
 * {cardsShow: Array, handCards: Array, huType: object, type: object,
 * currentScore: 10, roomScore: -20, openId:'83729'}, {cardsShow: Array,
 * handCards: Array, huType: object, type: object, currentScore: 15,
 * roomScore:10, openId:'83745'}, ]} s | success: true, t | type: 20 }
 * 其中：cardsShow: [ //说明：msg字段用来记录额外的数据， 如吃类型中，用来记录吃的那一组牌 {'cardName': 'e',
 * /碰/type: 'pong', msg: {from: otherOpenId} }, {'cardName': 'w2', /明杠/type:
 * 'mingGang', msg: {from: otherOpenId}}, //自己有三张，别人打一张 {'cardName': 't1',
 * /暗杠/type: 'anGang', msg: {from: meOpenId}}, //自己用四张扛 {'cardName': 't4',
 * /补杠/type: 'buGang', msg: {from: meOpenId}}, //自己原来碰了，又摸到一张 // 吃
 * cardName表示吃的那张牌，注意，只能吃自己上家出的牌 {'cardName': 't4', type: 'chi', msg: {from:
 * '098893', cards: ['t3', 't4', 't5'] } }, // 旋风扛(中、发、白) {'cardName': '', type:
 * 'specialGang', msg: {from: '098893', childType: 'xuanFengGang', cards: ['z',
 * 'f', 'b'] } }, // 幺扛(一条、一万、一筒) {'cardName': '', type: 'specialGang', msg:
 * {from: '098893', childType: 'yaoGang', cards: ['o1', 't1', 'w1'] } }, //
 * 九扛(九条、九万、九筒) {'cardName': '', type: 'specialGang', msg: {from: '098893',
 * childType: 'jiuGang', cards: ['o9', 't9', 'w9'] } }, // 白八九扛(白、八条、九万)
 * {'cardName': '', type: 'specialGang', msg: {from: '098893', childType:
 * 'baiJiuGang', cards: ['b', 't8', 'w9'] } }, ]
 * 
 * handCards = [ 'e', 'w2', 't8', 'w4', 't8', 'w2', 't8', 'w2', 's', 'w2', 't8',
 * 'w2', 't8', 'w2', 'n'] //huType只有真正胡牌的那个人才有内容，其它人的都是空对象 huType: { color:
 * 'same_color', //只要能胡牌，一定是 same_color | word_color | other_color 这三个值中的一种
 * fourNum: 0, //只要能胡牌，一定是 0，1，2，3 这四个值中的一种(注意，这里只统计了还在手中的牌) type: 'jHu' |
 * 'piHu'|pengPengHu, //只要能胡牌，一定是 'jHu' | 'piHu'|pengPengHu 这三个值中的一种 card: 'w2',
 * //胡的牌 from: openId, //胡的是哪个人的牌 } type: { optType: normal | fangPao | zimo |
 * jiePao, //放炮 自摸 接炮 没胡也没放炮（四个人都是normal,则是后烧） isHasOutCard: true,
 * //是不是已经出过第一张牌，判断天胡地胡 isQianShao: true, //是不是前烧 huCardState:
 * othersGangOut(别人扛了，出的牌，扛上炮) othersBuGang(别人补扛的牌，抢扛) selfGang(自已扛的牌，扛上花)
 * isTing: true, isZhuang: true, }
 */

public class QZJuScoreService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		long roomId = WSSessionKit.getRoomId(session);
		Room room = MJCache.getCache().getRoom(roomId);
		long huId = action.getP();
		PlayerInfo huPlayer = MJCache.getCache().getPlayerInfo(huId);
		if (log.isInfoEnabled()) {
			log.info("QZJuScoreService room: " + room);
			log.info("QZJuScoreService huPlayer: " + huPlayer);
			log.info("QZJuScoreService action: " + action);
		}
		// 删除胡牌消息
		MJCache.getCache().removeMsg(room.getRoomId(), MJCache.getCache().getPlayerInfo(action.getP()).getOrder());
		OptCheckMsg huOpt = JsonUtils.parse(JsonUtils.toJson(action.getM()), OptCheckMsg.class);
		SendMsg result = new SendMsg(huId, true, action.getT(), room.getMsgSeq().incrementAndGet());
		if (huOpt.getT() == IMsg.HUANG_TYPE) {
			room.setPreZhuangId(0);
			// room.setZhuangId(room.nextZhuang());
		} else {
			room.juScore(huPlayer, huOpt);
			// 局数加1
			room.setUseJuNum(room.getUseJuNum() + 1);
			room.setStep(MJConst.STEP_END);
			// 房间所有人的准备置空
			room.getReadyNumber().clear();
			// 重新生成牌
			room.reSetCards();
			// 设置下一把庄家为胡牌的人
			room.setPreZhuangId(room.getZhuangId());
			room.setZhuangId(huId);
		}
		JoinMsg socreMsg = new JoinMsg(room, huId);
		Map<Integer, Long> roomUsers = room.getRoomUsers();
		Iterator<Entry<Integer, Long>> it = roomUsers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Long> entry = (Map.Entry<Integer, Long>) it.next();
			PlayerInfo p = MJCache.getCache().getPlayerInfo(entry.getValue());
			if (null != p) {
				p.getUserInfo().setReady(false);
				socreMsg.addPlayers(p);
			}
		}
		result.setM(socreMsg);

		room.broadcast(result);

		// 缓存回放消息
		room.addReplayMsg(result);
		// 保存回放消息
		saveReplayRecord(room, socreMsg);

		// 如果使用局数大于等于总局数或者分数用完了,则房间结束
		if (isOver(room)) {
			SendMsg roomScore = new SendMsg(0, true, IMsg.ROOM_SCORE_TYPE, room.getMsgSeq().incrementAndGet());
			result.setM(socreMsg);
			room.dismissClear();
			room.broadcast(roomScore);
		}
	}

	private boolean isOver(Room room) {
		boolean isFen = room.getOpt()[0] == QZMJRule.F_ONE_HUNDRED || room.getOpt()[0] == QZMJRule.F_TWO_HUNDRED;
		if (isFen) {
			Map<Integer, Long> roomUsers = room.getRoomUsers();
			Iterator<Entry<Integer, Long>> it = roomUsers.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, Long> entry = (Map.Entry<Integer, Long>) it.next();
				PlayerInfo p = MJCache.getCache().getPlayerInfo(entry.getValue());
				if (null != p) {
					if (p.getRoomScore() <= 0) {
						return true;
					}
				}
			}
		} else {
			if (room.getTotalJuNum() < room.getUseJuNum()) {
				return true;
			}
		}
		return false;
	}

	private void saveReplayRecord(Room room, JoinMsg socreMsg) {
		List<PlayerInfo> list = socreMsg.getPlayers();
		for (int i = 0; i < list.size(); i++) {
			PlayerInfo player = list.get(i);
			GamerRecord record = new GamerRecord();
			// // Map<String, Object> record = new HashMap<String, Object>();
			record.setRoomId(room.getRoomId());
			record.setEndTime(Tools.date2String(new Date()));
			record.setGamerId(player.getUserInfo().getUserId());
			// record.put("roomId", room.getRoomId());
			record.setJuNum(room.getUseJuNum() - 1);
			record.setWinPoints(player.getRoomScore());
			record.setNickName(player.getUserInfo().getNickName());
			record.setType("qzmj");
			String replayId = room.getRoomId() + "" + (room.getUseJuNum() - 1) + player.getOrder();
			record.setReplayId(replayId);
			record.save();

			ReplayInfo repay = new ReplayInfo();
			repay.setReplayId(Integer.parseInt(replayId));
			Map<String, Object> repMsg = new HashMap<String, Object>();
			repMsg.put("p", player.getUserInfo().getUserId());
			repMsg.put("m", room.getReplayMsgs());
			repay.setRecord(JsonUtils.toJson(repMsg));
			repay.setType("qzmj");
			repay.save();
		}
	}
}
