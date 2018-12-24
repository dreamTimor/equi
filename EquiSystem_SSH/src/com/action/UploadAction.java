package com.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.support.DaoSupport;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Controller;

import com.bean.Upload;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.UploadService;
import com.utils.JsonDateValueProcessorUtil;
@Controller
@Scope("prototype")
public class UploadAction extends ActionSupport implements ModelDriven<Upload>{
//	页面交互数据
	private JSONObject result = null;//JSON数据转换
	Map<String, Object> map = new HashMap<String, Object>();//用于返回给页面判断
	public Upload uploadObject = new Upload();//模型驱动
	public String page;//分页的页数
	public String rows;//分页每页的数据数
	public String queryData;//查询条件
//	Service层
	@Autowired
	private UploadService serviceupload;
//	获取项目地址
	String path = ServletActionContext.getServletContext().getRealPath("/");
//	String path = "F:/work/Java-Web-Class/Test";
//	上传下载
	private File icon;//临时文件
	private String iconFileName;//文件名
	private String iconContentType;//文件类型
	File filepath = null;//下载地址
	//提供下载流
	public InputStream getFilepathStream() throws Exception{
		return new FileInputStream(filepath);
	}

	
	
	/**
	 * 获取文档的数据，放入session域
	 * @return
	 */
	public String getListSession(){
		List<Upload> uploads = serviceupload.getAll(1);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("uploads", uploads);
		return "list";
	}
	/**
	 * 获取视频的数据，放入Session
	 * @return
	 */
	public String getVideoListSession(){
		List<Upload> uploads = serviceupload.getAll(2);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("uploads", uploads);
		return "video";
	}
	

	/**
	 * 上传功能
	 */
	public String upload() throws Exception {
//		添加上传路径的同时，加一个新文件夹
		File file2 = new File(path+"FileAndVideo");
//		若是目录不存在，则创建
		if(!file2.exists()){
			file2.mkdirs();
		}
//		将临时文件保存到指定目录下
		try {
			FileUtils.copyFile(icon, new File(file2, iconFileName));
			map.put("s", serviceupload.add(iconFileName, uploadObject));
		} catch (Exception e) {
			System.out.println("上传失败");
			map.put("s", 0);
		}
//		返回是否成功
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	/**
	 * 下载 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String download() throws UnsupportedEncodingException{
//		从请求域里拿到文件名
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		String filename = URLDecoder.decode(request.getParameter("name"),"UTF-8");
//		将地址和文件名结合，读取后下载
		filepath = new File( path + "FileAndVideo" +"\\"+ filename);
		return SUCCESS;
	}
	/**
	 * 播放视频
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String play() throws UnsupportedEncodingException{
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		String videoName = URLDecoder.decode(request.getParameter("videoName"), "UTF-8");
		System.out.println("路径：http://192.21.100.19:8080/EquiSystem_SSH/FileAndVideo/"+videoName);
		request.setAttribute("videoPath", "http://192.21.100.19:8080/EquiSystem_SSH/FileAndVideo/"+videoName);
		return "video";
	}
	
	
	
	
	
	/**
	 * 获取数据转换JSON后传给jsp页面
	 * @return
	 */
	public String getList(){
		List<Upload> uploads;
		int total;
		if(queryData != null){
			uploads = serviceupload.query(queryData);
			total = uploads.size();
		}else{
			uploads = serviceupload.getList(page, rows);
			total = serviceupload.getSize();
		}
		map.put("total", total);
		map.put("rows", uploads);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessorUtil());
		result = JSONObject.fromObject(map, jsonConfig);
		return SUCCESS;
	}
	public String update(){
		map.put("s", serviceupload.update(uploadObject));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}
	public String delete(){
		HttpServletRequest request = (HttpServletRequest)ServletActionContext.getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		map.put("s", serviceupload.delete(id));
		result = JSONObject.fromObject(map);
		return SUCCESS;
	}

	
	
	
	
	
	
	
	public JSONObject getResult(){
		return result;
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
	public String getFilepathName(){
		return filepath.getName();
	}
	@Override
	public Upload getModel() {
		return uploadObject;
	}

	
}
