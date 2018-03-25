package com.uitrs.game.majiang.web.controller.mahjong.adminInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.kit.MJCache;
import com.uitrs.game.majiang.web.common.MType;
import com.uitrs.game.majiang.web.model.AdminInfo;
import com.uitrs.game.majiang.web.model.CardType;
import com.uitrs.game.majiang.web.model.GamerInfo;
import com.uitrs.game.majiang.web.model.Profit;
import com.uitrs.game.majiang.web.model.SuperPage;
import com.uitrs.game.majiang.web.service.adminInfo.AdminInfoService;
import com.uitrs.game.majiang.web.service.cardType.CardTypeService;
import com.uitrs.game.majiang.web.service.gamerInfo.GamerInfoService;
import com.uitrs.web.common.controller.ControllerPath;
import com.uitrs.web.common.interceptor.SessionInterceptor;
import com.uitrs.web.common.kit.SystemUtil;
import com.uitrs.web.common.kit.security.Encodes;

/**
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:41:01
 */
@Before({ SessionInterceptor.class })
@ControllerPath(controllerKey = "/adminInfo")
public class AdminInfoController extends Controller {
	/**
	 * 登陆的方法
	 */
	@Clear(SessionInterceptor.class)
	public void login() {
		String username = getPara("username");
		String password = getPara("password");
		boolean login = AdminInfoService.getService().login(username, password);
		if (login) {
			AdminInfo adminInfo = AdminInfoService.getService().getAdminByUserName(username);
			setSessionAttr("username", username);
			setSessionAttr("adminId", adminInfo.getAdminId());
			setSessionAttr("userType", adminInfo.getSysType());
			renderJson("result", "true");
		} else {
			renderJson("result", "false");
		}
	}

	/**
	 * 主页
	 */
	public void main() {
		String device = MType.getType().getDevice();
		int userType = (Integer) getSession().getAttribute("userType");
		setAttr("userType", userType);
		render("/WEB-INF/" + device + "/default/mahjong/index.html");
	}

	/**
	 * 查询当前登陆用户的信息 后边加上显示信息 比如 内存使用的大小和当前在线的人数 等等
	 */
	public void getAdminInfo() {
		setAttr("admin",
				AdminInfoService.getService().getAdminByUserName((String) getSession().getAttribute("username")));
		String device = MType.getType().getDevice();
		setAttr("roomSize", MJCache.getCache().getRooms().size());
		setAttr("userSize", MJCache.getCache().getPlayerInfos().size());
		setAttr("totalSize", SystemUtil.getTotalMemory());
		setAttr("freeSize", SystemUtil.getFreeMemory());
		setAttr("threadNum", SystemUtil.getThreadNum());
		render("/WEB-INF/" + device + "/default/mahjong/person.html");
	}

