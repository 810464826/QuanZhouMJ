package com.jfinal.core;

import org.boilit.bsl.Engine;
import org.boilit.bsl.IEngine;

import com.jfinal.config.Constants;
import com.jfinal.plugin.IPlugin;
import com.jfinal.render.IMainRenderFactory;
import com.jfinal.render.Render;
import com.uitrs.web.common.render.BslRender;

/**
 * Bsl模版视图插件
 * 
 * @author lucio
 *
 */
public class BslPlugin implements IPlugin {
	private static IEngine engine = null;
	private String baseViewPath;

	public static IEngine getEngine() {
		return engine;
	}

	public BslPlugin() {
	}

	public BslPlugin(String baseViewPath) {
		this.baseViewPath = baseViewPath;
	}

	@Override
	public boolean start() {
		try {
			engine = Engine.getEngine();
			Constants c = Config.getConstants();
			c.setMainRenderFactory(new IMainRenderFactory() {
				@Override
				public Render getRender(String view) {
					BslRender bslRender = new BslRender(view);
					if (null != baseViewPath) {
						bslRender.setBasePath(baseViewPath);
					}
					return bslRender;
				}

				@Override
				public String getViewExtension() {
					return ".html";
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean stop() {
		if (null != engine && engine.isUseTemplateCache()) {
			engine.getTemplateCache().clear();
			engine = null;
		}
		return true;
	}

	/**
	 * 清除模版缓存, 当useTemplateCache=true时, 如果修改了模版内容调用此方法清除后才能生效
	 */
	public void clearTemplateCache() {
		engine.getTemplateCache().clear();
	}

}
