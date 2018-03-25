package com.uitrs.game.majiang.common.exception;

import com.jfinal.kit.PropKit;

/**
 * 麻将自定义异常
 * 
 * @author lucio
 *
 */
public class MJException extends CodeException {

	private static final long serialVersionUID = -3879586559804204097L;

	public MJException(Throwable e) {
		super(e);
	}

	public MJException(int code, Throwable e) {
		super(code, e);
		this.errorTip = PropKit.get(EConst.PREFIX + this.code);
	}

	/** 错误提示信息 */
	private String errorTip;

	public String getErrorTip() {
		if (null == errorTip) {
			errorTip = PropKit.get(EConst.PREFIX + this.code);
		}
		return errorTip;
	}

	// public void setErrorTip(String errorTip) {
	// this.errorTip = errorTip;
	// }
}
