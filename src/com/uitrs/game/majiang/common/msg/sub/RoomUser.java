package com.uitrs.game.majiang.common.msg.sub;

import java.util.List;

public class RoomUser extends UserInfo {
	private static final long serialVersionUID = -7581844255323255225L;
	private int order;
	private String napai;
	private List<String> shoupais;
	private List<String> chupais;
	private List<String> defen;

	public List<String> getDefen() {
		return defen;
	}

	public void setDefen(List<String> defen) {
		this.defen = defen;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getNapai() {
		return napai;
	}

	public void setNapai(String napai) {
		this.napai = napai;
	}

	public List<String> getShoupais() {
		return shoupais;
	}

	public void setShoupais(List<String> shoupais) {
		this.shoupais = shoupais;
	}

	public List<String> getChupais() {
		return chupais;
	}

	public void setChupais(List<String> chupais) {
		this.chupais = chupais;
	}

}
