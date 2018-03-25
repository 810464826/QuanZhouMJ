package com.uitrs.game.majiang.common;

/**
 * 麻将牌枚举
 * 
 * @author lucio
 *
 */
public enum MJPai {

	TIAO1("1条", MJConst.TIAO + "1", 0), TIAO2("2条", MJConst.TIAO + "2", 1), TIAO3("3条", MJConst.TIAO + "3", 2), TIAO4(
			"4条", MJConst.TIAO + "4",
			3), TIAO5("5条", MJConst.TIAO + "5", 4), TIAO6("6条", MJConst.TIAO + "6", 5), TIAO7("7条", MJConst.TIAO + "7",
					6), TIAO8("8条", MJConst.TIAO + "8", 7), TIAO9("9条", MJConst.TIAO + "9", 8),

	TONG1("1筒", MJConst.TONG + "1", 9), TONG2("2筒", MJConst.TONG + "2", 10), TONG3("3筒", MJConst.TONG + "3", 11), TONG4(
			"4筒", MJConst.TONG + "4",
			12), TONG5("5筒", MJConst.TONG + "5", 13), TONG6("6筒", MJConst.TONG + "6", 14), TONG7("7筒",
					MJConst.TONG + "7", 15), TONG8("8筒", MJConst.TONG + "8", 16), TONG9("9筒", MJConst.TONG + "9", 17),

	WAN1("1万", MJConst.WAN + "1", 18), WAN2("2万", MJConst.WAN + "2", 19), WAN3("3万", MJConst.WAN + "3", 20), WAN4("4万",
			MJConst.WAN + "4", 21), WAN5("5万", MJConst.WAN + "5", 22), WAN6("6万", MJConst.WAN + "6", 23), WAN7("7万",
					MJConst.WAN + "7", 24), WAN8("8万", MJConst.WAN + "8", 25), WAN9("9万", MJConst.WAN + "9", 26),

	ZHONG("中", MJConst.JIAN + "1", 27), FA("發", MJConst.JIAN + "2", 28), BAI("白", MJConst.JIAN + "3", 29),

	DONG("东", MJConst.FENG + "1", 30), NAN("南", MJConst.FENG + "2", 31), XI("西", MJConst.FENG + "3", 32), BEI("北",
			MJConst.FENG + "4", 33),

	CHUN("春", MJConst.JI_JIE + "1", 34), XIA("夏", MJConst.JI_JIE + "2", 35), QIU("秋", MJConst.JI_JIE + "3",
			36), DON("冬", MJConst.JI_JIE + "4", 37),

	MEI("梅", MJConst.HUA + "1", 38), LAN("兰", MJConst.HUA + "2", 39), ZHU("竹", MJConst.HUA + "3", 40), JU("菊",
			MJConst.HUA + "4", 41),

	LAIZI("金", "y", 60); // 癞子

	private String name; // 牌名
	private String code; // 牌对应的字符
	private int index; // 牌对应的下标

	private MJPai(String name, String code, int index) {
		this.name = name;
		this.code = code;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
