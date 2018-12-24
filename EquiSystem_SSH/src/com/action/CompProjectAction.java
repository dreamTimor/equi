package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Controller;

import com.bean.Comp_project;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.CompProjectService;
import com.utils.JsonDateValueProcessorUtil;

@Controller
@Scope("prototype")
public class CompProjectAction extends ActionSupport implements ModelDriven<Comp_project>{
//	网页数据交互
	private Comp_project comp = new Comp_project();
	private JSONObject result = null;
	public String page;
	public String rows;
	public String queryData;
	
//	Service层
	@Autowired
	private CompProjectService servicecomp;
	
	
	Map<String, Object> map = new HashMap<String, Object>();
/*-----------------------叶雄峰--------------------------*/
	
	
	public String getList(){
		List<Comp_project> comps;
		int size;
		if(queryData != null){
			comps = servicecomp.query(queryData);
			size = comps.size();
		}else{
			comps = servicecomp.getList(page, rows);
			size = servicecomp.getListSize();
		}
		
		map.put("total", size);
		map.put("rows", comps);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());
		result = JSONObject.fromObject(map, jsonConfig);
		return SUCCESS;
	}
	
	public String add(){
		map.put("s", servicecomp.add(comp));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	public String update(){
		map.put("s", servicecomp.update(comp));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	public String delete(){
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		
		map.put("s", servicecomp.delete(id));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	
	
	public JSONObject getResult(){
		return result;
	}
	@Override
	public Comp_project getModel() {
		return comp;
	}
	
}
