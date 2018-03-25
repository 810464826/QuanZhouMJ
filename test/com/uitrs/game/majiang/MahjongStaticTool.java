package com.uitrs.game.majiang;

public class MahjongStaticTool {
	private static MahjongTile[] removeSomeMahjongTiles(MahjongTile[] mahjongTiles, int[] indexs) {
		int lenNew = mahjongTiles.length - indexs.length;
		if (lenNew > 0) {
			MahjongTile[] mahjongTilesNew = new MahjongTile[lenNew];
			int index = 0;
			for (int i = 0; i < mahjongTiles.length; i++) {
				boolean isAppend = true;
				for (int j = 0; j < indexs.length; j++) {
					if (i == indexs[j]) {
						isAppend = false;
						break;
					}
				}
				if (isAppend) {
					mahjongTilesNew[index] = mahjongTiles[i];
					index++;
				}
			}
			return mahjongTilesNew;
		}
		return null;
	}

	// 从数组长度为arrayLen的整形数组中任意抽取两个元素，把所有可能的组合的索引列成一个二位数组返回出来
	private static int[][] siphonTwoIndexs(int arrayLen) {
		int len = (arrayLen * (arrayLen - 1)) / 2;
		if (len > 0) {
			int[][] indexs = new int[len][2];
			int index = 0;
			for (int i = 0; i < arrayLen; i++) {
				for (int j = (i + 1); j < arrayLen; j++) {
					indexs[index][0] = i;
					indexs[index][1] = j;
					index++;
				}
			}

			return indexs;
		} else {
			return null;
		}
	}

	// 从数组长度为arrayLen的整形数组中任意抽取两个元素，把所有可能的组合的索引列成一个二位数组返回出来
	private static int[][] siphonThreeIndexs(int arrayLen) {
		int len = (arrayLen * (arrayLen - 1) * (arrayLen - 2)) / 6;
		if (len > 0) {
			int[][] indexs = new int[len][3];
			int index = 0;
			for (int i = 0; i < arrayLen; i++) {
				for (int j = (i + 1); j < arrayLen; j++) {
					for (int k = (j + 1); k < arrayLen; k++) {
						indexs[index][0] = i;
						indexs[index][1] = j;
						indexs[index][2] = k;
						index++;
					}
				}
			}
			return indexs;
		} else {
			return null;
		}
	}

	private static MahjongTile[][] appendSomeMahjongTiles(MahjongTile[][] saveMahjongTileses,
			MahjongTile[] mahjongTiles) {
		if (saveMahjongTileses == null) {
			MahjongTile[][] mahjongTilesesReturn = new MahjongTile[1][];
			mahjongTilesesReturn[0] = mahjongTiles;
			return mahjongTilesesReturn;
		} else {
			MahjongTile[][] mahjongTilesesReturn = new MahjongTile[saveMahjongTileses.length + 1][];
			System.arraycopy(saveMahjongTileses, 0, mahjongTilesesReturn, 0, saveMahjongTileses.length);
			mahjongTilesesReturn[saveMahjongTileses.length] = mahjongTiles;
			return mahjongTilesesReturn;
		}
	}

	/**
	 * 
	 * @param mahjongTiles
	 * @param twoNum
	 * @param threeNum
	 * @return MahjongTile[][]
	 */
	public static MahjongTile[][] tryCombination(MahjongTile[] mahjongTiles, int twoNum, int threeNum) {
		return MahjongStaticTool.tryCombination(mahjongTiles, twoNum, threeNum, null);
	}

