package com.ja.finalproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

public class AuthInterceptor extends WebContentInterceptor{

	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws ModelAndViewDefiningException {
		
		// System.out.println("인터셉터 로직 수행...");
		// 로그인 하지 않았으면... 로그인이 필요한 페이지임을 알리자...
		
		if(request.getSession().getAttribute("sessionUser") == null) {
			
			ModelAndView mv = new ModelAndView();
			mv.setViewName("member/loginRequired");
			
			throw new ModelAndViewDefiningException(mv);
			
		}
		
		return true;
	}
	
}
