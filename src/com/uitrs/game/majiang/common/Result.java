package com.uitrs.game.majiang.common;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017-05-06.
 */
public class Result {
	private List<String> cards;

	/** 可以胡的牌 */
	private Set<String> hps;

	public List<String> getCards() {
		return cards;
	}

	public void setCards(List<String> cards) {
		this.cards = cards;
	}

	public Set<String> getHps() {
		return hps;
	}

	public void setHps(Set<String> hps) {
		this.hps = hps;
	}

	public Result(List<String> cards, Set<String> hps) {
		this.cards = cards;
		this.hps = hps;
	}

	@Override
	public String toString() {

		String msg = "";
		for (String p : cards) {
			msg += p + " , ";
		}

		msg += "------------>可胡牌为 ：";
		for (String rp : hps) {
			msg += rp + " , ";
		}
		return msg;
	}
}
