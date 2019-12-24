package com.yu.house.web.controller;

import org.apache.commons.lang3.StringUtils;

import com.yu.house.common.model.User;
import com.yu.house.common.result.ResultMsg;
/**
 * 用户验证
 * @author jinfang yu
 *
 */
public class UserHelper {
	//返回页面信息
	public static ResultMsg validata(User account) {
		if(StringUtils.isBlank(account.getEmail())) {//需要导入apache.commons.lang3的jar包
			return ResultMsg.errorMsg("Email 有误");
		}
		if(StringUtils.isBlank(account.getName())) {
			return ResultMsg.errorMsg("Name 有误");
		}
		//密码一致
		if(StringUtils.isBlank(account.getConfirmPasswd())//确认密码
				||StringUtils.isBlank(account.getPasswd())//用户密码
				|| !account.getPasswd().equals(account.getConfirmPasswd())) {//两次密码是否相同
			return ResultMsg.errorMsg("密码 有误");
		}
		if(account.getPasswd().length()<1) {
			return ResultMsg.errorMsg("密码要大于6位数");
		}
		//验证成功，返回一个空字符串（验证通过
		return ResultMsg.errorMsg("");
	}
}
