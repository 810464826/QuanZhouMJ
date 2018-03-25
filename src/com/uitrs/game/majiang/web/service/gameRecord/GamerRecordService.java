package com.uitrs.game.majiang.web.service.gameRecord;

import java.util.List;
import java.util.Map;

import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.GamerRecord;
import com.uitrs.game.majiang.web.model.SuperPage;

/**
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:55:52
 */
public class GamerRecordService {
	private static GamerRecordService gamerRecordService = new GamerRecordService();

	private GamerRecordService() {

	}

	public static GamerRecordService getService() {
		return gamerRecordService;
	}

	/**
	 * 查询该玩家的成绩记录
	 */
	public List<GamerRecord> getRecord(int gamerId) {
		return GamerRecord.dao.find("select * from gamerecord where gamerId = ? ", gamerId);
	}

	/**
	 * 查询所有的成绩
	 */
	public List<GamerRecord> getRecords() {
		return GamerRecord.dao.find("select * from gamerecord");
	}

	/**
	 * 模糊查询成绩
	 */
	public List<GamerRecord> getMHRecord(int gamerId) {
		return GamerRecord.dao.find("select * from gamerecord where gamerId like '%" + gamerId + "%'");
	}

	/**
	 * 模糊查询成绩 没有输条件
	 */
	public List<GamerRecord> getMHRecordByNull() {
		return GamerRecord.dao.find("select * from gamerecord");
	}

	/**
	 * 获取扩展对象
	 */
	public SuperPage<GamerRecord> getRecordPage(int gamerId) {
		List<GamerRecord> record = getMHRecord(gamerId);
		if (record.size() > 0) {
			SuperPage<GamerRecord> page = new SuperPage<GamerRecord>();
			for (GamerRecord r : record) {
				page.getList().add(r);
			}
			page.setCurrentPage(1);
			page.setTotalPages(Tools.getSize(record.size()));
			return page;
		} else {
			return null;
		}
	}

	/**
	 * 获取扩展对象 没有输任何条件
	 */
	public SuperPage<GamerRecord> getRecordPageByNull() {
		List<GamerRecord> record = getMHRecordByNull();
		if (record.size() > 0) {
			SuperPage<GamerRecord> page = new SuperPage<GamerRecord>();
			for (GamerRecord r : record) {
				page.getList().add(r);
			}
			page.setCurrentPage(1);
			page.setTotalPages(Tools.getSize(record.size()));
			return page;
		} else {
			return null;
		}
	}

	/**
	 * 查询所有玩家的成绩记录
	 */
	public List<GamerRecord> getAllRecords() {
		return GamerRecord.dao.find("select * from  gamerecord");
	}

	/**
	 * 分页查询 查询所有的管理员的信息
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> paginate(int pageNumber, int pageSize) {
		return GamerRecord.dao.paginate(pageNumber, pageSize);
	}

	/**
	 * 根据id查询该记录
	 */
	public GamerRecord getOneRecord(int id) {
		return GamerRecord.dao.findById(id);
	}
}
