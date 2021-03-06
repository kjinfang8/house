package com.yu.house.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yu.house.biz.service.UserService;
import com.yu.house.common.constants.CommonConstants;
import com.yu.house.common.model.User;
import com.yu.house.common.result.ResultMsg;
import com.yu.house.common.utils.HashUtils;

/**
 * user交互
 * 
 * @author jinfang yu
 *
 */
@Controller
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 获取用户
	 * 
	 * @return
	 */
	@RequestMapping("user")
	public List<User> getUser() {
		return userService.getUser();
	}

	/**
	 * 注册页面跳转、发送邮件、验证失败重定向 注册页获取：account默认是对象，如果account为空（就是页面请求）
	 * 
	 * @return
	 */
	@RequestMapping("accounts/register")
	public String accountsRegister(User account, ModelMap modelMap) {
		System.err.print("contorller-------> \n");
		if (account == null || account.getName() == null) {
			return "/user/accounts/register";
		}
		System.err.print("contorller-------> \n");
		System.err.println(account.toString());
		// 用户验证
		ResultMsg resultMsg = UserHelper.validata(account);
		// 表单验证通过
		System.out.println(resultMsg + "--------------");
		// 重定向到发送邮件成功页面、添加到数据库，状态为false
		if (resultMsg.isCuccess() && userService.addAcount(account)) {
			modelMap.put("email", account.getEmail());
			return "/user/accounts/registerSubmit";
		} else {// 重定向
			return "redirect:/accounts/register?" + resultMsg.getUrlParams();
		}
	}

	/**
	 * 激活链接
	 * 
	 * @param key
	 * @return
	 */
	@RequestMapping("accounts/verify")
	public String verify(String key) {
		boolean result = userService.enable(key);
		if (result) {
			return "redirect:/index?" + ResultMsg.successMsg("激活成功").getUrlParams();
		} else {
			return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期");
		}
	}

	// --------------登录页面-----------------

	/**
	 * 登录接口
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/accounts/signin")
	public String signin(HttpServletRequest req) {
		// 获取参数
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String target = req.getParameter("target");
		//
		if (username == null || password == null) {
			req.setAttribute("target", target);
			return "/user/accounts/signin";
		}
		// 验证操作
		User user = userService.auth(username, password);
		if (user == null) {
			return "redirect:/accounts/signin?" + "target=" + target + "&username=" + username + "&"
					+ ResultMsg.errorMsg("用户名或密码错误").getUrlParams();
		} else {
			HttpSession session = req.getSession(true);
			session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
			return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
		}
	}

	/**
	 * 登出操作
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("accounts/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.invalidate();
		return "redirect:/index";
	}

	// ---------------------个人信息页-------------------------
	/**
	 * 1.能够提供页面信息 2.更新用户信息
	 * 
	 * @param updateUser
	 * @param model
	 * @return
	 */
	@RequestMapping("accounts/profile")
	public String profile(HttpServletRequest req, User updateUser, ModelMap model) {
		if (updateUser.getEmail() == null) {
			return "/user/accounts/profile";
		}
		userService.updateUser(updateUser, updateUser.getEmail());
		User query = new User();
		query.setEmail(updateUser.getEmail());
		// 更新后，重新刷新页面的话，就重新获取用户信息，重定向提示更新成功
		List<User> users = userService.getUserByQuery(query);
		req.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, users.get(0));
		return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").getUrlParams();
	}

	/**
	 * 修改密码操作
	 * 
	 * @param email
	 * @param password
	 * @param newPassword
	 * @param confirmPassword
	 * @param mode
	 * @return
	 */
	@RequestMapping("accounts/changePassword")
	public String changePassword(String email, String password, String newPassword, String confirmPassword,
			ModelMap mode) {
		User user = userService.auth(email, password);
		if (user == null || !confirmPassword.equals(newPassword)) {
			return "redirct:/accounts/profile?" + ResultMsg.errorMsg("密码错误").getUrlParams();
		}
		User updateUser = new User();
		updateUser.setPasswd(HashUtils.encryPassword(newPassword));
		userService.updateUser(updateUser, email);
		return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").getUrlParams();
	}
	
	
}
