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

import com.bean.Course_table;
import com.bean.Lab_bespeak;
import com.bean.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.CourseTableService;
import com.utils.JsonDateValueProcessorUtil;

@Controller
@Scope("prototype")
public class CourseTableActoin extends ActionSupport implements ModelDriven<Course_table>{
//	用于传递数据：变量名与action里的param对应
	private JSONObject result = null;
	public JSONObject getResult(){
		return result;
	}
	public void setResult(JSONObject result){
		this.result = result;
	}
//	service层
	@Autowired
	private CourseTableService courseTableService;
//	模型驱动：封装前端传过来的数据
	private Course_table course = new Course_table();
	@Override
	public Course_table getModel() {
		return course;
	}
	public String rows;// 每页显示的记录数
	public String page;// 当前第几页
	public String queryData;// 查询框数据

/*----------------------------叶雄峰------------------------------*/
	
	
	
//  前端显示内容调用的方法
	public String getList(){
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");//获取登录用户信息
		List<Course_table> courses;//获取分页后的数据
		int total;//获取总数据数
		
		if(queryData!=null){
			courses = courseTableService.query(page, rows, queryData, user);
			total = courses.size();
		}else{
			courses = courseTableService.getList(page, rows, user);
			total = courseTableService.getListSize();
		}
		
//		将Dao的获取的对象集合放入Map
		Map<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", courses);
//		System.out.println("Action："+courses);
		
//		配置格式化样式  + 转换测试
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());
//		System.out.println("map：" + JSONObject.fromObject(map, jsonConfig));
//		转换
		this.setResult(JSONObject.fromObject(map, jsonConfig));
		
		return SUCCESS;
	}
	
	
//	添加
	public String add(){
//		System.out.println("添加连接测试" + course);
		courseTableService.add(course);
		return SUCCESS;
	}
	
	
//	修改
	public String update(){
//		System.out.println("修改测试："+course);
		courseTableService.update(course);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("s", 1);
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}


//	删除
	public String delete(){
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		courseTableService.delete(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", 1);
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
}
