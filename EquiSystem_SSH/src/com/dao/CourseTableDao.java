package com.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;











import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
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
public class CourseTableDao {
//	通过spring配置，注入sessionFactory
	@Autowired
	private SessionFactory sessionFactory;
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	private static int size=0;
	
/*--------------------------叶雄峰--------------------------*/
	
	
	/**
	 * 获取仅和当前教师名相关的数据（判断：如果是管理员）
	 * 通过List<对象>返回
	 * @return
	 */
	public List<Course_table> getList(String page, String rows, User user){
		int pagesize = Integer.parseInt((rows == null || rows == "0") ? "5" : rows);// 每页多少条
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
//		从session域中获取账户信息
		if(user.getRole() == 1){
			String hql = "FROM Course_table";
			String hqlSize = "SELECT count(*) FROM Course_table";
			size = Integer.parseInt(getSession().createQuery(hqlSize).uniqueResult().toString());
			List<Course_table> courses = getSession().createQuery(hql).setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
			return courses;
		}else{
			String hql = "FROM Course_table c WHERE c.teacher = ?";
			String hqlSize = "SELECT count(*) FROM Course_table c WHERE c.teacher = ?";
			size = Integer.parseInt(getSession().createQuery(hqlSize).setString(0, user.getName()).uniqueResult().toString());
			List<Course_table> courses = getSession().createQuery(hql).setString(0, user.getName())
					.setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
			return courses;
		}
	}
//	返回总数据数目
	public int getListSize(){
		return size;
	}
	
	/**
	 * @param page 条数
	 * @param rows 页数
	 * @param queryData 查询条件
	 * @param user 用户信息
	 * @return
	 */
	public List<Course_table> query(String page, String rows, String queryData, User user){
		int pagesize = Integer.parseInt((rows == null || rows == "0") ? "5" : rows);// 每页多少条
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
		
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
		Criteria criteria = getSession().createCriteria(Course_table.class);
		Conjunction conjunction = Restrictions.conjunction();
		Disjunction disjunction = Restrictions.disjunction();
		if(user.getRole()==1){
//			OR
			disjunction.add(Restrictions.like("teacher", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("node", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("room", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("is_true", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("day", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ge("begindate", time));
		}else{
//			AND
			conjunction.add(Restrictions.eq("teacher", user.getName()));
//			OR
			disjunction.add(Restrictions.like("node", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("room", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("is_true", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("day", queryData, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ge("begindate", time));
		}
		criteria.add(conjunction);
		criteria.add(disjunction);
		
		return criteria.list();
	}
	
	
	
	/**
	 * add功能：从前端获取封装成对象的数据，存入数据库
	 * @return
	 */
	public void add(Course_table course){
//		传过来是没有is_true的，要设置is_true为默认值：处理中（管理员同意 ？ 通过:未通过）
		getSession().save(course);
	}
	
	
	/**
	 * 修改
	 * @param course
	 */
	public void update(Course_table course){
		getSession().update(course);
	}
	
	
	/**
	 * 删除：根据id删除
	 * @param course
	 */
	public void delect(Course_table course){
		getSession().delete(course);
	}
}
