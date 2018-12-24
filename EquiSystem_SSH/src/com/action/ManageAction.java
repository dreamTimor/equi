package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.bean.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.LabEquiService;
import com.service.ManageService;
import com.utils.JsonDateValueProcessorUtil;

@Controller
@Scope("prototype")
public class ManageAction extends ActionSupport implements ModelDriven<User>{
//	Service层
	@Autowired
	private ManageService serviceManage;
//	json数据用于给页面返回值	
	JSONObject result = new JSONObject();
	public JSONObject getResult(){
		return result;
	}
//	页面接收数据
	public User user = new User();
	public String page;
	public String rows;
	public String queryData;
	@Override
	public User getModel() {
		return user;
	}
	
/*------------------------叶雄峰------------------------------*/	
	
	public String getList(){
		List<User> labs;
		int size;
		if(queryData!=null){
			labs = serviceManage.query(page, rows, queryData);
			size = serviceManage.getListSize();
		}else {
			labs = serviceManage.getList(page, rows);
			size = serviceManage.getListSize();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", size);
		map.put("rows", labs);
		
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Data.class, new JsonDateValueProcessorUtil());
		result = JSONObject.fromObject(map, config);
		return SUCCESS;
	}
	
	public String add(){
		serviceManage.add(user);
		return SUCCESS;
	}
	
	public String delete(){
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		serviceManage.delete(id);
		
		return SUCCESS;
	}
	
	public String update(){
		serviceManage.update(user);
		return SUCCESS;
	}
	
	// 用户修改密码
	public String updatePwd() {
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String pwd = request.getParameter("password");
		User user = (User) session.getAttribute("user");
		int i = serviceManage.updatePwd(pwd,user.getId());
		Map<String, Object> map = new HashMap<String, Object>();
		if(i > 0) {
			map.put("success", 1);
			result = JSONObject.fromObject(map);
		}
		return SUCCESS;
	}
	
	
}
