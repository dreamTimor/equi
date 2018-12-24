package com.bean;

import java.util.Date;

//			 �豸��ȡ
public class Equi_get {

	private int id;
	private String name;//�豸��
	private String model;//�ͺŹ��
	private int num;//����
	private String unit;//��λ
	private String keeper;//���ŵĹ���Ա
	private String receiver;//��ȡ��
	private Date innertime;//��ȡʱ��
	private String remark;//��ע
	
	public Equi_get(int id, String name, String model, int num, String unit,
			String keeper, String receiver, Date innertime, String remark) {
		super();
		this.id = id;
		this.name = name;
		this.model = model;
		this.num = num;
		this.unit = unit;
		this.keeper = keeper;
		this.receiver = receiver;
		this.innertime = innertime;
		this.remark = remark;
	}
	public Equi_get() {
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
	public String getKeeper() {
		return keeper;
	}
	public void setKeeper(String keeper) {
		this.keeper = keeper;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Date getInnertime() {
		return innertime;
	}
	public void setInnertime(Date innertime) {
		this.innertime = innertime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
