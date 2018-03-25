package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.web.model.ReplayInfo;
import com.uitrs.game.majiang.websockets.WebSocketUtil;

/**
 * 回放
 * 
 * @author lucio
 *
 */
public class ReplayService extends SupperService {

	@Override
	public void doService(Session session, ReceiveMsg action) {
		SendMsg result = new SendMsg(action.getP(), true, action.getT());
		//获取replayId
		Object m = action.getM();
		if (null != m) {
			int replayId = Integer.parseInt(m.toString().trim());
			ReplayInfo repMsg = ReplayInfo.dao.findFirst("select * from replayinfo where replayId=?", replayId);
			if (null != repMsg) {
				result.setM(repMsg);
			} else {
				result.setS(false);
				result.setE("回放不存在或已失效!");
			}
		} else {
			result.setS(false);
			result.setE("回放不存在或已失效!");
		}

		WebSocketUtil.sendAsyncMsg(result, session);
	}

}
