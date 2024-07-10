package com.moneymanager.anukya.configurations.interceptors;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.moneymanager.anukya.utils.AnukyaConstants;

public class AnukyaInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		// Adding custom fields for logging.
		ThreadContext.put(AnukyaConstants.TRACE_ID, UUID.randomUUID().toString());
		ThreadContext.put(AnukyaConstants.USER_ID, request.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID));

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// Removing custom fields for logging.
		ThreadContext.clearMap();
	}
}
