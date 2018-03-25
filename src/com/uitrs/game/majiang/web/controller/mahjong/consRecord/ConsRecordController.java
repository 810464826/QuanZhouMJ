package com.uitrs.game.majiang.web.controller.mahjong.consRecord;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.uitrs.web.common.controller.ControllerPath;
import com.uitrs.web.common.interceptor.SessionInterceptor;

/**
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:41:57
 */
@Before({ SessionInterceptor.class })
@ControllerPath(controllerKey = "/sysNotice")
public class ConsRecordController extends Controller {

}
