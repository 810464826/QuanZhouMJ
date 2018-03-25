package com.uitrs.game.majiang.common.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import javax.websocket.Session;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.MJConst;
import com.uitrs.game.majiang.common.command.BroadcastCommand;
import com.uitrs.game.majiang.common.command.Command;
import com.uitrs.game.majiang.common.command.SendMsgCommand;
import com.uitrs.game.majiang.common.command.SendMsgComparator;
import com.uitrs.game.majiang.common.kit.DismissTask;
import com.uitrs.game.majiang.common.kit.HeartBeatTask;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.common.kit.MJUtil;
import com.uitrs.game.majiang.common.kit.WSSessionKit;
import com.uitrs.game.majiang.common.msg.IMsg;
import com.uitrs.game.majiang.common.msg.PlayerInfo;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.SendMsg;
import com.uitrs.game.majiang.common.msg.result.HupaiMsg;
import com.uitrs.game.majiang.common.msg.sub.JoinMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;
import com.uitrs.game.majiang.common.msg.sub.ToJoinMsg;
import com.uitrs.game.majiang.common.rule.MJRule;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.RoomInfo;

/**
 * 房间
 * 
 * @author lucio
 *
 */
public abstract class Room {

	private static Log log = Log.getLog(Room.class);
	/** 房间ID */
	protected long roomId;
	/** 总局数或总分数 */
	protected int totalJuNum;
	/** 已经使用的局数 */
	protected int useJuNum;
	/** 总圈数 */
	protected int totalQuanNum;
	/** 已经使用的圈数 */
	protected int useQuanNum;
	/** 房间容纳总数 */
	protected int totalNumber;
	/** 已经准备人数 */
	protected Map<Long, Boolean> readyNumber;
	/** 已经对出牌执行操作的人数 */
	protected Map<Long, Boolean> optNumber;
	/** 缓存回放消息 */
	protected List<SendMsg> replayMsg;
	/** 现有人数 */
	protected int nowNumber;
	/** 创建时间 */
	protected Date createTime;
	/** 房间用户,位置号,userId */
	protected Map<Integer, Long> roomUsers;
	/** 麻将规则 */
	protected MJRule mjRule;
	/** 初始牌的序列 */
	protected String[] cards;
	/** 封顶分数,默认800分 */
	protected int maxScore = 800;
	/** 当前庄家ID */
	protected long zhuangId;
	/** 上一把庄家ID */
	protected long preZhuangId;
	/** 创建者ID */
	protected long createId;
	/** 发到第几张牌 */
	protected int cardNum;
	/** 得分结果 */
	protected List<HupaiMsg> roomResults;
	/** 同意退出人数 */
	protected Map<Long, Boolean> dissmisNumber;
	/** 打牌命令,如碰,吃,杠,胡队列 */
	protected Queue<SendMsgCommand> commands;
	/** 是否开始了牌局,已经发牌 */
	protected boolean isStarted;
	/** 玩法选项 ,1,2,3,4,5... */
	protected int[] opt;
	/** 出牌者位置 */
	protected int baseOrder;
	/** 活动用户是谁,位置号 */
	protected int activer;
	/**
	 * 1-创建或加入房间(刚进入房间,
	 * 还没发牌),2-开始打牌(已经发牌了--某个人胡牌之前),3-单局结束(某个人胡了后--下一局发牌之前),4-申请解散的过程中
	 */
	protected int step;
	/** 是否有用户掉线,能否发消息 */
	protected boolean isSendEnable;
	/** 当前状态,出牌2,拿牌1,闲置0 */
	protected int status;
	/** 底分 */
	protected int baseFen;
	/** 定时器 */
	protected Timer timer;
	/** 解散定时检测任务 */
	protected DismissTask disMissTask;
	/** 心跳定时检测任务 */
	protected HeartBeatTask heartTask;
	/** 消息编号自增函数 */
	protected AtomicLong msgSeq;
	/** 房间中的其他需要设置的参数,子类定制 */
	protected Map<String, Integer> args;
	/** 发起解散者 */
	protected long toDismisser;

