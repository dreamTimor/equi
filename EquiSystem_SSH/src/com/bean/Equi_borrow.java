package com.bean;

import java.util.Date;

//			 �豸����
public class Equi_borrow {

	private int id;
	private String name;// 姓名
	private String model;// 型号规格
	private int num;// 数量
	private String unit;//
	private String borrower;// ������
	private String occupation;// ְҵ
	private int state; // 是否审批
	private String return_status;// �黹
	private Date lendtime;// ����ʱ��
	private Date borrowtime;// Ԥ���黹ʱ��
	private Date truetime;// ʵ�ʹ黹ʱ��
	private int uid; // 用户主键 id

	public Equi_borrow() {
		super();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
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

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getReturn_status() {
		return return_status;
	}

	public void setReturn_status(String return_status) {
		this.return_status = return_status;
	}

	public Date getLendtime() {
		return lendtime;
	}

	public void setLendtime(Date lendtime) {
		this.lendtime = lendtime;
	}

	public Date getBorrowtime() {
		return borrowtime;
	}

	public void setBorrowtime(Date borrowtime) {
		this.borrowtime = borrowtime;
	}

	public Date getTruetime() {
		return truetime;
	}

	public void setTruetime(Date truetime) {
		this.truetime = truetime;
	}

	@Override
	public String toString() {
		return "Equi_borrow [id=" + id + ", name=" + name + ", model=" + model
				+ ", num=" + num + ", unit=" + unit + ", borrower=" + borrower
				+ ", occupation=" + occupation + ", state=" + state
				+ ", return_status=" + return_status + ", lendtime=" + lendtime
				+ ", borrowtime=" + borrowtime + ", truetime=" + truetime
				+ ", uid=" + uid + "]";
	}

}
