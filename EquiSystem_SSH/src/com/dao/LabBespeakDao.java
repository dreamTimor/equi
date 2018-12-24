package com.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.ehcache.search.expression.And;

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

import com.bean.Course_table;
import com.bean.Lab_bespeak;
import com.bean.User;
import com.opensymphony.xwork2.ActionContext;

@Repository
@Scope("prototype")
public class LabBespeakDao {
//	通过spring配置，注入sessionFactory
	@Autowired
	private SessionFactory sessionFactory;
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	private static int size=0;
	
/*--------------------------叶雄峰--------------------------*/	
	
	/**
	 * 分页显示内容
	 * @param page：页数
	 * @param rows：每页数据长度
	 * @param user：用户信息
	 * @return
	 */
	public List<Lab_bespeak> getList(String page,String rows, User user){
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
		int pagesize = Integer.parseInt((rows == null || rows == "0") ? "5" : rows);// 每页多少条
		System.out.println("用户："+user);
//		判断用户是不是管理员，是管理员按照分页返回信息
		if(user.getRole() == 1){
			String hql = "FROM Lab_bespeak";
			String hqlSize = "SELECT count(*) FROM Lab_bespeak";
			List<Lab_bespeak> bespeaks = getSession().createQuery(hql).setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
			size = Integer.parseInt(getSession().createQuery(hqlSize).uniqueResult().toString());
			return bespeaks;
		}else{                              //迫切左外连接查询User的ID：这里的l.bespeak_uid是一个User对象
			String hql = "FROM Lab_bespeak l WHERE l.bespeak_uid = :uid";
			String hqlSize = "SELECT count(*) FROM Lab_bespeak l WHERE l.bespeak_uid = :uid";
			
			List<Lab_bespeak> bespeaks = getSession().createQuery(hql).setInteger("uid", user.getId()).setFirstResult((currentpage-1)*pagesize)
					.setMaxResults(pagesize).list();
			size = Integer.parseInt(getSession().createQuery(hqlSize).setInteger("uid", user.getId()).uniqueResult().toString());
			System.out.println("长度："+size + "  数据："+bespeaks);
			return bespeaks;
		}
	}
	/**
	 * @return 返回总数据数目
	 */
	public int getListSize(){
		return size;
	}
	
	/**
	 * 模糊查询
	 * @param queryData:查询条件
	 * @param user：用户信息
	 * @return
	 */
	public List<Lab_bespeak> query(String page, String rows, String queryData,User user){
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
		int pagesize = Integer.parseInt((rows == null || rows == "0") ? "5" : rows);// 每页多少条
		
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
//		QBC检索
		Criteria criteria = getSession().createCriteria(Lab_bespeak.class);
		Conjunction conjunction = Restrictions.conjunction();
		Disjunction disjunction = Restrictions.disjunction();
		if(user.getRole()==1){
//			OR
			disjunction.add(Restrictions.like("people", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("occupation", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("room", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("node", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("user_explain", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("is_succeed", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ge("usetime", time));
		}else{
//			AND
			conjunction.add(Restrictions.eq("bespeak_uid", user.getId()));
//			OR
			disjunction.add(Restrictions.like("occupation", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("room", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("node", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("user_explain", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("is_succeed", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ge("usetime", time));
		}
		criteria.add(conjunction);
		criteria.add(disjunction);
		
//		按分页查询数据
		List<Lab_bespeak> labs = criteria.setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
//		使用count获取数目，节约性能
		criteria.setProjection(Projections.rowCount());
//		uniqueryResult：在只返回一个值  或者  值为Null时使用，所以需要判断，不然可能报空指针异常
		if(criteria.uniqueResult()!=null){
			size = Integer.parseInt(criteria.uniqueResult().toString());
		}
		
		return labs;
		
	}
	
	
	/**
	 * 添加
	 * @param lab
	 */
	public void add(Lab_bespeak lab){
		System.out.println("添加数据："+lab);
		getSession().save(lab);
	}
	
	/**
	 * 删除
	 * @param lab
	 */
	public void delete(Lab_bespeak lab){
		getSession().delete(lab);
	}
	
	/**
	 * 修改
	 * @param bespeak
	 */
	public void update(Lab_bespeak bespeak){
		getSession().update(bespeak);
	}
	
	
}
