package com.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.catalina.connector.Request;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bean.Book;
import com.bean.Book_borrow;
import com.bean.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.BookBorrowService;
import com.utils.JsonDateValueProcessorUtil;

@Controller
@Scope("prototype")
public class BookBorrowAction extends ActionSupport implements ModelDriven<Book_borrow>{
//	service层
	@Autowired
	private BookBorrowService serviceBookBorrow;
//	JSON数据交互
	private JSONObject result = null;
	public JSONObject getResult(){
		return result;
	}
//	接收页面数据
	public String page;
	public String rows;
	public String Data;
	public Book_borrow bookborrow=new Book_borrow();
	@Override
	public Book_borrow getModel() {
		return bookborrow;
	}
	Map<String, Object> map = new HashMap<String, Object>();
	
	
	public String getList(){
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		List<Book_borrow> books;
		int total;
		
		if(Data != null){
			System.out.println("查询："+Data);
			books = serviceBookBorrow.query(page, rows, Data, user);
			total = serviceBookBorrow.getSize();
		}else{
			books = serviceBookBorrow.getList(page, rows, user);
			total = serviceBookBorrow.getSize();
		}
		
		map.put("total", total);
		map.put("rows", books);
//		配置格式化样式  + 转换测试
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());
		System.out.println("map：" + JSONObject.fromObject(map, jsonConfig));
//		转换
		result = (JSONObject.fromObject(map, jsonConfig));
		return SUCCESS;
	}
	
	public String delete(){
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		int id = Integer.parseInt( request.getParameter("id") );
		serviceBookBorrow.delete(id);
		
		map.put("s", 1);
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}

	public String update_yes(){
		map.put("s", serviceBookBorrow.update_yes(bookborrow));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	public String update_no(){
		map.put("s", serviceBookBorrow.update_no(bookborrow));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	public String update_return(){
		map.put("s", serviceBookBorrow.update_return(bookborrow));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	
	
	
	
	/**
	 * service层返回数据，通过result传给jsp页面，1成功，0失败
	 * @return
	 */
	public String add(){
		map.put("s", serviceBookBorrow.add(bookborrow));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	
}
