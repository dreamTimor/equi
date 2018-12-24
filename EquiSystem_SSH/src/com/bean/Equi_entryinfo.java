package com.bean;

import java.util.Date;

//			 �����Ϣ
public class Equi_entryinfo {
	private int id;
	private String name;//�豸��
	private String model;//�ͺŹ��
	private int num;//����
	private String unit;//��λ
	private String inspector;//����Ա
	private Date entrytime;//���ʱ��
	private String deposit;//��ŵ�
	
	
	public Equi_entryinfo(String name, String model, int num, String unit,
			String inspector, Date entrytime, String deposit) {
		super();
		this.name = name;
		this.model = model;
		this.num = num;
		this.unit = unit;
		this.inspector = inspector;
		this.entrytime = entrytime;
		this.deposit = deposit;
	}
	public Equi_entryinfo() {
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
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public Date getEntrytime() {
		return entrytime;
	}
	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	
	
}
