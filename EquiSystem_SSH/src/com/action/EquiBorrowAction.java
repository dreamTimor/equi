package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.bean.Equi_borrow;
import com.bean.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.EquiBorrowService;
import com.utils.JsonDateValueProcessorUtil;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class EquiBorrowAction extends ActionSupport implements ModelDriven<Equi_borrow> {
	private EquiBorrowService equiBorrowService;
	private String page;
	private String rows;
	private Equi_borrow preEqui_borrow = new Equi_borrow();
	private JSONObject result; // 第三方的jar包json对象，设置get/set方法，必须
	public String queryData;
	HttpSession session = ServletActionContext.getRequest().getSession();

	// 添加设备借用记录
	public String add() {
		//System.out.println("preEqui_borrow:"+preEqui_borrow);
		equiBorrowService.addEquiBorrow(preEqui_borrow);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("success", 1);
		this.setResult(JSONObject.fromObject(json));
		System.out.println(JSONObject.fromObject(json));
		return SUCCESS;
	}

	// 根据 id 删除设备借用记录
	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();
		// 获取 jsp 传过来的id
		int id = Integer.parseInt(request.getParameter("id"));
		// 根据 id 删除设备借用记录
		int i = equiBorrowService.delEquiBorrowById(id);
		Map<String, Object> json = new HashMap<String, Object>();
		if(i>0) {
			json.put("success", 1);
			this.setResult(JSONObject.fromObject(json));
		}else {
			json.put("success", 0);
			this.setResult(JSONObject.fromObject(json));
		}
		return SUCCESS;
	}
	
	// 修改设备借用记录
	public String update() {
		System.out.println("preEqui_borrow:"+preEqui_borrow);
		equiBorrowService.updateEquiBorrowById(preEqui_borrow);
		Map<String, Object> json = new HashMap<String, Object>();
		// success=success!!! 放到 json 中返回到 jsp
		json.put("success", "success!!!");
		this.setResult(JSONObject.fromObject(json));
		return SUCCESS;
	}

	// 查询指定用户的设备借用记录
	public String getListByUser() throws Exception {
		User user = (User) session.getAttribute("user");  // 获取 session 中的 user
		Map<String, Object> map = new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		List<Equi_borrow> list = null;
		if(queryData != null) { // 用户输入查询信息不为空
			list = equiBorrowService.query(queryData, page, rows, user);
		}else { // 为空
			list = equiBorrowService.getAllByUid(user,page,rows);
		}
		int total = equiBorrowService.getAllCount(); // 获取集合的总记录条数
		map.put("total", total);
		map.put("rows", list);
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());
		int i = (int) map.get("total"); // 获取 total 的记录条数
		if(i > 0) map.put("success", 1);
			else map.put("success", 0);
		this.setResult(JSONObject.fromObject(map, jsonConfig));
		return SUCCESS;
	}	
	
	// 归还设备
	public String returnEqui() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int equiId = Integer.parseInt(request.getParameter("id"));
		int i = equiBorrowService.returnEqui(equiId);
		Map<String,Object> map = new HashMap<>();
		//System.out.println("i:"+i);
		if(i>0) {
			map.put("success", 1);
			this.setResult(JSONObject.fromObject(map));
		}else {
			map.put("success", 0);
			this.setResult(JSONObject.fromObject(map));
		}
		return SUCCESS;
	}
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	@Override
	public Equi_borrow getModel() {
		return preEqui_borrow;
	}

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public void setEquiBorrowService(EquiBorrowService equiBorrowService) {
		this.equiBorrowService = equiBorrowService;
	}

	public Equi_borrow getPreEqui_borrow() {
		return preEqui_borrow;
	}

	public void setPreEqui_borrow(Equi_borrow preEqui_borrow) {
		this.preEqui_borrow = preEqui_borrow;
	}

}
