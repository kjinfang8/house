//package com.yu.house.biz.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.alibaba.druid.filter.Filter;
//import com.alibaba.druid.filter.stat.StatFilter;
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.google.common.collect.Lists;
//@Configuration
//public class DruidConfig {
//	/**
//	 * 创建datasource-bean
//	 * @return
//	 */
//	@ConfigurationProperties(prefix = "spring.druid")//自动将druid文件写入到dataSource中
//	@Bean(initMethod = "init",destroyMethod = "close")
//	public DruidDataSource dataSource() {
//		DruidDataSource dataSource = new DruidDataSource();
//		//Lists---需要guava
//		dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
//		return dataSource;
//	}
//	/**
//	 * 创建拦截器
//	 * @return
//	 */
//	@Bean
//	public Filter statFilter() {
//		StatFilter statFilter = new StatFilter();
//		statFilter.setSlowSqlMillis(1);//时间
//		statFilter.setLogSlowSql(true);//打印日志
//		statFilter.setMergeSql(true);//合并日志
//		return statFilter;
//	}
//	/**
//	 * 添加监控
//	 * @return
//	 */
//	@Bean
//	public ServletRegistrationBean ServletRegistrationBean() {
//		return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
//	}
//}
