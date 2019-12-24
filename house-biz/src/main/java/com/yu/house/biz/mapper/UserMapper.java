package com.yu.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yu.house.common.model.User;

@Mapper
public interface UserMapper {
	//查询用户
	public List<User> selectUser();
	//用户注册插入
	public int insert(User account);
	//删除用户
	public int delete(String email);
	//用户信息查询
	public List<User> selectUsersByQuery(User user);
	
}
