package com.bean;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//			 实验室预约
public class Lab_bespeak {
	
	private int id;
	private String people;//预约人
	private String room;//预约教室
	private Date usetime;//使用日期
	private String node;//节次
	private String user_explain;//用途
	private String occupation;//职业
	private String is_succeed;//预约结果
	private int bespeak_uid;//外键
	
	public Lab_bespeak() {
		super();
	}
	
	
	public Lab_bespeak(String people, String room, Date usetime,
			String node, String user_explain, String occupation,
			String is_succeed, int bespeak_uid) {
		super();
		this.people = people;
		this.room = room;
		this.usetime = usetime;
		this.node = node;
		this.user_explain = user_explain;
		this.occupation = occupation;
		this.is_succeed = is_succeed;
		this.bespeak_uid = bespeak_uid;
	}


	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public int getBespeak_uid() {
		return bespeak_uid;
	}
	public void setBespeak_uid(int bespeak_uid) {
		this.bespeak_uid = bespeak_uid;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPeople() {
		return people;
	}
	public void setPeople(String people) {
		this.people = people;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public Date getUsetime() {
		return usetime;
	}
	public void setUsetime(Date usetime) {
		this.usetime = usetime;
	}
	public String getUser_explain() {
		return user_explain;
	}
	public void setUser_explain(String user_explain) {
		this.user_explain = user_explain;
	}
	public String getIs_succeed() {
		return is_succeed;
	}
	public void setIs_succeed(String is_succeed) {
		this.is_succeed = is_succeed;
	}
	@Override
	public String toString() {
		return "Lab_bespeak [id=" + id + ", people=" + people + ", room="
				+ room + ", usetime=" + usetime + ", node=" + node
				+ ", user_explain=" + user_explain + ", occupation="
				+ occupation + ", is_succeed=" + is_succeed + ", bespeak_uid="
				+ bespeak_uid + "]";
	}
	
}
