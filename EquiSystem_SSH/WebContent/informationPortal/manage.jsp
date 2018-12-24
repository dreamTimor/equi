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
									url : 'ManageAction_getList',
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
										field : 'username',
										title : '账号',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'password',
										title : '密码',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true,
										formatter:function(value, row, index){
											if(value!=null){
												return "******";
											}
										}
									}, {
										field : 'name',
										title : '姓名',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'sex',
										title : '性别',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'academy',
										title : '院系',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'cla',
										title : '班级',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'phone',
										title : '电话',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'role',
										title : '角色',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true,
										formatter:function(value, row, index){
											if(value==2){
												return "教师";
											}else{
												return "学生";
											}
										}
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
					//对象形式获取鼠标选中的那行数据
					var data = $('#tt').datagrid('getSelected');
					//确定选中数据，不为空
					if(data != null){
						//若是教师，修改时不显示班级输入框；选中学生则显示
						if(data.role==3){
							document.getElementById('update_claInput').style.display='';
						}else{
							document.getElementById('update_claInput').style.display='none';
						}
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
						$.messager.confirm("提示","确认删除"+data[0].name+"的账号吗？",function(r){
							if(r){
								var id = data[0].id;
								//发送post请求给Action，同时发送id数据，并接收返回值确认是否成功删除
								$.post("ManageAction_delete",{"id":id},function(result){
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
						url : "ManageAction_add",
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
						url : "ManageAction_update",
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
				
				//添加界面：角色的触发事件
				$('#add_role').on('change',function(){
					var value = $('#add_role').val();
					//下拉框选择学生，则显示班级输入框；否则隐藏
					if(value==3){
						document.getElementById('add_claInput').style.display='';
					}else{
						document.getElementById('add_claInput').style.display='none';
					}
				})
				//修改界面：角色的触发事件
				$('#update_role').on('change',function(){
					var value = $('#update_role').val();
					//下拉框选择学生，则显示班级输入框；否则隐藏
					if(value==3){
						document.getElementById('update_claInput').style.display='';
					}else{
						document.getElementById('update_claInput').style.display='none';
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
						<td align="right"><label for="username"><span class="x">*</span>账号</label></td>
						<td><input type="text" name="username" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="password"><spanclass="x">*</span>密码</label></td>
						<td><input type="text" name="password" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="name"><span class="x">*</span>姓名</label></td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="sex"><span class="x">*</span>性别</label></td>
						<td>
							<select name="sex">
								<option value="男">男</option>
								<option value="女">女</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right"><label for="academy"><span class="x">*</span>院系</label></td>
						<td><input type="text" name="academy" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="phone"><span class="x">*</span>电话</label></td>
						<td><input type="text" name="phone" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="add_role"><span class="x">*</span>职业</label></td>
						<td>
							<select name="role" id="add_role">
								<option value="2" selected="selected">教师</option>
								<option value="3">学生</option>
							</select>
						</td>
					</tr>
					<tr id="add_claInput" style="display : none">
						<td align="right"><label for="add_cla"><span class="x">*</span>班级</label></td>
						<td><input type="text" name="cla" id="add_cla"/></td>
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
					<!-- 154行已经自动填写表单的值，id会自动发送，不用给值 -->
					<tr style="display: none">
						<td><input type="text" name="id" id="id"/></td>
					</tr>
					<tr>
						<td align="right"><label for="username"><span class="x">*</span>账号</label></td>
						<td><input type="text" name="username" readonly="readonly"/></td>
					</tr>
					<tr>
						<td align="right"><label for="password"><spanclass="x">*</span>密码</label></td>
						<td><input type="text" name="password" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="name"><span class="x">*</span>姓名</label></td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="sex"><span class="x">*</span>性别</label></td>
						<td>
							<select name="sex">
								<option value="男">男</option>
								<option value="女">女</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right"><label for="academy"><span class="x">*</span>院系</label></td>
						<td><input type="text" name="academy" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="phone"><span class="x">*</span>电话</label></td>
						<td><input type="text" name="phone" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="role"><span class="x">*</span>职业</label></td>
						<td>
							<select name="role" id="update_role">
								<option value="2" selected="selected">教师</option>
								<option value="3">学生</option>
							</select>
						</td>
					</tr>
					<tr id="update_claInput" style="display : none">
						<td align="right"><label for="update_cla"><span class="x">*</span>班级</label></td>
						<td><input type="text" name="cla" id="update_cla"/></td>
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
