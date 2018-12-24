package com.bean;

import java.util.Date;
//			 ͨ��
public class Annouce {
	
	private int id;
	private String title;//ͨ�����
	private Date pubtime;//����ʱ��
	private String publisher;//������

	public Annouce(String title, Date pubtime, String publisher) {
		super();
		this.title = title;
		this.pubtime = pubtime;
		this.publisher = publisher;
	}
	public Annouce() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPubtime() {
		return pubtime;
	}
	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	@Override
	public String toString() {
		return "Annouce [id=" + id + ", title=" + title + ", pubtime="
				+ pubtime + ", publisher=" + publisher + "]";
	}
	
	
}
