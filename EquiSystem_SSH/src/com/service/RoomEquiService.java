package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Room_equi;
import com.dao.RoomEquiDao;

@Service
@Scope("prototype")
public class RoomEquiService {
	@Autowired
	private RoomEquiDao daoRoomEqui;
	
	
	public List<Room_equi> getList(String page, String rows){
		return daoRoomEqui.getList(page, rows); 
	}
	public int getListSize(){
		return daoRoomEqui.getListSize();
	}
	public List<Room_equi> query(String queryData){
		return daoRoomEqui.query(queryData);
	}
	
	
	public int add(Room_equi roomEqui){
		return daoRoomEqui.add(roomEqui);
	}
	public int update(Room_equi roomEqui){
		return daoRoomEqui.update(roomEqui);
	}
	public int delete(int id){
		Room_equi roomEqui = new Room_equi();
		roomEqui.setId(id);
		return daoRoomEqui.delete(roomEqui);
	}
	
}
