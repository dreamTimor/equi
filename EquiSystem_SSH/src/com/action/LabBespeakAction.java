package com.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

import com.bean.Lab_bespeak;
import com.bean.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.LabBespeakService;
import com.utils.JsonDateValueProcessorUtil;

@Controller
@Scope("prototype")//每次进来都是一个新实例
public class LabBespeakAction extends ActionSupport implements ModelDriven<Lab_bespeak>{
//	用于转换json数据：变量名与action里的param对应
	private JSONObject result = null;
	public JSONObject getResult(){
		return result;
	}
//	Service层
	@Autowired
	public LabBespeakService serviceLab;
	
//	模型接收前端发出的post数据
	public Lab_bespeak bespeak = new Lab_bespeak();
	public String rows;// 每页显示的记录数
	public String page;// 当前第几页
	public String queryData;
	@Override
	public Lab_bespeak getModel() {
		return bespeak;
	}
/*----------------------------叶雄峰------------------------------*/
	
	
//	页面显示数据
	public String getList() throws UnsupportedEncodingException{
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");//获取登录用户信息
		List<Lab_bespeak> bespeaks;//获取分页后的数据
		int total;//获取总数据数
		
//		如果查询框有值，进入查询功能，否则正常分页
		if(queryData!=null){
			bespeaks = serviceLab.query(page, rows, queryData, user);
			total = serviceLab.getListSize();
		}else{
			bespeaks = serviceLab.getList(page ,rows, user);
			total = serviceLab.getListSize();
		}
//		数据放入map
		Map<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", bespeaks);
		
//		使用JsonConfig转换日期数据
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());
//		转换后放入result传给页面
		result = JSONObject.fromObject(map, jsonConfig);
		return SUCCESS;
	}
	

	
//	添加：模型驱动获取form数据
	public String add(){
//		直接使用对象
		serviceLab.add(bespeak);
		
//		将map转为json返回给页面
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("success", 1);
//		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	
	
//	修改:模型驱动获取form表单信息
	public String update(){
		serviceLab.update(bespeak);
		
		return SUCCESS;
	}
	
	
//	页面删除记录
	public String delete(){
//		转为HttpServletRequest，使用getParameter获取客户端请求数据
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
//		传给service调用dao
		serviceLab.delete(id);
		
//		将map转为json返回给页面
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", 1);
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	
}
