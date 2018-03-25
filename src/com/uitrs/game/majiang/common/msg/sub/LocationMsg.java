package com.uitrs.game.majiang.common.msg.sub;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 地理位置信息
 * @author lucio
 *
 */
public class LocationMsg extends AbstractMsg {
	private static final long serialVersionUID = 9147130547932205014L;
	/** 纬度 */
	protected double lat;
	/** 经度 */
	protected double lon;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
}
