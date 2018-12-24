package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Course_table;
import com.bean.User;
import com.dao.CourseTableDao;

@Service
@Scope("prototype")
public class CourseTableService {
	@Autowired
	private CourseTableDao courseTableDao;
	

/*--------------------------叶雄峰--------------------------*/
	

//	分页显示
	public List<Course_table> getList(String page, String rows, User user){
		return courseTableDao.getList(page, rows, user);
	}
	public int getListSize(){
		return courseTableDao.getListSize();
	}
//	查询
	public List<Course_table> query(String page, String rows, String queryData, User user){
		return courseTableDao.query(page, rows, queryData, user);
	}
	
	
//	添加
	public void add(Course_table course){
		courseTableDao.add(course);
	}
	
//	修改
	public void update(Course_table course){
		courseTableDao.update(course);
	}
	
	
//	删除
	public void delete(int id){
		Course_table course = new Course_table();
		course.setId(id);
		courseTableDao.delect(course);
	}
}
