package com.uitrs.game.majiang.web.common;

public class MType {
	private String device;
	public static MType type = new MType();

	private MType() {

	}

	public static MType getType() {
		return type;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

}
