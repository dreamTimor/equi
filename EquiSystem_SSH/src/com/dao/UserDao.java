package com.dao;


import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.bean.User;

@Repository
@Scope("prototype")
public class UserDao{
//	通过spring配置，注入sessionFactory
	@Autowired
	private SessionFactory sessionFactory;
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
/*--------------------------叶雄峰--------------------------*/
	
	public List<User> getUserId(String username, String password)throws SQLException{
//		hql语句查询对应用户信息
		String hql="FROM User u WHERE u.username = ? AND u.password = ?";
		List<User> users = getSession().createQuery(hql).setString(0, username).setString(1, password).list();
		return users;
	}
	
}
