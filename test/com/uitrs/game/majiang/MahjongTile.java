package com.uitrs.game.majiang;

import java.util.Arrays;

/**
 * 单张麻将牌
 *
 * @author 奔跑 QQ:361817468
 */
public class MahjongTile {

	public static int MAHJONG_TILE_TYPE_TEN_THOUSAND = 1;
	public static int MAHJONG_TILE_TYPE_PIE = 2;
	public static int MAHJONG_TILE_TYPE_STRIP = 3;
	public static int MAHJONG_TILE_TYPE_WIND = 4;
	public static int MAHJONG_TILE_TYPE_MESS = 5;
	public static int MAHJONG_TILE_TYPE_FLOWER = 6;

	/**
	 * 标准麻将的各种牌的名称，该名称为一个三维数组，第一维为各套独立的名称 第二维为每套名称中的不同类别，例如万和桶九属于不同类型的牌
	 * 第三维维具体的名称
	 */
	public final static String[][][] STANDARD_MAHJONG_NAMES = {

			new String[][] { { "一万", "二万", "三万", "四万", "五万", "六万", "七万", "八万", "九万" },
					{ "一桶", "二桶", "三桶", "四桶", "五桶", "六桶", "七桶", "八桶", "九桶" },
					{ "一条", "二条", "三条", "四条", "五条", "六条", "七条", "八条", "九条" }, { "东风", "南风", "西风", "北风" },
					{ "红中", "发财", "白板" }, { "春", "夏", "秋", "冬", "梅", "兰", "竹", "菊" } },
			new String[][] { { "一万", "二万", "三万", "四万", "五万", "六万", "七万", "八万", "九万" },
					{ "一饼", "二饼", "三饼", "四饼", "五饼", "六饼", "七饼", "八饼", "九饼" },
					{ "一条", "二条", "三条", "四条", "五条", "六条", "七条", "八条", "九条" }, { "东风", "南风", "西风", "北风" },
					{ "红中", "发财", "白板" }, { "春", "夏", "秋", "冬", "梅", "兰", "竹", "菊" } } };

	private final int type;
	private final int typeId;
	private final int uniqueId;

	public MahjongTile(String name)
			throws MahjongTileInitWrongTypeAndTypeIdException, MahjongTileInitWrongNameException {
		for (String[][] standardMahjongName : STANDARD_MAHJONG_NAMES) {
			for (int j = 0; j < standardMahjongName.length; j++) {
				for (int k = 0; k < standardMahjongName[j].length; k++) {
					if (standardMahjongName[j][k].equals(name)) {
						this.type = j + 1;
						this.typeId = k + 1;
						this.uniqueId = computeUniqueId(type, typeId);
						return;
					}
				}
			}
		}
		throw new MahjongTileInitWrongNameException(name);
	}

	/**
	 * 
	 * @param type
	 *            牌型
	 * @param typeId
	 *            牌号
	 * @throws MahjongTileInitWrongTypeAndTypeIdException
	 */
	public MahjongTile(int type, int typeId) throws MahjongTileInitWrongTypeAndTypeIdException {
		this.uniqueId = computeUniqueId(type, typeId);
		this.type = type;
		this.typeId = typeId;
	}

	private void initCheck(int type, int typeId) throws MahjongTileInitWrongTypeAndTypeIdException {
		if (STANDARD_MAHJONG_NAMES[0].length < type || type < 1) {
			throw new MahjongTileInitWrongTypeAndTypeIdException(type, typeId, true);
		} else if (STANDARD_MAHJONG_NAMES[0][type - 1].length < typeId || typeId < 1) {
			throw new MahjongTileInitWrongTypeAndTypeIdException(type, typeId, false);
		}
	}

	private int computeUniqueId(int type, int typeId) throws MahjongTileInitWrongTypeAndTypeIdException {
		initCheck(type, typeId);
		if (type == MAHJONG_TILE_TYPE_TEN_THOUSAND) {
			return typeId;
		} else if (type == MAHJONG_TILE_TYPE_PIE) {
			return typeId + 9;
		} else if (type == MAHJONG_TILE_TYPE_STRIP) {
			return typeId + 18;
		} else if (type == MAHJONG_TILE_TYPE_WIND) {
			return typeId + 27;
		} else if (type == MAHJONG_TILE_TYPE_MESS) {
			return typeId + 31;
		} else {
			return typeId + 34;
		}
	}

	public int getType() {
		return type;
	}

	public int getTypeId() {
		return typeId;
	}

	public int getUniqueId() {
		return typeId;
	}

	public boolean isCanTwo(MahjongTile mahjongTile) {
		if (isCanAny() || mahjongTile.isCanAny()) {
			return true;
		} else {
			return uniqueId == mahjongTile.uniqueId;
		}
	}

	private boolean isIdLink(int id1, int id2, int id3) {
		int[] ids = { id1, id2, id3 };
		Arrays.sort(ids);
		if (ids[2] - ids[1] != 1) {
			return false;
		} else if (ids[1] - ids[0] != 1) {
			return false;
		}
		return true;
	}

	public boolean isCanThree(MahjongTile mahjongTileOne, MahjongTile mahjongTileTwo) {
		if (type == mahjongTileOne.type && type == mahjongTileTwo.type) {
			if (typeId == mahjongTileOne.typeId && typeId == mahjongTileTwo.typeId) {
				return true;
			} else if (isIdLink(typeId, mahjongTileOne.typeId, mahjongTileTwo.typeId) && type != MAHJONG_TILE_TYPE_WIND
					&& type != MAHJONG_TILE_TYPE_MESS && type != MAHJONG_TILE_TYPE_FLOWER) {
				return true;
			}

		}
		if (isCanAny()) {
			if (mahjongTileOne.isCanAny() || mahjongTileTwo.isCanAny()) {
				return true;
			} else if (Math.abs(mahjongTileOne.typeId - mahjongTileTwo.typeId) <= 2
					&& mahjongTileOne.type == mahjongTileTwo.type) {
				return true;
			}
		} else if (mahjongTileOne.isCanAny()) {
			if (isCanAny() || mahjongTileTwo.isCanAny()) {
				return true;
			} else if (Math.abs(typeId - mahjongTileTwo.typeId) <= 2 && type == mahjongTileTwo.type) {
				return true;
			}
		} else if (mahjongTileTwo.isCanAny()) {
			if (mahjongTileOne.isCanAny() || isCanAny()) {
				return true;
			} else if ((Math.abs(typeId - mahjongTileOne.typeId) <= 2) && type == mahjongTileOne.type) {
				return true;
			}
		}

		return false;
	}

	public boolean isCanAny() {
		// if (type == 1 && typeId == 9) {
		// return true;
		// }
		return false;
	}

	@Override
	public String toString() {
		String name = STANDARD_MAHJONG_NAMES[0][type - 1][typeId - 1];
		if (isCanAny()) {
			name = name + "(百搭)";
		}
		return name;
	}

}
