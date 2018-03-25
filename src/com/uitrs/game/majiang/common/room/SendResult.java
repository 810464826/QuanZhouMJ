package com.uitrs.game.majiang.common.room;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.jfinal.weixin.sdk.utils.RetryUtils.ResultCheck;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.MJUtil;
import com.uitrs.game.majiang.common.msg.SendMsg;

public class SendResult implements ResultCheck {

	private long roomId;

	public SendResult(long roomId) {
		this.roomId = roomId;
	}

	@Override
	public boolean matching() {
		Map<Integer, SendMsg> msgs = MJCache.getCache().getMsgs(this.roomId);
		for (Iterator<Entry<Integer, SendMsg>> iterator = msgs.entrySet().iterator(); iterator.hasNext();) {
			Entry<Integer, SendMsg> entry = iterator.next();
			SendMsg msg = entry.getValue();
			if (null != msg) {
				System.err.println("SendResult: "+msg.toJson());
				if (MJUtil.isOptMsg(msg)) {
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}

	@Override
	public String getJson() {
		return "roomId: "+roomId;
	}

}
