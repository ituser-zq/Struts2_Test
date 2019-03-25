package com.business.struts2.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 登陆校验拦截器
 * 
 * 
 * 
 */
public class LoginInterceptor extends AbstractInterceptor {

	private String excludeMethods;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		// 判断session中是否含有 user对象
		if (ServletActionContext.getRequest().getSession().getAttribute("user") == null) {
			// 未登陆 ---- 判断是否配置放行
			if (excludeMethods != null) {
				String[] methods = excludeMethods.split(",");
				// 判断methods（放行数组） 是否包含当前 访问
				String currentMethod = ActionContext.getContext().getName(); // user_login
				for (String method : methods) {
					if (method.equals(currentMethod)) {
						// 当前method 被配置放行
						return actionInvocation.invoke();
					}
				}
			}

			// 设置错误信息，ActionSupport提供方法
			ActionSupport action = (ActionSupport) actionInvocation.getAction();
			action.addActionError(action.getText("nologin"));
			return "login";// 登陆视图
		} else {
			// 已经登陆
			return actionInvocation.invoke();
		}
	}

	public void setExcludeMethods(String excludeMethods) {
		this.excludeMethods = excludeMethods;
	}

}
