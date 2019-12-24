package com.yu.house.web.filter;
//
//import java.util.ArrayList;
//
//import java.util.List;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author jinfang yu
// */
//@Configuration
//public class FilterBeanConfig {
//	
//	/**
//	 * 1、构造filter
//	 * 2、配置拦截器urlPattern
//	 * 3、利用FilterRegistrationBean进行包装
//	 */
//	@Bean
//	public FilterRegistrationBean<LogFilter> logFilter() {
//		//
//		FilterRegistrationBean<LogFilter> filterRegistrationBean = //
//				new FilterRegistrationBean<LogFilter>();
//		filterRegistrationBean.setFilter(new LogFilter());
//		//url拦截器集合
//		List<String> urlList = new ArrayList<String>();
//		urlList.add("*");
//		//拦截器包装
//		filterRegistrationBean.setUrlPatterns(urlList);
//		return filterRegistrationBean;
//	}
//}
