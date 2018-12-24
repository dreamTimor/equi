package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Lab_equi;
import com.bean.User;
import com.dao.LabEquiDao;


@Service
@Scope("prototype")
public class LabEquiService {
	@Autowired
	private LabEquiDao daoLab;
	
	public List<Lab_equi> getList(String page, String rows){
		return daoLab.getList(page, rows);
	}
	public int getListSize(){
		return daoLab.getListSize();
	}
	public List<Lab_equi> query(String page, String rows, String queryData){
		return daoLab.query(page, rows, queryData);
	}
	
	public void add(Lab_equi lab_equi){
		daoLab.add(lab_equi);
	}
	public void delete(int id){
		Lab_equi lab = new Lab_equi();
		lab.setId(id);
		daoLab.delete(lab);
	}
	public void update(Lab_equi lab_equi){
		daoLab.update(lab_equi);
	}
}
