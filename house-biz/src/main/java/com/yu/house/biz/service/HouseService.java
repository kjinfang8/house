package com.yu.house.biz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.yu.house.biz.mapper.HouseMapper;
import com.yu.house.common.model.Community;
import com.yu.house.common.model.House;
import com.yu.house.common.page.PageData;
import com.yu.house.common.page.PageParams;

@Service
public class HouseService {
	
	@Autowired
	private HouseMapper houseMapper;
	@Value("${file.prefix}")
	private String imgPrefix;
	/**
	 * 1.查询小区
	 * 2.添加图片服务器地址前缀
	 * 3.构建分页结果
	 * @param query
	 * @param build
	 */
	public PageData<House> queryHouse(House query, PageParams pageParams) {
		List<House> houses = Lists.newArrayList();
		if (!Strings.isNullOrEmpty(query.getName())) {
			Community community = new Community();
			//获取小区属性名称
			community.setName(query.getName());
			//查询小区
			List<Community> communities = houseMapper.selectCommunity(community);
			if (!communities.isEmpty()) {
				//用户设置小区
				query.setCommunityId(communities.get(0).getId());
			}
		}
		houses = queryAndSetImg(query,pageParams);//添加图片服务器地址前缀
		Long count = houseMapper.selectPageCount(query);
		return PageData.buildPage(houses, count, pageParams.//
				getPageSize(), pageParams.getPageNum());
	}
	
	/**
	 * 添加图片服务器地址前缀
	 * @param query
	 * @param pageParams
	 * @return
	 */
	public List<House> queryAndSetImg(House query, PageParams pageParams) {
		//查询一下房屋分页
		List<House> houses = houseMapper.selectPageHouses(query,pageParams);
		//迭代遍历这个集合
		houses.forEach(h ->{
			h.setFirstImg(imgPrefix + h.getFirstImg());
			h.setImageList(h.getImageList().stream()//
					.map(img -> imgPrefix + img).collect(Collectors.toList()));
			h.setFloorPlanList(h.getFloorPlanList().stream()//
					.map(img ->imgPrefix + img)//
					.collect(Collectors.toList()));
		});
		return houses;
	}
	
	
}
