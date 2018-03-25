package com.uitrs.game.majiang.web.service.gamerInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.GamerInfo;
import com.uitrs.game.majiang.web.model.SuperPage;

/**
 * 游戏记录表
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:55:56
 */
public class GamerInfoService {

	private static GamerInfoService gamerInfoService = new GamerInfoService();

	private GamerInfoService() {

	}

	public static GamerInfoService getService() {
		return gamerInfoService;
	}

	/**
	 * 修改指定玩家的信息
	 */
	public boolean editGammer(int id, String openId, int sex, String name, String pic, int nums, int num, String phone,
			String QQ) {
		GamerInfo gamer = new GamerInfo();
		boolean b = gamer.set("id", id).set("openId", openId).set("nickName", name).set("headFace", pic).set("sex", sex)
				.set("totalCards", nums).set("surplusCards", num).set("telephone", phone).set("qqNum", QQ)
				.set("enableLogin", 0).update();
		return b;
	}

	/**
	 * 根据id查询玩家
	 */
	public GamerInfo getGamerById(int id) {
		return GamerInfo.dao.findById(id);
	}

	/**
	 * 查询所有玩家信息
	 */
	public Map<String, Object> getAllGamer(int pageNumber, int pageSize) {
		return GamerInfo.dao.paginate(pageNumber, pageSize);
	}

	/**
	 * 查询所有的玩家
	 */
	public List<GamerInfo> getPlayers() {
		return GamerInfo.dao.find("select * from gamerinfo limit 6");
	}

	/**
	 * 根据openId查询对象
	 */
	public GamerInfo getGammer(String openid) {
		return GamerInfo.dao.findFirst("select * from gamerinfo where openId = ? ", openid);
	}

	/**
	 * 模糊查询指定的玩家
	 */
	public List<GamerInfo> getMHPlayers(String nickName) {
		return GamerInfo.dao.find("select * from gamerinfo where nickName like '%" + nickName + "%' limit 6");
	}

	/**
	 * 模糊查询指定的玩家
	 */
	public List<GamerInfo> getMHPlayersById(int id) {
		return GamerInfo.dao.find("select * from gamerinfo where id like '%" + id + "%'  limit 6");
	}

	/**
	 * 查询指定的玩家
	 */
	public GamerInfo getPlayer(String name) {
		return GamerInfo.dao.findFirst("select * from gamerinfo where nickName = ? ", name);
	}

	/**
	 * 根据OpenId查询指定的玩家
	 */
	public GamerInfo findByOpenId(String openId) {
		return GamerInfo.dao.findFirst("SELECT * FROM GAMERINFO WHERE OPENID = ? ", openId);
	}

	/**
	 * 查询指定条件玩家 名字是模糊查询 openId 也要模糊查询
	 */
	public List<GamerInfo> getGamer(String openId, String nickName) {
		String neirong = "1=1 ";
		// 这里是模糊查询名字
		if (!"".equals(nickName)) {
			neirong += "and nickName like '%" + nickName + "%'";
		}
		if (!"".equals(openId)) {
			neirong += " and openId like '%" + openId + "%'";
		}
		// 我记得下次模糊的时候 不用插入 用 拼接 是这样的
		String sql = "select * from gamerinfo where " + neirong;
		return GamerInfo.dao.find(sql);
	}

	/**
	 * 获取扩展对象
	 */
	public SuperPage<GamerInfo> getGamerPage(String openId, String nickName) {
		List<GamerInfo> gamer = getGamer(openId, nickName);
		if (gamer.size() > 0) {
			SuperPage<GamerInfo> page = new SuperPage<GamerInfo>();
			for (GamerInfo g : gamer) {
				page.getList().add(g);
			}
			page.setCurrentPage(1);
			page.setTotalPages(Tools.getSize(gamer.size()));
			return page;
		} else {
			return null;
		}
	}

	/**
	 * 玩家充值房卡时添加房卡数
	 */
	public boolean addCards(int gamerId, int payCards) {
		return GamerInfo.dao.addCards(gamerId, payCards);
	}

	/**
	 * 查询玩家的openId
	 */
	public String getOpenId(int id) {
		return getGamerById(id).getOpenId();
	}

	/**
	 * 玩家分享的时候 判断一下当天有没有分享过
	 */
	public boolean isShared(int gamerId) {
		GamerInfo gamerInfo = getGamerById(gamerId);
		String shareTime = gamerInfo.getShareTime();
		if(shareTime==null){
			return false;
		}
		Date stringNoSFM2Date = Tools.StringNoSFM2Date(shareTime);
		String date2String = Tools.dateNoSFM2String(new Date());
		String date2String2 = Tools.dateNoSFM2String(stringNoSFM2Date);
		if (date2String2.equals(date2String)) {
			return true;
		}
		return false;
	}

	/**
	 * 玩家分享 给该玩家增加 5张房卡
	 */
	public boolean addCardForShareGamer(int gamerId,int cards) {
		GamerInfo gamerInfo = getGamerById(gamerId);
		return gamerInfo.set("id", gamerId).set("surplusCards", gamerInfo.getSurplusCards() + cards).set("surplusCards", gamerInfo.getTotalCards() + cards)
				.set("shareNum", gamerInfo.getShareNum() + 1).set("shareTime",Tools.dateNoSFM2String(new Date())).update();
	}
	
	/**
	 * 查询下级玩家
	 */
	public List<GamerInfo> getNextGamers(int gamerId){
		return GamerInfo.dao.find("select * from gamerinfo where parentId = ? ",gamerId);
	}
}

