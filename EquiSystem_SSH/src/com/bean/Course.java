package com.bean;
//			 �γ�
public class Course {
	private int id;
	private String name;//�γ���
	private int eredit;//ѧ��
	private int total_hours;//�ܿ�ʱ
	private String type;//�γ�����
	private int num;//�γ�����
	private int lab_time;//ʵ���ʱ
	private String lovation;//�Ͽεص�
	
	
	public Course(String name, int eredit, int total_hours, String type,
			int num, int lab_time, String lovation) {
		super();
		this.name = name;
		this.eredit = eredit;
		this.total_hours = total_hours;
		this.type = type;
		this.num = num;
		this.lab_time = lab_time;
		this.lovation = lovation;
	}
	public Course() {
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
	public int getEredit() {
		return eredit;
	}
	public void setEredit(int eredit) {
		this.eredit = eredit;
	}
	public int getTotal_hours() {
		return total_hours;
	}
	public void setTotal_hours(int total_hours) {
		this.total_hours = total_hours;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getLab_time() {
		return lab_time;
	}
	public void setLab_time(int lab_time) {
		this.lab_time = lab_time;
	}
	public String getLovation() {
		return lovation;
	}
	public void setLovation(String lovation) {
		this.lovation = lovation;
	}
	
}
