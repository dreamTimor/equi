package com.bean;
//			 ���
public class Equi_stock {
	private int id;
	private String name;//�豸��
	private int num;//ʣ������
	private String model;//�ͺŹ��
	private String unit;//��λ
	private int sum;//������
	private String remark;//��ע
	

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
