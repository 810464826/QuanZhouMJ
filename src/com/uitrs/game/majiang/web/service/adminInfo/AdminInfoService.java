package com.uitrs.game.majiang.web.service.adminInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.uitrs.game.majiang.common.kit.IdGen;
import com.uitrs.game.majiang.web.common.Tools;
import com.uitrs.game.majiang.web.model.AdminInfo;
import com.uitrs.game.majiang.web.model.Agent;
import com.uitrs.game.majiang.web.model.Profit;
import com.uitrs.game.majiang.web.model.SuperPage;
import com.uitrs.web.common.kit.security.Encodes;

/**
 * 代理用户列表
 * 
 * @author 小石潭记
 *
 *         2017年4月28日 上午10:52:03
 */
public class AdminInfoService {
	/**
	 * 饿汉式模式 类加载的时候 就创建了 这样线程安全
	 */
	private static AdminInfoService adminService = new AdminInfoService();

	private AdminInfoService() {

	}

	/**
	 * 懒汉式 就是申明的时候不new 在下边的时候判断是否为空的时候 才new出来 这样线程不安全
	 * 
	 * @return
	 */
	public static AdminInfoService getService() {
		return adminService;
	}

	/**
	 * 判断该用户是否存在
	 * 
	 * @return
	 */
	public boolean isExist(String username) {
		// 为空代表没有查到 返回false 用户不存在
		if (null == getAdminByUserName(username)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 模糊查询用户
	 */
	public List<AdminInfo> getMHAdminInfoById(int adminId) {
		// 测试了一下 模糊查询需要使用拼接
		return AdminInfo.dao.find("select * from admininfo where adminId like '%" + adminId + "%' limit 6");
	}

	/**
	 * 模糊查询用户
	 */
	public List<AdminInfo> getMHAdminInfoByName(String nickName) {
		// 测试了一下 模糊查询需要使用拼接
		return AdminInfo.dao.find("select * from admininfo where nickName like '%" + nickName + "%' limit 6");
	}

	/**
	 * 保存用户
	 */
	public boolean saveAdmin(String username, String password, String telephone, String sysType, int pay_cardsAll,
			int pay_cardsLeft) {
		AdminInfo admin = new AdminInfo();
		boolean result = admin.set("adminId", IdGen.getInstance().getUserId()).set("nickName", username)
				.set("password", password)
				// 默认注册的时候 类型为3 类型 1超级管理员 2 普通管理员 3刚注册 无权限
				.set("sysType", Integer.valueOf(sysType)).set("telephone", telephone).set("loginTime", new Date())
				.set("totalCards", pay_cardsAll).set("surplusCards", pay_cardsLeft).save();
		return result;
	}

	/**
	 * 获取所有的管理员
	 */
	public List<AdminInfo> getAllAdmins() {
		return AdminInfo.dao.find("select * from admininfo");
	}

	/**
	 * 判断是不是登陆成功了
	 */
	public boolean login(String username, String password) {
		AdminInfo admin = getAdminByUserName(username);
		if (admin.getPassword().equals(Encodes.encodeBase64(password))) {
			boolean update = admin.set("id", admin.getId()).set("loginTime", new Date()).update();
			return update;
		}
		return false;
	}

	/**
	 * 根据用户名查用户
	 */
	public AdminInfo getAdminByUserName(String username) {
		return AdminInfo.dao.findFirst("select * from admininfo where nickName = ? ", username);
	}

	/**
	 * 根据id查找用户
	 */
	public AdminInfo getAdminById(int id) {
		return AdminInfo.dao.findById(id);
	}

	/**
	 * 根据id查找用户
	 */
	public AdminInfo getAdminByAdminId(int adminId) {
		return AdminInfo.dao.findFirst("select * from admininfo where adminId = ? ", adminId);
	}

	/**
	 * 修改密码 Encodes.encodeBase64 给密码加密
	 */
	public boolean updatePwd(String username, String oldPwd, String newPwd) {
		AdminInfo adminInfo = getAdminByUserName(username);
		String password = adminInfo.getPassword();
		if (oldPwd.equals(password)) {
			AdminInfo admin = new AdminInfo();
			boolean result = admin.set("id", adminInfo.getId()).set("password", newPwd).update();
			return result;
		} else {
			return false;
		}
	}

	/**
	 * 分页查询 查询所有的管理员的信息
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> paginate(int pageNumber, int pageSize) {
		return AdminInfo.dao.paginate(pageNumber, pageSize);
	}

	/**
	 * 删除选定的管理员
	 */
	public boolean delete(int id) {
		return AdminInfo.dao.deleteById(id);
	}

	/**
	 * 修改用户
	 */
	public boolean update(int userId, String password, String nickName, int totalCards, int surplusCards, int sysType,
			String telephone, int adminId) {
		String newPass = "";
		AdminInfo admin = AdminInfoService.getService().getAdminById(userId);
		// 这是密码没有被修改的时候
		if (password.equals(admin.getPassword())) {
			newPass = password;
		} else {
			// 如果密码修改了 就要加密
			newPass = Encodes.encodeBase64(password);
		}
		// 如果adminId被修改时
		if (admin.getAdminId() != adminId) {
			AdminInfo adminInfo = AdminInfoService.getService().getAdminByAdminId(adminId);
			// 说明该adminId被占用
			if (adminInfo != null) {
				return false;
			} else {
				boolean update = admin.set("id", userId).set("password", newPass).set("sysType", sysType)
						.set("adminId", adminId).set("nickName", nickName).set("totalCards", totalCards)
						.set("surplusCards", surplusCards).set("telephone", telephone).set("loginTime", new Date())
						.update();
				return update;
			}
		} else {
			// 这是adminId没有被修改的时候
			boolean update = admin.set("id", userId).set("password", newPass).set("sysType", sysType)
					.set("adminId", adminId).set("nickName", nickName).set("totalCards", totalCards)
					.set("surplusCards", surplusCards).set("telephone", telephone).set("loginTime", new Date())
					.update();
			return update;
		}
	}

	/**
	 * 添加用户
	 */
	public boolean addOneAdmin(String password, String nickName, int totalCards, int surplusCards, int sysType,
			String telephone, int adminId) {
		AdminInfo admin = new AdminInfo();
		AdminInfo adminInfo = AdminInfoService.getService().getAdminByAdminId(adminId);
		if (adminInfo != null) {
			return false;
		} else {
			boolean save = admin.set("password", Encodes.encodeBase64(password)).set("sysType", sysType)
					// .set("headIcon",
					// "http://wx.qlogo.cn/mmopen/McYMgia19V0UbzCKO6IAHzIrXfr8HZGxjlltTicjD0cOc75Bywe7xGHgI8hiaY2jSYzlpVDDvwAeAA8DH2LgHnyx8pOZ7Crhyjw/0")
					.set("adminId", adminId).set("nickName", nickName).set("totalCards", totalCards)
					.set("surplusCards", surplusCards).set("telephone", telephone).set("loginTime", new Date()).save();
			return save;
		}
	}

	/**
	 * 获取扩展对象 注意使用面向对象的思想解决问题！！！
	 */
	public SuperPage<AdminInfo> getAdminPage(int adminId) {
		List<AdminInfo> adminInfos = getMHAdminInfoByAdminId(adminId);
		if (adminInfos.size() > 0) {
			SuperPage<AdminInfo> page = new SuperPage<AdminInfo>();
			for (AdminInfo a : adminInfos) {
				page.getList().add(a);
			}
			page.setCurrentPage(1);
			page.setTotalPages(Tools.getSize(adminInfos.size()));
			return page;
		} else {
			return null;
		}
	}

	/**
	 * 获取扩展对象 注意使用面向对象的思想解决问题！！！
	 */
	public SuperPage<AdminInfo> getAllAdminPage() {
		List<AdminInfo> adminInfos = getMHAllAdminInfo();
		if (adminInfos.size() > 0) {
			SuperPage<AdminInfo> page = new SuperPage<AdminInfo>();
			for (AdminInfo a : adminInfos) {
				page.getList().add(a);
			}
			page.setCurrentPage(1);
//			page.setTotalPages(1);
			page.setTotalPages(Tools.getSize(adminInfos.size()));
			return page;
		} else {
			return null;
		}
	}

	/**
	 * 模糊查询用户
	 */
	public List<AdminInfo> getMHAdminInfoByAdminId(int adminId) {
		// 测试了一下 模糊查询需要使用拼接
		return AdminInfo.dao.find("select * from admininfo where adminId like '%" + adminId + "%' ");
	}

	/**
	 * 模糊查询用户
	 */
	public List<AdminInfo> getMHAllAdminInfo() {
		// 测试了一下 模糊查询需要使用拼接
		return AdminInfo.dao.find("select * from admininfo");
	}

	/**
	 * 查询所有的管理员
	 */
	public List<AdminInfo> getAdmins() {
		return AdminInfo.dao.find("select * from admininfo limit 6");
	}

	/**
	 * 管理员类型
	 */
	public List<String> getTypes() {
		List<String> types = new ArrayList<String>();
		types.add("超级管理员");
		types.add("普通管理员");
		return types;
	}
	
	/**
	 * 查询代理比例
	 */
	public Double getAgent(){
		Agent agent = Agent.dao.findById(1);
		return agent.getBili();
	}
	
	/**
	 * 修改代理比例
	 */
	public boolean updateBili(Double bili){
		Agent agent = Agent.dao.findById(1);
		agent.setBili(bili);
		return agent.update();
	}
	
	/**
	 * 查询获利表
	 */
	public List<Profit> getProfit(int id){
		return Profit.dao.find("select * from profit where winnerId=?",id);
	}
	
	public List<Profit> getProfits(){
		return Profit.dao.find("select * from profit ");
	}
	
	/**
	 * 查询获利表
	 */
	public Profit getProfitById(int id){
		return Profit.dao.findById(id);
	}
	
	public boolean deleteById(int id){
		return Profit.dao.deleteById(id);
	}
}
