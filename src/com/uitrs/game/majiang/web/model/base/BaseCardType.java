package com.uitrs.game.majiang.web.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 消费记录表
 */
@SuppressWarnings("serial")
public abstract class BaseCardType<M extends BaseCardType<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setTypeName(java.lang.String typeName) {
		set("typeName", typeName);
	}

	public java.lang.String getTypeName() {
		return get("typeName");
	}

	public void setCardNum(java.lang.Integer cardNum) {
		set("cardNum", cardNum);
	}

	public java.lang.Integer getCardNum() {
		return get("cardNum");
	}

	public void setCardMoney(java.lang.Double cardMoney) {
		set("cardMoney", cardMoney);
	}

	public java.lang.Double getCardMoney() {
		return get("cardMoney");
	}

}
