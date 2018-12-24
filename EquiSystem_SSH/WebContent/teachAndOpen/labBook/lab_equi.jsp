<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*,java.util.*,net.sf.json.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>实验室简介</title>
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
									height : $("#body").height() - $('#search_area').height() - 5,
									width : $("#body").width(),
									idField : 'id',
									url : 'LabEquiAction_getList',
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
									nowrap : false,//内容超出列宽，自动换行
									remoteSort : false,
									columns : [ [ {
										field : 'name',
										title : '实验室名',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'charger',
										title : '负责人',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'build_info',
										title : '实验室简介',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}] ], 
									toolbar : '#tt_btn',
									//pagination : true,
									onDblClickRow : function(rowIndex, rowData) {
										viewDetail(rowData.userId);
									}
								});
				
				//双击数据提示框
				function viewDetail(date, id) {
					var data = $('#tt').datagrid('getSelected');
					alert(data.build_info);
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
				
				
				//添加弹窗
				$("#save").on("click", function() {
					$('#add_win').window('open');
				});
				//修改弹窗
				$("#update").on("click", function() {
					var data = $('#tt').datagrid('getSelected');
					if(data != null){
						$('#update_win').window('open');
						$('#update_form').form('load',data);
					}else{
						$.messager.alert("提示框","请选择要改的数据");
					}
				});
				//删除
				$("#delete").on("click", function() {
					//返回选中行的数据
					var data = $('#tt').datagrid('getSelections');
					if(data.length>0){
						$.messager.confirm("提示","确认删除"+data[0].people+"吗？",function(r){
							if(r){
								var id = data[0].id;
								$.post("LabEquiAction_delete",{"id":id},function(result){
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
			
				
			
				//添加窗口的确认键
				$("#addUser_saveAndAdd").on("click",function(){
					$('#add_form').form('submit',{
						url : "LabEquiAction_add",
						success : function(result){
							if(result){
								$("#add_win").window("close");
								$("#tt").datagrid("reload");
							}else{
								$.messager.alert("提示消息","提交失败","info");
							}
						}
					});
				});
				//添加的取消键
				$("#addUser_cancel").on("click", function(){
					$("#add_win").window("close");
				});
				
				//修改的确定键
				$("#editUser_saveAndEdit").on("click", function(){
					$("#update_form").form('submit', {
						url : "LabEquiAction_update",
						success : function(result){
							if(result){
								$("#update_win").window("close");
								$("#tt").datagrid("reload");
							}else{
								$.messager.alert("提示","修改失败","info");
							}
						}
					});
				});
				//修改的取消键
				$("#editUser_cancel").on("click", function(){
					$("#update_win").window("close");
				});
				
				//查询功能
				$('#query').on('click',function(){
					$('#tt').datagrid('load',{'queryData' : $('#condition').val()});
				});
				
				
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
			          <td>查询条件:</td>
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
			<c:if test="${user.role==1 }">
				<a href="javascript:void(0)" id="save" class="easyui-linkbutton"
					iconCls="icon-add" plain="true">添加实验室简介</a> 
				<a href="javascript:void(0)" id="update" class="easyui-linkbutton"
				 iconCls="icon-edit" plain="true">修改</a> 
				<a href="javascript:void(0)" id="delete" class="easyui-linkbutton" 
					iconCls="icon-remove" plain="true">删除</a>
			</c:if>
		</div>
		
		
		
		<!-- 添加弹出框 -->
		<div id="add_win" class="easyui-window" title="添加实验室简介"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 400px; padding: 10px;">
			<form id="add_form" method="post">
				<table width="280" border="0" align="center" cellpadding="4">
					<tr>
						<td width="30" align="center"><label for="name"><span class="x">*</span>实验室名</label>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td width="30" align="center"><label for="charger"><span class="x">*</span>负责人&nbsp&nbsp&nbsp</label>
							<input type="text" name="charger" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td width="30" align="center"><label for="build_info"><span
								class="x">*</span>实验室简介</label></td>
					</tr>
					<tr>
						<td width="200" align="center">
							<input name="build_info" class="easyui-textbox" data-options="multiline:true" 
									  style="width:300px;height:150px"/>
						</td>
					</tr>
				</table>
			</form>
			<!-- 添加和取消按钮 -->
			<div class="windowButton" style="text-align:center;padding:5px">
				<a id="addUser_saveAndAdd" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-ok" href="javascript:void(0)"> 保存并刷新</a>
			    <a id="addUser_cancel" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-cancel" href="javascript:void(0)">取消</a>
			</div>
		</div>
		<!-- 添加弹出框结束-->
		
		<!-- 修改弹出框开始-->
		<div id="update_win" class="easyui-window" title="修改实验室简介"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="update_form" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr style="display: none">
						<td><input type="text" name="id" id="id"/></td>
					</tr>
					<tr>
						<td width="30" align="center"><label for="name"><span class="x">*</span>实验室名</label>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td width="30" align="center"><label for="charger"><span class="x">*</span>负责人&nbsp&nbsp&nbsp</label>
							<input type="text" name="charger" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td width="30" align="center"><label for="build_info"><span
								class="x">*</span>实验室简介</label></td>
					</tr>
					<tr>
						<td width="200" align="center">
							<input type="text" name="build_info" class="easyui-textbox"
								data-options="multiline:true" style="width:300px;height:150px"/>
						</td>
					</tr>
				</table>
			</form>
			<!-- 保存修改和取消按钮 -->
			<div class="windowButton" style="text-align:center;padding:5px">
				<a id="editUser_saveAndEdit" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-ok" href="javascript:void(0)">保存并修改</a>
			    <a id="editUser_cancel" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-cancel" href="javascript:void(0)">取消</a>
			</div>
		</div>
		<!-- 修改弹出框结束-->
		
	</div>
</body>




</html>
