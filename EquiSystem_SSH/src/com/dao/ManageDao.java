package com.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.bean.User;

@Repository
@Scope("prototype")
public class ManageDao {
	@Autowired
	private SessionFactory sessionFactory;
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
//	记录总数据数
	private static int size=0;

/*------------------------叶雄峰------------------------------*/	

	/**
	 * 分页显示数据
	 * @param page 页数
	 * @param rows 每页数据条数
	 * @return 指定页的数据
	 */
	public List<User> getList(String page, String rows){
		int currentPage = Integer.parseInt((page==null || page=="0")?"1":page);
		int pageSize = Integer.parseInt((rows==null||rows=="0")?"5":rows);
		String hql="FROM User u WHERE u.role=2 OR u.role=3";
		String hqlSize="SELECT count(*) FROM User u WHERE u.role=2 OR u.role=3";
			
		size = Integer.parseInt(getSession().createQuery(hqlSize).uniqueResult().toString());
		List<User> users = getSession().createQuery(hql).setFirstResult((currentPage-1) * pageSize).setMaxResults(pageSize).list();
		return users;
	}
	/**
	 * @return 返回总数据条数
	 */
	public int getListSize(){
		return size;
	}
	/**
	 * 查询功能
	 * @param page 当前页数
	 * @param rows 一页的数据数
	 * @param queryData 查询条件
	 * @return 指定页的数据
	 */
	public List<User> query(String page, String rows, String queryData){
		int currentPage = Integer.parseInt((page=="0" || page==null) ? "1":page);
		int pageSize = Integer.parseInt((rows==null || rows=="0") ? "5":rows);
		
		Criteria criteria = getSession().createCriteria(User.class);
		Conjunction conjunction = Restrictions.conjunction();
		Disjunction disjunction = Restrictions.disjunction();
//		AND：role为2或3
		conjunction.add(Restrictions.or(Restrictions.eq("role", 2),Restrictions.eq("role", 3)));
//		OR
		disjunction.add(Restrictions.like("username", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("name", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("sex", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("academy", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("cla", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("phone", queryData, MatchMode.ANYWHERE));
		criteria.add(conjunction);
		criteria.add(disjunction);
		
		List<User> users = criteria.setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
//		使用count获取数目，节约性能
		criteria.setProjection(Projections.rowCount());
//		uniqueryResult：在只返回一个值  或者  值为Null时使用，所以需要判断，不然可能报空指针异常
		if(criteria.uniqueResult()!=null){
			size = Integer.parseInt(criteria.uniqueResult().toString());
		}
		
		return users;
	}
	
	/**
	 * 添加
	 * @param user
	 */
	public void add(User user){
		getSession().save(user);
	}
	/**
	 * 修改
	 * @param user
	 */
	public void update(User user){
		getSession().update(user);
	}
	/**
	 * 删除
	 * @param user
	 */
	public void delete(User user){
		getSession().delete(user);
	}
	
	
	/**
	 * 通过用户 id 修改密码
	 * @param pwd 要修改的密码
	 * @param id 用户 id
	 */
	public int updatePwd(String pwd, int id) {
		return getSession().createSQLQuery("update user set password = ? where id = ?")
						   .setParameter(0, pwd)
						   .setParameter(1, id)
						   .executeUpdate();
	}
	
}
