package com.bean;
//			 �༶
public class StuClass {

	private int id;
	private String name;//�༶��
	private String academy;//Ժϵ
	private int num;//�༶����
	
	
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