	private static MahjongTile[][] tryCombination(MahjongTile[] mahjongTiles, int twoNum, int threeNum,
			MahjongTile[][] saveMahjongTileses) {
		if (mahjongTiles == null) {
			if (twoNum == 0 && threeNum == 0) {
				return saveMahjongTileses;
			} else {
				return null;
			}
		}
		if (mahjongTiles.length == ((twoNum * 2) + (threeNum * 3))) {
			if (threeNum > 0) {
				int[][] indexs = siphonThreeIndexs(mahjongTiles.length);
				if (indexs == null) {
					return null;
				}

				for (int[] index : indexs) {
					if (mahjongTiles[index[0]].isCanThree(mahjongTiles[index[1]], mahjongTiles[index[2]])) {
						MahjongTile[][] saveMahjongTilesesCache = appendSomeMahjongTiles(saveMahjongTileses,
								new MahjongTile[] { mahjongTiles[index[0]], mahjongTiles[index[1]],
										mahjongTiles[index[2]] });
						MahjongTile[][] mahjongTilesesReturn = MahjongStaticTool.tryCombination(
								removeSomeMahjongTiles(mahjongTiles, new int[] { index[0], index[1], index[2] }),
								twoNum, threeNum - 1, saveMahjongTilesesCache);
						if (mahjongTilesesReturn != null) {
							return mahjongTilesesReturn;
						}
					}
				}
			} else if (twoNum > 0) {
				int[][] indexs = siphonTwoIndexs(mahjongTiles.length);
				if (indexs == null) {

					return null;
				}
				for (int[] index : indexs) {
					if (mahjongTiles[index[0]].isCanTwo(mahjongTiles[index[1]])) {
						MahjongTile[][] saveMahjongTilesesCache = appendSomeMahjongTiles(saveMahjongTileses,
								new MahjongTile[] { mahjongTiles[index[0]], mahjongTiles[index[1]] });
						MahjongTile[][] mahjongTilesesReturn = MahjongStaticTool.tryCombination(
								removeSomeMahjongTiles(mahjongTiles, new int[] { index[0], index[1] }), twoNum - 1,
								threeNum, saveMahjongTilesesCache);
						if (mahjongTilesesReturn != null) {
							return mahjongTilesesReturn;
						}
					}
				}
			} else {
				return saveMahjongTileses;
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		MahjongTile[] mahjongTiles = new MahjongTile[] { 
				new MahjongTile(1, 1), 
				new MahjongTile(1, 2),
				new MahjongTile(1, 3), 
				new MahjongTile(1, 4), 
				new MahjongTile(1, 5), 
				new MahjongTile(1, 6),
				new MahjongTile(2, 1), 
				new MahjongTile(2, 2), 
				new MahjongTile(2, 3), 
				new MahjongTile(2, 4),
				new MahjongTile(2, 4),
				
		};

		System.out.println("检查所有下列牌:");
		for (int i = 0; i < mahjongTiles.length; i++) {
			if (i != 0) {
				System.out.print(",");
			}
			System.out.print(mahjongTiles[i]);
		}
		System.out.println("");
		System.out.println("length:"+mahjongTiles.length);
		MahjongTile[][] mahjongTileses = tryCombination(mahjongTiles, 1, 3);
		if (mahjongTileses != null) {
			System.out.println("检查通过!");
			System.out.println("组合结果如下:");
			int twoIndex = 1;
			int threeIndex = 1;
			for (MahjongTile[] mahjongTilesRow : mahjongTileses) {
				if (mahjongTilesRow.length == 2) {
					System.out.print("第" + twoIndex + "对组合：");
					for (int j = 0; j < mahjongTilesRow.length; j++) {

						if (j != 0) {
							System.out.print(",");
						}
						System.out.print(mahjongTilesRow[j]);
					}
					System.out.println("");
					twoIndex++;
				} else if (mahjongTilesRow.length == 3) {
					System.out.print("第" + threeIndex + "趟组合：");
					for (int j = 0; j < mahjongTilesRow.length; j++) {

						if (j != 0) {
							System.out.print(",");
						}
						System.out.print(mahjongTilesRow[j]);
					}
					System.out.println("");
					threeIndex++;
				}
			}
		} else {
			System.out.println("检查未通过!");
		}
	}
}
