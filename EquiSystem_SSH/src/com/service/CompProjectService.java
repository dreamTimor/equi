package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Comp_project;
import com.dao.CompProjectDao;

@Service
@Scope("prototype")
public class CompProjectService {
	@Autowired
	private CompProjectDao daocomp;
	
	
	public List<Comp_project> getList(String page, String rows){
		return daocomp.getList(page, rows); 
	}
	public int getListSize(){
		return daocomp.getListSize();
	}
	public List<Comp_project> query(String queryData){
		return daocomp.query(queryData);
	}
	
	public int add(Comp_project comp){
		return daocomp.add(comp);
	}
	public int update(Comp_project comp){
		return daocomp.update(comp);
	}
	public int delete(int id){
		Comp_project comp = new Comp_project();
		comp.setId(id);
		return daocomp.delete(comp);
	}
	
}