	/**
	 * 初始化
	 * 
	 * @param name
	 */
	protected void init(String name) {
		this.totalNumber = MJConst.TOTAL;
		this.createTime = new Date();
		this.mjRule = MJRule.getRule(name);
		this.cards = mjRule.createPais();
	}

	/**
	 * 创建房间，初始化基本信息
	 * 
	 * @param s
	 * @param totalJuNum
	 * @param openId
	 * @return boolean
	 */
	public boolean createRoom(int totalQuanNum, long userId) {
		// 初始化变量
		initVariable();
		// 选项1为2圈,选项2为4圈
		this.totalQuanNum = totalQuanNum;
		// this.totalJuNum = totalQuanNum * 4;

		// this.heartTask = new HeartBeatTask(this);
		// timer.schedule(heartTask, 5000);
		PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(userId);
		// 设置房间ID和座位,从1开始
		playerInfo.clearAndInit(this.roomId, 1);
		// 默认创建者为庄家
		this.zhuangId = playerInfo.getUserInfo().getUserId();
		this.createId = this.zhuangId;

		// 添加用户和对应位置信息
		roomUsers.put(1, userId);
		readyNumber.put(userId, false);
		// 其他位置初始化为null
		for (int i = 0; i < this.totalNumber - 1; i++) {
			roomUsers.put(i + 2, 0l);
		}
		// 创建房间,子类特殊实现
		creatEvent(playerInfo);
		return true;
	}

	public void initVariable() {
		this.useJuNum = 1;
		this.useQuanNum = 1;
		this.baseOrder = 1;
		this.activer = 1;
		this.step = MJConst.STEP_CREATE;
		this.roomUsers = new ConcurrentHashMap<Integer, Long>(this.totalNumber);
		this.roomResults = new CopyOnWriteArrayList<HupaiMsg>();
		this.commands = new PriorityQueue<SendMsgCommand>(5, SendMsgComparator.getInstance());
		this.dissmisNumber = new ConcurrentHashMap<Long, Boolean>();
		this.optNumber = new ConcurrentHashMap<Long, Boolean>();
		this.replayMsg = Collections.synchronizedList(new ArrayList<SendMsg>());
		this.readyNumber = new ConcurrentHashMap<Long, Boolean>();
		this.timer = new Timer("Room" + this.roomId);
		this.msgSeq = new AtomicLong(10000);
		this.args = new ConcurrentHashMap<String, Integer>();
	}

	/**
	 * 进入房间,判断是否满了
	 * 
	 * @param s
	 * @param openId
	 * @return boolean
	 */
	public synchronized boolean enterRoom(Session s, long userId) {
		if (log.isInfoEnabled()) {
			log.info("Room[" + roomId + "] has a user join..., nowNumber is " + getNowNumber());
		}
		readyNumber.put(userId, false);
		isStarted = false;
		if (getNowNumber() < totalNumber) {
			WSSessionKit.putRoomId(s, this.roomId);
			PlayerInfo playerInfo = MJCache.getCache().getPlayerInfo(userId);
			playerInfo.setRoomId(this.roomId);
			// 设置房间ID和座位,从1开始,如果已经存在则不变，否则找个空位
			int myOrder = findOrder(userId);
			playerInfo.clearAndInit(roomId, myOrder == 0 ? calcOrder() : myOrder);
			roomUsers.put(playerInfo.getOrder(), userId);
			// 如果没有庄家则设置第一个加入的为庄家
			if (this.zhuangId == 0) {
				this.zhuangId = playerInfo.getUserInfo().getUserId();
			}
			// 子类实现特殊逻辑
			joinEvent(playerInfo);

			if (getNowNumber() == totalNumber) {
				// try {
				// Thread.sleep(500);// 暂停一下
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				fullEvent();
			}
			return true;
		}
		return false;
	}

