package com.uitrs.game.majiang.web.service.consRecord;

/**
 * 消费记录列表
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:55:16
 */
public class ConsRecordService {
	private static ConsRecordService consRecordService = new ConsRecordService();

	private ConsRecordService() {

	}

	public static ConsRecordService getService() {
		return consRecordService;
	}
}
