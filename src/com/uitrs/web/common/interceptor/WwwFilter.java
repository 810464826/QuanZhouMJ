package com.uitrs.web.common.interceptor;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WwwFilter implements Filter {
	private String originUrl;
	private String targetUrl;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.originUrl = filterConfig.getInitParameter("originUrl");// 来源url
		this.targetUrl = filterConfig.getInitParameter("targetUrl");// 目标url
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		String hostName = req.getServerName();
		if (hostName.startsWith(originUrl)) {
			HttpServletResponse response = (HttpServletResponse) resp;
			HttpServletRequest httpRequest = (HttpServletRequest) req;
			String queryString = (httpRequest.getQueryString() == null ? "" : "?" + httpRequest.getQueryString());
			response.setStatus(301);
			String requestUrl = httpRequest.getRequestURL().toString();
			requestUrl = requestUrl.replace(originUrl, targetUrl);

			response.setHeader("Location", requestUrl + queryString);
			response.setHeader("Connection", "close");
		} else {
			chain.doFilter(req, resp);
		}
	}

}
