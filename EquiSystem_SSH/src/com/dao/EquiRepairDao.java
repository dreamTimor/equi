package com.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.bean.Equi_repair;

@Repository
@Scope("prototype")
public class EquiRepairDao {
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
	public List<Equi_repair> getList(String page, String rows){
		int currentpage = Integer.parseInt((page=="0" || page==null) ? "1":page);
		int pagesize = Integer.parseInt((rows=="0" || rows==null) ? "20":rows);
		
		String hql = "FROM Equi_repair";
		return getSession().createQuery(hql).setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
	}
	/**
	 * 返回数据的总长度
	 * @return
	 */
	public int getListSize(){
		String hql = "SELECT count(*) FROM Equi_repair";
		return Integer.parseInt(getSession().createQuery(hql).uniqueResult().toString());
	}
	public List<Equi_repair> query(String queryData){
		Date time=null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.setLenient(false);
			time = format.parse(queryData);
		} catch (Exception e) {
			System.out.println("不符合时间条件");
		}
		
		Criteria criteria = getSession().createCriteria(Equi_repair.class);
		criteria.add(Restrictions.or(Restrictions.like("name", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("rep_people", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("reson", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("way", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("place", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("solve_people", queryData, MatchMode.ANYWHERE),
									 Restrictions.ge("name", time)));
		return criteria.list();
	}
	
	
	
	public int add(Equi_repair equiRepair){
		getSession().save(equiRepair);
		return 1;
	}
	public int update(Equi_repair equiRepair){
		getSession().update(equiRepair);
		return 1;
	}
	public int delete(Equi_repair equiRepair){
		getSession().delete(equiRepair);
		return 1;
	}
	
}
