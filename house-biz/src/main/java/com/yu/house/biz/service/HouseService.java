package com.yu.house.biz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.yu.house.biz.mapper.HouseMapper;
import com.yu.house.common.constants.HouseUserType;
import com.yu.house.common.model.Community;
import com.yu.house.common.model.House;
import com.yu.house.common.model.HouseUser;
import com.yu.house.common.model.User;
import com.yu.house.common.model.UserMsg;
import com.yu.house.common.page.PageData;
import com.yu.house.common.page.PageParams;
import com.yu.house.common.utils.BeanHelper;

@Service
public class HouseService {

	@Autowired
	private HouseMapper houseMapper;
	@Value("${file.prefix}")
	private String imgPrefix;
	@Autowired
	private FileService fileService;
	@Autowired
	private AgencyService agencyService;
	@Autowired
	private EmailService mailService;
	
	/**
	 * 1.查询小区 2.添加图片服务器地址前缀 3.构建分页结果
	 * 
	 * @param query
	 * @param build
	 */
	public PageData<House> queryHouse(House query, PageParams pageParams) {
		List<House> houses = Lists.newArrayList();
		if (!Strings.isNullOrEmpty(query.getName())) {
			Community community = new Community();
			// 获取小区属性名称
			community.setName(query.getName());
			// 查询小区
			List<Community> communities = houseMapper.selectCommunity(community);
			if (!communities.isEmpty()) {
				// 用户设置小区
				query.setCommunityId(communities.get(0).getId());
			}
		}
		houses = queryAndSetImg(query, pageParams);// 添加图片服务器地址前缀
		Long count = houseMapper.selectPageCount(query);
		return PageData.buildPage(houses, count, pageParams.//
				getPageSize(), pageParams.getPageNum());
	}

	/**
	 * 添加图片服务器地址前缀
	 * 
	 * @param query
	 * @param pageParams
	 * @return
	 */
	public List<House> queryAndSetImg(House query, PageParams pageParams) {
		// 查询一下房屋分页
		List<House> houses = houseMapper.selectPageHouses(query, pageParams);
		// 迭代遍历这个集合
		houses.forEach(h -> {
			h.setFirstImg(imgPrefix + h.getFirstImg());
			h.setImageList(h.getImageList().stream()//
					.map(img -> imgPrefix + img).collect(Collectors.toList()));
			h.setFloorPlanList(h.getFloorPlanList().stream()//
					.map(img -> imgPrefix + img)//
					.collect(Collectors.toList()));
		});
		return houses;
	}

	/**
	 * 获取所有小区
	 * 
	 * @return
	 */
	public List<Community> getAllCommunitys() {
		Community community = new Community();
		return houseMapper.selectCommunity(community);
	}

	/**
	 * 添加房屋图片 添加户型图片 插入房产信息 绑定用户到房产的关系
	 * 
	 * @param house
	 * @param user
	 */
	public void addHouse(House house, User user) {
		// 添加房屋图片
		if (CollectionUtils.isNotEmpty(house.getHouseFiles())) {
			String images = Joiner.on(",").join(fileService.getImgePaths(house.getHouseFiles()));
			house.setImages(images);
		}
		// 添加户型图片
		if (CollectionUtils.isNotEmpty(house.getFloorPlanFiles())) {
			String images = Joiner.on(",").join(fileService.getImgePaths(house.getFloorPlanFiles()));
			house.setFloorPlan(images);
		}
		BeanHelper.onInsert(house);
		// 插入房产信息
		houseMapper.insert(house);
		// 绑定用户到房产的关系
		bindUser2House(house.getId(), user.getId(), false);
	}

	/**
	 * 绑定用户到房产的关系
	 * 
	 * @param id
	 * @param id2
	 * @param b
	 */
	public void bindUser2House(Long houseId, Long userId, boolean collect) {
		HouseUser existhouseUser = houseMapper.selectHouseUser(userId, houseId,
				collect ? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value);
		if (existhouseUser != null) {
			return;
		}
		HouseUser houseUser = new HouseUser();
		houseUser.setHouseId(houseId);
		houseUser.setUserId(userId);
		houseUser.setType(collect ? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value);
		BeanHelper.setDefaultProp(houseUser, HouseUser.class);
		BeanHelper.onInsert(houseUser);
		houseMapper.insertHouseUser(houseUser);//开始绑定
	}
	/**
	 * 详细房屋查询
	 * @param id
	 * @return
	 */
	public House queryOneHouse(Long id) {
		House query = new House();
		query.setId(id);
		List<House> houses = queryAndSetImg(query, PageParams.build(1, 1));
		if(!houses.isEmpty()) {
			return houses.get(0);
		}
		return null;
	}
	/**
	 * 查询关联经纪人
	 * 获取经纪人与房屋绑关系
	 * @param houseId
	 * @return
	 */
	public HouseUser getHouseUser(Long houseId) {
		HouseUser houseUser =  houseMapper.selectSaleHouseUser(houseId);
		return houseUser;
	}
	/**
	 * 用户留言方法
	 * @param userMsg
	 */
	public void addUserMsg(UserMsg userMsg) {
		BeanHelper.onInsert(userMsg);
		houseMapper.insertUserMsg(userMsg);
		User agent = agencyService.getAgentDeail(userMsg.getAgentId());
		//发送邮件
		mailService.sendMail("来自用户"+userMsg.getEmail()+"的留言",//
				userMsg.getMsg(), agent.getEmail());
	}

}
