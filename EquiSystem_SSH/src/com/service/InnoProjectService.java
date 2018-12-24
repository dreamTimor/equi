package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Inno_project;
import com.dao.InnoProjectDao;

@Service
@Scope("prototype")
public class InnoProjectService {
	@Autowired
	private InnoProjectDao daoInno;
	
	public List<Inno_project> getList(String page, String rows){
		return daoInno.getList(page, rows);
	}
	public int getListSize(){
		return daoInno.getListSize();
	}
	public List<Inno_project> query(String queryData){
		return daoInno.query(queryData);
	}
	
	
	public int add(Inno_project inno){
		return daoInno.add(inno);
	}
	public int update(Inno_project inno){
		return daoInno.update(inno);
	}
	public int delete(int id){
		Inno_project inno = new Inno_project();
		inno.setId(id);
		return daoInno.delete(inno);
	}
}
