package com.uitrs.web.common.kit;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	private static final ThreadLocal<Map<String, Object>> threadSysConfig = new ThreadLocal<Map<String, Object>>();
	public static final String DEVICE_TYPE = "deviceType";
	public static final String MOBILE = "mobile";
	public static final CharSequence SCOPE = "snsapi_base";

	public static void setThreadLocalConfig(String name, Object value) {
		if (null == threadSysConfig.get()) {
			threadSysConfig.set(new HashMap<String, Object>());
		}
		threadSysConfig.get().put(name, value);
	}

	public static void getThreadLocalConfig(String name) {
		if (null == threadSysConfig.get()) {
			threadSysConfig.set(new HashMap<String, Object>());
		}
		threadSysConfig.get().get(name);
	}

	public static void removeThreadLocalConfig(String name) {
		if (null == threadSysConfig.get()) {
			threadSysConfig.set(new HashMap<String, Object>());
		}
		threadSysConfig.get().remove(name);
	}

}
