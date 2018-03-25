package com.uitrs.game.majiang.web.service.gamerPayRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.AdminInfo;
import com.uitrs.game.majiang.web.model.CardType;
import com.uitrs.game.majiang.web.model.GamerInfo;
import com.uitrs.game.majiang.web.model.GamerPayRecord;
import com.uitrs.game.majiang.web.model.SuperPage;
import com.uitrs.game.majiang.web.service.adminInfo.AdminInfoService;
import com.uitrs.game.majiang.web.service.cardType.CardTypeService;
import com.uitrs.game.majiang.web.service.gamerInfo.GamerInfoService;

/**
 * 玩家充值列表
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:56:02
 */
public class GamerPayService {
	private static GamerPayService gamerPayService = new GamerPayService();

	private GamerPayService() {

	}

	public static GamerPayService getService() {
		return gamerPayService;
	}

	/**
	 * 查询所有充值记录信息
	 */
	public Map<String, Object> getAdminAllPayRecord(int pageNumber, int pageSize, int adminId) {
		return GamerPayRecord.dao.paginateGamer(pageNumber, pageSize, adminId);
	}

	/**
	 * 查询所有的充值记录 默认6条 按充值时间的倒叙
	 */
	public List<GamerPayRecord> getRecords() {
		return GamerPayRecord.dao.find("select * from gamerpayrecord order by payTime desc limit 6");
	}

	/**
	 * 查询指定玩家的充值记录 默认6条 按充值时间的倒叙
	 */
	public List<GamerPayRecord> getSameRecordsByAdminId(int adminId) {
		return GamerPayRecord.dao.find("select * from gamerpayrecord where adminId = ? order by payTime desc limit 6",
				adminId);
	}

	public List<GamerPayRecord> getSameRecordsByGamerId(int gamerId) {
		return GamerPayRecord.dao.find(
				"select * from gamerpayrecord where gamerId like '%" + gamerId + "%' order by payTime desc limit 6");
	}

	public List<GamerPayRecord> getSameRecordsByNoId() {
		return GamerPayRecord.dao.find("select * from gamerpayrecord order by payTime desc ");
	}

	/**
	 * 删除充值记录
	 * 
	 * @return
	 */
	public boolean deleteRecord(int id) {
		return GamerPayRecord.dao.deleteById(id);
	}

	/**
	 * 查询单个的充值记录
	 */
	public GamerPayRecord getOneRecords(int id) {
		return GamerPayRecord.dao.findById(id);
	}

	/**
	 * 查询指定记录信息
	 */
	public List<GamerPayRecord> getPayRecordById(String gamerId, String openId) {
		String neirong = "1=1 ";
		if (!"".equals(gamerId)) {
			neirong += "and gamerId like '%" + gamerId + "%'";
		}
		if (!"".equals(openId)) {
			neirong += " and openId like '%" + openId + "%'";
		}
		// 我记得下次模糊的时候 不用插入 用 拼接 是这样的
		String sql = "select * from gamerpayrecord where " + neirong;
		return GamerPayRecord.dao.find(sql);
	}

	/**
	 * 查询指定记录信息 该代理商的
	 */
	public List<GamerPayRecord> getAdminPayRecordById(int gamerId, int adminId) {
		// 我记得下次模糊的时候 不用插入 用 拼接 是这样的
		String sql = "select * from gamerpayrecord where gamerId= ? and adminId= ? ";
		return GamerPayRecord.dao.find(sql, gamerId, adminId);
	}

	/**
	 * 查询扩展类
	 */
	public SuperPage<GamerPayRecord> getGamerPayRecordPage(int gamerId) {
		List<GamerPayRecord> records = getSameRecordsByGamerId(gamerId);
		if (records.size() > 0) {
			SuperPage<GamerPayRecord> page = new SuperPage<GamerPayRecord>();
			for (GamerPayRecord r : records) {
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
	public SuperPage<GamerPayRecord> getGamerPayRecordPageByNoId() {
		List<GamerPayRecord> records = getSameRecordsByNoId();
		if (records.size() > 0) {
			SuperPage<GamerPayRecord> page = new SuperPage<GamerPayRecord>();
			for (GamerPayRecord r : records) {
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
	 * 这里进行玩家的房卡充值 如果管理员不是超级管理员得话 就将该代理商得房卡数减掉
	 */
	public boolean payCardForGamer(double payMoney, int adminId, int gamerId, String payType) {
		GamerPayRecord payRecord = new GamerPayRecord();
		AdminInfo adminInfo = AdminInfoService.getService().getAdminByAdminId(adminId);
		GamerInfo gamerInfo = GamerInfoService.getService().getGamerById(gamerId);
		// 这是超级管理员得充值
		if (adminInfo.getSysType() == 0) {
			boolean save = payRecord.set("payMoney", payMoney).set("payTime", Tools.date2String(new Date()))
					.set("adminId", adminId).set("headFace",gamerInfo.getHeadFace()).set("gamerId", gamerId).set("payType", payType).save();
			/* 充值成功后，将该玩家的信息中的房卡加上 */
			if (save) {
				CardType cardType = CardTypeService.getService().getPayCardsByType(payType).get(0);
				double cardMoney = cardType.getCardMoney();
				int cardNum = cardType.getCardNum();
				// 计算充值的数量
				int payCardsNum = (int) (cardNum / cardMoney * payMoney);
				boolean update = gamerInfo.set("totalCards", gamerInfo.getTotalCards() + payCardsNum)
						.set("surplusCards", gamerInfo.getSurplusCards() + payCardsNum).set("id", gamerInfo.getId())
						.update();
				return update;
			}
		} else {
			CardType cardType = CardTypeService.getService().getPayCardsByType(payType).get(0);
			double cardMoney = cardType.getCardMoney();
			int cardNum = cardType.getCardNum();
			// 计算充值的数量
			int payCardsNum = (int) (cardNum / cardMoney * payMoney);
			Integer surplusCards = adminInfo.getSurplusCards();
			if (surplusCards >= payCardsNum) {
				boolean save = payRecord.set("payMoney", payMoney).set("payTime", Tools.date2String(new Date()))
						.set("adminId", adminId).set("headFace",gamerInfo.getHeadFace()).set("gamerId", gamerId).set("payType", payType).save();
				if (save) {
					boolean update = gamerInfo.set("totalCards", gamerInfo.getTotalCards() + payCardsNum)
							.set("surplusCards", gamerInfo.getSurplusCards() + payCardsNum).set("id", gamerInfo.getId())
							.update();
					if (update) {
						boolean update2 = adminInfo.set("id", adminInfo.getId())
								.set("surplusCards", adminInfo.getSurplusCards() - payCardsNum).update();
						return update2;
					}
				}
			}
		}

		return false;
	}

}
