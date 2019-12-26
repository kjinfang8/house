package com.yu.house.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yu.house.biz.mapper.CommentMapper;
import com.yu.house.common.model.Comment;
import com.yu.house.common.model.User;

@Service
public class CommentService {
	
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private UserService userService;
	
	public List<Comment> getHouseComments(Long houseId, int size) {
		//通过id查找对应的评价内容
		List<Comment> comments = commentMapper.selectComments(houseId,size);
		comments.forEach(comment ->{
			User user = userService.getUserById(comment.getUserId());
			comment.setAvatar(user.getAvatar());
			comment.setUserName(user.getName());
		});
		return comments;
	}
	
}
