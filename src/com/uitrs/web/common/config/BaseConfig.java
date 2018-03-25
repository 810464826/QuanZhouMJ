package com.uitrs.web.common.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.BslPlugin;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.uitrs.game.majiang.web.model._MappingKit;
import com.uitrs.web.common.controller.AutoBindRoutes;
import com.uitrs.web.common.interceptor.BaseInterceptor;
import com.uitrs.web.common.kit.security.ToolIDEA;

/**
 * API引导式配置
 */
public class BaseConfig extends JFinalConfig {

	private final Log log = Log.getLog(BaseConfig.class);

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		// 此方法直接调用指定配置文件指定参数PropKit.use("system.properties").get("baseUploadPath")
		PropKit.use("develop.properties");
		me.setDevMode(PropKit.getBoolean("devMode", true));
		ApiConfigKit.setDevMode(me.getDevMode());
		ApiConfigKit.setThreadLocalApiConfig(getApiConfig());

	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		// 配置Controller路径
		// me.add("/fzmj", FrontController.class, "/fzmj"); //
		// 第三个参数为该Controller的视图存放路径
		AutoBindRoutes autoBindRoutes = new AutoBindRoutes();
		me.add(autoBindRoutes);
	}

	/**
	 * 配置数据库连接
	 */
	public static C3p0Plugin createC3p0Plugin() {
		return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}

	public static DruidPlugin createDruidPlugin() {
		// System.out.println(ToolIDEA.encrypt("root"));
		// System.out.println(ToolIDEA.encrypt("123456"));
		// 非开发模式下,用户名和密码必须加密设置
		if (PropKit.getBoolean("devMode")) {
			return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user").trim(), PropKit.get("password").trim());
		} else {
			return new DruidPlugin(PropKit.get("jdbcUrl"), ToolIDEA.decrypt(PropKit.get("user").trim()),
					ToolIDEA.decrypt(PropKit.get("password").trim()));
		}
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		// C3p0Plugin C3p0Plugin = createC3p0Plugin();
		// me.add(C3p0Plugin);
		//
		DruidPlugin druidPlugin = createDruidPlugin();
		me.add(druidPlugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		me.add(arp);
		//
		// // 所有配置在 MappingKit 中搞定
		_MappingKit.mapping(arp);

		// 缓存配置
		// EhCachePlugin ecp = new EhCachePlugin(PathKit.getRootClassPath() +
		// "/ehcache.xml");
		// me.add(ecp);

		// 配置Bsl引擎模版视图
		me.add(new BslPlugin(PropKit.get("viewPath")));
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new BaseInterceptor());
		// 国际化
		// me.add(new I18nInterceptor());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		// 一个Handler类可以接管所有 web 请求，并对应用拥有完全的控制权，可以很方便地实现更高层的功能性扩展。 例：BaseHandler
	}

	/**
	 * 重写JFinalConfig中的方法，会在系统启动完成后回调
	 */
	public void afterJFinalStart() {
		log.info("JFinal框架加载完成!");
	};

	/**
	 * 重写JFinalConfig中的方法，会在系统关闭前回调
	 */
	public void beforeJFinalStop() {
		log.info("JFinal框架开始加载...");
	};

	/**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的 ApiConfig 对象即可 可以通过在请求 url 中挂参数来动态从数据库中获取
	 * ApiConfig 属性值
	 */
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();

		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));

		/**
		 * 是否对消息进行加密，对应于微信平台的消息加解密方式： 1：true进行加密且必须配置 encodingAesKey
		 * 2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目 运行此 main
	 * 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
