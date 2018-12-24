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

import com.bean.Book;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.BookService;
import com.utils.JsonDateValueProcessorUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
@Scope("prototype")
public class BookAction extends ActionSupport implements ModelDriven<Book> {
	@Autowired
	private BookService bookService;
	private JSONObject result; // 第三方的jar包json对象，设置get/set方法，必须
	private Book preBook = new Book();
	private String rows;
	private String page; 
	public String queryData;//查询条件
	
	// 查询所有图书信息
	public String getList() {
		List<Book> books;
		int total;
		if(queryData!=null){
			books = bookService.query(page, rows, queryData);
			total = bookService.getCount();
		}else{
			books = bookService.getList(page,rows);
			total = bookService.getCount();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", books);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());
		this.setResult(JSONObject.fromObject(map, jsonConfig));
		return SUCCESS;
	}
	
	
	// 添加图书信息
	public String add() {
		bookService.add(preBook);
		
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("success", "success!!!");  // 成功后将 success="success!!!" 传递回前端
		this.setResult(JSONObject.fromObject(json));  // 转换为json格式
		return SUCCESS;
	}
	
	// 修改图书信息
	public String update(){
		bookService.update(preBook);
		return SUCCESS;
	}
	
	// 根据 id 删除指定图书信息
	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		//System.out.println("id:"+id);
		bookService.deleteById(id);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("success", "success!!!");  // 成功后将 success="success!!!" 传递回前端
		this.setResult(JSONObject.fromObject(json));  // 转换为json格式
		return SUCCESS;
	}
	
	@Override
	public Book getModel() {
		return preBook;
	}
	public JSONObject getResult() {
		return result;
	}
	public void setResult(JSONObject result) {
		this.result = result;
	}
	public Book getPreBook() {
		return preBook;
	}
	public void setPreBook(Book preBook) {
		this.preBook = preBook;
	}
//	public void setBookService(BookService bookService) {
//		this.bookService = bookService;
//	}
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
}
