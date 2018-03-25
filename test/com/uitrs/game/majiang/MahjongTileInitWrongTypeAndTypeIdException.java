package com.uitrs.game.majiang;

public class MahjongTileInitWrongTypeAndTypeIdException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5471476488490244912L;
	private final int type;
	private final int typeId;
	private final boolean isTypeWrong;

	public MahjongTileInitWrongTypeAndTypeIdException(int type, int typeId, boolean isTypeWrong) {
		this.type = type;
		this.typeId = typeId;
		this.isTypeWrong = isTypeWrong;
	}

	public int type() {
		return type;
	}

	public int typeId() {
		return typeId;
	}

	public boolean isTypeWrong() {
		return isTypeWrong;
	}
}
