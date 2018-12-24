package com.dao;

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

import com.bean.Inno_project;

@Repository
@Scope("prototype")
public class InnoProjectDao {
	@Autowired
	private SessionFactory sessionFactory;
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 返回当前页数的数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Inno_project> getList(String page, String rows){
		int currentpage = Integer.parseInt((page=="0" || page==null) ? "1":page);//页数
		int pagesize = Integer.parseInt((rows=="0" || rows==null) ? "5":rows);//当前页的数据长度
		
		String hql = "FROM Inno_project";
		return getSession().createQuery(hql).setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
	}
	/**
	 * 返回所有数据的长度
	 * @return
	 */
	public int getListSize(){
		String hql = "SELECT count(*) FROM Inno_project";
		return Integer.parseInt(getSession().createQuery(hql).uniqueResult().toString());
	}
	/**
	 * 返回不分页的查询记录
	 * @param queryData
	 * @return
	 */
	public List<Inno_project> query(String queryData){
		Date time = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.setLenient(false);
			time = format.parse(queryData);
		} catch (Exception e){
			System.out.println("不符合时间转换条件");
		}
		
		Criteria criteria = getSession().createCriteria(Inno_project.class);
		criteria.add(Restrictions.or(Restrictions.like("name", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("level", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("type", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("charger", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("teacher", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("memberinfo", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("bor_equi", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("proinfo", queryData, MatchMode.ANYWHERE),
									 Restrictions.ge("starttime", time)));
		return criteria.list();
	}
	
	
	public int add(Inno_project inno){
		getSession().save(inno);
		return 1;
	}
	public int update(Inno_project inno){
		getSession().update(inno);
		return 1;
	}
	public int delete(Inno_project inno){
		getSession().delete(inno);
		return 1;
	}
	
}
