package com.bean;
//			 班级
public class StuClass {

	private int id;
	private String name;//班级名
	private String academy;//院系
	private int num;//班级人数
	
	
	public StuClass(String name, String academy, int num) {
		super();
		this.name = name;
		this.academy = academy;
		this.num = num;
	}
	public StuClass() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAcademy() {
		return academy;
	}
	public void setAcademy(String academy) {
		this.academy = academy;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
