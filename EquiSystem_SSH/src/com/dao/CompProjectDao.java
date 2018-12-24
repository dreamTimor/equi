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

import com.bean.Comp_project;

@Repository
@Scope("prototype")
public class CompProjectDao {
	@Autowired
	private SessionFactory sessionFactory;
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	private static int judge=0;
	
	/**
	 * 分页返回全部数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Comp_project> getList(String page, String rows){
		int pagesize = Integer.parseInt((rows == null || rows == "0") ? "5" : rows);// 每页多少条
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
		
		String hql="FROM Comp_project";
		return getSession().createQuery(hql).setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
	}
	/**
	 * 返回全部数据的长度
	 * @return
	 */
	public int getListSize(){
		String hql = "SELECT count(*) FROM Comp_project";
		return Integer.parseInt(getSession().createQuery(hql).uniqueResult().toString());
	}
	/**
	 * 模糊查询：不分页
	 * @param queryData
	 * @return
	 */
	public List<Comp_project> query(String queryData){
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
		Criteria criteria = getSession().createCriteria(Comp_project.class);
		criteria.add(Restrictions.or(Restrictions.like("name", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("inspector", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("members", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("bor_equi", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("is_attend", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("award", queryData, MatchMode.ANYWHERE),
//									 Restrictions.eq("funds", Integer.parseInt(queryData)),
									 Restrictions.ge("enter_time", time)));
		return criteria.list();
	}
	
	public int add(Comp_project comp){
		getSession().save(comp);
		return 1;
	}
	public int update(Comp_project comp){
		getSession().update(comp);
		return 1;
	}
	public int delete(Comp_project comp){
		try {
			getSession().delete(comp);
			judge = 1;
		} catch (Exception e) {
			judge = 0;
		}
		return judge;
	}
	
}
