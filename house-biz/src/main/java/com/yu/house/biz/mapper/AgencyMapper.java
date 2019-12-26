package com.yu.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yu.house.common.model.Agency;
import com.yu.house.common.model.User;
import com.yu.house.common.page.PageParams;

@Mapper
public interface AgencyMapper {

    List<User>	selectAgent(@Param("user")User user,@Param("pageParams") PageParams pageParams);
	//查询关联经纪人
	List<Agency> select(Agency agency);
	//查询经纪人数量
	Long selectAgentCount(@Param("user")User user);

	
	
	
}
