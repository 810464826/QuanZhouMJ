package com.uitrs.web.common.kit;

import com.jfinal.log.Log;

/**
 * 系统信息
 * 
 * @author lucio
 *
 */
public final class SystemUtil {
	private static Log log = Log.getLog(SystemUtil.class);

	/**
	 * 可用内存,mb
	 */
	public static long getFreeMemory() {
		return Runtime.getRuntime().freeMemory() / 1024 / 1024;
	}

	/**
	 * 总内存,mb
	 */
	public static long getTotalMemory() {
		return Runtime.getRuntime().totalMemory() / 1024 / 1024;
	}

	/**
	 * 总线程数
	 */
	public static int getThreadNum() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		while (group.getParent() != null) {
			group = group.getParent();
		}
		Thread[] threads = new Thread[group.activeCount()];
		return group.enumerate(threads);

	}

	public static String getThreadList() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		while (group.getParent() != null) {
			group = group.getParent();
		}
		Thread[] threads = new Thread[group.activeCount()];
		group.enumerate(threads);
		StringBuffer buf = new StringBuffer();
		for (Thread thread : threads) {
			if (thread == null) {
				continue;
			}
			try {
				ThreadGroup tgroup = thread.getThreadGroup();
				String groupName = tgroup == null ? "null" : tgroup.getName();
				buf.append("ThreadGroup:").append(groupName).append(", ");
				buf.append("Id:").append(thread.getId()).append(", ");
				buf.append("Name:").append(thread.getName()).append(", ");
				buf.append("isDaemon:").append(thread.isDaemon()).append(", ");
				buf.append("isAlive:").append(thread.isAlive()).append(", ");
				buf.append("Priority:").append(thread.getPriority());
				if (log.isInfoEnabled()) {
					log.info(buf.toString());
				}
			} catch (Throwable e) {
				log.error("getThreadList error!", e);
			}
		}
		return buf.toString();
	}
}
