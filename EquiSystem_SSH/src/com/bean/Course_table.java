package com.bean;

import java.util.Date;
//			教师实验室课程预约
public class Course_table {
	private int id;
	private String teacher;//教师
	private Date begindate;//开始日期
	private String day;//周几
	private Date enddate;//结束日期
	private String node;//节次
	private String room;//实验室
	private String course;//实验课程 
	private String is_true;//是否预约成功
	
	public Course_table(String teacher, Date begindate, String day,
			Date enddate, String node, String room, String course,
			String is_true) {
		super();
		this.teacher = teacher;
		this.begindate = begindate;
		this.day = day;
		this.enddate = enddate;
		this.node = node;
		this.room = room;
		this.course = course;
		this.is_true = is_true;
	}
	public Course_table() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public Date getBegindate() {
		return begindate;
	}
	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getIs_true() {
		return is_true;
	}
	public void setIs_true(String is_true) {
		this.is_true = is_true;
	}
	
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	@Override
	public String toString() {
		return "Course_table [id=" + id + ", teacher=" + teacher
				+ ", begindate=" + begindate + ", day=" + day + ", enddate="
				+ enddate + ", node=" + node + ", room=" + room + ", course="
				+ course + ", is_true=" + is_true + "]";
	}
	
}
