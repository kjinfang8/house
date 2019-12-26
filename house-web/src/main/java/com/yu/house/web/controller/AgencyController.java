package com.yu.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yu.house.biz.service.AgencyService;
import com.yu.house.biz.service.HouseService;
import com.yu.house.biz.service.RecommendService;
import com.yu.house.common.constants.CommonConstants;
import com.yu.house.common.model.House;
import com.yu.house.common.model.User;
import com.yu.house.common.page.PageData;
import com.yu.house.common.page.PageParams;

/**
 * 经纪人controller
 * 
 * @author jinfang
 *
 */
@Controller
public class AgencyController {

	@Autowired
	private AgencyService agencyService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private HouseService houseService;
	
	
	/**
	 * 获取经纪人列表页面
	 * 
	 * @param pageSize
	 * @param pageNum
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/agency/agentList")
	public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap) {
		if (pageSize == null) {
			pageSize = 6;
		}
		PageData<User> ps = agencyService.getAllAgent(PageParams.build(pageSize, pageNum));
		//热门房产显示
		List<House> houses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", houses);
		modelMap.put("ps", ps);
		return "/user/agent/agentList";
	}
	/**
	 * 加载经纪人数据列表
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/agency/agentDetail")
	public String agentDetail(Long id, ModelMap modelMap) {
		User user = agencyService.getAgentDeail(id);
		List<House> houses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		House query = new House();
		query.setUserId(id);
		query.setBookmarked(false);
		PageData<House> bindHouse = houseService.queryHouse(query, new PageParams(3, 1));
		if (bindHouse != null) {
			modelMap.put("bindHouses", bindHouse.getList());
		}
		modelMap.put("recomHouses", houses);
		modelMap.put("agent", user);
		modelMap.put("agencyName", user.getAgencyName());
		return "/user/agent/agentDetail";
	}
	
	
	
	
}
