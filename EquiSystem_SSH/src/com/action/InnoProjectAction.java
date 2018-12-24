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
import org.springframework.stereotype.Controller;

import com.bean.Inno_project;
import com.mysql.cj.Query;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.InnoProjectService;
import com.utils.JsonDateValueProcessorUtil;

@Controller
@Scope("prototype")
public class InnoProjectAction extends ActionSupport implements ModelDriven<Inno_project>{
//	数据交互
	private JSONObject result = null;
	Map<String, Object> map = new HashMap<String, Object>();
	Inno_project inno_project = new Inno_project();
	public String queryData;
	public String page;
	public String rows;
//	Service层
	@Autowired
	private InnoProjectService serviceInno;
	
	
	public String getList(){
		List<Inno_project> innos;
		int total;
		if(queryData != null){
			innos = serviceInno.query(queryData);
			total = innos.size();
		}else{
			innos = serviceInno.getList(page, rows);
			total = serviceInno.getListSize();
		}
		
		map.put("total", total);
		map.put("rows", innos);
		
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());
		result = JSONObject.fromObject(map, config);
		System.out.println("获取数据：" + result);
		return SUCCESS;
	}
	
	
	public String add(){
		map.put("s", serviceInno.add(inno_project));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	public String update(){
		map.put("s", serviceInno.update(inno_project));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	public String delete(){
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		
		map.put("s", serviceInno.delete(id));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	
	
	
	public JSONObject getResult(){
		return result;
	}
	@Override
	public Inno_project getModel() {
		return inno_project;
	}
}
