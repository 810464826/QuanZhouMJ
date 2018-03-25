package com.uitrs.web.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class SessionInterceptor implements Interceptor {
	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		String username = controller.getSessionAttr("username");
		controller.setAttr("userType", controller.getSessionAttr("userType"));
		if (username != null) {
			inv.invoke();
		} else {
			controller.redirect("/");
		}
	}
}
