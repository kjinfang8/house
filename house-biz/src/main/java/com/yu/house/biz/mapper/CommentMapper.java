package com.yu.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yu.house.common.model.Comment;

@Mapper
public interface CommentMapper {
	
	//获取房屋评价内容
	List<Comment> selectComments(@Param("houseId")Long houseId, @Param("size")int size);
	
	
	
}