	/**
	 * 申请解散房间
	 * 
	 * @param s
	 * @param action
	 * @param openId
	 * @return
	 */
	public synchronized int toDismissRoom(long userId, boolean isAgree) {
		if (dissmisNumber.size() == 0) {
			this.toDismisser = userId;
		}
		// 如果有人不同意则说明解散失败
		if (!isAgree) {
			dissmisNumber.clear();
			if (null != this.disMissTask) {
				this.disMissTask.cancel();
				this.disMissTask = null;
			}
			return 2;// 解散失败
		}
		// 将每个人的意见添加到map中
		dissmisNumber.put(userId, isAgree);

		// 启动定时器, 默认5分钟后检查,如果有用户没有同意的用户自动同意
		if (null == this.disMissTask) {
			this.disMissTask = new DismissTask(this);
			timer.schedule(this.disMissTask, PropKit.getLong("dismissTime"));
		}

		// 检查解散是否成功
		return dismissCheck();
	}

	/**
	 * 解散房间后清空数据
	 * 
	 * @return int 是否成功
	 */
	public int dismissCheck() {
		// 如果还没有开始,则当前房间人数都同意就可以解散
		if (isStarted) {
			if (dissmisNumber.size() == totalNumber) {
				dissmisNumber.clear();
				disMissTask.cancel();
				timer.cancel();
				timer = null;
				return 1;// 解散成功
			}
		} else if (dissmisNumber.size() == getNowNumber()) {
			dissmisNumber.clear();
			disMissTask.cancel();
			disMissTask = null;
			timer.cancel();
			timer = null;
			return 1;// 解散成功
		}
		return 0;// 申请解散
	}

	public Map<Long, Boolean> getDissmisNumber() {
		return dissmisNumber;
	}

	/**
	 * 解散房间
	 * 
	 * @param s
	 * @param action
	 * @param openId
	 * @return
	 */
	public synchronized boolean dismissRoom(Session s) {
		this.leaveRoom(s);
		// 如果房间没有人了,则直接解散,释放缓存
		if (this.getNowNumber() <= 0) {
			MJCache.getCache().removeRoom(this);
			roomUsers.clear();
			if (log.isInfoEnabled()) {
				log.info("Room dismiss,roomid: " + this.getRoomId());
			}
			return true;
		}
		return false;
	}

	/**
	 * 庄家解散房间,则把所有用户踢出房间
	 * 
	 * @param s
	 * @param action
	 * @param openId
	 * @return
	 */
	public synchronized boolean dismissRoom() {
		this.setStep(MJConst.STEP_DISMISS);

		// 如果超过一局则统计结果,否则直接返回解散房间信息
		if (this.getUseJuNum() > 1) {
			// 将房间得分汇总结果返回,展示结果
			JoinMsg socreMsg = new JoinMsg(this, 0);
			Map<Integer, Long> roomUsers = this.getRoomUsers();
			Iterator<Entry<Integer, Long>> it = roomUsers.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, Long> entry = (Map.Entry<Integer, Long>) it.next();
				PlayerInfo p = MJCache.getCache().getPlayerInfo(entry.getValue());
				if (null != p) {
					p.getUserInfo().setReady(false);
					socreMsg.addPlayers(p);
				}
			}
			SendMsg result = new SendMsg(0, true, IMsg.ROOM_SCORE_TYPE, this.getMsgSeq().incrementAndGet());
			result.setM(socreMsg);
			this.broadcast(result);
		} else {
			// 通知所有人房间解散了
			SendMsg dissMsg = new SendMsg(0, true, IMsg.DISMISS_TYPE, this.getMsgSeq().incrementAndGet());
			dissMsg.setM(true);
			this.broadcast(dissMsg);
		}

