package com.uitrs.game.majiang.common.kit;

import com.jfinal.log.Log;
import com.uitrs.web.common.kit.SystemUtil;

/**
 * 监视线程,查看房间数和在线用户数
 * 
 * @author lucio
 *
 */
public class DeamonThread implements Runnable {

	private static final Log log = Log.getLog(DeamonThread.class);
	public static boolean isStop = false;

	public DeamonThread() {
	}

	@Override
	public void run() {
		while (!isStop) {
			try {
				if (log.isInfoEnabled()) {
					log.info("Online Rooms:[" + MJCache.getCache().getRooms().size() + "]");
					log.info("Online Users:[" + MJCache.getCache().getPlayerInfos().size() + "]");
					log.info("Total Thread:[" + SystemUtil.getThreadNum() + "]");
					log.info("Total Memory:[" + SystemUtil.getTotalMemory() + " mb]");
					log.info("Free  Memory:[" + SystemUtil.getFreeMemory() + " mb]");
				}
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
