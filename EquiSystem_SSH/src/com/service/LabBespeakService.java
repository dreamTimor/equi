package com.service;

import java.util.List;








import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Lab_bespeak;
import com.bean.User;
import com.dao.LabBespeakDao;

@Service
@Scope("prototype")
public class LabBespeakService {
	@Autowired
	private LabBespeakDao daoLab;
	
	
//	分页显示
	public List<Lab_bespeak> getList(String page, String rows, User user){
		return daoLab.getList(page, rows, user);
	}
//	分页查询的总记录数
	public int getListSize(){
		return daoLab.getListSize();
	}
//	查询后分页显示
	public List<Lab_bespeak> query(String page, String rows, String queryData,User user){
		return daoLab.query(page, rows, queryData,user);
	}
	
	
//	添加
	public void add(Lab_bespeak lab) {
		daoLab.add(lab);
	}
//	删除
	public void delete(int id){
		Lab_bespeak lab = new Lab_bespeak();
		lab.setId(id);
		daoLab.delete(lab);
	}
//	修改
	public void update(Lab_bespeak bespeak){
		daoLab.update(bespeak);
	}
}
