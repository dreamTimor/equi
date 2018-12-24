package com.bean;

import java.util.Date;
// 			 ����Ŀ��¼
public class Inno_project {
	private int id;
	private String name;//项目名
	private String level;//级别
	private String type;//类型
	private String charger;//负责人
	private String teacher;//指导老师
	private int stu_num;//学生人数
	private String memberinfo;//成员信息
	private String bor_equi;//借用设备
	private Date starttime;//开始时间
	private Date endtime;//结题时间
	private int funds;//经费报销
	private String proinfo;//项目简介
	
	
	@Override
	public String toString() {
		return "Inno_project [id=" + id + ", name=" + name + ", level=" + level
				+ ", type=" + type + ", charger=" + charger + ", teacher="
				+ teacher + ", stu_num=" + stu_num + ", bor_equi=" + bor_equi
				+ ", starttime=" + starttime + ", endtime=" + endtime
				+ ", funds=" + funds + ", proinfo=" + proinfo + ", memberinfo="
				+ memberinfo + "]";
	}
	public Inno_project() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCharger() {
		return charger;
	}
	public void setCharger(String charger) {
		this.charger = charger;
	}
	public int getStu_num() {
		return stu_num;
	}
	public void setStu_num(int stu_num) {
		this.stu_num = stu_num;
	}
	public String getBor_equi() {
		return bor_equi;
	}
	public void setBor_equi(String bor_equi) {
		this.bor_equi = bor_equi;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public int getFunds() {
		return funds;
	}
	public void setFunds(int funds) {
		this.funds = funds;
	}
	public String getProinfo() {
		return proinfo;
	}
	public void setProinfo(String proinfo) {
		this.proinfo = proinfo;
	}
	public String getMemberinfo() {
		return memberinfo;
	}
	public void setMemberinfo(String memberinfo) {
		this.memberinfo = memberinfo;
	}
	
	
}
