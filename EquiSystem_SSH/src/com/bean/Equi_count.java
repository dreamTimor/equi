package com.bean;
//			  �豸��ĩͳ��
public class Equi_count {
	private int id;
	private String name;//�豸��
	private int num;//�豸����
	private String model;//�ͺŹ��
	private String unit;//��λ
	
	
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
