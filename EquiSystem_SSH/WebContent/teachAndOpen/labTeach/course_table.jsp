<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*,java.util.*,net.sf.json.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>实验室课程预约</title>
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
									url : 'CourseTableAction_getList',
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
										field : 'teacher',
										title : '教师',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'room',
										title : '教室',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'begindate',
										title : '开始日期',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'enddate',
										title : '结束日期',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'day',
										title : '星期(一~五)',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'node',
										title : '节次',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'is_true',
										title : '是否预约成功',
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
								$.post("CourseTableAction_delete",{"id":id},function(result){
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
						url : "CourseTableAction_add",
						success : function(result){
							if(result){
								$("#add_win").window("close");
								$("#tt").datagrid("reload");
								$("#add_form").datagrid("clear");
							}else{
								$.messager.alert("提示消息","提交失败","info");
							}
						}
					});
				});
				//添加的取消键
				$("#addUser_cancel").on("click", function(){
					$("#add_win").window("close");
					$('#add_form').form('clear');
				});
				
				//修改的确定键
				$("#editUser_saveAndEdit").on("click", function(){
					$("#update_form").form('submit', {
						url : "CourseTableAction_update",
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
					$('#update_form').form('clear');
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
			          <td>姓名:</td>
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
			<a href="javascript:void(0)" id="save" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">实验室课程申请</a> 
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
						<td align="right"><label for="teacher"><span class="x">*</span>预约人</label></td>
						<td><input type="text" name="teacher" value="${user.name }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td width="80" align="right"><label for="room"><span class="x">*</span>教室</label></td>
						<td width="200">
							<input type="text" name="room" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td align="right"><label for="begindate"><span class="x">*</span>开始日期</label></td>
						<td><input type="text" name="begindate" class= "easyui-datebox" required ="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="enddate"><span class="x">*</span>结束日期</label></td>
						<td><input type="text" name="enddate" class= "easyui-datebox" required ="required" /></td>
					</tr>
					<tr>
						<td align="right"><label for="day"><span class="x">*</span>上课时间:</label></td>
						<td>
							<select name="day">
								<option value="星期一" selected="selected">星期一</option>
								<option value="星期二">星期二</option>
								<option value="星期三">星期三</option>
								<option value="星期四">星期四</option>
								<option value="星期五">星期五</option>
							</select>
							<select name="node">
								<option value="1~2节" selected="selected">1~2节</option>
								<option value="3~4节">3~4节</option>
								<option value="5~6节">5~6节</option>
								<option value="7~8节">7~8节</option>
								<option value="9~10节">9~10节</option>
							</select>
						</td>
					</tr>
					<tr style="display: none">
						<td><input type="text" name="is_true" value="未审批" /></td>
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
					<tr style="display: none">
						<td><input type="text" name="id" id="id"/></td>
					</tr>
					<tr>
						<td align="right"><label for="teacher"><span class="x">*</span>预约人</label></td>
						<td><input type="text" name="teacher" id="teacher"
							value="${user.name }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td width="80" align="right"><label for="room"><span
								class="x">*</span>教室</label></td>
						<td width="200"><input class="easyui-validatebox" type="text" name="room" id="room" /></td>
					</tr>
					<tr>
						<td align="right"><label for="begindate"><span class="x">*</span>开始日期</label></td>
						<td><input type="text" name="begindate" id="begindate" class= "easyui-datebox" required ="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="enddate"><span class="x">*</span>结束日期</label></td>
						<td><input type="text" name="enddate" id="enddate" class= "easyui-datebox" required ="required" /></td>
					</tr>
					<tr>
						<td align="right"><label for="day"><span class="x">*</span>上课时间:</label></td>
						<td>
							<select name="day">
								<option value="星期一">星期一</option>
								<option value="星期二">星期二</option>
								<option value="星期三">星期三</option>
								<option value="星期四">星期四</option>
								<option value="星期五">星期五</option>
							</select>
							<select name="node">
								<option value="1~2节">1~2节</option>
								<option value="3~4节">3~4节</option>
								<option value="5~6节">5~6节</option>
								<option value="7~8节">7~8节</option>
								<option value="9~10节">9~10节</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right"><label for="is_true"><span
								class="x">*</span>审核</label></td>
						<td>
							<select name="is_true">
								<option value="未审批" selected="selected">未审批</option>
								<option value="通过">通过</option>
								<option value="未通过">未通过</option>
							</select>
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
