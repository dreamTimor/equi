package com.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bean.Experiment_report;
import com.bean.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.ExperimentReportService;

@Controller
@Scope("prototype")
public class ExperimentReportAction extends ActionSupport implements ModelDriven<Experiment_report>{
//	页面交互数据
	private JSONObject result=null;
	private Experiment_report experiment = new Experiment_report();
	public String queryData;
	public String page;
	public String rows;
	Map<String, Object> map = new HashMap<String, Object>();
//	Service层
	@Autowired
	private ExperimentReportService serviceExperiment;
//	上传下载
	private File icon;//临时文件
	private String iconFileName;//文件名
	private String iconContentType;//文件类型
//	String path = "F:/work/Java-Web-Class/file";//上传地址
	String path = ServletActionContext.getServletContext().getRealPath("/");
	File filepath = null;//变量：用于下载
	
	
	
	
	public String getList(){
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		List<Experiment_report> experiments;
		int total;
		
		if(queryData != null){
			System.out.println("条件："+queryData);
			experiments = serviceExperiment.query(queryData, user);
			total = experiments.size();
		}else{
			experiments = serviceExperiment.getList(page, rows, user);
			total = serviceExperiment.getListSize();
		}
		map.put("total", total);
		map.put("rows", experiments);
		
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	
	
	public String delete(){
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		
		map.put("s", serviceExperiment.delete(id));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	
	
	/**
	 * 上传
	 * @return
	 */
	public String upload(){
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
//		添加上传路径；目录不存在则创建。
		System.out.println("上传路径："+path+"FileAndVideo");
		File file2 = new File(path+"FileAndVideo");
		if(!file2.exists()){
			file2.mkdirs();
		}
		try {//		将临时文件保存到指定目录下
			FileUtils.copyFile(icon, new File(file2, iconFileName));
			map.put("s", serviceExperiment.add(experiment, iconFileName));
		} catch (Exception e) {
			map.put("s", 0);
		}
		
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	/**
	 * 下载
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String download() throws UnsupportedEncodingException{
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		String filename = URLDecoder.decode(request.getParameter("filename"),"UTF-8");
		System.out.println("Action测试："+path + "FileAndVideo" + "\\"  + filename);
		
		filepath = new File( path + "FileAndVideo" + "\\"  + filename);
		return SUCCESS;
	}
	
	
	
	
	
	
	
	//提供下载流
	public InputStream getFilepathStream() throws Exception{
		return new FileInputStream(filepath);
	}
	public String getFilepathName(){
		return filepath.getName();
	}
	public void setIcon(File icon) {
		this.icon = icon;
	}
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}
	public void setIconContentType(String iconContentType) {
		this.iconContentType = iconContentType;
	}
	public JSONObject getResult(){
		return result;
	}
	@Override
	public Experiment_report getModel() {
		return experiment;
	}
}
