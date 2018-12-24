package com.bean;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Book_borrow {
	private int id;
//	private int userid;
//	private int bookid;
	private String username;//用户名
	private String bookname;//书名
	private int booknum;	//书的数量
	private Date borrowtime;//借书时间
	private Date returntime;//预计还书时间
	private Date actualtime;//实际还书时间
	private String is_succeed;//申请结果
	
	private User user;
	private Book book;
	
	public Book_borrow() {
		super();
	}
	
	
	
	public String getIs_succeed() {
		return is_succeed;
	}
	public void setIs_succeed(String is_succeed) {
		this.is_succeed = is_succeed;
	}
	@ManyToOne
	@JoinColumn(name="username")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne
	@JoinColumn(name="bookname")
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getBooknum() {
		return booknum;
	}
	public void setBooknum(int booknum) {
		this.booknum = booknum;
	}
	public Date getActualtime() {
		return actualtime;
	}
	public void setActualtime(Date actualtime) {
		this.actualtime = actualtime;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getBorrowtime() {
		return borrowtime;
	}
	public void setBorrowtime(Date borrowtime) {
		this.borrowtime = borrowtime;
	}
	public Date getReturntime() {
		return returntime;
	}
	public void setReturntime(Date returntime) {
		this.returntime = returntime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	@Override
	public String toString() {
		return "Book_borrow [id=" + id + ", username=" + username
				+ ", bookname=" + bookname + ", booknum=" + booknum
				+ ", borrowtime=" + borrowtime + ", returntime=" + returntime
				+ ", actualtime=" + actualtime + ", is_succeed=" + is_succeed
				+ ", user=" + user + ", book=" + book + "]";
	}
	
}
