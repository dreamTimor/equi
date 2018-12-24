<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*,java.util.*,net.sf.json.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报修界面</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/jquery-easyui-1.3.5/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/jquery-easyui-1.3.5/themes/themes/default/messager.css">
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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-easyui-1.3.5/plugins/jquery.messager.js"></script>
<script>
	$(function() {
		$("#tt").datagrid(
				{
					height : $("#body").height() - $('#search_area').height() - 5,
					width : $("#body").width(),
					idField : 'id',
					url : 'EquiRepairAction_getList',
					striped : true,  //斑马线效果
					singleSelect : true,  // 开启单选
					nowrap : true,  
					fitColumns : true,
					rownumbers : true,
					pageSize : 20,
					pageList : [20,40,60],
					remoteSort : false,
					showPageList : false,
					loadMsg : '正在加载数据......',
					columns : [ [ {
						field : 'name',
						title : '设备',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'rep_people',
						title : '报修人',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'reson',
						title : '原因说明',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'time',
						title : '报修时间',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'way',
						title : '维修方式',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'place',
						title : '存放地',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'solve_people',
						title : '处理人',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					} ] ],
					toolbar : '#tt_btn',  // 绑定工具栏添加，修改，删除
					pagination : true,    // 启用底部分页工具栏
					onDblClickRow : function(rowIndex, rowData) {  // 双击显示选中数据的详细信息
						viewDetail(rowData);
					}
				});
		
		//监听窗口大小变化
		window.onresize = function() {
			setTimeout(domresize, 300);
		};
		//改变表格宽高
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
		//添加窗口的确认键
		$("#addUser_saveAndAdd").on("click",function(){
			$('#add_form').form('submit',{
				url : "EquiRepairAction_add",
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
		//修改的确定键
		$("#editUser_saveAndEdit").on("click", function(){
			$("#update_form").form('submit', {
				url : "EquiRepairAction_update",
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
		
		// 删除
		$("#delete").on("click",function() {
			var data = $("#tt").datagrid("getSelections");// 获取表格选中的数据
			if (data.length > 0 && data != null) { // 选中
				$.messager.confirm("提示","确实要删除吗？",function(r) {
					if (r) { // 如同 r==true
						var id = data[0].id; // 获取数据的 id,即idField : 'id',
						$.post("EquiRepairAction_delete",{id : id}, // 将 id 传到 action
							function(result) { // action 返回的参数
								if(result.s==1) { // 删除的时候 检查 json 里有没有 success 这个 key
									$("#tt").datagrid("reload"); // 删除后刷新页面
								}else{
									$.messager.alert("提示", "删除失败","info");
								}
							},'json');
					}
				});
			} else {
				$.messager.alert("提示", "请选中一条记录","info");
			}
		});
		
		
		// 查询按钮
		$("#query").click(function(){
			$("#tt").datagrid("load",{"queryData":$("#condition").val()});
		});
		// 重置按钮
		$("#reset").click(function(){
			alert(2);
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
		          <td>查询条件：</td>
		          <td ><input name="condition" id="condition" /></td>
		          <td>
		              <a  href="javascript:void(0)" id="query" class="easyui-linkbutton my-search-button" iconCls="icon-search" plain="true">查询</a>
		              <!-- <a  href="javascript:void(0)" id="reset" class="easyui-linkbutton my-search-button" iconCls="icon-reset" plain="true" >重置</a> --> 
		          </td>
		        </tr>
		      </table>
		    </div>
		    <span id="openOrClo --%>se"></span> 
		</div>

		<!-- 菜单工具栏 -->
		<div id="tt_btn">
			<a href="javascript:void(0)" id="save" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加图书借阅记录</a>
			<c:if test="${user.role==1 }">
				<a href="javascript:void(0)" id="update" class="easyui-linkbutton"
					 iconCls="icon-remove" plain="true">修改</a>
				<a href="javascript:void(0)" id="delete" class="easyui-linkbutton"
					 iconCls="icon-remove" plain="true">删除</a>
			</c:if>
		</div>
		
		<!-- 表格栏 -->
		<table id="tt" style="table-layout: fixed;"></table>
	
		
		<!-- 添加弹出框 -->
		<div id="add_win" class="easyui-window" title="设备借用申请"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="add_form" action="" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr>
						<td align="right"><label for="name"><span class="x">*</span>设备名</label></td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="rep_people"><span class="x">*</span>报修人</label></td>
						<td><input type="text" name="rep_people" value="${user.username }" readonly="readonly"/></td>
					</tr>
					<tr>
						<td align="right"><label for="place"><span class="x">*</span>存放地</label></td>
						<td><input type="text" name="place" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="reson"><span class="x">*</span>原因说明</label></td>
						<td><input type="text" name="reson" class="easyui-validatebox" data-options="required:true"/></td>
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
						<td align="right"><label for="name"><span class="x">*</span>设备名</label></td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="rep_people"><span class="x">*</span>报修人</label></td>
						<td><input type="text" name="rep_people" readonly="readonly"/></td>
					</tr>
					<tr>
						<td align="right"><label for="place"><span class="x">*</span>存放地</label></td>
						<td><input type="text" name="place" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="reson"><span class="x">*</span>原因说明</label></td>
						<td><input type="text" name="reson" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="way"><span class="x">*</span>维修方式</label></td>
						<td><input type="text" name="way" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="time"><span class="x">*</span>报修时间</label></td>
						<td><input type="text" name="time" class="easyui-datebox" required="required"/></td>
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
