package com.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.bean.Lab_bespeak;
import com.bean.Lab_equi;
import com.bean.User;
@Repository
@Scope("prototype")
public class LabEquiDao {
	@Autowired
	private SessionFactory sessionFactory;
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	private static int size=0;
/*--------------------------叶雄峰---------------------------------*/
	
	/**
	 * 分页显示
	 * @param page：页数
	 * @param rows：每页数据长度
	 * @return
	 */
	public List<Lab_equi> getList(String page, String rows){
		int currentPage = Integer.parseInt((page=="0"||page==null)?"1":page);//当前页数
		int pageSize = Integer.parseInt((rows=="0"||rows==null)?"5":rows);//页数长度
		
		String hql="FROM Lab_equi";
//		获取总数据数
		size = Integer.parseInt(getSession().createQuery("SELECT count(*) FROM Lab_equi").uniqueResult().toString());
//		根据分页获取数据
		List<Lab_equi> labs=getSession().createQuery(hql).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		return labs;
	}
	/**
	 * @return：返回总数据条数
	 */
	public int getListSize(){
		return size;
	}
	/**
	 * 模糊查询
	 * @param queryData：查询条件
	 * @return
	 */
	public List<Lab_equi> query(String page, String rows, String queryData){
		int currentPage = Integer.parseInt((page=="0"||page==null)?"1":page);//当前页数
		int pageSize = Integer.parseInt((rows=="0"||rows==null)?"5":rows);//页数长度
		
		String hql = "FROM Lab_equi l WHERE (l.name LIKE :query OR l.charger LIKE :query)";
		String hqlSize = "SELECT count(*) FROM Lab_equi l WHERE (l.name LIKE :query OR l.charger LIKE :query)";
//		获取总数据数
		size = Integer.parseInt(getSession().createQuery(hqlSize).setString("query", queryData).uniqueResult().toString());
//		根据分页获取数据
		List<Lab_equi> labs = getSession().createQuery(hql).setString("query", queryData)
				.setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		return labs;
	}
	/**
	 * 添加
	 * @param lab_equi
	 */
	public void add(Lab_equi lab_equi){
		getSession().save(lab_equi);
	}
	/**
	 * 删除
	 * @param lab
	 */
	public void delete(Lab_equi lab){
		getSession().delete(lab);
	}
	/**
	 * 修改
	 * @param lab_equi
	 */
	public void update(Lab_equi lab_equi){
		getSession().update(lab_equi);
	}
}
