package com.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.bean.Equi_paylist;
import com.bean.User;

@Repository
@Scope("prototype")
public class EquiPayListDao {

	/*@Autowired
	private HibernateTemplate hibernateTemplate;*/
	
	@Autowired
	private SessionFactory sessionFactory;
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}


//	用于模糊查询（format用于检测数据是否符合这个格式）
	Date time = null;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Integer size = 0;  //避免多写一堆分页查询获取条数
	
	/**
	 * 查询某个老师的所有预采购
	 * 
	 * @param int uid
	 * @return List<Equi_paylist>
	 * @author cxg
	 */
	public List<Equi_paylist> getAllById(int uid) {
		
		String hql = "from Equi_paylist where uid = :uid";
		Query query = getSession().createQuery(hql).setInteger("uid", uid);
		List list = query.list();
		return list;
		
	}

	// 管理员分页查询所有老师预采购
	public List<Equi_paylist> listAllPaylist(String page, String rows) {
		String hql = "from Equi_paylist";
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
		int pagesize = Integer.parseInt((rows == null || rows == "0") ? "5" : rows);// 每页多少条
		@SuppressWarnings("unchecked")
		List<Equi_paylist> list =  getSession().createQuery(hql)
				.setFirstResult((currentpage - 1) * pagesize).setMaxResults(pagesize).list();
		
		return list;
	}

	// 查询某个老师一共有多少预采购
	public int getAllPayListSize() {
		String hql = "from Equi_paylist";
		return  getSession().createQuery(hql).list().size();
	}

	// 根据id查询单个预采购订单
	public Equi_paylist getPayById(int id) {
		return  (Equi_paylist) getSession().get(Equi_paylist.class, id);
	}

	// 添加预采购
	public void addEquiPayList(Equi_paylist paylist) {
		 getSession().save(paylist);
	}

	// 编辑预采购-
	public void updateProduct(Equi_paylist paylist) {
		 getSession().update(paylist);
	}

	// 删除预采购-
	public void deleteProduct(Equi_paylist paylist) {
		 getSession().delete(paylist);
	}

	// 根据第几页获取，每页几行获取数据,根据某个老师的所有预采购
	public List<Equi_paylist> getPayList(String page, String rows, int uid) {
		// 当为缺省值的时候进行赋值
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
		int pagesize = Integer.parseInt((rows == null || rows == "0") ? "5" : rows);// 每页多少条
		
		String hql = "from Equi_paylist where uid = :uid";
		List list =  getSession().createQuery(hql).setInteger("uid", uid).setFirstResult((currentpage - 1) * pagesize)
				.setMaxResults(pagesize).list();
		return list;
		
	}

	// 查询某个老师一共有多少预采购
	public int getPayListSize(int uid) {
		String hql = "from Equi_paylist where uid = :uid";
		return  getSession().createQuery(hql).setInteger("uid", uid).list().size();
	}

	// 分页+条件查询
	public List<Equi_paylist> query(String page, String rows, String queryData,User user) {
		try {// 设置禁止SimpleDateFormat的自动计算功能（输入2018-55-1会把55换算成4年加进年份）
			format.setLenient(false);
			time = format.parse(queryData);
			System.out.println("成功转换：" + time);
		} catch (Exception e) {
			System.out.println("不符合时间条件");
		}
		// 当为缺省值的时候进行赋值
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
		int pagesize = Integer.parseInt((rows == null || rows == "0") ? "5" : rows);// 每页多少条
		
		Criteria criteria = getSession().createCriteria(Equi_paylist.class);
		
		Disjunction disjunction = Restrictions.disjunction();
		Conjunction conjunction = Restrictions.conjunction();
		
		if(user.getRole()==1){
			disjunction.add(Restrictions.like("name", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("model", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("unit", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("applicant", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("ifpass", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ge("applydate", time));	
		}else {
			conjunction.add(Restrictions.eq("uid", user.getId()));
			disjunction.add(Restrictions.like("name", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("model", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("unit", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("ifpass", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ge("applydate", time));	
		}
		
		criteria.add(disjunction);
		criteria.add(conjunction);
		
		size = criteria.list().size();	
		System.out.println("条件查询的条数=================："+size);
		return criteria.setFirstResult((currentpage - 1) * pagesize).setMaxResults(pagesize).list();
	}
	
	public int querysize() {
		return size;
	}

}
