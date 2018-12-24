package com.service;

import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Equi_repair;
import com.bean.User;
import com.dao.EquiRepairDao;

@Service
public class EquiRepairService {
	@Autowired
	private EquiRepairDao daoEquiRepair;
	
	
	
	public List<Equi_repair> getList(String page, String rows){
		return daoEquiRepair.getList(page, rows); 
	}
	public int getListSize(){
		return daoEquiRepair.getListSize();
	}
	public List<Equi_repair> query(String queryData){
		return daoEquiRepair.query(queryData);
	}
	
	
	public int add(Equi_repair equiRepair){
		equiRepair.setTime(new Date());
		return daoEquiRepair.add(equiRepair);
	}
	public int update(Equi_repair equiRepair){
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		equiRepair.setSolve_people(user.getName());
		return daoEquiRepair.update(equiRepair);
	}
	public int delete(int id){
		Equi_repair equiRepair = new Equi_repair();
		equiRepair.setId(id);
		return daoEquiRepair.delete(equiRepair);
	}
	
	
	
}
