package com.yu.house.web.interceptor;

import com.yu.house.common.model.User;

public class UserContext {
	//本地线程
	private static final ThreadLocal<User> USER_HODLER = new ThreadLocal<>();
	
	//添加用户
	public static void setUser(User user) {
		USER_HODLER.set(user);
	}
	//移除用户
	public static void remove() {
		USER_HODLER.remove();
	}
	//获取用户
	public static User getUser() {
		return USER_HODLER.get();
	}
}
