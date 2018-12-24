package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Equi_paylist;
import com.bean.User;
import com.dao.EquiPayListDao;

@Service
@Scope("prototype")
public class EquiPayListService {

	@Autowired
	private EquiPayListDao equiPayListDao;
	
	public List<Equi_paylist> getAllById(int uid){
		return equiPayListDao.getAllById(uid);
	}
	
	public Equi_paylist getPayById(int id) {
		return equiPayListDao.getPayById(id);
	}
	
	public void addEquiPayList(Equi_paylist paylist) {
		equiPayListDao.addEquiPayList(paylist);
	}
	
	public void updatePaylist(Equi_paylist paylist) {
		equiPayListDao.updateProduct(paylist);
	}
	
	public void deleteProduct(Equi_paylist paylist) {
		equiPayListDao.deleteProduct(paylist);
	}
	
	public List<Equi_paylist> listAllPaylist(String page, String rows){
		return equiPayListDao.listAllPaylist(page, rows);
	}
	
	public int getAllPayListSize() {
		return equiPayListDao.getAllPayListSize();
	}
	
	//分页
	public List<Equi_paylist> getPayList(String page, String rows,int uid) {
		return equiPayListDao.getPayList(page, rows, uid);
	}
	public int getPayListSize(int uid) {
		return equiPayListDao.getPayListSize(uid);
	}
	
	//分页条件查询
	public List<Equi_paylist> query(String page, String rows, String queryData,User user) {
		return equiPayListDao.query(page, rows, queryData, user);
	}
	//public int querysize
	
	public int querysize() {
		return equiPayListDao.querysize();
	}
	
	
}
