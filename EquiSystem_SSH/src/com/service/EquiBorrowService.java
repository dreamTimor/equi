package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Equi_borrow;
import com.bean.User;
import com.dao.EquiBorrowDao;

/**
 * 设备借用服务层
 * @author dreamTimor
 *
 */
public class EquiBorrowService {
	private EquiBorrowDao equiBorrowDao;
	
	public void setEquiBorrowDao(EquiBorrowDao equiBorrowDao) {
		this.equiBorrowDao = equiBorrowDao;
	}

	public List<Equi_borrow> getAllByUid(User user, String page, String rows){
		return equiBorrowDao.getAllByUid(user,page,rows);
	}

	public void addEquiBorrow(Equi_borrow equi_borrow) {
		equiBorrowDao.addEquiBorrow(equi_borrow);
	}

	public int delEquiBorrowById(int id) {
		return equiBorrowDao.delEquiBorrowById(id);
	}

	public void updateEquiBorrowById(Equi_borrow preEqui_borrow) {
		equiBorrowDao.updateEquiBorrowById(preEqui_borrow);
	}

	public int getAllCount() {
		return equiBorrowDao.getAllCount();
	}

	public List<Equi_borrow> query(String data, String page, String rows, User user) {
		return equiBorrowDao.query(data,page,rows,user);
	}

	public int returnEqui(int equiId) {
		return equiBorrowDao.returnEqui(equiId);
	}
	
}