		// 把所有用户踢出房间
		return dismissClear();
	}

	public synchronized boolean dismissClear() {
		// 把所有用户踢出房间
		Iterator<Entry<Integer, Long>> it = this.roomUsers.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> type = it.next();
			Session s = MJCache.getCache().getSession(type.getValue());
			this.leaveRoom(s);
		}
		// 如果房间没有人了,则直接解散,释放缓存
		if (this.getNowNumber() <= 0) {
			RoomInfo roomInfo = RoomInfo.dao.findByRoomId(this.roomId);
			if (null != roomInfo) {
				roomInfo.setUseRounds(this.getUseJuNum());
				roomInfo.setTotalRounds(this.getTotalJuNum() - 1);
				roomInfo.setEndTime(Tools.date2StringSecond(new Date()));
				roomInfo.update();
			}
			MJCache.getCache().removeRoom(this);
			// sessions.clear();
			this.roomUsers.clear();
			this.readyNumber.clear();
			if (null != this.timer) {
				this.timer.cancel();
				this.timer = null;
			}
			if (log.isInfoEnabled()) {
				log.info("Room dismiss,roomid: " + this.getRoomId());
			}
			return true;
		}
		return false;
	}

	/**
	 * 玩家准备,判断是否都准备好了
	 * 
	 * @param s
	 * @return boolean
	 */
	public synchronized boolean readyRoom(Session s) {
		// 设置用户准备好了
		long userId = WSSessionKit.getUserId(s);
		MJCache.getCache().getUserInfo(userId).setReady(true);
		this.readyNumber.put(userId, true);
		if (this.calcReadyNumber() == totalNumber) {
			this.step = MJConst.STEP_START;
			// 全部准备好了,则标识已经开始
			this.isStarted = true;
			this.cards = this.mjRule.createPais();
			this.cardNum = 0;
			// try {
			// Thread.sleep(500);// 暂停一下
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			readyEvent();
			// try {
			// Thread.sleep(500);// 暂停一下
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			return true;
		}
		return false;
	}

	/**
	 * 判断是否所有消息收集完了,优先级最高的消息
	 * 
	 * @param s
	 * @return boolean
	 */
	public synchronized boolean operateCheck(Session s, ReceiveMsg action) {
		SendMsg send = new SendMsg(action.getP(), true, IMsg.OPT_PASS, this.getMsgSeq().incrementAndGet());
		long userId = WSSessionKit.getUserId(s);
		this.optNumber.put(userId, true);
		// 玩家位子
		int order = MJCache.getCache().getPlayerInfo(action.getP()).getOrder();
		Object m = action.getM();
		// 如果不为空则说明有操作
		if (null != m && m.toString().length() > 0) {
			if (log.isInfoEnabled()) {
				log.info("optCheckMsg: " + m);
			}
			String jsonString = JsonUtils.toJson(m);
			OptCheckMsg optMsg = JsonUtils.parse(jsonString, OptCheckMsg.class);
			optMsg.setP(action.getP());
			send.setT(optMsg.getT());
			send.setM(optMsg);
			SendMsgCommand command = new BroadcastCommand(action.getP(), this, MJUtil.convertLevel(optMsg.getT()),
					order);
			command.setMsg(send);
			this.commands.add(command);
			if (log.isInfoEnabled()) {
				log.info("optCheckMsg add command level: " + command.level());
			}
		} else {// 没有操作则执行pass操作
			SendMsgCommand command = new BroadcastCommand(action.getP(), this, Command.NINE_LEVEL, order);
			command.setMsg(send);
			this.commands.add(command);
			if (log.isInfoEnabled()) {
				log.info("optCheckMsg add command level: " + command.level());
			}
		}
		// 如果收到了所有检查消息则返回true
		if (this.isOptOver()) {
			this.optNumber.clear();
			return true;
		}
		return false;
	}

	/**
	 * 计算每局的得分
	 * 
	 * @param s
	 * @param juUserResult
	 * @return boolean
	 */
	public synchronized boolean JuScore(ReceiveMsg action, PlayerInfo juUserResult) {
		juUserResult.getUserInfo().setUserId(action.getP());
		MJCache.getCache().getUserInfo(action.getP()).setReady(false);
		// 每局所有玩家得分消息
		HupaiMsg hupaiMsg = null;
		// 判断当前是否有玩家存入了数据,有则使用,没有则创建
		if (this.getRoomResults().size() < this.getUseJuNum()) {
			hupaiMsg = new HupaiMsg();
			this.addRoomResult(hupaiMsg);
		}
		hupaiMsg = this.getRoomResults().get(this.getUseJuNum() - 1);

		// 将每局每个玩家的数据存入
		hupaiMsg.addJuResult(juUserResult);
		// 如果4个人都存了数据,则将本局结果存入房间结果中,作为第n局的数据, 房间打完后用于展示
		if (log.isInfoEnabled()) {
			log.info("getJuResults size: " + hupaiMsg.getJuResults().size());
		}
		if (hupaiMsg.getJuResults().size() == this.getTotalNumber()) {
			// 局数加1
			this.setUseJuNum(this.getUseJuNum() + 1);
			this.step = MJConst.STEP_END;
			// 房间所有人的准备置空
			this.readyNumber.clear();
			// 重新生成牌
			this.reSetCards();
			// 单局统计事件,根据具体房间规则来统计
			juScoreEvent(this.maxScore);
			return true;
		}
		return false;
	}

	/**
	 * 玩家离开房间
	 * 
	 * @param s
	 * @return
	 */
	public synchronized boolean leaveRoom(Session s) {
		WSSessionKit.putRoomId(s, 0);
		long userId = WSSessionKit.getUserId(s);
		if (log.isInfoEnabled()) {
			log.info("leaveRoom openId: " + userId);
		}
		if (0 != userId) {
			PlayerInfo playInfo = MJCache.getCache().getPlayerInfo(userId);
			// 清空打牌数据
			playInfo.clearAndInit(0, 0);
			// 遍历所有用户,设置该用户的openId为空,说明空出一个位置
			leaveOrder(userId);
			this.getReadyNumber().remove(userId);
		}
		return true;
	}

	/**
	 * 计算空缺位置号
	 * 
	 * @return
	 */
	private synchronized int calcOrder() {
		Iterator<Entry<Integer, Long>> it = this.roomUsers.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> type = it.next();
			if (0 == type.getValue()) {
				return type.getKey();
			}
		}
		return 0;
	}

	/**
	 * 计算空缺位置号
	 * 
	 * @return
	 */
	private synchronized int findOrder(long userId) {
		Iterator<Entry<Integer, Long>> it = this.roomUsers.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> type = it.next();
			if (0 != type.getValue() && userId == type.getValue()) {
				return type.getKey();
			}
		}
		return 0;
	}

	/**
	 * 空出位置
	 * 
	 * @param openId
	 */
	private void leaveOrder(long userId) {
		Iterator<Entry<Integer, Long>> it = this.roomUsers.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> type = it.next();
			if (type.getValue() == userId) {
				roomUsers.put(type.getKey(), 0l);
			}
		}
	}

	/**
	 * 返回model数据
	 * 
	 * @param session
	 * @param action
	 * @return 返回action内容
	 */
	public void responseMsg(ReceiveMsg action, Object model) {
		SendMsg result = new SendMsg(action.getP(), true, action.getT(), this.getMsgSeq().incrementAndGet());
		result.setM(model);
		this.broadcast(result);
	}

	/**
	 * 给某个用户发送消息
	 * 
	 * @param msg
	 * @return
	 */
	// public boolean sendMsg2Client(SendMsg msg) {
	// return sendMsg(msg, MJCache.getCache().getSession(msg.getP()));
	// }

	/**
	 * 给某个用户发送消息
	 * 
	 * @param msg
	 * @param userId
	 * @return
	 */
	public boolean sendMsg2Client(long userId, SendMsg msg) {
		return sendMsg(userId, msg, MJCache.getCache().getSession(userId));
	}

	/**
	 * 广播房间内的所有人消息
	 * 
	 * @param SendMsg
	 */
	public void broadcast(SendMsg msg) {
		if (null != msg) {
			Iterator<Entry<Integer, Long>> it = this.roomUsers.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Integer, Long> user = it.next();
				long userId = user.getValue();
				if (0 != userId) {
					sendMsg(userId, msg, MJCache.getCache().getSession(userId));
				}
			}
		}
	}

	/**
	 * 广播房间内的所有人消息
	 * 
	 * @param SendMsg
	 * @param excludeOpenId
	 *            不用发消息的openId
	 */
	public void broadcast(SendMsg msg, long excludeUserId) {
		if (null != msg) {
			Iterator<Entry<Integer, Long>> it = this.roomUsers.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Integer, Long> user = it.next();
				long userId = user.getValue();
				if (0 != userId && excludeUserId != userId) {
					this.sendMsg(userId, msg, MJCache.getCache().getSession(userId));
				}
			}
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param msg
	 * @param session
	 */
	public boolean sendMsg(long userId, SendMsg msg, Session session) {
		if (null != session) {
			if (null != msg) {
				// 缓存重连消息
				MJCache.getCache().addSendCheckMsg(userId, msg);
				// 缓存重构消息
				MJCache.getCache().addMsg(session, msg);
				if (null != session && session.isOpen()) {
					// long userId = WSSessionKit.getUserId(session);
					if (log.isInfoEnabled()) {
						log.info("Start  Send ASync Msg:[" + msg + "],sessionId:[" + session.getId() + "],userId:"
								+ userId);
					}
					session.getAsyncRemote().sendText(msg.toJson());
					if (log.isInfoEnabled()) {
						log.info("finish Send ASync Msg:[" + msg + "],sessionId:[" + session.getId() + "],userId:"
								+ userId);
					}
					return true;

				} else {
					this.isSendEnable = false;
					log.error("Failed Send Sync Msg:[" + msg + "],userId:" + msg.getP() + ", maybe someone offline!");
				}
			} else {
				return true;
			}
		}

		return false;
	}

	// private boolean isAllOnline() {
	// Iterator<Entry<Integer, Long>> it =
	// this.getRoomUsers().entrySet().iterator();
	// while (it.hasNext()) {
	// Entry<Integer, Long> entry = it.next();
	// long userId = entry.getValue();
	// PlayerInfo player = MJCache.getCache().getPlayerInfo(userId);
	// // 如果有人离线则返回false
	// if (null != player && player.getUserInfo().getState() == 0) {
	// log.error("player offline,player: " + player);
	// return false;
	// }
	// }
	// this.isSendEnable = true;
	// return true;
	// }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Room room = (Room) o;
		return this.roomId == room.roomId;
	}

	/**
	 * 构建重新登录或者join消息
	 * 
	 * @param userInfo
	 * @param room
	 * @return ReBuildMsg
	 */
	public JoinMsg buildReBuildMsg(long userId) {
		JoinMsg reMsg = new JoinMsg(this, userId);
		Map<Integer, Long> roomUsers = this.getRoomUsers();
		Iterator<Entry<Integer, Long>> it = roomUsers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Long> entry = (Map.Entry<Integer, Long>) it.next();
			PlayerInfo p = MJCache.getCache().getPlayerInfo(entry.getValue());
			if (null != p) {
				// 如果不是自己则构造一个没牌的数据给客户端重新构建
				if (userId != p.getUserInfo().getUserId()) {
					reMsg.addPlayers(p.buildOther());
				} else {
					reMsg.addPlayers(p);
				}
			}
		}
		return reMsg;
	}

	/**
	 * 构建回放的起始消息
	 * 
	 * @return
	 */
	public JoinMsg buildReplayStartMsg() {
		JoinMsg reMsg = new JoinMsg();
		reMsg.buildRoom(this);
		reMsg.getRoom().put("step", MJConst.STEP_START + "");
		Map<Integer, Long> roomUsers = this.getRoomUsers();
		Iterator<Entry<Integer, Long>> it = roomUsers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Long> entry = (Map.Entry<Integer, Long>) it.next();
			PlayerInfo p = MJCache.getCache().getPlayerInfo(entry.getValue());
			if (null != p) {
				reMsg.addPlayers(p.copyStart());
			}
		}
		return reMsg;
	}

	public long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public long getToDismisser() {
		return toDismisser;
	}

	public void setToDismisser(long toDismisser) {
		this.toDismisser = toDismisser;
	}

	public int getTotalNumber() {
		return this.totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public int getNowNumber() {
		int num = 0;
		Iterator<Entry<Integer, Long>> it = this.roomUsers.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Long> type = it.next();
			if (0 != type.getValue()) {
				num++;
			}
		}
		this.nowNumber = num;
		return this.nowNumber;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String[] getCards() {
		return this.cards;
	}

	public void setCards(String[] cards) {
		this.cards = cards;
	}

	public long getZhuangId() {
		return this.zhuangId;
	}

	public void setZhuangId(long zhuangId) {
		this.zhuangId = zhuangId;
	}

	public int getTotalJuNum() {
		return this.totalJuNum;
	}

	public void setTotalJuNum(int totalJuNum) {
		this.totalJuNum = totalJuNum;
	}

	public int getUseJuNum() {
		return this.useJuNum;
	}

	public void setUseJuNum(int useJuNum) {
		this.useJuNum = useJuNum;
	}

	public Map<Long, Boolean> getReadyNumber() {
		return this.readyNumber;
	}

	public synchronized int calcReadyNumber() {
		Iterator<Entry<Long, Boolean>> it = this.readyNumber.entrySet().iterator();
		int count = 0;
		while (it.hasNext()) {
			Entry<Long, Boolean> ready = it.next();
			if (ready.getValue()) {
				count++;
			}
		}
		return count;
	}

	public Map<Integer, Long> getRoomUsers() {
		return this.roomUsers;
	}

	public void setRoomUsers(Map<Integer, Long> roomUsers) {
		this.roomUsers = roomUsers;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public List<HupaiMsg> getRoomResults() {
		return roomResults;
	}

	public void addRoomResult(HupaiMsg hupaiMsg) {
		this.roomResults.add(hupaiMsg);
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public int[] getOpt() {
		return opt;
	}

	public void setOpt(int[] opt) {
		this.opt = opt;
	}

	public long getCreateId() {
		return createId;
	}

	public void setCreateId(long createId) {
		this.createId = createId;
	}

	public long getPreZhuangId() {
		return preZhuangId;
	}

	public void setPreZhuangId(long preZhuangId) {
		this.preZhuangId = preZhuangId;
	}

	public int getBaseOrder() {
		return baseOrder;
	}

	public void setBaseOrder(int baseOrder) {
		this.baseOrder = baseOrder;
	}

	public Map<Long, Boolean> getOptNumber() {
		return optNumber;
	}

	public Map<Long, Boolean> addOptNumber(long userId) {
		optNumber.put(userId, false);
		return optNumber;
	}

	/**
	 * 检查是否已经完成返回所有操作
	 * 
	 * @return boolean
	 */
	public boolean isOptOver() {
		if (optNumber.values().contains(false)) {
			return false;
		}
		return true;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public boolean isOver() {
		return isStarted;
	}

	public int getActiver() {
		return activer;
	}

	public void setActiver(int activer) {
		this.activer = activer;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getTotalQuanNum() {
		return totalQuanNum;
	}

	public void setTotalQuanNum(int totalQuanNum) {
		this.totalQuanNum = totalQuanNum;
	}

	public int getUseQuanNum() {
		return useQuanNum;
	}

	public void setUseQuanNum(int useQuanNum) {
		this.useQuanNum = useQuanNum;
	}

	public boolean isSendEnable() {
		return isSendEnable;
	}

	public void setSendEnable(boolean isSendEnable) {
		this.isSendEnable = isSendEnable;
	}

	public int getBaseFen() {
		return baseFen;
	}

	public void setBaseFen(int baseFen) {
		this.baseFen = baseFen;
	}

	public List<SendMsg> getReplayMsgs() {
		return replayMsg;
	}

	public void clearReplayMsg() {
		this.replayMsg.clear();
	}

	public void addReplayMsg(SendMsg replayMsg) {
		this.replayMsg.add(replayMsg);
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void setDisMissTask(DismissTask disMissTask) {
		this.disMissTask = disMissTask;
	}

	public void setHeartTask(HeartBeatTask heartTask) {
		this.heartTask = heartTask;
	}

	public DismissTask getDisMissTask() {
		return disMissTask;
	}

	public HeartBeatTask getHeartTask() {
		return heartTask;
	}

	public AtomicLong getMsgSeq() {
		return msgSeq;
	}

	public Map<String, Integer> getArgs() {
		return args;
	}

	public void setArgs(Map<String, Integer> args) {
		this.args = args;
	}

	public void addArg(String key, int value) {
		this.args.put(key, value);
	}

	public int getArg(String key) {
		Integer value = this.args.get(key);
		return null == value ? 0 : value.intValue();
	}

	/**
	 * 根据位置获取下家userId
	 * 
	 * @param order
	 * @return userId
	 */
	public long nextUserIdByOrder(int order) {
		return this.roomUsers.get(nextOrder(order));
	}

	/**
	 * 获取下家userId
	 * 
	 * @param order
	 * @return userId
	 */
	public long nextUserId() {
		return this.roomUsers.get(nextOrder());
	}

	/**
	 * 根据userId获取下家userId
	 * 
	 * @param userId
	 * @return userId
	 */
	public long nextUserIdByUser(long userId) {
		return this.roomUsers.get(nextOrder(findOrder(userId)));
	}

	public long nextZhuang() {
		return this.roomUsers.get(nextOrder(findOrder(this.zhuangId)));
	}

	/**
	 * 相对当前的下家位置
	 * 
	 * @param order
	 * @return nextOrder
	 */
	public int nextOrder(int order) {
		return order == 1 ? 4 : (order - 1);
	}

	/**
	 * 下家位置
	 * 
	 * @param order
	 * @return nextOrder
	 */
	public int nextOrder() {
		return this.baseOrder == 1 ? 4 : (this.baseOrder - 1);
	}

	/**
	 * 庄家发14张牌,闲家发13张牌
	 * 
	 * @param from
	 * @param isZhuang
	 * @return String[] 发牌
	 */
	public synchronized String[] faPais(int from, boolean isZhuang) {
		if (isZhuang) {
			cardNum += 14;
			return Arrays.copyOfRange(cards, from, from + 14);
		}
		cardNum += 13;
		return Arrays.copyOfRange(cards, from, from + 13);
	}

	/**
	 * 发一张牌
	 * 
	 * @param type
	 * @return String
	 */
	public synchronized String fetchPai() {
		// 如果取到最后一张则返回null
		if (cards.length == cardNum) {
			return null;
		}
		cardNum += 1;
		return this.cards[cardNum - 1];
	}

	/**
	 * 重新设置新的麻将顺序
	 */
	public void reSetCards() {
		this.cards = mjRule.createPais();
	}

	public MJRule getMjRule() {
		return mjRule;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 获取命令堆栈
	 * 
	 * @return
	 */
	public Queue<SendMsgCommand> getCommands() {
		return commands;
	}

	/**
	 * 添加命令
	 * 
	 * @param command
	 */
	public void addCommand(SendMsgCommand command) {
		this.commands.add(command);
	}

	/**
	 * 提取命令
	 */
	public void pollCommand() {
		this.commands.poll();
	}

	/**
	 * 清除命令
	 */
	public void clearCommand() {
		this.commands.clear();
	}

	/**
	 * 创建事件
	 * 
	 * @param playerInfo
	 */
	public abstract void creatEvent(PlayerInfo playerInfo);

	/**
	 * 加入事件
	 * 
	 * @param 玩家位置
	 */
	protected abstract void joinEvent(PlayerInfo playerInfo);

	/**
	 * 成员满了事件
	 */
	public abstract void fullEvent();

	/**
	 * 成员满了且都准备好了事件
	 */
	public abstract void readyEvent();

	/**
	 * 所有人对某牌的操作事件
	 */
	public abstract SendMsg operateEvent();

	/**
	 * 广播所有加入者消息
	 * 
	 * @return
	 */
	public abstract ToJoinMsg toJoinMsg(int order);

	/**
	 * 单局分数统计
	 */
	public abstract void juScoreEvent(int maxScore);

	/**
	 * 单局分数统计
	 * 
	 * @param huMsg
	 */
	public abstract void juScore(PlayerInfo huPlayer, OptCheckMsg huMsg);

	/**
	 * 销毁房间
	 */
	// public void destroy() {
	// this.initVariable();
	// }
}
