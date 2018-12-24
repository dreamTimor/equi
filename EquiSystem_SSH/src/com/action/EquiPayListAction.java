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

import com.bean.Equi_borrow;
import com.bean.Equi_paylist;
import com.bean.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.EquiPayListService;
import com.utils.JsonDateValueProcessorUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class EquiPayListAction extends ActionSupport implements ModelDriven<Equi_paylist>{

	@Autowired
	private EquiPayListService equiPayListService;
	
	private Equi_paylist paylist = new Equi_paylist();
	
	private String rows;// 每页显示的记录数
	private String page;// 当前第几页
	private String queryData;// 查询框数据
	
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	
	public String getQueryData() {
		return queryData;
	}
	public void setQueryData(String queryData) {
		this.queryData = queryData;
	}


	private JSONObject result;
	public JSONObject getResult() {
		return result;
	}
	public void setResult(JSONObject result) {
		this.result = result;
	}
	
	/**
	  *  分页查询预采购列表
	 * @param  int uid
	 * @return List<Equi_paylist>
	 * @author cxg
	 * @throws UnsupportedEncodingException 
	 */
	//分页查询预采购列表
	public String listEquiPaylist() throws UnsupportedEncodingException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user = (User) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Equi_paylist> list = null ;
		int total = 0;
		
		System.out.println("user角色========================"+user.getRole());
		if(queryData == null) {   //查询条件为空的时候
			if(user.getRole()==1) {
				list = equiPayListService.listAllPaylist(page, rows); //所有老师的预采购列
				total = equiPayListService.getAllPayListSize(); //所有老师的预采购条数总计
			}
			else {
				list = equiPayListService.getPayList(page,rows,user.getId());
				total = equiPayListService.getPayListSize(user.getId());//获取某个老师申请的预采购条数总计！
			}
		}
		else {
			System.out.println("进入了查询=================================");
			list = equiPayListService.query(page, rows, URLDecoder.decode(queryData, "UTF-8"), user);
			total = equiPayListService.querysize();
			System.out.println("查询的条数："+total);
		}
		
		//System.out.println("total============="+total);
		
		if(list!=null) {
			map.put("total", total);  //某个老师申请的预采购条数总计
			map.put("rows", list);			
			JsonConfig jsonConfig = new JsonConfig();                                                          
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());    
			this.setResult(JSONObject.fromObject(map,jsonConfig));
		}
		return SUCCESS;
	}
	
	
	//添加预采购
	public String addEquiPaylist() {
		equiPayListService.addEquiPayList(paylist);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("success", "success!!!");
		this.setResult(JSONObject.fromObject(json));
		System.out.println(JSONObject.fromObject(json));
		return SUCCESS;
	}
	
	//修改预采购
	public String updatePaylist() {
		equiPayListService.updatePaylist(paylist);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("success", "success!!!");
		this.setResult(JSONObject.fromObject(json));
		System.out.println(JSONObject.fromObject(json));
		return SUCCESS;
	}
	
	//删除预采购
	public String deletePaylist() {	
		HttpServletRequest request = ServletActionContext.getRequest();
		// 获取 jsp 传过来的id
		int id = Integer.parseInt(request.getParameter("id"));
		paylist = equiPayListService.getPayById(id);
		equiPayListService.deleteProduct(paylist);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("success", "success!!!");
		this.setResult(JSONObject.fromObject(json));
		System.out.println(JSONObject.fromObject(json));
		return SUCCESS;
	}
	

	@Override
	public Equi_paylist getModel() {
		// TODO Auto-generated method stub
		return paylist;
	}
	
	
	
	
}
