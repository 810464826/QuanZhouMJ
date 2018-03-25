package com.uitrs.game.majiang.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.result.ScoreRecords;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.web.model.GamerRecord;
import com.uitrs.game.majiang.websockets.WebSocketUtil;

/**
 * 查询个人战绩,前端
 * 
 * @author lucio
 *
 */
public class QueryRoomService extends SupperService {

	/**
	 * dataArr = [ {roomId: '6389292', time: '2016-11-29
	 * 14:03',juNum:1,repId:80000114 usersInfo: [ {nickName: '如花',score: 118},
	 * {nickName: '如花',score: 78}, {nickName: '如花',score: -28}, {nickName:
	 * '如花',score: -28}]}]
	 */
	@Override
	public void doService(Session session, ReceiveMsg action) {
		long roomId = Long.parseLong(action.getM().toString());
		List<GamerRecord> roomRecords = GamerRecord.dao
				.find("select * from gamerecord where roomid =? order by endtime desc", roomId);

		List<ScoreRecords> resultList = buildResult(roomRecords, action.getP());
		SendMsg result = new SendMsg(action.getP(), true, action.getT());
		result.setM(resultList);
		// result.setE(e);
		WebSocketUtil.sendAsyncMsg(result, session);
	}

	private List<ScoreRecords> buildResult(List<GamerRecord> roomRecords, long p) {
		Map<Integer, ScoreRecords> resultMap = new HashMap<Integer, ScoreRecords>();
		for (int i = 0; i < roomRecords.size(); i++) {
			GamerRecord roomRecord = roomRecords.get(i);
			Integer juNum = roomRecord.getJuNum();
			if (null == resultMap.get(juNum)) {
				resultMap.put(juNum, new ScoreRecords(roomRecord.getRoomId()));
			}
			ScoreRecords result = resultMap.get(juNum);
			result.setJuNum(juNum);
			if (roomRecord.getGamerId() == p) {
				result.setRepId(roomRecord.getReplayId());
			}
			result.setTime(roomRecord.getEndTime());
			Map<String, Object> usersInfo = new HashMap<String, Object>();
			usersInfo.put("nickName", roomRecord.getNickName());
			usersInfo.put("score", roomRecord.getWinPoints());
			result.addUsersInfo(usersInfo);
		}

		// 转换为list返回
		List<ScoreRecords> resultList = new ArrayList<ScoreRecords>();
		Iterator<Entry<Integer, ScoreRecords>> it = resultMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, ScoreRecords> type = it.next();
			resultList.add(type.getValue());
		}
		// 按房间号排序
		Collections.sort(resultList);
		return resultList;
	}
}
