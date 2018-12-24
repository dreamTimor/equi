package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Equi_get;
import com.dao.EquiGetDao;

@Service
@Scope("prototype")
public class EquiGetService {

	@Autowired
	private EquiGetDao equiGetDao;
	
	public List<Equi_get> equiGetList(Integer page,Integer rows){	
		return equiGetDao.equiGetList(page, rows);
	}
	public int size() {
		return equiGetDao.size();
	}

	
	public Equi_get equiGetById(int id) {
		return equiGetDao.equiGetById(id);
	}
	
	public void addEquiGet(Equi_get eg) {
		equiGetDao.addEquiGet(eg);
	}
	// 编辑设备领取登记
	public void updateEquiGet(Equi_get eg) {
		equiGetDao.updateEquiGet(eg);
	}
	// 删除设备领取登记
	public void deleteEquiGet(Equi_get eg) {
		equiGetDao.deleteEquiGet(eg);
	}
	
	public List<Equi_get> query(Integer page, Integer rows, String queryData){
		return equiGetDao.query(page, rows, queryData);
	}
}
