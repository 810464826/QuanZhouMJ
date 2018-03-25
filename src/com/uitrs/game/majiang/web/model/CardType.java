package com.uitrs.game.majiang.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.base.BaseCardType;

@SuppressWarnings("serial")
public class CardType extends BaseCardType<CardType> {
	public static final CardType dao = new CardType();

	/**
	 * 实现分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> paginate(int pageNumber, int pageSize) {
		List<Object> results = new ArrayList<Object>();
		Page<CardType> page = paginate(pageNumber, pageSize, "select *", "from cardtype order by id asc");
		for (int i = 0; i < page.getList().size(); i++) {
			Map<String, Object> model = new HashMap<String, Object>();
			CardType cardType = page.getList().get(i);
			model.put("id", cardType.getId());
			model.put("typeName", cardType.getTypeName());
			model.put("cardMoney", cardType.getCardMoney());
			model.put("cardNum", cardType.getCardNum());
			results.add(Tools.mapToJson(model));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", results);
		map.put("curentPage", page.getPageNumber());
		map.put("totalPages", page.getTotalPage());
		return map;
	}
}
