package com.yu.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yu.house.biz.service.UserService;
import com.yu.house.common.model.User;

@Controller
public class HelloController {
  @Autowired
  private UserService userService;
  @RequestMapping("hello")
  public String  hello(ModelMap modelMap){
    List<User> users = userService.getUser();
    User one = users.get(0);
    //模拟500错误
//    if(one == null) {
//    	throw new IllegalArgumentException();
//    }
    modelMap.put("user", one);
    return "hello";
  }
  /**
   * 返回首页
   * @return
   */
  @RequestMapping("index")
  public String index() {
	  return "homepage/index";
  }
}
