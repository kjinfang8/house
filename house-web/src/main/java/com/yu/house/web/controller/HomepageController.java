package com.yu.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yu.house.biz.service.RecommendService;
import com.yu.house.common.model.House;

/**
 * 首页房产推荐
 * 
 * @author jinfang
 *
 */
@Controller
public class HomepageController {

	@Autowired
	private RecommendService recommendService;

	@RequestMapping("index")
	public String accountsRegister(ModelMap modelMap) {
		// 房产推荐
		List<House> houses = recommendService.getLastest();
		modelMap.put("recomHouses", houses);
		return "/homepage/index";
	}
	//地址为""默认是首页
	@RequestMapping("")
	public String index(ModelMap modelMap) {
		return "redirect:/index";
	}

}
