package com.yu.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yu.house.common.model.Community;
import com.yu.house.common.model.House;
import com.yu.house.common.model.HouseUser;
import com.yu.house.common.model.User;
import com.yu.house.common.model.UserMsg;
import com.yu.house.common.page.PageParams;

@Mapper
public interface HouseMapper {
	//房屋分页查询
	public List<House> selectPageHouses(@Param("house") House house, @Param("pageParams") PageParams pageParams);
	//查询分页数
	public Long selectPageCount(@Param("house") House query);
	//添加用户
	public int insert(User account);
	//添加房产
	public int insert(House house);
	//查询小区
	public List<Community> selectCommunity(Community community);
	//绑定查询
	public HouseUser selectHouseUser(@Param("userId")Long userId,@Param("id") Long houseId,@Param("type") Integer integer);
	//用户与房产绑定方法
	public int insertHouseUser(HouseUser houseUser);
	//查询关联经纪人
	public HouseUser selectSaleHouseUser(Long id);
	//用户留言方法
	public int insertUserMsg(UserMsg userMsg);
	//房产评分
	public void updateHouse(House updateHouse);
	//设置房产收藏状态为假
	public  int downHouse(Long id);
	//收藏删除房产与用户绑定
	public int deleteHouseUser(@Param("id")Long id,@Param("userId") Long userId,@Param("type") Integer value);
	

	
	
}
