package com.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.SizeSequence;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.DisjunctionFragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.bean.Book;
import com.bean.Lab_bespeak;

@Repository
@Scope("prototype")
public class BookDao extends HibernateDaoSupport {
	@Autowired
	private SessionFactory sessionFactory;
	private Session getSession(){
		return getSessionFactory().getCurrentSession();
	}
	private static int size=0;
	
	/**
	 * 查询所有的图书信息
	 * @return
	 */
	public List<Book> getList(String page,String rows) {
		int currentPage = Integer.parseInt((page==null||page=="0")?"1":page);
		int pageSize = Integer.parseInt((rows==null||rows=="0")?"5":rows);
		String hql = "FROM Book";
		size = Integer.parseInt(getSession().createQuery("SELECT count(*) FROM Book").uniqueResult().toString());
		return getSession().createQuery(hql).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
	}
	
	/**
	 * 查询图书的总记录数
	 * @return
	 */
	public int getTotalCout() {
		return size;
	}
	
	public List<Book> query(String page, String rows, String queryData){
		int currentPage = Integer.parseInt((page==null||page=="0")?"1":page);
		int pageSize = Integer.parseInt((rows==null||rows=="0")?"5":rows);
//		format用于检测数据是否符合这个格式
		Date time = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {//设置禁止SimpleDateFormat的自动计算功能（输入2018-55-1会把55换算成4年加进年份）
			format.setLenient(false);
			time = format.parse(queryData);
			System.out.println("成功转换："+time);
		} catch (Exception e) {
			System.out.println("不符合时间条件");
		}
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Book.class);
		Disjunction disjunction = Restrictions.disjunction();
//		
//		disjunction.add(Restrictions.like("price", queryData, MatchMode.ANYWHERE));
//		disjunction.add(Restrictions.like("total", queryData, MatchMode.ANYWHERE));
//		disjunction.add(Restrictions.like("residueNum", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("bookname", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("author", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("isbn", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("publish", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("batch", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.ge("pubtime", time));
		criteria.add(disjunction);
//		按分页查询数据
		List<Book> labs = criteria.setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
//		使用count获取数目，节约性能
		criteria.setProjection(Projections.rowCount());
		
//		uniqueryResult：在只返回一个值  或者  值为Null时使用，所以需要判断，不然可能报空指针异常
//		if(criteria.uniqueResult()!=null){
			size = Integer.parseInt(criteria.uniqueResult().toString());
//		}
		
		return labs;
	}
	
	
	/**
	 * 添加一个图书信息
	 * @param preBook
	 */
	public void add(Book preBook) {
		getSession().save(preBook);
	}
	
	/**
	 * 修改信息
	 * @param prebook
	 */
	public void update(Book prebook){
		getSession().update(prebook);
	}
	
	/**
	 * 根据 id 删除指定图书信息
	 * @param id
	 */
	public void deleteById(int id) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session arg0) throws HibernateException {
				return arg0.createQuery("delete from Book where id = ?").setInteger(0, id).executeUpdate();
			}
		});
	}

}
