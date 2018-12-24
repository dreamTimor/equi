package com.bean;

import java.util.Date;

public class Upload {
	
	private int id;
	private String username;//上传人
	private String filename;//文件名，用于下载调用
	private Date uploadTime;//上传时间
	private int fileType;
	public Upload() {
		super();
	}
	
	
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Override
	public String toString() {
		return "Upload [id=" + id + ", username=" + username + ", filename="
				+ filename + ", uploadTime=" + uploadTime + ", fileType="
				+ fileType + "]";
	}
}
