package com.uitrs.game.majiang.common.exception;

/**
 * 有异常码的自定义异常,便于出现异常时对异常进行处理,和异常问题定位
 * 
 * @author lucio
 *
 */
public class CodeException extends Throwable {
	private static final long serialVersionUID = 324534067156945741L;
	/** 异常码 */
	protected int code;
	/** 异常信息 */
	protected Throwable e;

	public CodeException(int code) {
		super();
		this.code = code;
	}

	public CodeException(Throwable e) {
		super();
		this.e = e;
	}

	public CodeException(int code, Throwable e) {
		super();
		this.code = code;
		this.e = e;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Throwable getE() {
		return e;
	}

	public void setE(Throwable e) {
		this.e = e;
	}

}
