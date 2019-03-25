package com.business.struts2.web.interceptor;

import com.business.struts2.exception.MySQLException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 我的自定义异常拦截器
 * 
 * 
 * 
 */
public class MyExceptionInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		try {
			return ai.invoke();
		} catch (MySQLException e) {
			// 捕获自定义异常
			// 记录日志 log4j
			// 跳转优化页面
			ActionSupport action = (ActionSupport) ai.getAction();
			action.addActionError(e.getMessage());
			return "error";
		}
	}

}
