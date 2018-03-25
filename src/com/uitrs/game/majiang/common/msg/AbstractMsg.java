package com.uitrs.game.majiang.common.msg;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.jfinal.weixin.sdk.utils.JsonUtils;

/**
 * 提供默认的toJson和toString方法,子类可以覆盖实现
 * 
 * @author lucio
 *
 */
public abstract class AbstractMsg implements IMsg, Serializable, Cloneable {
	private static final long serialVersionUID = 7042210887186383611L;

	@Override
	public String toJson() {
		return JsonUtils.toJson(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
