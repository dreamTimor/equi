<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*,java.util.*,net.sf.json.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>竞赛项目管理</title>
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
									url : 'CompProjectAction_getList',
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
									columns : [ [ {
										field : 'name',
										title : '竞赛名称',
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
										field : 'members',
										title : '参赛成员',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'bor_equi',
										title : '借用设备',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'enter_time',
										title : '参赛时间',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'funds',
										title : '报销经费',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'award',
										title : '获奖情况',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'is_attend',
										title : '是否参赛',
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
				
				//添加弹窗
				$("#add").on("click", function(){
					$("#add_win").window("open");
				});
				//修改弹窗
				$("#update").on("click", function() {
					//对象形式获取鼠标选中的那行数据
					var data = $('#tt').datagrid('getSelected');
					//确定选中数据，不为空
					if(data != null){
						//弹出修改窗口
						$('#update_win').window('open');
						//将数据放到窗口表格上
						$('#update_form').form('load',data);
					}else{
						$.messager.alert("提示框","请选择要改的数据");
					}
				});
				//删除
				$("#delete").on("click", function() {
					//对象形式获取选中行的数据
					var data = $('#tt').datagrid('getSelections');
					if(data.length>0){
						$.messager.confirm("提示","确认删除"+data[0].filename+"文件吗？",function(r){
							if(r){
								var id = data[0].id;
								//发送post请求给Action，同时发送id数据，并接收返回值确认是否成功删除
								$.post("CompProjectAction_delete",{"id":id},function(result){
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
			
				
				//添加的确定键
				$("#addUser_saveAndAdd").on("click", function(){
					$("#add_form").form("submit", {
						url : "CompProjectAction_add",
						success : function(result){
							if(result[5]==1){
								$("#add_win").window("close");
								$("#tt").datagrid("reload");
							}else{
								alert("添加失败");
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
						url : "CompProjectAction_update",
						success : function(result){
							if(result[5]==1){
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
			<a href="javascript:void(0)" id="add" class="easyui-linkbutton"
				 iconCls="icon-edit" plain="true">添加竞赛记录</a> 
			<a href="javascript:void(0)" id="update" class="easyui-linkbutton"
			 iconCls="icon-edit" plain="true">修改</a> 
			<a href="javascript:void(0)" id="delete" class="easyui-linkbutton" 
				iconCls="icon-remove" plain="true">删除</a>
		</div>
		
		
		<!-- 添加弹出框 -->
		<div id="add_win" class="easyui-window" title="实验室申请"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="add_form" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr>
						<td align="right"><label for="name"><span class="x">*</span>竞赛名称</label></td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td width="80" align="right"><label for="inspector"><span class="x">*</span>指导老师</label></td>
						<td width="200">
							<input type="text" name="inspector" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td align="right"><label for="members"><span class="x">*</span>参赛成员</label></td>
						<td><input type="text" name="members" class= "easyui-validatebox" required ="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="bor_equi"><span class="x">*</span>借用设备</label></td>
						<td><input type="text" name="bor_equi" class= "easyui-validatebox" required ="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="enter_time"><span class="x">*</span>参赛时间</label></td>
						<td><input type="text" name="enter_time" class= "easyui-datebox" required ="required" /></td>
					</tr>
					<tr>
						<td align="right"><label for="funds"><span class="x">*</span>报销经费</label></td>
						<td><input type="text" name="funds" class= "easyui-validatebox" required ="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="is_attend"><span class="x">*</span>是否参赛</label></td>
						<td>
							<select name="is_attend">
								<option value="是" selected="selected">是</option>
								<option value="否">否</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right"><label for="award"><span class="x">*</span>获奖情况</label></td>
						<td><input type="text" name="award" class= "easyui-validatebox" required ="required"/></td>
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
		<div id="update_win" class="easyui-window" title="实验室申请修改"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="update_form" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr style="display:none">
						<td><input type="text" name="id" /></td>
					</tr>
					<tr>
						<td align="right"><label for="name"><span class="x">*</span>竞赛名称</label></td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td width="80" align="right"><label for="inspector"><span class="x">*</span>指导老师</label></td>
						<td width="200">
							<input type="text" name="inspector" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td align="right"><label for="members"><span class="x">*</span>参赛成员</label></td>
						<td><input type="text" name="members" class= "easyui-validatebox" required ="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="bor_equi"><span class="x">*</span>借用设备</label></td>
						<td><input type="text" name="bor_equi" class= "easyui-validatebox" required ="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="enter_time"><span class="x">*</span>参赛时间</label></td>
						<td><input type="text" name="enter_time" class= "easyui-datebox" required ="required" /></td>
					</tr>
					<tr>
						<td align="right"><label for="funds"><span class="x">*</span>报销经费</label></td>
						<td><input type="text" name="funds" class= "easyui-validatebox" required ="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="is_attend"><span class="x">*</span>是否参赛</label></td>
						<td>
							<select name="is_attend">
								<option value="是" selected="selected">是</option>
								<option value="否">否</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right"><label for="award"><span class="x">*</span>获奖情况</label></td>
						<td><input type="text" name="award" class= "easyui-validatebox" required ="required"/></td>
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
