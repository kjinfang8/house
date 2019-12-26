package com.yu.house.biz.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.yu.house.common.model.House;
import com.yu.house.common.page.PageParams;

import redis.clients.jedis.Jedis;

/**
 * 房产列表分页查询显示
 * 
 * @author jinfang
 *
 */
@Service
public class RecommendService {

	private static final String HOT_HOUSE_KEY = "house";
	private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);

	@Autowired
	private HouseService houseService;

	/**
	 * 获取房产列表
	 * 
	 * @param recomSize
	 * @return
	 */
	public List<House> getHotHouse(Integer size) {
		House query = new House();
		List<Long> list = getHot();// 发现这里报错
		// 热门房产处理
		list = list.subList(0, Math.min(list.size(), size));
		if (list.isEmpty()) {
			return Lists.newArrayList();
		}
		query.setIds(list);
		final List<Long> order = list;
		List<House> houses = houseService.queryAndSetImg(query, PageParams.build(size, 1));
		// 热门房产处理
		Ordering<House> houseSort = Ordering.natural().onResultOf(hs -> {
			return order.indexOf(hs.getId());
		});
		return houseSort.sortedCopy(houses);
	}

	/**
	 * 获取主机
	 * 
	 * @return
	 */
	private List<Long> getHot() {
		try {
			Jedis jedis = new Jedis("127.0.0.1");
			Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY, 0, -1);
			jedis.close();
			List<Long> ids = idSet.stream().map(Long::parseLong)//
					.collect(Collectors.toList());
			return ids;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);// 有同学反应在未安装redis时会报500,在这里做下兼容,
			return Lists.newArrayList();
		}
	}

	/**
	 * 增强-房产列表分页查询显示
	 * 
	 * @param id
	 */
	public void increase(Long id) {
		try {
			Jedis jedis = new Jedis("127.0.0.1");// 连接redis服务器地址
			jedis.zincrby(HOT_HOUSE_KEY, 1.0D, id + "");
			// 0代表第一个元素,-1代表最后一个元素，保留热度由低到高末尾10个房产
			jedis.zremrangeByRank(HOT_HOUSE_KEY, 0, -11);
			jedis.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 首页房产推荐
	 * 
	 * @return
	 */
	public List<House> getLastest() {
		House query = new House();
		query.setSort("create_time");
		List<House> houses = houseService.queryAndSetImg(query, new PageParams(8, 1));
		return houses;
	}

}
