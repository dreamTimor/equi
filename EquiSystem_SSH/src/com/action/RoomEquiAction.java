package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.bean.Room_equi;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.RoomEquiService;
import com.utils.JsonDateValueProcessorUtil;

@Controller
@Scope("prototype")
public class RoomEquiAction extends ActionSupport implements ModelDriven<Room_equi>{
	
	private JSONObject result = null;
	private Room_equi roomEqui = new Room_equi();
	public String queryData;
	public String page;
	public String rows;
	Map<String, Object> map = new HashMap<String, Object>();
//	Service层
	@Autowired
	private RoomEquiService serviceRoomEqui;
	
	
	
	public String getList(){
		List<Room_equi> roomEquis;
		int total;
		if(queryData != null){
			roomEquis = serviceRoomEqui.query(queryData);
			total = roomEquis.size();
		}else{
			roomEquis = serviceRoomEqui.getList(page, rows);
			total = serviceRoomEqui.getListSize();
		}
		
		map.put("total", total);
		map.put("rows", roomEquis);
		
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());
		result = JSONObject.fromObject(map, config);
		return SUCCESS;
	}
	
	public String add(){
		System.out.println("添加："+roomEqui);
		serviceRoomEqui.add(roomEqui);
		return SUCCESS;
	}
	public String update(){
		System.out.println("修改："+roomEqui);
		serviceRoomEqui.update(roomEqui);
		return SUCCESS;
	}
	public String delete(){
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		
		map.put("s", serviceRoomEqui.delete(id));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	
	
	

	
	public JSONObject getResult(){
		return result;
	}
	@Override
	public Room_equi getModel() {
		return roomEqui;
	}
}
