package com.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.bean.Room_equi;

@Repository
@Scope("prototype")
public class RoomEquiDao {
	@Autowired
	private SessionFactory sessionFactory;
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
	/**
	 * 返回分页后的数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Room_equi> getList(String page, String rows){
		int currentpage = Integer.parseInt((page=="0" || page==null) ? "1":page);
		int pagesize = Integer.parseInt((rows=="0" || rows==null) ? "20":rows);
		String hql = "FROM Room_equi";
		return getSession().createQuery(hql).setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
	}
	/**
	 * 返回所有数据的长度
	 * @return
	 */
	public int getListSize(){
		String hql = "SELECT count(*) FROM Room_equi";
		return Integer.parseInt(getSession().createQuery(hql).uniqueResult().toString());
	}
	/**
	 * 返回查询的所有数据
	 * @param queryData
	 * @return
	 */
	public List<Room_equi> query(String queryData){
		Date time = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.setLenient(false);
			time = format.parse(queryData);
		} catch (ParseException e) {
			System.out.println("不符合时间条件");
		}
		
		Criteria criteria = getSession().createCriteria(Room_equi.class);
		criteria.add(Restrictions.or(Restrictions.like("classroom", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("name", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("model", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("unit", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("source", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("configue", queryData, MatchMode.ANYWHERE),
									 Restrictions.ge("time", time)));
		
		return criteria.list();
	}
	
	
	
	public int add(Room_equi roomEqui){
		getSession().save(roomEqui);
		return 1;
	}
	public int update(Room_equi roomEqui){
		getSession().update(roomEqui);
		return 1;
	}
	public int delete(Room_equi roomEqui){
		getSession().delete(roomEqui);
		return 1;
	}
	
	
}
