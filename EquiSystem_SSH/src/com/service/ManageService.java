package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.User;
import com.dao.ManageDao;

@Service
@Scope("prototype")
public class ManageService {
	@Autowired
	private ManageDao daoManage;

/*------------------------叶雄峰------------------------------*/	

	public List<User> getList(String page, String rows){
		return daoManage.getList(page, rows);
	}
	public int getListSize(){
		return daoManage.getListSize();
	}
	public List<User> query(String page, String rows, String queryData){
		return daoManage.query(page, rows, queryData);
	}
	
	public void add(User user){
		if(user.getRole() == 2){
			user.setCla("");
		}
		daoManage.add(user);
	}
	public void update(User user){
		if(user.getRole() == 2){
			user.setCla("");
		}
		daoManage.update(user);
	}
	public void delete(int id){
		User user = new User();
		user.setId(id);
		daoManage.delete(user);
	}

	
	public int updatePwd(String pwd, int id) {
		return daoManage.updatePwd(pwd,id);
	}
}
