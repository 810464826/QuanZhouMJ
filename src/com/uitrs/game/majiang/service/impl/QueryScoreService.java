package com.uitrs.game.majiang.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.kit.MJCache;
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
public class QueryScoreService extends SupperService {

	/**
	 * dataArr = [ {roomId: '6389292', time: '2016-11-29 14:03', usersInfo: [
	 * {nickName: '如花',score: -28}, {nickName: '如花',score: 15}, {nickName:
	 * '如花',score: -28}, {nickName: '如花',score: -28}]}]
	 */
	@Override
	public void doService(Session session, ReceiveMsg action) {
		List<GamerRecord> roomRecords = GamerRecord.dao.find(
				"select * from gamerecord where roomid in(select roomid from gamerecord where gamerid=? ) order by endtime desc ,roomid asc limit 100",
				MJCache.getCache().getUserInfo(action.getP()).getUserId());

		List<ScoreRecords> resultList = buildResult(roomRecords);
		SendMsg result = new SendMsg(action.getP(), true, action.getT());
		result.setM(resultList);
		// result.setE(e);
		WebSocketUtil.sendAsyncMsg(result, session);
	}

	private List<ScoreRecords> buildResult(List<GamerRecord> roomRecords) {
		Map<Long, ScoreRecords> resultMap = new HashMap<Long, ScoreRecords>();
		for (int i = 0; i < roomRecords.size(); i++) {
			GamerRecord roomRecord = roomRecords.get(i);
			if (null == resultMap.get(roomRecord.getRoomId())) {
				resultMap.put(roomRecord.getRoomId(), new ScoreRecords(roomRecord.getRoomId()));
			}
			ScoreRecords result = resultMap.get(roomRecord.getRoomId());
			result.setTime(roomRecord.getEndTime());
			Map<String, Object> usersInfo = new HashMap<String, Object>();
			usersInfo.put("nickName", roomRecord.getNickName());
			usersInfo.put("score", roomRecord.getWinPoints());
			result.addUsersInfo(usersInfo);
		}

		// 转换为list返回
		List<ScoreRecords> resultList = new ArrayList<ScoreRecords>();
		Iterator<Entry<Long, ScoreRecords>> it = resultMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Long, ScoreRecords> type = it.next();
			resultList.add(type.getValue());
		}
		// 按房间号排序
		Collections.sort(resultList);
		return resultList;
	}
}
