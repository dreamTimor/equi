package com.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.User;
import com.dao.UserDao;
import com.opensymphony.xwork2.ModelDriven;

@Service
@Scope("prototype")
public class UserService{
//	Dao层获取数据返回数据
	@Autowired
	private UserDao userDao;

	
/*--------------------------叶雄峰--------------------------*/
	
	
	public String loginService(String username, String password)throws SQLException{
		String str;
//		若是非空集合，把用户信息存入session域，并判断角色
		List<User> users = userDao.getUserId(username, password);
		if(users.size() != 0) {
//			获取用户信息
			User user = users.get(0);

//			存入session域，用于后面判断用户是否已登录
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("user", user);
			
//			角色判断
			if(user.getRole() == 1)       // 管理员
				str = "admin";
			else if(user.getRole() == 2)  // 教师
				str = "tea";
			else  				
				str = "stu";			  // 学生
		}else {
			str = "error";
		}
//		给Action返回一个调用对应页面的值
		return str;
	}

	
}
