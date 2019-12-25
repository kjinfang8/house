package com.yu.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yu.house.biz.service.HouseService;
import com.yu.house.biz.service.RecommendService;
import com.yu.house.common.constants.CommonConstants;
import com.yu.house.common.model.House;
import com.yu.house.common.page.PageData;
import com.yu.house.common.page.PageParams;

@Controller
public class HouseController {
	
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private RecommendService recommendService;
	
	/**
	 * 1.实现分页
	 * 2.支持小区搜索、类型搜索
	 * 3.支持排序
	 * 4.支持展示图片、价格、标题、地址等信息
	 * @return
	 */
	@RequestMapping("/house/list")
	public String houseList(Integer pageSize,Integer pageNum,House query,ModelMap modelMap){
	  PageData<House> ps =  houseService.queryHouse(query,PageParams.build(pageSize, pageNum));
	  List<House> hotHouses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
	  modelMap.put("recomHouses", hotHouses);
	  modelMap.put("ps", ps);
	  modelMap.put("vo", query);
	  return "house/listing";
	}
	
}
