package com.bean;

import java.util.Date;

/**
 * 书架信息
 * @author dreamTimor
 *
 */
public class Book {
	private int id;
	private String bookname;//书名
	private String author;//作者
	private String isbn;//ISBN码
	private String publish;//出版社
	private Date pubtime;//出版时间
	private double price;//定价
	private String batch;// 批次
	private int total; // 总数量
	private int residueNum;//剩余数量

	public Book() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public int getResidueNum() {
		return residueNum;
	}

	public void setResidueNum(int residueNum) {
		this.residueNum = residueNum;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public Date getPubtime() {
		return pubtime;
	}

	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", bookname=" + bookname + ", author="
				+ author + ", isbn=" + isbn + ", publish=" + publish
				+ ", pubtime=" + pubtime + ", price=" + price + ", batch="
				+ batch + ", total=" + total + ", residueNum=" + residueNum
				+ "]";
	}

}
