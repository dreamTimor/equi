package com.bean;

import java.util.Date;

//			 教室设备
public class Room_equi {
	private int id;
	private String classroom;//教室
	private String name;//设备名
	private String model;//型号规格
	private int num;//数量
	private String unit;//单位
	private Date time;//时间
	private String source;//来源
	private String configue;//配置说明
	
	
	public Room_equi() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassroom() {
		return classroom;
	}
	public void setClassroom(String classroom) {
		this.classroom = classroom;
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
	public String getConfigue() {
		return configue;
	}
	public void setConfigue(String configue) {
		this.configue = configue;
	}
	@Override
	public String toString() {
		return "Room_equi [id=" + id + ", classroom=" + classroom + ", name="
				+ name + ", model=" + model + ", num=" + num + ", unit=" + unit
				+ ", time=" + time + ", source=" + source + ", configue="
				+ configue + "]";
	}
	
}
