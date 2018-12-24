<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*,java.util.*,net.sf.json.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>实验报告</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath }/js/jquery-easyui-1.3.5/themes/gray/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath }/js/jquery-easyui-1.3.5/themes/icon.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/js/jquery-easyui-1.3.5/jquery-1.10.2.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/js/extends.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/js/common.js"></script>
		<script>
			$(function() {
				$("#tt").datagrid(
								{
									height : $("#body").height()
											- $('#search_area').height() - 5,
									width : $("#body").width(),
									idField : 'id',
									url : 'ExperimentReportAction_getList',
									striped : true,
									singleSelect : true,
									nowrap : true,
									fitColumns : true,
									rownumbers : true,
									showPageList : false,
									pagination : true,
									pageSize : 20,
									pageList : [10,20,30,40],
									remoteSort : false,
									columns : [ [ {
										field : 'filename',
										title : '实验报告',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'stu_name',
										title : '学生姓名',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'stu_id',
										title : '学号',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'cls',
										title : '班级',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'course',
										title : '课程',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'inspector',
										title : '指导老师',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'score',
										title : '评分',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}] ], 
									toolbar : '#tt_btn',
									onDblClickRow : function(rowIndex, rowData) {
										viewDetail(rowData.userId);
									}
								});
				
				//双击数据提示框
				function viewDetail(date, id) {
					$parent.messager.alert("提示框", "", "info");
				};
	
				//窗口改变触发事件,添加了延时器
				window.onresize = function() {
					setTimeout(domresize, 300);
				};
				//
				function domresize() {
					$('#tt').datagrid(
							'resize',
							{
								height : $("#body").height()- $('#search_area').height() - 5,
								width : $("#body").width()
							});
				};				
				
				//删除
				$("#delete").on("click", function() {
					//对象形式获取选中行的数据
					var data = $('#tt').datagrid('getSelections');
					if(data.length>0){
						$.messager.confirm("提示","确认删除"+data[0].filename+"文件吗？",function(r){
							if(r){
								var id = data[0].id;
								//发送post请求给Action，同时发送id数据，并接收返回值确认是否成功删除
								$.post("ExperimentReportAction_delete",{"id":id},function(result){
									if(result){
										$('#tt').datagrid('reload');
										
									}else{
										$.messager.alert({title:"ERROR", msg:"删除失败"});
									}
								},'json');
							}
						});
					}else{
						$parent.messager.alert({title:'提示', msg:'请至少选中一条记录'});
					}
				});
			
				
				//查询功能
				$('#query').on('click',function(){
					$('#tt').datagrid('load',{'queryData' : $('#condition').val()});
				});
				
				// 上传文件的弹出框
				$("#upload").click(function(){
					$("#upload_win").window("open");
				});
				// 上传窗口的保存
				$("#upload_save").click(function(){
					//alert("上传成功");
					$("#upload_form").form("submit",{
						url : "ExperimentReportAction_upload",
						success : function(result){
							if(result[5]==1){
								alert("上传成功");
								$("#upload_win").window("close");
								$("#tt").datagrid("reload");
							}else{
								alert("上传失败");
							}
						}
					});
				});
				//上传窗口的取消键
				$("#upload_cancel").click(function(){
					$("#upload_win").window("close");
				});
				
				//下载按钮：获取当前选中的数据的文件名进行下载
				$("#download").click(function(){
					var data = $("#tt").datagrid("getSelections");
					if(data.length>0){
						var url = "ExperimentReportDownload_download?filename="+encodeURI(encodeURI(data[0].filename));
						document.getElementById("download").href = url;
					}else{
						$.messager.alert("提示","请选中数据","info");
					}
				})
				
				
			})//结尾
</script>
</head>

<body class="easyui-layout">

	 

	<div id="body" region="center">

		<!-- 查询 -->
		<div id="search_area" >
		    <div id="conditon">
			      <table border="0">
			        <tr>
			          <td>查询条件:</td>
			          <td ><input  name="condition" id="condition" /></td>
			          <td>
			              <a  href="javascript:void(0)" id="query" 
			              class="easyui-linkbutton my-search-button" iconCls="icon-search" plain="true">查询</a> 
			          </td>
			        </tr>
			      </table>
		    </div>
		    <span id="openOrClo --%>se"></span> 
		</div>


		<!-- 数据显示 -->
		<table id="tt" style="table-layout: fixed;"></table> 


		<!-- 按键触发页面功能 -->
		<div id="tt_btn">
			<c:if test="${user.role==3 }">
				<a href="javascript:void(0)" id="upload" class="easyui-linkbutton"
			 		iconCls="icon-save" plain="true">上传</a> 
			</c:if>
			<a href="javascript:void(0)" id="download" class="easyui-linkbutton"
			 	iconCls="icon-save" plain="true">下载</a> 
			<a href="javascript:void(0)" id="delete" class="easyui-linkbutton" 
				iconCls="icon-remove" plain="true">删除</a>
		</div>
		
		<!-- 上传文件弹出框开始-->
		<div id="upload_win" class="easyui-window" title="修改密码"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 400px; height: 300px; padding: 5px;">
			<form id="upload_form" action="" method="post" enctype="multipart/form-data">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr>
						<td align="right"><label for="icon"><span class="x">*</span>选择文件</label></td>
						<td><input type="file" name="icon"></td>
					</tr>
					<tr>
						<td align="right"><label for="course"><span class="x">*</span>课程名</label></td>
						<td><input type="text" name="course" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="stu_name"><spanclass="x">*</span>姓名</label></td>
						<td><input type="text" name="stu_name" value="${user.name }" readonly="readonly"/></td>
					</tr>
					<tr>
						<td align="right"><label for="stu_id"><spanclass="x">*</span>学号</label></td>
						<td><input type="text" name="stu_id" value="${user.username }" readonly="readonly"/></td>
					</tr>
					<tr>
						<td align="right"><label for="cls"><spanclass="x">*</span>班级</label></td>
						<td><input type="text" name="cls" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="inspector"><spanclass="x">*</span>教师</label></td>
						<td><input type="text" name="inspector" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
				</table>
			</form>
			<!-- 保存修改和取消按钮 -->
			<div class="windowButton" style="text-align:center;padding:5px">
				<a id="upload_save" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-ok" href="javascript:void(0)">确定上传</a>
			    <a id="upload_cancel" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-cancel" href="javascript:void(0)">取消</a>
			</div>
		</div>
		<!-- 上传文件弹出框结束-->
		
		
	</div>
</body>




</html>
