package com.yu.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yu.house.biz.service.AgencyService;
import com.yu.house.biz.service.CityService;
import com.yu.house.biz.service.CommentService;
import com.yu.house.biz.service.HouseService;
import com.yu.house.biz.service.RecommendService;
import com.yu.house.common.constants.CommonConstants;
import com.yu.house.common.constants.HouseUserType;
import com.yu.house.common.model.Comment;
import com.yu.house.common.model.House;
import com.yu.house.common.model.HouseUser;
import com.yu.house.common.model.User;
import com.yu.house.common.model.UserMsg;
import com.yu.house.common.page.PageData;
import com.yu.house.common.page.PageParams;
import com.yu.house.common.result.ResultMsg;
import com.yu.house.web.interceptor.UserContext;

@Controller
public class HouseController {

	@Autowired
	private HouseService houseService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private CityService cityService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private AgencyService agencyService;

	/**
	 * 1.实现分页 2.支持小区搜索、类型搜索 3.支持排序 4.支持展示图片、价格、标题、地址等信息
	 * 
	 * @return
	 */
	@RequestMapping("/house/list")
	public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap) {
		PageData<House> ps = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
		// 热门房产显示
		List<House> hotHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", hotHouses);
		modelMap.put("ps", ps);
		modelMap.put("vo", query);
		return "house/listing";
	}

	///////////////////////////////////////
	/**
	 * 填写添加房产表单
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/house/toAdd")
	public String toAdd(ModelMap modelMap) {
		modelMap.put("citys", cityService.getAllCitys());
		modelMap.put("communitys", houseService.getAllCommunitys());
		return "/house/add";//
	}

	@RequestMapping("/house/add")
	public String doAdd(House house) {
		User user = UserContext.getUser();
		house.setState(CommonConstants.HOUSE_STATE_UP);
		houseService.addHouse(house, user);
		return "redirect:/house/ownlist";
	}

	// 重定向到自己房产页面
	@RequestMapping("house/ownlist")
	public String ownlist(House house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
		User user = UserContext.getUser();
		house.setUserId(user.getId());
		house.setBookmarked(false);
		modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
		modelMap.put("pageType", "own");
		return "/house/ownlist";
	}

	/**
	 * 查询房屋详情 查询关联经纪人
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("house/detail")
	public String houseDetail(Long id, ModelMap modelMap) {
		// 查询房屋
		House house = houseService.queryOneHouse(id);
		// 获取用户与房屋绑关系：查询关联经纪人
		HouseUser houseUser = houseService.getHouseUser(id);
		// 增强-房产列表分页查询显示（热门房产显示）
		recommendService.increase(id);
		// 获取房屋评价内容
		List<Comment> comments = commentService.getHouseComments(id, 8);
		if (houseUser.getUserId() != null && !houseUser.getUserId().equals(0)) {
			// agencyService代理服务,访问user表获取详情
			modelMap.put("agent", agencyService.getAgentDeail(houseUser.getUserId()));
		}
		// 分页查询
		List<House> reHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		// 封装到modelMap对象中
		modelMap.put("recomHouses", reHouses);
		modelMap.put("house", house);
		modelMap.put("commentList", comments);
		// 给经纪人留言使用，留言后，重定向到该页面：带着经纪人id
		UserMsg msg = new UserMsg();
		msg.setHouseId(id);
		return "/house/detail";
	}

	/**
	 * 显示房屋详细信息-给经纪人留言
	 */
	@RequestMapping("house/leaveMsg")
	public String houseMsg(UserMsg userMsg) {
		houseService.addUserMsg(userMsg);
		return "redirect:/house/detail?id=" + userMsg.getHouseId() + "&" + ResultMsg.successMsg("留言成功").getUrlParams();
	}
/////////////////////////////////////////////////////////////

	// 评分、收藏、删除收藏、收藏列表

	// 1.评分
	@ResponseBody
	@RequestMapping("house/rating")
	public ResultMsg houseRate(Double rating, Long id) {
		houseService.updateRating(id, rating);
		return ResultMsg.successMsg("ok");
	}

	// 2.收藏
	@ResponseBody
	@RequestMapping("house/bookmark")
	public ResultMsg bookmark(Long id) {
		User user = UserContext.getUser();
		houseService.bindUser2House(id, user.getId(), true);
		return ResultMsg.successMsg("ok");
	}

	// 3.删除收藏
	@ResponseBody
	@RequestMapping("house/unbookmark")
	public ResultMsg unbookmark(Long id) {
		User user = UserContext.getUser();
		houseService.unbindUser2House(id, user.getId(), HouseUserType.BOOKMARK);
		return ResultMsg.successMsg("ok");
	}

	/**
	 * 删除收藏后刷新自己的页面
	 */
	@RequestMapping(value = "house/del")
	public String delsale(Long id, String pageType) {
		User user = UserContext.getUser();
		houseService.unbindUser2House(id, user.getId(), pageType.equals("own")//
				? HouseUserType.SALE
				: HouseUserType.BOOKMARK);
		return "redirect:/house/bookmarked";
	}

	// 4.收藏列表
	@RequestMapping("house/bookmarked")
	public String bookmarked(House house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
		User user = UserContext.getUser();
		house.setBookmarked(true);
		house.setUserId(user.getId());
		modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
		modelMap.put("pageType", "book");
		return "/house/ownlist";
	}
}
