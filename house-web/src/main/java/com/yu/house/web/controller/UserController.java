package com.yu.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yu.house.biz.service.UserService;
import com.yu.house.common.model.User;
import com.yu.house.common.result.ResultMsg;

/**
 * user交互
 * @author jinfang yu
 *
 */
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	/**
	 * 获取用户
	 * @return
	 */
	@RequestMapping("user")
	public List<User> getUser(){
		return userService.getUser();
	}
	
	/**
	 * 注册页面跳转、发送邮件、验证失败重定向
	 * 注册页获取：account默认是对象，如果account为空（就是页面请求）
	 * @return
	 */
	@RequestMapping("accounts/register")
	public String accountsRegister(User account,ModelMap modelMap) {
		if(account.getName()==null || account.getName().equals("")) {
			 return "/user/accounts/register";//注册页面跳转
		}
		System.err.print("contorller-------> \n");
		System.err.println(account.toString());
		//用户验证
		ResultMsg resultMsg = UserHelper.validata(account);
		//表单验证通过
		//重定向到发送邮件成功页面、添加到数据库，状态为false
		boolean flag = userService.addAcount(account);
		if(resultMsg.isCuccess() && flag) {
			//modelMap.put("email", account.getEmail());
			System.out.println("注册成功---------------");
			return "/user/accounts/registerSubmit";
		}else {//重定向
			return "redirect:/accounts/register?"+resultMsg.getUrlParams();
		}
	}
	
}
