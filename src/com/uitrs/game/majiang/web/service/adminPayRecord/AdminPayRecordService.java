package com.uitrs.game.majiang.web.service.adminPayRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.AdminInfo;
import com.uitrs.game.majiang.web.model.AdminPayRecord;
import com.uitrs.game.majiang.web.model.CardType;
import com.uitrs.game.majiang.web.model.SuperPage;
import com.uitrs.game.majiang.web.service.adminInfo.AdminInfoService;
import com.uitrs.game.majiang.web.service.cardType.CardTypeService;

/**
 * 代理商购买记录列表
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:52:07
 */
public class AdminPayRecordService {
	private static AdminPayRecordService adminPayRecordService = new AdminPayRecordService();

	private AdminPayRecordService() {

	}

	public static AdminPayRecordService getService() {
		return adminPayRecordService;
	}

	/**
	 * 查询所有充值记录信息
	 */
	public Map<String, Object> getAdminAllPayRecord(int pageNumber, int pageSize) {
		return AdminPayRecord.dao.paginateAdmin(pageNumber, pageSize);
	}

	/**
	 * 查询所有的充值记录 默认6条 按充值时间的倒叙
	 */
	public List<AdminPayRecord> getRecords() {
		return AdminPayRecord.dao.find("select * from adminpayrecord order by payTime desc limit 6");
	}

	/**
	 * 查询该管理员的充值记录
	 */
	public List<AdminPayRecord> getRecordsByAdminId(int adminId) {
		return AdminPayRecord.dao.find(
				"select * from adminpayrecord where adminId like '%" + adminId + "%' order by payTime desc limit 6");
	}

	/**
	 * 查询指定玩家的充值记录 默认6条 按充值时间的倒叙
	 */
	public List<AdminPayRecord> getSameRecords(int adminId) {
		return AdminPayRecord.dao.find("select * from adminpayrecord where adminId = ? order by payTime desc limit 6",
				adminId);
	}

	/**
	 * 删除充值记录
	 * 
	 * @return
	 */
	public boolean deleteRecord(int id) {
		return AdminPayRecord.dao.deleteById(id);
	}

	/**
	 * 查询单个的充值记录
	 */
	public AdminPayRecord getOneRecords(int id) {
		return AdminPayRecord.dao.findById(id);
	}

	/**
	 * 查询指定记录信息
	 */
	public List<AdminPayRecord> getPayRecordById(String gamerId, String openId) {
		String neirong = "1=1 ";
		if (!"".equals(gamerId)) {
			neirong += "and gamerId like '%" + gamerId + "%'";
		}
		if (!"".equals(openId)) {
			neirong += " and openId like '%" + openId + "%'";
		}
		// 我记得下次模糊的时候 不用插入 用 拼接 是这样的
		String sql = "select * from adminpayrecord where " + neirong;
		return AdminPayRecord.dao.find(sql);
	}

	/**
	 * 查询指定记录信息 该代理商的
	 */
	public List<AdminPayRecord> getAdminPayRecordById(int adminId) {
		// 我记得下次模糊的时候 不用插入 用 拼接 是这样的
		String sql = "select * from adminpayrecord where adminId like '%" + adminId + "%' ";
		return AdminPayRecord.dao.find(sql);
	}

	/**
	 * 查询指定记录信息 该代理商的
	 */
	public List<AdminPayRecord> getAdminPayRecordNoAdminId() {
		// 我记得下次模糊的时候 不用插入 用 拼接 是这样的
		String sql = "select * from adminpayrecord ";
		return AdminPayRecord.dao.find(sql);
	}

	/**
	 * 查询扩展类
	 */
	public SuperPage<AdminPayRecord> getAdminPayRecordPage(int adminId) {
		List<AdminPayRecord> records = getAdminPayRecordById(adminId);
		if (records.size() > 0) {
			SuperPage<AdminPayRecord> page = new SuperPage<AdminPayRecord>();
			for (AdminPayRecord r : records) {
				page.getList().add(r);
			}
			page.setCurrentPage(1);
			page.setTotalPages(Tools.getSize(records.size()));
			return page;
		} else {
			return null;
		}
	}

	/**
	 * 查询扩展类
	 */
	public SuperPage<AdminPayRecord> getNoAdminIdRecordPage() {
		List<AdminPayRecord> records = getAdminPayRecordNoAdminId();
		if (records.size() > 0) {
			SuperPage<AdminPayRecord> page = new SuperPage<AdminPayRecord>();
			for (AdminPayRecord r : records) {
				page.getList().add(r);
			}
			page.setCurrentPage(1);
			page.setTotalPages(Tools.getSize(records.size()));
			return page;
		} else {
			return null;
		}
	}

	/**
	 * 这是超级管理员给代理商得充值
	 */
	public boolean payForAdmin(double payMoney, int adminId, String payType) {
		AdminPayRecord adminPay = new AdminPayRecord();
		boolean save = adminPay.set("adminId", adminId).set("payMoney", payMoney).set("payType", payType)
				.set("payTime", Tools.date2String(new Date())).set("state", 0).save();
		if (save) {
			CardType cardType = CardTypeService.getService().getPayCardsByType(payType).get(0);
			double cardMoney = cardType.getCardMoney();
			int cardNum = cardType.getCardNum();
			// 计算充值的数量
			int payCardsNum = (int) (cardNum / cardMoney * payMoney);
			// 充值成功之后 将该代理商得房卡数量添加上
			AdminInfo adminInfo = AdminInfoService.getService().getAdminByAdminId(adminId);
			boolean update = adminInfo.set("id", adminInfo.getId())
					.set("totalCards", adminInfo.getTotalCards() + payCardsNum)
					.set("surplusCards", adminInfo.getSurplusCards() + payCardsNum).update();
			return update;
		}
		return false;
	}
}
