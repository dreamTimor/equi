<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*,java.util.*,net.sf.json.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>实验室预约</title>
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
									idField : 'no',
									url : 'LabBespeakAction_getList',
									singleSelect : true,
									nowrap : true,
									fitColumns : true,
									rownumbers : true,
									showPageList : false,
									columns : [ [ {
										field : 'people',
										title : '预约人',
										width : 100,
										halign : "center",
										align : "center"
									}, {
										field : 'room',
										title : '预约课室',
										width : 100,
										halign : "center",
										align : "center"
									}, {
										field : 'usetime',
										title : '日期',
										width : 100,
										halign : "center",
										align : "center"
									}, {
										field : 'node',
										title : '节次',
										width : 100,
										halign : "center",
										align : "center"
									}, {
										field : 'user_explain',
										title : '用途',
										width : 100,
										halign : "center",
										align : "center"
									}, {
										field : 'is_succeed',
										title : '预约结果',
										width : 100,
										halign : "center",
										align : "center"
									}] ], 
									toolbar : '#tt_btn',
									pagination : true,
									onDblClickRow : function(rowIndex, rowData) {
										viewDetail(rowData.userId);
									}
								});
				
				// 添加弹窗键
				$("#save").on("click", function() {
					$("#add_win").window("open");
				});
				
				//修改弹窗键
				$("#update").on("click", function() {
					var data = $("#tt").datagrid("getSelected");
					if(data!=null){
						$("#update_win").window("open");
						$('#update_form').form('load',data);
					}else{
						$.messager.alert("提示框", "请选择要修改的数据");
					}
				});
				
				//删除键
				$("#delete").on("click", function() {
					//datagrid的方法，返回被选中的所有行(没有则为空数组)
					var data = $("#tt").datagrid("getSelections");
					if(data.length>0){
						//弹窗，确认删除则执行
						$.messager.confirm("提示","确认删除"+data[0].people+"吗？",function(r){
							if(r){
								var id = data[0].id;
								//提交请求
								$.post("LabBespeakAction_delete",{"id":id},function(result){
										if(result.success){
											$("#tt").datagrid("reload");
										}else{
											$.messager.show({
												title:"ERROR",
												msg:"删除失败"
											});
										}
								},'json');
							}
						});
					}else{
						$parent.messager.show({title:'提示',msg:'请至少选中一条记录'});
					}
				});
			

			//双击数据提示框
			function viewDetail(date, id) {
				/* $parent.messager.alert("提示框", "内容", "info"); */
				var flag = confirm("确定要删除吗");
				if(flag){
					var url = this.href;
					var args = {"time" : new Date()};
					$.post(url, args, function(date){
						
					});
				}
			}
			
			//窗口改变触发事件
			window.onresize = function() {
				setTimeout(domresize, 300);
			};
			//表格宽高自适应
			function domresize() {
				$('#tt').datagrid(
						'resize',
						{
							height : $("#body").height()- $('#search_area').height() - 5,
							width : $("#body").width()
						});
			}
			
			//模糊查询：获取查询条件，修改显示的请求url，换成查询
			$("#query").on("click",function(){
				var querydata = $("#condition").val();
				$('#tt').datagrid('options').url = "LabBespeakAction_query?querydata="+querydata;
				$('#tt').datagrid('reload');
			});
			//添加窗口的取消
			$("#addUser_cancel").on("click",function(){
				$("#add_form").form("clear");//清空表格
				$("#add_win").window("close");//关闭窗口
			});
			//修改窗口的取消
			$("#editUser_cancel").on("click",function(){
				$("#update_form").form("clear");
				$("#update_win").window("close");
			})
			//添加窗口的确认键
			$("#addUser_saveAndAdd").on("click",function(){
				$('#add_form').form('submit',{
					url : "LabBespeakAction_add",
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
			//修改窗口的确认键
			$("#editUser_saveAndEdit").on("click",function(){
				$('#update_form').form('submit',{
					url : "LabBespeakAction_update",
					success : function(result){
						if(result){
							$("#update_win").window("close");
							$("#tt").datagrid("reload");
						}else{
							$.messager.alert("提示消息","修改失败","info")
						}
					}
				});
			});
			
		});
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
				     <td ><input name="condition" id="condition" /></td>
				     <td>
				         <a  href="javascript:void(0)" id="query"class="easyui-linkbutton my-search-button"
				          iconCls="icon-search" plain="true">查询</a> 
				     </td>
				   </tr>
				 </table>
		    </div>
		    <span id="openOrClo --%>se"></span>
		</div>

		<!-- 数据显示表格 -->
		<table id="tt" style="table-layout: fixed;"></table> 

		<!-- 菜单工具键 -->
		<div id="tt_btn">
			<a href="javascript:void(0)" id="save" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">实验室申请</a> 
			<c:if test="${user.role==1 }">
				<a href="javascript:void(0)" id="update" class="easyui-linkbutton"
				 iconCls="icon-edit" plain="true">修改</a> 
				<a href="javascript:void(0)" id="delete" class="easyui-linkbutton" 
					iconCls="icon-remove" plain="true">删除</a>
			</c:if>
		</div>
		
		
		<!-- 添加弹出框 -->
		<div id="add_win" class="easyui-window" title="实验室申请"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="add_form" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr>
						<td width="80" align="right"><label for="room"><span
								class="x">*</span>实验室</label></td>
						<td width="200"><input class="easyui-validatebox" 
							data-options="required:true,validType:'length[1,20]',missingMessage:'不能为空',invalidMessage:'字符在1~20之间'" 
							type="text" name="room" id="room" /></td>
					</tr>
					<tr>
						<td align="right"><label for="usetime"><span class="x">*</span>使用日期</label></td>
						<td><input type="date" name="usetime" id="usetime" value="" /></td>
					</tr>
					<tr>
						<td align="right"><label for="node"><span class="x">*</span>节次</label></td>
						<td><input type="text" name="node" id="node" /></td>
					</tr>
					<tr>
						<td align="right"><label for="user_explain"><span class="x">*</span>用途</label></td>
						<td><input type="text" name="user_explain" id="user_explain" /></td>
					</tr>
					<tr>
						<td align="right"><label for="borrower"><span class="x">*</span>预约人</label></td>
						<td><input type="text" name="people" id="people"
							value="${user.name }" readonly="readonly" /></td>
					</tr>
					<tr style="display: none">
						<td><input type="text" name="is_succeed" id="state" value="未审批" /></td>
					</tr>
					<%-- <tr>
						<td align="right"><label for="occupation"><span
								class="x">*</span>职业</label></td>
						<td><input type="text" name="occupation" id="occupation"
							value="${user.role==2?'老师':'学生' }" readonly="readonly" /></td>
					</tr> --%>
					<%-- <tr style="display: none">
						<td><input type="text" name="uid" id="uid"
							value="${user.id }" /></td>
					</tr> --%>
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
					<tr style="display: none">
						<td><input type="text" name="id" id="id"
							value="${user.id }" /></td>
					</tr>
					<tr>
						<td width="80" align="right"><label for="room"><span
								class="x">*</span>实验室</label></td>
						<td width="200"><input class="easyui-validatebox" 
							data-options="required:true,validType:'length[1,20]',missingMessage:'不能为空',invalidMessage:'字符在1~20之间'" 
							type="text" name="room" id="room" /></td>
					</tr>
					<tr>
						<td align="right"><label for="usetime"><span
								class="x">*</span>使用日期</label></td>
						<td><input type="date" name="usetime" id="usetime" value="" /></td>
					</tr>
					<tr>
						<td align="right"><label for="node"><span
								class="x">*</span>节次</label></td>
						<td><input type="text" name="node" id="node" /></td>
					</tr>
					<tr>
						<td align="right"><label for="user_explain"><span class="x">*</span>用途</label></td>
						<td><input type="text" name="user_explain" id="user_explain" /></td>
					</tr>
					<tr>
						<td align="right"><label for="people"><span
								class="x">*</span>预约人</label></td>
						<td><input type="text" name="people" id="people"
							value="${user.name }" readonly="readonly" /></td>
					</tr>
					<tr style="display: none">
						<td><input type="text" name="is_succeed" id="is_succeed" value="未审批" /></td>
					</tr>
					<%-- <tr style="display:none">
						<td><input type="text" name="id" value=""/></td>
					</tr>
					<tr style="display: none">
						<td><input type="date" name="truetime" id="truetime" value="" /></td>
					</tr>
					<tr style="display: none">
						<td><input type="text" name="uid" id="uid"
							value="${user.id }" /></td>
					</tr> --%>
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
