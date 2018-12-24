package com.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

import com.bean.Equi_get;
import com.bean.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.EquiGetService;
import com.utils.JsonDateValueProcessorUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class EquiGetAction extends ActionSupport implements ModelDriven<Equi_get>{

	@Autowired
	private EquiGetService equiGetService;
	
	private Equi_get equiGet = new Equi_get();
	
	private Integer rows;// 每页显示的记录数
	private Integer page;// 当前第几页
	private String queryData;// 查询框数据
	private JSONObject result;//返回json
	
	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getQueryData() {
		return queryData;
	}

	public void setQueryData(String queryData) {
		this.queryData = queryData;
	}
	
	public JSONObject getResult() {
		return result;
	}
	public void setResult(JSONObject result) {
		this.result = result;
	}

	
	public String equiGetList() throws UnsupportedEncodingException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user = (User) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Equi_get> list = null;
		int total = 0;
		
		if(queryData==null) {
			list = equiGetService.equiGetList(page, rows);
			total = equiGetService.size();	
		}else {
			System.out.println("action...."+URLDecoder.decode(queryData, "UTF-8"));
			list =equiGetService.query(page, rows, URLDecoder.decode(queryData, "UTF-8"));
			total = equiGetService.size();
		}
		if(list!=null) {
			map.put("total", total);  
			map.put("rows", list);			
			JsonConfig jsonConfig = new JsonConfig();       
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());    
			this.setResult(JSONObject.fromObject(map,jsonConfig));
		}
		return SUCCESS;
	}
	
	public String addEquiGet() {	
		equiGetService.addEquiGet(equiGet);
		Map<String, Object> json = new HashMap<String,Object>();
		json.put("success", "success!!!");
		this.setResult(JSONObject.fromObject(json));
		System.out.println(JSONObject.fromObject(json));
		return SUCCESS;
	}
	public String updateEquiGet() {
		equiGetService.updateEquiGet(equiGet);
		Map<String, Object> json = new HashMap<String,Object>();
		json.put("success", "success!!!");
		this.setResult(JSONObject.fromObject(json));
		System.out.println(JSONObject.fromObject(json));
		return SUCCESS;
	}
	public String deleteEquiGet() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		equiGet = equiGetService.equiGetById(id);
		equiGetService.deleteEquiGet(equiGet);
		Map<String, Object> json = new HashMap<String,Object>();
		json.put("success", "success!!!");
		this.setResult(JSONObject.fromObject(json));
		System.out.println(JSONObject.fromObject(json));
		return SUCCESS;
	}
	

	
	
	
	
	
	@Override
	public Equi_get getModel() {
		// TODO Auto-generated method stub
		return equiGet;
	}

}