	/**
	 * 修改密码页面
	 */
	public void toUpdate() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/revise_password.html");
	}

	/**
	 * 修改密码
	 */
	public void updatePwd() {
		boolean b = AdminInfoService.getService().updatePwd((String) getSession().getAttribute("username"),
				Encodes.encodeBase64(getPara("oldPwd")), Encodes.encodeBase64(getPara("newPwd")));
		renderJson("result", b);
	}

	/**
	 * 管理员信息页面
	 */
	public void admin() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/admin.html");
	}

	/**
	 * 查询所有管理员 分页
	 */
	public void getPageAdminInfo() {
		// 前台传递当前页
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> adminInfo = AdminInfoService.getService().paginate(getParaToInt("curentPage"), 10);
		model.put("curentPage", adminInfo.get("curentPage"));
		model.put("totalPages", adminInfo.get("totalPages"));
		model.put("allAdmins", adminInfo.get("list"));
		renderJson(model);
	}

	/**
	 * 删除选择的管理员
	 */
	public void delete() {
		renderJson("result", AdminInfoService.getService().delete(getParaToInt("id")));
	}

	/**
	 * 修改时 查询单个用户
	 */
	public void getById() {
		setAttr("admin", AdminInfoService.getService().getAdminById(getParaToInt("userId")));
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/manager.html");
	}

	/**
	 * 修改用户信息
	 */
	public void updateAdmin() {
		boolean update = AdminInfoService.getService().update(getParaToInt("userId"), getPara("password"),
				getPara("nickName"), getParaToInt("totalCards"), getParaToInt("surplusCards"), getParaToInt("sysType"),
				getPara("telephone"), getParaToInt("adminId"));
		renderJson("result", update);
	}

	/**
	 * 添加管理员信息页面
	 */
	public void addAdmin() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/addmanager.html");
	}

	/**
	 * 添加管理员处理
	 */
	public void addOneAdmin() {
		boolean save = AdminInfoService.getService().addOneAdmin(getPara("password"), getPara("nickName"),
				getParaToInt("totalCards"), getParaToInt("surplusCards"), getParaToInt("sysType"), getPara("telephone"),
				getParaToInt("adminId"));
		renderJson("result", save);
	}

	/**
	 * 查询指定用户名的用户的信息 查询单个用户
	 */
	public void getAdminByAdminId() {
		String adminId = getPara("adminId");
		if (adminId != null && !"".equals(adminId)) {
			SuperPage<AdminInfo> adminPage = AdminInfoService.getService().getAdminPage(Integer.parseInt(adminId));
			if (adminPage != null) {
				renderJson(JsonUtils.toJson(adminPage));
			} else {
				renderJson("false");
			}
		} else {
			SuperPage<AdminInfo> adminPage = AdminInfoService.getService().getAllAdminPage();
			if (adminPage != null) {
				renderJson(JsonUtils.toJson(adminPage));
			} else {
				renderJson("false");
			}
		}
	}

	/**
	 * ***************************玩家信息页面**************************
	 */
	public void player() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/player_info.html");
	}

	/**
	 * ***************************玩家成绩页面****************************
	 */
	public void palyerScore() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/player_score.html");
	}

	/**
	 * ****************************房间信息页面***************************
	 */
	public void roomInfo() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/room_info.html");
	}

	/**
	 * ***************************给管理员充值记录页面 ***********************
	 */
	public void rechargeAdmin() {
		String device = MType.getType().getDevice();
		Integer type = AdminInfoService.getService().getAdminByUserName((String) getSession().getAttribute("username"))
				.getSysType();
		setAttr("type", type);
		render("/WEB-INF/" + device + "/default/mahjong/recharge_record_admin.html");
	}

	/**
	 * **************************给玩家充值记录页面 ***********************
	 */
	public void rechargeGamer() {
		String device = MType.getType().getDevice();
		Integer type = AdminInfoService.getService().getAdminByUserName((String) getSession().getAttribute("username"))
				.getSysType();
		setAttr("type", type);
		render("/WEB-INF/" + device + "/default/mahjong/recharge_record_gamer.html");
	}

	/**
	 * ******************房卡类型页面*******************
	 */
	public void roomCard() {
		List<Object> allTypes = CardTypeService.getService().getAllTypes();
		setAttr("allTypes", allTypes);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/room_card.html");
	}

	/********************* 手机端 ****************************/
	/**
	 * 跳转到个人信息的选项 后边加上显示信息 比如 内存使用的大小和当前在线的人数 等等
	 */
	public void goToPerson() {
		AdminInfo admin = AdminInfoService.getService()
				.getAdminByUserName((String) getSession().getAttribute("username"));
		setAttr("admin", admin);

		setAttr("roomSize", MJCache.getCache().getRooms().size());
		setAttr("userSize", MJCache.getCache().getPlayerInfos().size());
		setAttr("totalSize", SystemUtil.getTotalMemory());
		setAttr("freeSize", SystemUtil.getFreeMemory());
		setAttr("threadNum", SystemUtil.getThreadNum());
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newPersonInfo.html");
	}

	/**
	 * 去向修改密码的页面
	 */
	public void goToPwd() {
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newPwd.html");
	}

	/**
	 * 查询所有的管理员
	 */
	public void getAdmins() {
		List<AdminInfo> admins = AdminInfoService.getService().getAdmins();
		setAttr("admins", admins);
		setAttr("person",
				AdminInfoService.getService().getAdminByUserName((String) getSession().getAttribute("username")));
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newAdmin.html");
	}

	/**
	 * 查询指定的管理员
	 */
	public void getOneAdmin() {
		int adminId = getParaToInt("adminId");
		String type = getPara("type");
		AdminInfo adminInfo = AdminInfoService.getService().getAdminById(adminId);
		setAttr("adminInfo", adminInfo);
		setAttr("type", type);
		if (adminInfo != null) {
			String device = MType.getType().getDevice();
			render("/WEB-INF/" + device + "/default/mahjong/newAdminInfo.html");
		}
	}

	/**
	 * 修改用户信息 mobile
	 */
	public void mUpdate() {
		int id = getParaToInt("id");
		int adminId = getParaToInt("adminId");
		String password = getPara("password");
		int type = getParaToInt("sysType");
		String phone = getPara("telephone");
		String nickName = getPara("nickName");
		int totalCards = 0;
		int surplusCards = 0;
		AdminInfo adminInfo = AdminInfoService.getService().getAdminById(id);
		if (adminInfo.getSysType() == 0) {
			totalCards = adminInfo.getTotalCards();
			surplusCards = adminInfo.getSurplusCards();
		} else {
			totalCards = getParaToInt("cardsAll");
			surplusCards = getParaToInt("cardsLeft");
		}
		boolean update = AdminInfoService.getService().update(id, password, nickName, totalCards, surplusCards, type,
				phone, adminId);
		renderJson("result", update);
	}

	/**
	 * 去向添加管理员的页面
	 */
	public void goToAdd() {
		List<String> types = AdminInfoService.getService().getTypes();
		setAttr("types", types);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newAddAdminInfo.html");
	}

	/**
	 * 添加用户时，判断是否存在该用户
	 */
	public void checkName() {
		if (AdminInfoService.getService().isExist(getPara("username"))) {
			renderJson("result", null);
		} else {
			boolean flag = AdminInfoService.getService().saveAdmin(getPara("username"),
					Encodes.encodeBase64(getPara("password")), getPara("telephone"), getPara("sysType"),
					getParaToInt("pay_cardsAll"), getParaToInt("pay_cardsLeft"));
			renderJson("result", flag);
		}
	}

	/**
	 * 模糊查询管理员
	 */
	public void getSameAdmins() {
		String para = getPara("adminId");
		if (para == null || para.length() == 0) {
			String device = MType.getType().getDevice();
			render("/WEB-INF/" + device + "/default/mahjong/error.html");
			return;
		}
		List<AdminInfo> admins = AdminInfoService.getService().getMHAdminInfoById(getParaToInt("adminId"));
		setAttr("admins", admins);
		setAttr("person",
				AdminInfoService.getService().getAdminByUserName((String) getSession().getAttribute("username")));
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newAdmin.html");
	}

	/**
	 * 查询所有的房卡类型
	 */
	public void getAllTypes() {
		List<CardType> types = CardTypeService.getService().getCardTypes();
		setAttr("types", types);
		setAttr("person",
				AdminInfoService.getService().getAdminByUserName((String) getSession().getAttribute("username")));
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newCard.html");
	}
	
	/**
	 * 这里查询的是 该代理商的下级用户
	 */
	public void getNextAdmin(){
		int gamerId = getParaToInt("id");
		List<GamerInfo> players = GamerInfoService.getService().getNextGamers(gamerId);
		setAttr("players", players);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/newPlayer.html");
	}
	
	/**
	 * 系统设置里的功能
	 */
	public void goToSetting(){
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/setting.html");
	}
	
	/**
	 * 进入修改代理的比例
	 */
	public void setBili(){
		String device = MType.getType().getDevice();
		Double agent = AdminInfoService.getService().getAgent();
		setAttr("bili", agent);
		render("/WEB-INF/" + device + "/default/mahjong/updateBili.html");
	}
	
	/**
	 * 修改代理比例
	 */
	public void updateBili(){
		String para = getPara("bili");
		AdminInfoService.getService().updateBili(Double.parseDouble(para));
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/setting.html");
	}
	
	/**
	 * 获取盈利的记录
	 */
	public void getProfits(){
		List<Profit> profits = AdminInfoService.getService().getProfits();
		setAttr("profits", profits);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/profits.html");
	}
	
	/**
	 * 查询单条盈利记录
	 */
	public void getOneProfit(){
		Integer id = getParaToInt("id");
		Profit profit = AdminInfoService.getService().getProfitById(id);
		setAttr("profit", profit);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/profitInfo.html");
	}
	
	/**
	 * 删除单条记录
	 */
	public void deleteProfit(){
		Integer id = getParaToInt("id");
		boolean b = AdminInfoService.getService().deleteById(id);
		renderJson("result",b);
	}
	
	/**
	 * 查询指定代理商的ID
	 */
	public void searchProfit(){
		Integer id = getParaToInt("adminId");
		List<Profit> profits= AdminInfoService.getService().getProfit(id);
		setAttr("profits", profits);
		String device = MType.getType().getDevice();
		render("/WEB-INF/" + device + "/default/mahjong/profits.html");
	}
}
