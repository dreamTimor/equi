package com.bean;

import java.util.Date;
//           �����¼
public class Lend_record {
	private int id;
	private	String no;//ѧ�š�ְ�����
	private String name;//������
	private Date lendtime;//����ʱ��
	private Date borrowtime;//Ԥ������ʱ��
	private Date truetime;//ʵ�ʻ���ʱ��
	private String isreturn;//�Ƿ�黹
//	���--���������Ϣ
	private String book_name;//����
	
	
	
	public Lend_record(String no, String name, Date lendtime, Date borrowtime,
			Date truetime, String isreturn, String book_name) {
		super();
		this.no = no;
		this.name = name;
		this.lendtime = lendtime;
		this.borrowtime = borrowtime;
		this.truetime = truetime;
		this.isreturn = isreturn;
		this.book_name = book_name;
	}
	public Lend_record() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Date getTruetime() {
		return truetime;
	}
	public void setTruetime(Date truetime) {
		this.truetime = truetime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getIsreturn() {
		return isreturn;
	}
	public void setIsreturn(String isreturn) {
		this.isreturn = isreturn;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	
	
	
}
