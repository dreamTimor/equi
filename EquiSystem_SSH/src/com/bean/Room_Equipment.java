package com.bean;

import java.util.Date;

//			 �����豸��¼
public class Room_Equipment {
	private int id;
	private String name;//�豸��
	private String model;//�ͺŹ�ģ
	private int num;//����
	private String unit;//��λ
	private String configue;//����˵��
	private Date time;//������ҵ�ʱ�䣿
	private String source;//�豸��Դ
	
	
	
	public Room_Equipment(String name, String model, int num, String unit,
			String configue, Date time, String source) {
		super();
		this.name = name;
		this.model = model;
		this.num = num;
		this.unit = unit;
		this.configue = configue;
		this.time = time;
		this.source = source;
	}
	public Room_Equipment() {
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getConfigue() {
		return configue;
	}
	public void setConfigue(String configue) {
		this.configue = configue;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}
