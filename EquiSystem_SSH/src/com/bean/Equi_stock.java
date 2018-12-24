package com.bean;
//			 库存
public class Equi_stock {
	private int id;
	private String name;//设备名
	private int num;//剩余数量
	private String model;//型号规格
	private String unit;//单位
	private int sum;//总数量
	private String remark;//备注
	

	public Equi_stock(String name, int num, String model, String unit, int sum,
			String remark) {
		super();
		this.name = name;
		this.num = num;
		this.model = model;
		this.unit = unit;
		this.sum = sum;
		this.remark = remark;
	}
	public Equi_stock() {
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
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
