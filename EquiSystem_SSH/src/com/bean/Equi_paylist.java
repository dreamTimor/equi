package com.bean;

import java.util.Date;

//			 Ԥ�ɹ��嵥
public class Equi_paylist {

	private int id;
	private String name;//�豸��
	private String model;//�ͺŹ��
	private int num;//����
	private String unit;//��λ
	private String applicant;//������
	private int uid;//老师的id
	private String remark;//��ע
	private String ifpass;//是否通过
	private Date applydate;//申请时间
	private Date passdate;//通过

	
	
	
	public Equi_paylist(int id, String name, String model, int num, String unit, String applicant, int uid,
			String remark, String ifpass, Date applydate, Date passdate) {
		super();
		this.id = id;
		this.name = name;
		this.model = model;
		this.num = num;
		this.unit = unit;
		this.applicant = applicant;
		this.uid = uid;
		this.remark = remark;
		this.ifpass = ifpass;
		this.applydate = applydate;
		this.passdate = passdate;
	}
	public Equi_paylist() {
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
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIfpass() {
		return ifpass;
	}
	public void setIfpass(String ifpass) {
		this.ifpass = ifpass;
	}
	public Date getApplydate() {
		return applydate;
	}
	public void setApplydate(Date applydate) {
		this.applydate = applydate;
	}
	public Date getPassdate() {
		return passdate;
	}
	public void setPassdate(Date passdate) {
		this.passdate = passdate;
	}
	@Override
	public String toString() {
		return "Equi_paylist [id=" + id + ", name=" + name + ", model=" + model
				+ ", num=" + num + ", unit=" + unit + ", applicant="
				+ applicant + ", uid=" + uid + ", remark=" + remark
				+ ", ifpass=" + ifpass + ", applydate=" + applydate
				+ ", passdate=" + passdate + "]";
	}
	
}
