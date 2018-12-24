package com.bean;
//			  设备期末统计
public class Equi_count {
	private int id;
	private String name;//设备名
	private int num;//设备数量
	private String model;//型号规格
	private String unit;//单位
	
	
	public Equi_count(String name, int num, String model, String unit) {
		super();
		this.name = name;
		this.num = num;
		this.model = model;
		this.unit = unit;
	}
	public Equi_count() {
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
	
}
