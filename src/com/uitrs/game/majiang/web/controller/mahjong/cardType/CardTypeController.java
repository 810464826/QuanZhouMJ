package com.uitrs.game.majiang.web.controller.mahjong.cardType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.web.common.MType;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.CardType;
import com.uitrs.game.majiang.web.model.SuperPage;
import com.uitrs.game.majiang.web.service.adminInfo.AdminInfoService;
import com.uitrs.game.majiang.web.service.cardType.CardTypeService;
import com.uitrs.web.common.controller.ControllerPath;
import com.uitrs.web.common.interceptor.SessionInterceptor;

/**
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:41:50
 */
@Before({ SessionInterceptor.class })
@ControllerPath(controllerKey = "/cardType")
public class CardTypeController extends Controller {
	/**
	 * 查询所有房卡信息
	 */
	public void getAll() {
		Map<String, Object> payCards = CardTypeService.getService().getAllPayCards(getParaToInt("curentPage"), 10);
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("curentPage", payCards.get("curentPage"));
		datas.put("totalPages", payCards.get("totalPages"));
		datas.put("message", payCards.get("list"));
		renderJson(datas);
	}

	/**
	 * 查询所有的房卡类型
	 */
	public void getAllTypes() {
		List<CardType> types = CardTypeService.getService().getCardTypes();
		setAttr("types", types);
		setAttr("person",
				AdminInfoService.getService().getAdminByUserName((String) getSession().getAttribute("username")));
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newCard.html");
	}

	/**
	 * 模糊查询指定的类型的房卡类型
	 */
	public void getSameTypes() {
		List<CardType> types = CardTypeService.getService().getMHTypes(getPara("name"));
		setAttr("types", types);
		setAttr("person",
				AdminInfoService.getService().getAdminByUserName((String) getSession().getAttribute("username")));
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newCard.html");
	}

	/**
	 * 去向添加房卡类型 mobile
	 */
	public void goToAddType() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newAddCardInfo.html");
	}

	/**
	 * 修改时 查询单个类型
	 */
	public void getById() {
		setAttr("cardType", CardTypeService.getService().getCardById(getParaToInt("typeId")));
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/edit_room.html");
	}

	/**
	 * 这里修改
	 */
	public void editType() {
		int id = getParaToInt("id");
		String typeName = getPara("typeName");
		double cardMoney = Double.parseDouble(getPara("cardMoney"));
		int cardNum = getParaToInt("cardNum");
		boolean update = CardTypeService.getService().update(id, typeName, cardMoney, cardNum);
		renderJson("result", update);
	}

	/**
	 * 查询指定id的房卡类型
	 */
	public void getOneType() {
		int id = getParaToInt("name");
		String type = getPara("type");
		CardType card = CardTypeService.getService().getCardById(id);
		setAttr("card", card);
		setAttr("type", type);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newCardInfo.html");
	}

	/**
	 * 查询制定类型的房卡信息
	 */
	public void getByType() {
		SuperPage<CardType> page = CardTypeService.getService().getPayCardsByTypePage(getPara("type"));
		if (page != null) {
			renderJson(JsonUtils.toJson(page));
		} else {
			renderJson("false");
		}
	}

	/**
	 * 去向添加房卡的页面
	 */
	public void toAddType() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/add_roomType.html");
	}

	/**
	 * 去向添加房卡类型 mobile
	 */
	public void goToAddTypeMobile() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newAddCardInfo.html");
	}

	/**
	 * 添加房卡类型
	 */
	public void addCardType() {
		String typeName = getPara("typeName");
		double payNum = getParaToInt("payNum");
		int payCards = getParaToInt("payCards");
		Map<String, Object> model = new HashMap<String, Object>();
		boolean result = CardTypeService.getService().add(typeName, payNum, payCards);
		String device = MType.getType().getDevice();
		if ("mobile".equals(device)) {
			renderJson("result", result);
		} else {
			if (result) {
				model.put("result", "true");
				model.put("msg", "添加成功！");
				renderJson(Tools.mapToJson(model));
			} else {
				model.put("result", "false");
				model.put("msg", "添加失败！");
				renderJson(Tools.mapToJson(model));
			}
		}
	}

	/**
	 * 删除指定的类型
	 */
	public void deleteType() {
		renderJson("result", CardTypeService.getService().delete(getParaToInt("id")));
	}

}
