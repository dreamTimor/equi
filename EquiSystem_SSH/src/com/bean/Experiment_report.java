package com.bean;
//			 ѧ��ʵ�鱨��
public class Experiment_report {
	
	private int id;
	private String score;//教师评分
//	���
	private String course;//课程
	private String stu_name;//姓名
	private String stu_id;//学号
	private String cls;//班级
	private String inspector;//指导老师
	
	private String filename;//实验报告文件名
	
	
	public Experiment_report() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Override
	public String toString() {
		return "Experiment_report [id=" + id + ", score=" + score + ", course="
				+ course + ", stu_name=" + stu_name + ", stu_id=" + stu_id
				+ ", cls=" + cls + ", inspector=" + inspector + ", filename="
				+ filename + "]";
	}
	
}
