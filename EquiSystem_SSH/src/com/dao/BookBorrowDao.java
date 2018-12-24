package com.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.objectweb.asm.commons.StaticInitMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.bean.Book;
import com.bean.Book_borrow;
import com.bean.User;

@Repository
@Scope("prototype")
public class BookBorrowDao {
	@Autowired
	private SessionFactory sessionFactory;
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	private static int size=0;

/*--------------------------------------------------------*/
	/**
	 * 根据用户名获取记录
	 * @param page 
	 * @param rows
	 * @param user
	 * @return
	 */
	public List<Book_borrow> getList(String page, String rows, User user){
		int pagesize = Integer.parseInt((rows == null || rows == "0") ? "5" : rows);// 每页多少条
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
		
		String hql;
		List<Book_borrow> borrows;
		if(user.getRole() == 1){
			hql = "FROM Book_borrow";
			borrows = getSession().createQuery(hql).setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
			//获得总数据数量
			String hqlSize = "SELECT count(*) FROM Book_borrow";
			size = Integer.parseInt(getSession().createQuery(hqlSize).uniqueResult().toString());
		}else{
			hql = "FROM Book_borrow b WHERE b.user.username = ?";
			borrows = getSession().createQuery(hql).setString(0, user.getName())
					.setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
			//获得总数据数量
			String hqlSize = "SELECT count(*) FROM Book_borrow b WHERE b.user.username = ?";
			size = Integer.parseInt(getSession().createQuery(hqlSize).setString(0, user.getName()).uniqueResult().toString());
		}
		setName(borrows);
		return borrows;
	}
	public List<Book_borrow> query(String page, String rows, String queryData, User user){
		int pagesize = Integer.parseInt((rows == null || rows == "0") ? "5" : rows);// 每页多少条
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
		
//		用于模糊查询（format用于检测数据是否符合这个格式）
		Date time = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {//设置禁止SimpleDateFormat的自动计算功能（输入2018-55-1会把55换算成4年加进年份）
			format.setLenient(false);
			time = format.parse(queryData);
			System.out.println("成功转换："+time);
		} catch (Exception e) {
			System.out.println("不符合时间条件");
		}
		
		String hql;
		List<Book_borrow> borrows;
		if(user.getRole() == 1){
			hql = "FROM Book_borrow b LEFT OUTER JOIN FETCH b.user AS user LEFT OUTER JOIN FETCH b.book AS book WHERE "
					+ "user.name LIKE :queryData OR book.bookname LIKE :queryData";
			borrows = getSession().createQuery(hql).setString("queryData", "%"+queryData+"%")
					.setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
			//获取总数据数量
			String hqlSize = "SELECT count(*) FROM Book_borrow b WHERE b.user.name LIKE :queryData OR b.book.bookname LIKE :queryData";
			size = Integer.parseInt(getSession().createQuery(hqlSize).setString("queryData", "%"+queryData+"%").uniqueResult().toString());
		}else{
			hql = "FROM Book_borrow b LEFT OUTER JOIN FETCH b.user AS user LEFT OUTER JOIN FETCH b.book AS book WHERE "
					+ "user.name = :username AND book.bookname LIKE :queryData";
			borrows = getSession().createQuery(hql).setString("username", user.getName()).setString("queryData", "%"+queryData+"%")
					.setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
			//获取总数据数量
			String hqlSize = "SELECT count(*) FROM Book_borrow b WHERE b.user.name = :username AND b.book.bookname LIKE :queryData";
			size = Integer.parseInt(getSession().createQuery(hqlSize).setString("username", user.getName()).setString("queryData", "%"+queryData+"%").uniqueResult().toString());
		}
		setName(borrows);
		return borrows;
	}
	public int getSize(){
		return size;
	}
	
	
	public void add(Book_borrow bookborrow){
		getSession().save(bookborrow);
	}
	
	public void delete(int id){
		Book_borrow bookborrow = (Book_borrow)getSession().get(Book_borrow.class, id);
		getSession().delete(bookborrow);
	}
	
	public int update(Book_borrow bookborrow){
		getSession().update(bookborrow);
		return 1;
	}
	
	
	/**
	 * @param bookName
	 * @return 返回Book对象
	 */
	public Book getBook(String bookName){
		return (Book)getSession().createQuery("FROM Book b WHERE b.bookname=?").setString(0, bookName).uniqueResult();
	}
	/**
	 * @param userName
	 * @return 返回User对象
	 */
	public User getUser(String userName){
		return (User)getSession().createQuery("FROM User u WHERE u.username=?").setString(0, userName).uniqueResult();
	}
	/**
	 * 将User,Book对象的name赋值到BookBorrow对象里
	 * @param borrows
	 */
	private void setName(List<Book_borrow> borrows){
//		将两个外键的name属性赋值到username、bookname
		for (Book_borrow book_borrow : borrows) {
			book_borrow.setBookname(book_borrow.getBook().getBookname());
			book_borrow.setUsername(book_borrow.getUser().getUsername());
		}
	}
	
}
