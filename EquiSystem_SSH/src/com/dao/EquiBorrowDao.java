package com.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.bean.Equi_borrow;
import com.bean.User;

/**
 * 设备借用dao层
 * 
 * @author dreamTimor
 *
 */
public class EquiBorrowDao {
	private SessionFactory sessionFactory;
	private static int size = 0;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 查询指定用户的所有设备借用记录
	 * 
	 * @param user  需要传入的 uid
	 * @param rows
	 * @param page
	 * @return 返回一个 list 集合
	 */
	@SuppressWarnings("unchecked")
	public List<Equi_borrow> getAllByUid(User user, String page, String rows) {
		int currentPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "15" : rows);
		List<Equi_borrow> list = null;
		if (user.getRole() == 1) { // 管理员
			String hql = "FROM Equi_borrow ORDER BY state";
			Query query = getSession().createQuery(hql);
			size = query.list().size();
			list = query.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).list();
		} else { // 老师/学生
			String hql = "FROM Equi_borrow WHERE UID = ? ";
			Query query = getSession().createQuery(hql).setInteger(0, user.getId());
			size = query.list().size();
			list = query.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).list();
		}
		return list;
	}

	/**
	 * 添加设备借用记录
	 * 
	 * @param equi_borrow
	 */
	public void addEquiBorrow(Equi_borrow equi_borrow) {
		getSession().save(equi_borrow);
	}

	/**
	 * 根据 id 删除指定设备借用记录
	 * 
	 * @param id
	 */
	public int delEquiBorrowById(int id) {
		return getSession().createQuery("delete from Equi_borrow where id = ?").setInteger(0, id).executeUpdate();
	}

	/**
	 * 修改设备借用记录
	 * 
	 * @param preEqui_borrow
	 */
	public void updateEquiBorrowById(Equi_borrow preEqui_borrow) {
		getSession().update(preEqui_borrow);
	}

	/**
	 * 查询所有记录的条数
	 * 
	 * @return
	 */
	public int getAllCount() {
		return size;
	}

	/**
	 * 根据用户输入的信息查询记录
	 * @param data
	 * @param page
	 * @param rows
	 * @param user 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Equi_borrow> query(String data, String page, String rows, User user) {
		int currentPage = Integer.parseInt((page == null || page == "0")?"1":page);
		int pageSize = Integer.parseInt((rows == null || rows == "0")?"15":rows);
		List<Equi_borrow> list = null;
		Query query = null;
		if(user.getRole() == 1) { // 管理员
			String hql = "FROM Equi_borrow e WHERE e.name LIKE :data OR e.model LIKE :data OR e.borrower LIKE :data OR"
					+ " e.occupation LIKE :data OR e.return_status LIKE :data OR e.state LIKE :data";
			query = getSession().createQuery(hql).setString("data", "%"+data+"%");
			size = query.list().size();
			list = query.setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		}else {  // 老师和学生
			String hql = "FROM Equi_borrow e WHERE e.name LIKE :data OR e.model LIKE :data OR e.borrower LIKE :data OR"
					+ " e.occupation LIKE :data OR e.return_status LIKE :data OR e.state LIKE :data AND e.uid = :uid";
			query = getSession().createQuery(hql).setString("data", "%"+data+"%").setInteger("uid", user.getId());
			size = query.list().size();
			list = query.setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		}
		return list;
	}

	/**
	 * 根据 id 修改归还记录
	 * @param equiId 设备id
	 * @param user 用户
	 * @return 
	 */
	public int returnEqui(int equiId) {
		String hql = "update Equi_borrow e set e.truetime = :truetime where id = :id";
		return getSession().createQuery(hql).setDate("truetime", new Date()).setInteger("id", equiId).executeUpdate();
		
	}

}
