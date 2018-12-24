<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*,java.util.*,net.sf.json.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教室设备</title>
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
									url : 'RoomEquiAction_getList',
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
										field : 'classroom',
										title : '教室',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'name',
										title : '设备',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'model',
										title : '型号规格',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'num',
										title : '数量',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'unit',
										title : '单位',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'time',
										title : '时间',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'source',
										title : '来源',
										width : 100,
										halign : "center",
										align : "center",
										sortable : true
									}, {
										field : 'configue',
										title : '配置说明',
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
								$.post("RoomEquiAction_delete",{"id":id},function(result){
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
						url : "RoomEquiAction_add",
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
						url : "RoomEquiAction_update",
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
			<c:if test="${user.role==1 }">
				<a href="javascript:void(0)" id="save" class="easyui-linkbutton"
					iconCls="icon-add" plain="true">添加教室设备</a> 
				<a href="javascript:void(0)" id="update" class="easyui-linkbutton"
				 iconCls="icon-edit" plain="true">修改</a> 
				<a href="javascript:void(0)" id="delete" class="easyui-linkbutton" 
					iconCls="icon-remove" plain="true">删除</a>
			</c:if>
		</div>
		
		
		
		<!-- 添加弹出框 -->
		<div id="add_win" class="easyui-window" title="设备借用申请"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="add_form" action="" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr>
						<td width="80" align="right"><label for="classroom"><span class="x">*</span>教室</label></td>
						<td width="200">
							<input class="easyui-validatebox" type="text" name="classroom" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td align="right"><label for="name"><span class="x">*</span>设备名</label></td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="model"><span class="x">*</span>型号规格</label></td>
						<td><input name="model" required="required" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="num"><span class="x">*</span>数量</label></td>
						<td><input type="text" name="num" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="unit"><span class="x">*</span>单位</label></td>
						<td><input type="date" name="unit" class="easyui-validatebox" required="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="time"><span class="x">*</span>存放时间</label></td>
						<td><input type="num" name="time" class="easyui-datebox" required="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="source"><span class="x">*</span>来源</label></td>
						<td><input type="text" name="source" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="configue"><span class="x">*</span>配置说明</label></td>
						<td><input type="num" name="configue" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
				</table>
			</form>
			<!-- 添加和取消按钮 -->
			<div class="windowButton" style="text-align:center;padding:5px">
				<a id="addUser_saveAndAdd" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-ok" href="javascript:void(0)"> 保存并新增</a>
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
						<td width="80" align="right"><label for="classroom"><span class="x">*</span>教室</label></td>
						<td width="200">
							<input class="easyui-validatebox" type="text" name="classroom" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td align="right"><label for="name"><span class="x">*</span>设备名</label></td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="model"><span class="x">*</span>型号规格</label></td>
						<td><input name="model" required="required" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="num"><span class="x">*</span>数量</label></td>
						<td><input type="text" name="num" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="unit"><span class="x">*</span>单位</label></td>
						<td><input type="date" name="unit" class="easyui-validatebox" required="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="time"><span class="x">*</span>存放时间</label></td>
						<td><input type="num" name="time" class="easyui-datebox" required="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="source"><span class="x">*</span>来源</label></td>
						<td><input type="text" name="source" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="configue"><span class="x">*</span>配置说明</label></td>
						<td><input type="num" name="configue" class="easyui-validatebox" data-options="required:true"/></td>
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
