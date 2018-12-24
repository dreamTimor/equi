package com.login.interceptor;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.bean.User;

/**
 * 非法登录拦截
 * @author dreamTimor
 *
 */
public class LoginInterceptor extends MethodFilterInterceptor {
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		// 从 session 域中获取 user
		User user = (User) getSession().get(User.class, "user");
		System.out.println("拦截器的："+user);
		// 判断 session 是否存在 user 对象
		if(user == null) { // 用户不存在，则登录失败，返回登录界面
			return "error";
		}
		else {  // 用户存在，登录成功
			System.out.println("username："+user.getName());
			return invocation.invoke();
		}
	}

}
