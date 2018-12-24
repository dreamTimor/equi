package com.bean;

import java.util.Date;

//			 设备报修
public class Equi_repair {

	private int id;
	private String name;//设备名
	private String rep_people;//报修人
	private Date time;//报修时间
	private String reson;//原因说明
	private String way;//维修方式
	private String place;//存放地
	private String solve_people;//处理人
	

	public Equi_repair() {
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
	public String getRep_people() {
		return rep_people;
	}
	public void setRep_people(String rep_people) {
		this.rep_people = rep_people;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getReson() {
		return reson;
	}
	public void setReson(String reson) {
		this.reson = reson;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getSolve_people() {
		return solve_people;
	}
	public void setSolve_people(String solve_people) {
		this.solve_people = solve_people;
	}
	@Override
	public String toString() {
		return "Equi_repair [id=" + id + ", name=" + name + ", rep_people="
				+ rep_people + ", time=" + time + ", reson=" + reson + ", way="
				+ way + ", place=" + place + ", solve_people=" + solve_people
				+ "]";
	}
	
}
