package com.uitrs.game.majiang.service.impl;

import javax.websocket.Session;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.service.SupperService;
import com.uitrs.game.majiang.web.service.gamerInfo.GamerInfoService;

/**
 * 分享奖励
 * @author lucio
 *
 */
public class WXShareGoldService extends SupperService {
	private final Log log = Log.getLog(WXShareGoldService.class);

	@Override
	public void doService(Session session, ReceiveMsg action) {
		// Room room = WSSessionKit.getRoom(session);
		if (PropKit.getBoolean("weixinMode")) {
			//1朋友  2朋友圈
			int type = (int) action.getM();
			//userId
			int gamerId = (int) action.getP();
			if(!GamerInfoService.getService().isShared(gamerId)){
				if(type==1){
					log.info("share friend!");
					GamerInfoService.getService().addCardForShareGamer(gamerId,10);
				}else if(type==2){
					log.info("share friend group!");
					GamerInfoService.getService().addCardForShareGamer(gamerId,5);
				}
			}
		}
	}
}
