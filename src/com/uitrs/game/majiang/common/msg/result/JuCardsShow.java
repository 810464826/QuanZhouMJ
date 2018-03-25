package com.uitrs.game.majiang.common.msg.result;

import com.uitrs.game.majiang.common.msg.AbstractMsg;

/**
 * 其中：cardsShow: [ //说明：msg字段用来记录额外的数据， 如吃类型中，用来记录吃的那一组牌 {'cardName': 'e',
 * /碰/type: 'pong', msg: {from: otherOpenId} }, {'cardName': 'w2', /明杠/type:
 * 'mingGang', msg: {from: otherOpenId}}, //自己有三张，别人打一张 {'cardName': 't1',
 * /暗杠/type: 'anGang', msg: {from: meOpenId}}, //自己用四张扛 {'cardName': 't4',
 * /补杠/type: 'buGang', msg: {from: meOpenId}}, //自己原来碰了，又摸到一张 // 吃
 * cardName表示吃的那张牌，注意，只能吃自己上家出的牌 {'cardName': 't4', type: 'chi', msg: {from:
 * '098893', cards: ['t3', 't4', 't5'] } }, // 旋风扛(中、发、白) {'cardName': '', type:
 * 'specialGang', msg: {from: '098893', childType: 'xuanFengGang', cards: ['z',
 * 'f', 'b'] } }, // 幺扛(一条、一万、一筒) {'cardName': '', type: 'specialGang', msg:
 * {from: '098893', childType: 'yaoGang', cards: ['o1', 't1', 'w1'] } }, //
 * 九扛(九条、九万、九筒) {'cardName': '', type: 'specialGang', msg: {from: '098893',
 * childType: 'jiuGang', cards: ['o9', 't9', 'w9'] } }, // 白八九扛(白、八条、九万)
 * {'cardName': '', type: 'specialGang', msg: {from: '098893', childType:
 * 'baiJiuGang', cards: ['b', 't8', 'w9'] } }, ]
 */
public class JuCardsShow extends AbstractMsg {
	private static final long serialVersionUID = -3451319889999814888L;
	/** 牌 */
	private String cardName;
	/** 类型,吃,碰,杠 */
	private String type;
	/**
	 * 具体信息 msg:{from: '098893', childType: 'jiuGang', cards: ['o9', 't9', 'w9']
	 * }
	 */
	private CardsShowMsg msg;

	public JuCardsShow() {
		super();
	}

	public JuCardsShow(String cardName, String type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CardsShowMsg getMsg() {
		return msg;
	}

	public void setMsg(CardsShowMsg msg) {
		this.msg = msg;
	}

}
