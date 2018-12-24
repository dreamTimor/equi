package com.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bean.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.UserService;

@Controller
@Scope("prototype")
public class LoginAction extends ActionSupport implements ModelDriven<User>{
//	使用属性驱动，获取表单信息
	private User preuser = new User();
	@Override
	public User getModel() {
		return preuser;
	}
//	Service层
	@Autowired
	private UserService userService;
	
/*--------------------------叶雄峰--------------------------*/
	
	
//	登录
	public String login()throws SQLException{
//		从页面获取信息传值给Service层做业务判断
		String str = userService.loginService(preuser.getUsername(), preuser.getPassword());
		return str;
	}
	
//	退出登录
	public String loginOut() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getSession().getAttribute("user") == null){
			try {
				throw new Exception("登录已超时！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		invalidate清空Session里的所有对象
		request.getSession().invalidate();
		return "error";
	}
}
