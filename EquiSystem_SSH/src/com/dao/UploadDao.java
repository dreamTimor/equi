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

import com.bean.Upload;

@Repository
@Scope("prototype")
public class UploadDao {
	@Autowired
	private SessionFactory sessionFactory;
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	/*
	 * 上传添加记录
	 */
	public void add(Upload upload){
		getSession().save(upload);
	}
	
	/**
	 * 返回文档的数据
	 * @return
	 */
	public List<Upload> getAll(){
		String hql = "FROM Upload u WHERE u.fileType=1";
		return getSession().createQuery(hql).list();
	}
	/**
	 * 返回视频的数据
	 * @return
	 */
	public List<Upload> getVedeo(){
		String hql = "FROM Upload u WHERE u.fileType=2";
		return getSession().createQuery(hql).list();
	}
	
	/**
	 * 分页返回记录
	 * @return
	 */
	public List<Upload> getList(String page, String rows){
		int pagesize = Integer.parseInt((rows == null || rows == "0") ? "5" : rows);// 每页多少条
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1" : page);// 第几页
		
		String hql = "FROM Upload";
		return getSession().createQuery(hql).setFirstResult((currentpage-1)*pagesize).setMaxResults(pagesize).list();
	}
	public int getSize(){
		String hql = "SELECT count(*) FROM Upload";
		
		return Integer.parseInt(getSession().createQuery(hql).uniqueResult().toString());
	}
	/**
	 * 不分页查询
	 * @param queryData
	 * @return
	 */
	public List<Upload> query(String queryData){
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
		Criteria criteria = getSession().createCriteria(Upload.class);
		criteria.add(Restrictions.or(
				Restrictions.like("username", queryData, MatchMode.ANYWHERE),
				Restrictions.like("filename", queryData, MatchMode.ANYWHERE),
				Restrictions.ge("uploadTime", time)));
		System.out.println("输出条件："+criteria);
		return criteria.list();
	}
	
	
	
	public int update(Upload uploadObject){
		getSession().update(uploadObject);
		return 1;
	}
	public int delete(Upload uploadObject){
		getSession().delete(uploadObject);
		return 1;
	}

}
