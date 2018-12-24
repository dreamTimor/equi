package com.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.bean.Equi_get;



@Repository
@Scope("prototype")
public class EquiGetDao {
	
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

	//设备领取登记分页显示
	public List<Equi_get> equiGetList(Integer page,Integer rows){	
		page = (page==null||page==0)?1:page;
		rows = (rows==null||rows==0)?5:rows;	
		String hql = " from Equi_get ";
		//String hqlsize = "SELECT count(*) FROM Equi_get";
		Query query = getSession().createQuery(hql);
		size = query.list().size();  //查询所有数据条数
		//hibernateTemplate.getSessionFactory().openSession().createQuery(hqlsize).uniqueResult()
		//查完再进行分页
		List<Equi_get> list = query.setFirstResult((page-1)*rows).setMaxResults(rows).list();
		return list;
	}
	public int size() {
		return size;
	}
	
	
	// 根据id查询单个设备领取登记
	public Equi_get equiGetById(int id) {
		return	(Equi_get) getSession().get(Equi_get.class, id);
	}
	
	//设备领取登记添加
	public void addEquiGet(Equi_get eg) {
		getSession().save(eg);
	}
	
	// 编辑设备领取登记
	public void updateEquiGet(Equi_get eg) {
		getSession().update(eg);
	}
	
	// 删除设备领取登记
	public void deleteEquiGet(Equi_get eg) {
		getSession().delete(eg);
	}

	public List<Equi_get> query(Integer page, Integer rows, String queryData){
		try {// 设置禁止SimpleDateFormat的自动计算功能（输入2018-55-1会把55换算成4年加进年份）
			format.setLenient(false);
			time = format.parse(queryData);
			System.out.println("成功转换：" + time);
		} catch (Exception e) {
			System.out.println("不符合时间条件");
		}
		page = (page==null||page==0)?1:page;
		rows = (rows==null||rows==0)?5:rows;
		
		Criteria criteria = getSession().createCriteria(Equi_get.class);
		Disjunction disjunction = Restrictions.disjunction();
		//Conjunction conjunction = Restrictions.conjunction();
		
		disjunction.add(Restrictions.like("name", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("model", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("unit", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("keeper", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.like("receiver", queryData, MatchMode.ANYWHERE));
		disjunction.add(Restrictions.ge("innertime", time));
		criteria.add(disjunction);
		//criteria.add(conjunction);
		size = criteria.list().size();	
		System.out.println(size);
		return criteria.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
}
