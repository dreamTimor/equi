package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Book;
import com.dao.BookDao;
@Service
@Scope("prototype")
public class BookService {
	@Autowired
	private BookDao bookDao;
//	public void setBookDao(BookDao bookDao) {
//		this.bookDao = bookDao;
//	}
	
	public List<Book> getList(String page,String rows) {
		return bookDao.getList(page,rows);
	}
	public int getCount() {
		return bookDao.getTotalCout();
	}
	public List<Book> query(String page, String rows, String queryData){
		return bookDao.query(page, rows, queryData);
	}
	

	public void add(Book preBook) {
		bookDao.add(preBook);
	}
	
	public void update(Book preBook){
		bookDao.update(preBook);
	}
	
	public void deleteById(int id) {
		bookDao.deleteById(id);
	}

}
