package com.yu.house.web.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
///**
// * 拦截器
// * @author jinfang yu
// */
//public class LogFilter implements Filter {
//	
//	private Logger logger = LoggerFactory.getLogger(LogFilter.class);
//	
//	/**
//	 * 拦截方法
//	 */
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, //
//			FilterChain chain) throws IOException, ServletException {
//		logger.info("Request-coming");//请求到了
//		chain.doFilter(request, response);
//	}
//}
