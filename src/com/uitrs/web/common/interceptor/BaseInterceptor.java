package com.uitrs.web.common.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.JFinal;
import com.uitrs.game.majiang.web.common.MType;
import com.uitrs.web.common.kit.Constants;
import com.uitrs.web.common.kit.UserAgentUtils;

public class BaseInterceptor implements Interceptor {

	String staticPath = "";

	@Override
	public void intercept(Invocation inv) {
		before(inv);
		inv.invoke();
		after(inv);
	}

	private void after(Invocation inv) {
		inv.getController().setAttr("staticPath", staticPath);
		inv.getController().setAttr("basePath", JFinal.me().getContextPath());
		// 项目所在域名
	}

	private void before(Invocation inv) {
		// 设置静态资源文件访问路径
		HttpServletRequest request = inv.getController().getRequest();
		System.err.println("sessionId:" + request.getSession().getId());
		// 判断设备是移动端还是PC
		// String deviceType = "views";

		// 判断设备是移动端还是PC
		String deviceType = (String) request.getSession().getAttribute(Constants.DEVICE_TYPE);
		String requestDeviceType = UserAgentUtils.isMobileOrTablet(request) ? "mobile" : "views";
		if (StringUtils.isBlank(deviceType) || !requestDeviceType.equalsIgnoreCase(deviceType)) {
			deviceType = requestDeviceType;
			request.getSession().setAttribute(Constants.DEVICE_TYPE, deviceType);
		}

		MType.getType().setDevice(deviceType);

		// 读取用户主题,如果用户没有设置,则使用默认主题
		String themes = "default";
		staticPath = JFinal.me().getContextPath() + "/static/" + deviceType + "/" + themes;
	}
}
