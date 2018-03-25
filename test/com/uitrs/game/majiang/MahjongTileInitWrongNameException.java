package com.uitrs.game.majiang;

public class MahjongTileInitWrongNameException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 672377841748233132L;
	private final String wrongName;

	public MahjongTileInitWrongNameException(String wrongName) {
		this.wrongName = wrongName;
	}

	public String getWrongName() {
		return wrongName;
	}

	public String[][][] standardMahjongNames() {
		return MahjongTile.STANDARD_MAHJONG_NAMES;
	}
}
