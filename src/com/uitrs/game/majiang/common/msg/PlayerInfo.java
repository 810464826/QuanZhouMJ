package com.uitrs.game.majiang.common.msg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.MJCheckKit;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.msg.sub.UserInfo;
import com.uitrs.game.majiang.common.room.Room;
import com.uitrs.game.majiang.common.rule.MJRule;

/**
 * 退出游戏,重新登录构造信息
 * 
 * @author lucio
 *
 */
public class PlayerInfo extends AbstractMsg {
	private static final long serialVersionUID = -5364197581752222358L;
	/** 房间号 */
	private long roomId;
	/** 拿的是什么牌 */
	private String napai;
	/** 所有出的牌 */
	private List<String> outAllCards;
	/** 剩余出的牌 */
	private List<String> outCards;
	/** 手牌 */
	private List<String> handCards;
	/** 花牌 */
	private List<String> huaCards;
	/** 手牌个数统计结果 */
	private int[] counts;
	/** 听牌结果 */
	private Set<String> tingCards;
	/** 操作类型,吃碰杠等 */
	private List<OptCheckMsg> optMsgs;
	/** 上局得分 */
	private int currentScore;
	/** 本房间当前得分 */
	private int roomScore;
	/** 用户信息 */
	private UserInfo userInfo;
	/** 加入房间的顺序,1,2,3,4 */
	private int order;
	/** 其他胡牌信息 */
	private Map<String, Integer> huInfo;
	/** 没有金的牌数统计 */
	private int[] noJinCount;
	/** 最近活跃时间 */
	private long aliveTime;

	public PlayerInfo() {
		super();
	}

	public PlayerInfo(UserInfo userInfo) {
		super();
		this.userInfo = userInfo;
	}

	public PlayerInfo(long roomId, UserInfo userInfo, int order) {
		super();
		this.userInfo = userInfo;
		init(roomId, order);
	}

	/**
	 * 清空原有数据
	 */
	public void clear() {
		init(0, 0);
		this.currentScore = 0;
		this.roomScore = 0;
		this.napai = "";
		this.userInfo.setReady(false);
		this.handCards = null;
		this.counts = null;
		this.noJinCount = null;
		this.order = 0;
	}

