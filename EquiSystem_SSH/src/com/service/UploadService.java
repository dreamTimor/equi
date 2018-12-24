package com.service;


import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bean.Upload;
import com.bean.User;
import com.dao.UploadDao;

@Service
@Scope("prototype")
public class UploadService {
	@Autowired
	private UploadDao daoupload;
	
	/**
	 * 将数据封装进upload对象，传入dao
	 * @param upload
	 * @param filename
	 * @return
	 */
	public int add(String iconFileName, Upload upload){
//		获取当前用户姓名
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		upload.setUsername(user.getName());
		upload.setFilename(iconFileName);
		upload.setUploadTime(new Date());
		daoupload.add(upload);
		return 1;
	}
//	返回全部记录
	public List<Upload> getAll(int fileType){
		if(fileType==1){
			return daoupload.getAll();
		}else {
			return daoupload.getVedeo();
		}
	}
//	分页返回数据
	public List<Upload> getList(String page, String rows){
		return daoupload.getList(page, rows);
	}
//	返回全部记录的长度
	public int getSize(){
		return daoupload.getSize();
	}
//	查询：不分页
	public List<Upload> query(String queryData){
		return daoupload.query(queryData);
	}
//	修改
	public int update(Upload uploadObject){
		return daoupload.update(uploadObject);
	}
//	删除
	public int delete(int id){
		Upload uploadObject = new Upload();
		uploadObject.setId(id);
		return daoupload.delete(uploadObject);
	}

}
