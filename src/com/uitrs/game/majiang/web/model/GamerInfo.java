package com.uitrs.game.majiang.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.base.BaseGamerInfo;

@SuppressWarnings("serial")
public class GamerInfo extends BaseGamerInfo<GamerInfo> {
	public static final GamerInfo dao = new GamerInfo();

	/**
	 * 实现分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> paginate(int pageNumber, int pageSize) {
		List<Object> results = new ArrayList<Object>();
		Page<GamerInfo> page = paginate(pageNumber, pageSize, "select *", "from gamerinfo order by id asc");
		for (int i = 0; i < page.getList().size(); i++) {
			Map<String, Object> model = new HashMap<String, Object>();
			GamerInfo gamerInfo = page.getList().get(i);
			model.put("id", gamerInfo.getId());
			model.put("openId", gamerInfo.getOpenId());
			model.put("nickName", gamerInfo.getNickName());
			model.put("headFace", gamerInfo.getHeadFace());
			model.put("sex", gamerInfo.getSex());
			model.put("totalCards", gamerInfo.getTotalCards());
			model.put("surplusCards", gamerInfo.getSurplusCards());
			model.put("telephone", gamerInfo.getTelephone());
			model.put("qqNum", gamerInfo.getQqNum());
			model.put("enableLogin", gamerInfo.getEnableLogin());
			model.put("password", gamerInfo.getPassword());
			model.put("parentId", gamerInfo.getParentId());
			model.put("shareNum", gamerInfo.getShareNum());
			model.put("shareTime", gamerInfo.getShareTime());
			results.add(Tools.mapToJson(model));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", results);
		map.put("curentPage", page.getPageNumber());
		map.put("totalPages", page.getTotalPage());
		return map;
	}

	/**
	 * 玩家充值房卡时添加房卡数
	 * 
	 * @param gamerId
	 * @param payCards
	 * @return
	 */
	public boolean addCards(int gamerId, int payCards) {
		GamerInfo gamerInfo = this.findById(gamerId);
		if (gamerInfo == null) {
			return false;
		} else {
			String openId = gamerInfo.getOpenId();
			int cards = gamerInfo.getTotalCards() + payCards;
			boolean result = gamerInfo.set("id", gamerId).set("openId", openId)
					.set("totalCards", cards)
					.set("surplusCards", gamerInfo.getSurplusCards() + payCards).update();
			if (result) {
				// 更新缓存
				PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(gamerId);
				if (playerInfo != null) {
					UserInfo userInfo = playerInfo.getUserInfo();
					if (userInfo != null) {
						userInfo.setCards(cards);
					}
				}
			}
			return result;
		}

	}

	/**
	 * 修改玩家房卡数,减掉对应的房卡数
	 * 
	 * @param gamerId
	 * @param payCards
	 * @return
	 */
	public boolean surplusCards(int gamerId, int payCards) {

		GamerInfo gamerInfo = this.findById(gamerId);
		if (gamerInfo == null) {
			return false;
		} else {
			boolean result = this.set("id", gamerId).set("surplusCards", gamerInfo.getSurplusCards() - payCards)
					.update();
			return result;
		}
	}
}
