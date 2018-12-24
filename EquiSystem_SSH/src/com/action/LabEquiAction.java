package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Controller;

import com.bean.Lab_equi;
import com.bean.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.LabEquiService;
import com.utils.JsonDateValueProcessorUtil;
@Controller
@Scope("prototype")
public class LabEquiAction extends ActionSupport implements ModelDriven<Lab_equi>{
//	JSON数据交互
	public JSONObject result = null;
//	Service层
	@Autowired
	private LabEquiService serviceLab;
//	Struts2模型驱动
	public Lab_equi lab_equi=new Lab_equi();
	public String page;
	public String rows;
	public String queryData;
	
/*-------------------------------叶雄峰-------------------------------------*/
//	显示数据+查询功能
	public String getList(){
		List<Lab_equi> labs;
		int size;
		if(queryData==null || queryData==""){//正常分页数据
			labs = serviceLab.getList(page, rows);
			size = serviceLab.getListSize();
		}else{//查询功能
			labs = serviceLab.query(page, rows, queryData);
			size = serviceLab.getListSize();
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
		serviceLab.add(lab_equi);
		return SUCCESS;
	}
	public String delete(){
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		serviceLab.delete(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", 1);
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	public String update(){
		serviceLab.update(lab_equi);
		return SUCCESS;
	}

	
	

	
	public JSONObject getResult(){
		return result;
	} 
	@Override
	public Lab_equi getModel() {
		return lab_equi;
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
	public String getQueryData() {
		return queryData;
	}
	public void setQueryData(String queryData) {
		this.queryData = queryData;
	}
}
