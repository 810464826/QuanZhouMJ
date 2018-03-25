package com.uitrs.game.majiang.web.service.cardType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.uitrs.game.majiang.web.model.CardType;
import com.uitrs.game.majiang.web.model.SuperPage;

/**
 * 房卡类型
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:54:00
 */
public class CardTypeService {
	private static CardTypeService cardTypeService = new CardTypeService();

	private CardTypeService() {

	}

	public static CardTypeService getService() {
		return cardTypeService;
	}

	/**
	 * 查询所有房卡信息
	 */
	public Map<String, Object> getAllPayCards(int pageNumber, int pageSize) {
		return CardType.dao.paginate(pageNumber, pageSize);
	}

	/**
	 * 查询所有的房卡销售
	 */
	public List<CardType> getCardTypes() {
		return CardType.dao.find("select * from cardtype limit 6");
	}

	/**
	 * 添加房卡类型
	 */
	public boolean add(String typeName, Double cardMoney, int cardNum) {
		CardType card = new CardType();
		boolean result = card.set("typeName", typeName).set("cardMoney", cardMoney).set("cardNum", cardNum).save();
		return result;
	}

	/**
	 * 修改房卡类型
	 */
	public boolean update(int id, String typeName, Double cardMoney, int cardNum) {
		CardType card = getCardById(id);
		boolean result = card.set("id", id).set("typeName", typeName).set("cardMoney", cardMoney)
				.set("cardNum", cardNum).update();
		return result;
	}

	/**
	 * 模糊查询单个类型房卡信息
	 */
	public List<CardType> getMHTypes(String typeName) {
		return CardType.dao.find("select * from cardtype where typeName like '%" + typeName + "%' limit 6");
	}

	public CardType getOnePayCard(String typeName) {
		return CardType.dao.findFirst("select * from cardtype where typeName = ? ", typeName);
	}

	/**
	 * 查询单个的房卡类型
	 */
	public CardType getCardById(int id) {
		return CardType.dao.findById(id);
	}

	/**
	 * 删除指定的类型
	 */
	public boolean delete(int id) {
		return CardType.dao.deleteById(id);
	}

	/**
	 * 查询单个类型房卡信息
	 */
	public List<CardType> getPayCardsByType(String typeName) {
		return CardType.dao.find("select * from cardtype where typeName = ? ", typeName);
	}

	/**
	 * 获取扩展对象
	 */
	public SuperPage<CardType> getPayCardsByTypePage(String typeName) {
		List<CardType> cards = getPayCardsByType(typeName);
		if (cards.size() > 0) {
			SuperPage<CardType> page = new SuperPage<CardType>();
			for (CardType p : cards) {
				page.getList().add(p);
			}
			page.setCurrentPage(1);
			page.setTotalPages(1);
			return page;
		} else {
			return null;
		}
	}

	/**
	 * 查询房卡类型
	 */
	public List<Object> getAllTypes() {
		List<Object> list = new ArrayList<Object>();
		Set<Object> set = new HashSet<Object>();
		List<CardType> notice = CardType.dao.find("select typeName from cardtype");
		for (CardType s : notice) {
			set.add(s.getTypeName());
		}
		list.addAll(set);
		return list;
	}

	/**
	 * 获取游戏类型
	 */
	public List<Object> getGamerTypes() {
		List<Object> list = new ArrayList<Object>();
		list.add("泉州麻将");
		return list;
	}

}
