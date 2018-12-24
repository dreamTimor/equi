package com.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Book;
import com.bean.Book_borrow;
import com.bean.User;
import com.dao.BookBorrowDao;

@Service
@Scope("prototype")
public class BookBorrowService {
	@Autowired
	private BookBorrowDao daoBookBorrow;
	
	public List<Book_borrow> getList(String page, String rows, User user){
		return daoBookBorrow.getList(page, rows, user);
	}
	public List<Book_borrow> query(String page, String rows, String queryData, User user){
		return daoBookBorrow.query(page, rows, queryData, user);
	}
	public int getSize(){
		return daoBookBorrow.getSize();
	}
	
	public int add(Book_borrow bookborrow){
		int judge;
		bookborrow.setUser( daoBookBorrow.getUser(bookborrow.getUsername()) );
		//获取Book的剩余数量，判断够不够借，不够反回失败，够就执行
		Book book = daoBookBorrow.getBook(bookborrow.getBookname());
		if(book.getResidueNum() >= bookborrow.getBooknum()){
			book.setResidueNum(book.getResidueNum() - bookborrow.getBooknum());
			bookborrow.setBook(book);
			daoBookBorrow.add(bookborrow);
			judge = 1;
		}else{
			judge = 0;
		}
		return judge;
	}
	/**
	 * 检查这个申请有没通过，有没还书
	 * @param id
	 */
	public void delete(int id){
		
		daoBookBorrow.delete(id);
	}
	
	/**
	 * 将is_succeed字段换为通过，在Dao层保存进去
	 * @param bookborrow
	 * @return
	 */
	public int update_yes(Book_borrow bookborrow){
		bookborrow.setUser( daoBookBorrow.getUser(bookborrow.getUsername()) );
		bookborrow.setBook( daoBookBorrow.getBook(bookborrow.getBookname()) );
		bookborrow.setIs_succeed("通过");
		return daoBookBorrow.update(bookborrow);
	}
	/**
	 * 字段修改未通过，并更改数据，让Book的剩余数量增加回来
	 * @param bookborrow
	 * @return
	 */
	public int update_no(Book_borrow bookborrow){
		bookborrow.setUser( daoBookBorrow.getUser(bookborrow.getUsername()) );
		
		Book book = daoBookBorrow.getBook(bookborrow.getBookname());
		book.setResidueNum( book.getResidueNum() + bookborrow.getBooknum() );
		bookborrow.setBook(book);
		
		bookborrow.setIs_succeed("未通过");
		return daoBookBorrow.update(bookborrow);
	}
	/**
	 * 添加还书时间，同时将借书数量补回去
	 * @param boorBorrow
	 * @return
	 */
	public int update_return(Book_borrow bookborrow){
//		获取User
		bookborrow.setUser( daoBookBorrow.getUser(bookborrow.getUsername()) );
//		获取Book
		Book book = daoBookBorrow.getBook(bookborrow.getBookname());
		book.setResidueNum( book.getResidueNum()+bookborrow.getBooknum() );
		bookborrow.setBook(book);
//		添加实际还书时间
		bookborrow.setActualtime(new Date());
		return daoBookBorrow.update(bookborrow);
	}
	
}
