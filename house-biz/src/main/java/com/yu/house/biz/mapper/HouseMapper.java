package com.yu.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yu.house.common.model.Community;
import com.yu.house.common.model.House;
import com.yu.house.common.model.User;
import com.yu.house.common.page.PageParams;

@Mapper
public interface HouseMapper {
	//房屋分页查询
	public List<House> selectPageHouses(@Param("house") House house, @Param("pageParams") PageParams pageParams);
	//查询分页数
	public Long selectPageCount(@Param("house") House query);
	//添加用户
	public int insert(User account);
	//查询小区
	public List<Community> selectCommunity(Community community);
	
	
	
}
