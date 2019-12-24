package com.yu.house.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.yu.house.common.constants.CommonConstants;
import com.yu.house.common.model.User;

/**
 * 拦截器接口
 * @author 18285
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	//在controller执行前执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取到所有的request
		Map<String, String[]> map = request.getParameterMap();
		map.forEach((k,v) -> {
			if (k.equals("errorMsg") || k.equals("successMsg") || k.equals("target")) {
				request.setAttribute(k, Joiner.on(",").join(v));
			}
		});
		String reqUri =	request.getRequestURI();
	    if (reqUri.startsWith("/static") || reqUri.startsWith("/error") ) {
			return true;
		}
	    
	    HttpSession session = request.getSession(true);
	    User user =  (User)session.getAttribute(CommonConstants.USER_ATTRIBUTE);
	    if (user != null) {
			UserContext.setUser(user);
		}
		return true;
	}
	//在controller执行之后执行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
	//页面渲染之后执行的
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserContext.remove();
	}

}
