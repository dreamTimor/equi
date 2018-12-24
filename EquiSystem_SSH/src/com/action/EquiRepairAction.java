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

import com.bean.Equi_repair;
import com.bean.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.EquiRepairService;
import com.utils.JsonDateValueProcessorUtil;

@Controller
@Scope("prototype")
public class EquiRepairAction extends ActionSupport implements ModelDriven<Equi_repair>{	
//	数据交互
	private JSONObject result = null;
	private Equi_repair equiRepair = new Equi_repair();
	public String queryData;
	public String page;
	public String rows;
	Map<String, Object> map = new HashMap<String, Object>();
//	Service层
	@Autowired
	private EquiRepairService serviceEquiRepair;
	
	
	
	public String getList(){
		List<Equi_repair> equis;
		int total;
		if(queryData != null){
			equis = serviceEquiRepair.query(queryData);
			total = equis.size();
		}else{
			equis = serviceEquiRepair.getList(page, rows);
			total = serviceEquiRepair.getListSize();
		}
		map.put("total", total);
		map.put("rows", equis);
		
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());
		result = new JSONObject().fromObject(map, config);
		return SUCCESS;
	}

	public String add(){
		map.put("s", serviceEquiRepair.add(equiRepair));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	public String update(){
		map.put("s", serviceEquiRepair.update(equiRepair));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	public String delete(){
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		map.put("s", serviceEquiRepair.delete(id));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	
	

	
	
	
	@Override
	public Equi_repair getModel() {
		return equiRepair;
	}
	public JSONObject getResult(){
		return result;
	}
}
