package com.uitrs.game.majiang.common.msg.result;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 本房间打牌结束后的结果
 * 
 * @author lucio
 *
 */
public class RoomScoreMsg extends AbstractMsg {

	private static final long serialVersionUID = -8314727224787876359L;
	/** 房间结束4个人的结果 */
	private List<HupaiMsg> roomResults = new CopyOnWriteArrayList<HupaiMsg>();

	public List<HupaiMsg> getRoomResults() {
		return roomResults;
	}

	public void addRoomResult(HupaiMsg hupaiMsg) {
		this.roomResults.add(hupaiMsg);
	}
}
