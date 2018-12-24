package com.bean;

//			 ʵ�����豸
public class Lab_equi {
	
	private int id;
	private String name;//实验室名
	private String build_info;//建设简介：双击显示
	private String charger;//负责人
	
	
	public Lab_equi(String name, String build_info, String charger) {
		super();
		this.name = name;
		this.build_info = build_info;
		this.charger = charger;
	}
	public Lab_equi() {
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
	public String getBuild_info() {
		return build_info;
	}
	public void setBuild_info(String build_info) {
		this.build_info = build_info;
	}
	public String getCharger() {
		return charger;
	}
	public void setCharger(String charger) {
		this.charger = charger;
	}
	@Override
	public String toString() {
		return "Lab_equi [id=" + id + ", name=" + name + ", build_info="
				+ build_info + ", charger=" + charger + "]";
	}
}
