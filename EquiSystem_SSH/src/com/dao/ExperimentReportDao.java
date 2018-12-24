package com.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.bean.Experiment_report;
import com.bean.User;

@Repository
@Scope("prototype")
public class ExperimentReportDao {
	@Autowired
	private SessionFactory sessionFactory;
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	private static int size=0;
	
	/**
	 * 返回分页后的数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Experiment_report> getList(String page, String rows, User user){
		int currentpage = Integer.parseInt((page=="0" || page==null) ? "1":page);
		int pagesize = Integer.parseInt((rows=="0" || rows==null) ? "1":rows);
		List<Experiment_report> experiments;
		
		if(user.getRole() == 3){
			
			String hql = "FROM Experiment_report e WHERE e.stu_id=?";
			experiments = getSession().createQuery(hql).setString(0, user.getUsername())
					.setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
			
			hql = "SELECT count(*) FROM Experiment_report e WHERE e.stu_id=?";
			size = Integer.parseInt(getSession().createQuery(hql).setString(0, user.getUsername()).uniqueResult().toString());
		}else if(user.getRole() == 2){
			
			String hql = "FROM Experiment_report e WHERE e.inspector=?";
			experiments = getSession().createQuery(hql).setString(0, user.getName())
					.setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
			hql = "SELECT count(*) FROM Experiment_report e WHERE e.inspector=?";
			size = Integer.parseInt(getSession().createQuery(hql).setString(0, user.getName()).uniqueResult().toString());
		}else{
			
			String hql = "FROM Experiment_report";
			experiments = getSession().createQuery(hql).setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
			hql = "SELECT count(*) FROM Experiment_report";
			size = Integer.parseInt(getSession().createQuery(hql).uniqueResult().toString());
		}
		return experiments;
	}
	/**
	 * 返回数据总数
	 * @return
	 */
	public int getListSize(){
		return size;
	}
	/**
	 * 返回查询数据：不分页
	 * @param queryData
	 * @return
	 */
	public List<Experiment_report> query(String queryData, User user){
		Date time = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.setLenient(false);
			time = format.parse(queryData);
		} catch (Exception e) {
			System.out.println("不符合时间条件");
		}
		
		Criteria criteria = getSession().createCriteria(Experiment_report.class);
		criteria.add(Restrictions.or(Restrictions.like("stu_name", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("stu_id", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("course", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("cls", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("inspector", queryData, MatchMode.ANYWHERE),
									 Restrictions.like("score", queryData, MatchMode.ANYWHERE)));
		
		if(user.getRole() == 3){
			criteria.add(Restrictions.eq("stu_id", user.getUsername()));
		}
		if(user.getRole() == 2){
			criteria.add(Restrictions.eq("inspector", user.getName()));
		}
		
		return criteria.list();
	}
	
	
	public int add(Experiment_report experiment){
		getSession().save(experiment);
		return 1;
	}
	public int delete(Experiment_report experiment){
		getSession().delete(experiment);
		return 1;
	}
}
