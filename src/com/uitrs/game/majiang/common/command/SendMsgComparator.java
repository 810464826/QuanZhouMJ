package com.uitrs.game.majiang.common.command;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 命令优先级比较器,配合PriorityQueue优先级队列使用
 * 
 * @author lucio
 *
 */
public class SendMsgComparator implements Comparator<SendMsgCommand> {

	private static SendMsgComparator comparator = new SendMsgComparator();

	private SendMsgComparator() {
	}

	public static SendMsgComparator getInstance() {
		return comparator;
	}

	/**
	 * level相同的情况下,比较位置
	 */
	@Override
	public int compare(SendMsgCommand c1, SendMsgCommand c2) {
		int x = c1.level - c2.level;
		// order是,1,2,3,4,逆时针优先级,即1出的牌,4>3>2,如果是4出的牌,3>2>1,3出的牌,2>1>4
		int order1 = c1.order > c1.baseOrder ? (c1.order - 4) : c1.order;
		int order2 = c2.order > c2.baseOrder ? (c2.order - 4) : c2.order;
		int y = order2 - order1;
		if (x == 0) {
			return y;
		}
		return x;
	}

	public static void main(String[] args) {
		SendMsgCommand c1 = new ResponseCommand(2, null, 2, 4) {
			@Override
			public int level() {
				return this.level;
			}

			@Override
			public void execute() {
				System.err.println("command1 level:" + this.level + ", order: " + this.order);
			}
		};
		SendMsgCommand c2 = new ResponseCommand(2, null, 2, 1) {
			@Override
			public int level() {
				return this.level;
			}

			@Override
			public void execute() {
				System.err.println("command2 level:" + this.level + ", order: " + this.order);
			}
		};
		SendMsgCommand c3 = new ResponseCommand(2, null, 1, 3) {
			@Override
			public int level() {
				return this.level;
			}

			@Override
			public void execute() {
				System.err.println("command3 level:" + this.level + ", order: " + this.order);
			}
		};

		Queue<SendMsgCommand> commands = new PriorityQueue<SendMsgCommand>(5, SendMsgComparator.getInstance());
		commands.add(c1);
		commands.add(c2);
		commands.add(c3);
		System.err.println(commands.size());
		while (commands.size() > 0) {
			commands.poll().execute();
		}
	}
}
