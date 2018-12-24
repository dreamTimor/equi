package com.bean;

import java.util.Date;
//			 竞赛项目
public class Comp_project {
	private int id;
	private String name;//竞赛名称
	private String inspector;//指导老师
	private String members;//参赛成员
	private String bor_equi;//借用设备
	private Date enter_time;//参赛时间
	private int funds;//报销经费
	private String is_attend;//是否参赛
	private String award;//获奖情况
	
	public Comp_project() {
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
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public String getBor_equi() {
		return bor_equi;
	}
	public void setBor_equi(String bor_equi) {
		this.bor_equi = bor_equi;
	}
	public Date getEnter_time() {
		return enter_time;
	}
	public void setEnter_time(Date enter_time) {
		this.enter_time = enter_time;
	}
	public int getFunds() {
		return funds;
	}
	public void setFunds(int funds) {
		this.funds = funds;
	}
	public String getIs_attend() {
		return is_attend;
	}
	public void setIs_attend(String is_attend) {
		this.is_attend = is_attend;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	@Override
	public String toString() {
		return "Comp_project [id=" + id + ", name=" + name + ", inspector="
				+ inspector + ", members=" + members + ", bor_equi=" + bor_equi
				+ ", enter_time=" + enter_time + ", funds=" + funds
				+ ", is_attend=" + is_attend + ", award=" + award + "]";
	}

	
}