	/**
	 * 如果开始游戏了,则必须初始化基本房间信息
	 * 
	 * @param roomId
	 *            房间号
	 * @param order
	 *            座位
	 */
	public void init(long roomId, int order) {
		this.roomId = roomId;
		this.order = order;
		this.napai = "";
		this.currentScore = 0;
		this.outCards = Collections.synchronizedList(new ArrayList<String>());
		this.optMsgs = Collections.synchronizedList(new ArrayList<OptCheckMsg>());
		this.outAllCards = Collections.synchronizedList(new ArrayList<String>());
		this.huaCards = Collections.synchronizedList(new ArrayList<String>());
		this.handCards = Collections.synchronizedList(new ArrayList<String>());
		this.tingCards = new HashSet<String>();
		this.huInfo = new ConcurrentHashMap<String, Integer>();
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getNapai() {
		return napai;
	}

	public void setNapai(String napai) {
		this.napai = napai;
	}

	public List<String> getOutCards() {
		return outCards;
	}

	public void setOutCards(List<String> outCards) {
		this.outCards = outCards;
	}

	public List<String> getHandCards() {
		return handCards;
	}

	public void setHandCards(List<String> handCards) {
		this.handCards = handCards;
		// this.counts = MJCheckKit.countCardNum(handCards,
		// MJCache.getCache().getRoom(this.roomId).getMjRule().enablePais());
	}

	public List<OptCheckMsg> getOptMsgs() {
		return optMsgs;
	}

	public void setOptMsg(List<OptCheckMsg> optMsgs) {
		this.optMsgs = optMsgs;
	}

	public void addOptMsg(OptCheckMsg optMsg) {
		this.optMsgs.add(optMsg);
	}

	public void addOptMsgs(List<OptCheckMsg> optMsgs) {
		if (null != optMsgs) {
			for (OptCheckMsg optMsg : optMsgs) {
				this.optMsgs.add(optMsg);
			}
		}
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int getRoomScore() {
		return roomScore;
	}

	public void setRoomScore(int roomScore) {
		this.roomScore = roomScore;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int[] getCounts() {
		return counts;
	}

	public void setCounts(int[] counts) {
		this.counts = counts;
	}

	public Set<String> getTingCards() {
		return tingCards;
	}

	public void setTingCards(Set<String> tingCards) {
		this.tingCards = tingCards;
	}

	public void setOptMsgs(List<OptCheckMsg> optMsgs) {
		this.optMsgs = optMsgs;
	}

	public Map<String, Integer> getHuInfo() {
		return huInfo;
	}

	public void setHuInfo(Map<String, Integer> huInfo) {
		this.huInfo = huInfo;
	}

	public void addHuInfo(String key, int value) {
		this.huInfo.put(key, value);
	}

	public int[] getNoJinCount() {
		return noJinCount;
	}

	public void setNoJinCount(int[] noJinCount) {
		this.noJinCount = noJinCount;
	}

	public long getAliveTime() {
		return aliveTime;
	}

	public void setAliveTime(long aliveTime) {
		this.aliveTime = aliveTime;
	}

	/**
	 * 手牌转换为数组
	 * 
	 * @return String[]
	 */
	public String[] handCards2Array() {
		if (null == this.getHandCards() || this.getHandCards().size() == 0) {
			return null;
		}
		String[] handCards = new String[this.getHandCards().size()];
		handCards = this.getHandCards().toArray(handCards);
		return handCards;
	}

	/**
	 * 拿牌
	 * 
	 * @param card
	 */
	public synchronized void fetchCard(String card) {
		if (!StringUtils.isEmpty(card)) {
			this.napai = card;
			this.handCards.add(card);
			// int index =
			// Arrays.binarySearch(MJCache.getCache().getRoom(this.roomId).getMjRule().enablePais(),
			// card);
			// this.counts[index]++;
		}
	}

	public List<String> getHuaCards() {
		return huaCards;
	}

	public void addHuaCard(String c) {
		this.huaCards.add(c);
	}

	/**
	 * 补花操作
	 * 
	 * @param hua
	 * @param c
	 */
	public void buHua(String hua, String c) {
		if (!StringUtils.isEmpty(c)) {
			this.huaCards.add(hua);
			this.handCards.remove(hua);
			this.fetchCard(c);
		}
	}

	public void setHuaCards(List<String> huaCards) {
		this.huaCards = huaCards;
	}

	/**
	 * 出牌,手牌删除,出牌增加
	 * 
	 * @param card
	 */
	public synchronized void outCard(String card) {
		this.handCards.remove(card);
		this.outCards.add(card);
		this.outAllCards.add(card);
	}

	/**
	 * 出操作牌,吃碰杠等
	 * 
	 * @param optCard,引发操作的牌
	 * @param from
	 *            谁出的牌
	 * @param cards
	 *            操作的牌,如碰3万,则操作牌为2张3万
	 */
	public void optCards(String optCard, long from, String[] cards) {
		if (!StringUtils.isEmpty(optCard) && 0 != from) {
			PlayerInfo fromPlayer = MJCache.getCache().getPlayerInfo(from);
			// 出牌者手中出的牌中,去掉该牌
			fromPlayer.getOutCards().remove(optCard);
		}

		if (null != cards) {
			for (int i = 0; i < cards.length; i++) {
				removeHandCard(cards[i]);
			}
		}
	}

	/**
	 * 检查别人出的牌自己是否能吃,碰,杠,胡
	 * 
	 * @param optMsg
	 * @return List<OptCheckMsg>
	 */
	public List<OptCheckMsg> checkOutOpts(long from, String outCard) {
		Room room = MJCache.getCache().getRoom(this.roomId);
		MJRule mjRule = room.getMjRule();
		return mjRule.checkOutOpts(room, this, from, outCard);
	}

	/**
	 * 构建可以听的牌
	 * 
	 * @param room
	 * @return
	 */
	public Set<String> buildTingCards(Room room) {
		this.tingCards = room.getMjRule().checkTingCards(room, this);
		return tingCards;
	}

	/**
	 * 构建手牌统计数组
	 * 
	 * @param room
	 * @return int[] counts
	 */
	public int[] buildCount(Room room) {
		this.counts = MJCheckKit.countCardNum(this.getHandCards(), room.getMjRule().enablePais());
		this.buildNoJinCount(room);
		return counts;
	}

	/**
	 * 构造没有金牌的数组
	 * 
	 * @param room
	 * @param enablePais
	 * @return int[] noJinCount
	 */
	public int[] buildNoJinCount(Room room) {
		int jinIndex = Arrays.binarySearch(room.getMjRule().enablePais(), room.getMjRule().getGoldCard());
		// if (null == noJinCount || this.counts[jinIndex] > 0) {
		this.noJinCount = Arrays.copyOf(this.counts, this.counts.length);
		// 金的数量置为0
		this.noJinCount[jinIndex] = 0;
		// }
		return noJinCount;
	}

	/**
	 * 删除一张手牌
	 * 
	 * @param card
	 */
	public void removeHandCard(String card) {
		this.handCards.remove(card);
	}

	public List<String> getOutAllCards() {
		return outAllCards;
	}

	public void setOutAllCards(List<String> outAllCards) {
		this.outAllCards = outAllCards;
	}

	/**
	 * 构建其他玩家可以看到的信息,用户客户端构建场景
	 * 
	 * @return PlayerInfo
	 */
	public PlayerInfo buildOther() {
		PlayerInfo pclone = new PlayerInfo(this.userInfo);
		pclone.setOptMsg(this.optMsgs);
		pclone.setCurrentScore(this.currentScore);
		List<String> handCards2 = new ArrayList<String>();
		if (null != handCards) {
			for (int i = 0; i < this.handCards.size(); i++) {
				if (!StringUtils.isEmpty(this.handCards.get(i))) {
					handCards2.add("");
				}
			}
		}

		pclone.setHandCards(handCards2);
		pclone.setHuaCards(this.huaCards);
		pclone.setNapai(this.napai);
		pclone.setOrder(this.order);
		pclone.setOutAllCards(this.outAllCards);
		pclone.setOutCards(this.outCards);
		pclone.setRoomId(this.roomId);
		pclone.setRoomScore(this.roomScore);
		return pclone;
	}

	/**
	 * 构建开始玩的信息,用户客户端构建场景
	 * 
	 * @return PlayerInfo
	 */
	public PlayerInfo copyStart() {
		PlayerInfo pclone = new PlayerInfo(this.userInfo);
		pclone.init(this.roomId, this.order);
		// pclone.setOptMsg(this.optMsgs);
		// pclone.setCurrentScore(0);
		List<String> handCards2 = new ArrayList<String>();
		if (null != handCards) {
			for (int i = 0; i < this.handCards.size(); i++) {
				if (!StringUtils.isEmpty(this.handCards.get(i))) {
					handCards2.add(this.handCards.get(i));
				}
			}
		}

		pclone.setHandCards(handCards2);
		pclone.setHuaCards(this.huaCards);
		pclone.setNapai(this.napai);
		// pclone.setOrder(this.order);
		// pclone.setOutAllCards(this.outAllCards);
		// pclone.setOutCards(this.outCards);
		// pclone.setRoomId(this.roomId);
		// pclone.setRoomScore(this.roomScore);
		return pclone;
	}

	public void clearAndInit(long roomId, int order) {
		init(roomId, order);
		this.roomScore = 0;
		this.userInfo.setReady(false);
		this.userInfo.setRoomId(roomId + "");
	}
}
