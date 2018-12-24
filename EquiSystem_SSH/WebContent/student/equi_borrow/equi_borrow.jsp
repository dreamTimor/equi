<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*,java.util.*,net.sf.json.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据界面</title>
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
								url : 'EquiBorrowAction_getList',
								striped : true,  //斑马线效果
								singleSelect : true,  // 开启单选
								nowrap : true,  
								fitColumns : true,
								rownumbers : true,
								showPageList : false,
								loadMsg : '正在加载数据......',
								columns : [ [ {
									field : 'name',
									title : '设备名称',
									width : 100,
									halign : "center",
									align : "left"
								}, {
									field : 'model',
									title : '型号规格',
									width : 100,
									halign : "center",
									align : "left"
								}, {
									field : 'num',
									title : '数量',
									width : 100,
									halign : "center",
									align : "left"
								}, {
									field : 'unit',
									title : '单位',
									width : 100,
									halign : "center",
									align : "left"
								}, {
									field : 'borrower',
									title : '借用人',
									width : 100,
									halign : "center",
									align : "left"
								}, {
									field : 'occupation',
									title : '职业',
									width : 100,
									halign : "center",
									align : "left"
								}, {
									field : 'state',
									title : '是否通过',
									width : 100,
									halign : "center",
									align : "left"
								}, {
									field : 'return_status',
									title : '归还时设备状态',
									width : 100,
									halign : "center",
									align : "left"
								}, {
									field : 'lendtime',
									title : '借用时间',
									width : 100,
									halign : "center",
									align : "left"
								}, {
									field : 'borrowtime',
									title : '归还时间',
									width : 100,
									halign : "center",
									align : "left"
								}, {
									field : 'truetime',
									title : '实际归还时间',
									width : 100,
									halign : "center",
									align : "left"
								} ] ],
								toolbar : '#tt_btn',  // 绑定工具栏添加，修改，删除
								pagination : true,    // 启用底部分页工具栏
								onDblClickRow : function(rowIndex, rowData) {  // 双击显示选中数据的详细信息
									viewDetail(rowData.id);
								}
							});
					// 申请借用设备
					$("#save").on("click", function() {
						$("#add_win").window("open");
					});
					// 修改
					$("#update").on("click",function() {
						var data = $('#tt').datagrid('getSelected');
						//$parent.messager.alert("提示", "update"+data.id, "info");
						if(data!=null) {
							$("#update_win").window("open");
							$("#update_form").form("load",data);
						}else{
							$.messager.alert('提示消息','请选择数据');
						}
					});
					// 删除
					$("#delete").on("click",function() {
						//$.messager.alert("提示", "delete1", "info");
						var data = $("#tt").datagrid(
								"getSelections");// 获取表格选中的数据
						if (data.length > 0 && data != null) { // 选中
							$.messager.confirm("提示","确实要删除吗？",function(r) {
								if (r) { // 如同 r==true
									var id = data[0].id; // 获取数据的 id,即idField : 'id',
									$.post("EquiBorrowAction_delete",{id : id}, // 将 id 传到 action
										function(result) { // action 返回的参数
											if (result.success) { // 删除的时候 检查 json 里有没有 success 这个 key
												//$.messager.alert("提示","删除成功","info");
												$("#tt").datagrid("reload"); // 删除后刷新页面
											} else {
												$.messager.alert("提示", "删除失败","info");
											}
										},'json');
								}
							});
						} else {
							$.messager.alert("提示", "请选中一条记录","info");
						}
					});
					// 查询详细
					function viewDetail(date, id) {
						$parent.messager.alert("提示", "查询详细" + id, "info");
					}

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
					}
					// 添加的取消按钮
					$("#addUser_cancel").on("click", function(){
						$("#add_form").form("clear"); // 清空表单数据
						$("#add_win").window("close");
					});
					// 修改的取消按钮
					$("#editUser_cancel").on("click", function(){
						$("#update_form").form("clear");
						$("#update_win").window("close");
					});
					// 添加的保存按钮
				    $("#addUser_saveAndAdd").on("click",function(){
				   		$("#add_form").form("submit",{
				   			url:"EquiBorrowAction_add",
				   			success:function(result){
				   				//alert("result:"+result);
				   				if(result){
				   					//alert("result.success"+result.success);
				   					$("#add_win").window("close");
				   					$("#tt").datagrid("reload"); // 添加成功后刷新页面
				   				}else{
				   					$.messager.alert("提示", "添加失败","info");
				   				}
				   			}
				   		});
				   	}); 
					// 修改的保存按钮
					$("#editUser_saveAndEdit").on("click",function(){
						$("#update_form").form("submit",{
							url:"EquiBorrowAction_update",
							success:function(result){
								if(result){
									$("#update_win").window("close");
									$("#tt").datagrid("reload"); // 修改成功后刷新页面
								}else{
									$.messager.alert("提示", "修改失败","info");
								}
							}
						});
					});
					
				});
			</script>
</head>

<body class="easyui-layout">

	<div id="body" region="center">

		<!-- 表格栏 -->
		<table id="tt" style="table-layout: fixed;"></table>

		<!-- 菜单工具栏 -->
		<div id="tt_btn">
			<a href="javascript:void(0)" id="save" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">设备借用申请</a>
			<c:if test="${user.role==1 }">
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
						<td width="80" align="right"><label for="name"><span
								class="x">*</span>设备名称</label></td>
						<td width="200"><input class="easyui-validatebox" 
							data-options="required:true,validType:'length[1,20]',missingMessage:'不能为空',invalidMessage:'字符在1~20之间'" 
							type="text" name="name" /></td>
					</tr>
					<tr>
						<td align="right"><label for="model"><span
								class="x">*</span>类型规格</label></td>
						<td><input type="text" name="model" /></td>
					</tr>
					<tr>
						<td align="right"><label for="num"><span class="x">*</span>数量</label></td>
						<td><input class="easyui-numberspinner" name="num" value="1"  data-options="min:1,max:100,missingMessage:'不能为空'" required="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="unit"><span class="x">*</span>单位</label></td>
						<td><input type="text" name="unit" /></td>
					</tr>
					<tr>
						<td align="right"><label for="borrower"><span
								class="x">*</span>借用人</label></td>
						<td><input type="text" name="borrower"
							value="${user.name }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"><label for="occupation"><span
								class="x">*</span>职业</label></td>
						<td><input type="text" name="occupation"
							value="${user.role==2?'老师':'学生' }" readonly="readonly" /></td>
					</tr>
					<tr style="display: none">
						<td align="right"><label for="return_status"><span
								class="x">*</span>设备状态</label></td>
						<td><input type="text" name="return_status" /></td>
					</tr>
					<tr>
						<td align="right"><label for="lendtime"><span
								class="x">*</span>借用时间</label></td>
						<td><input type="date" name="lendtime" required="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="borrowtime"><span
								class="x">*</span>归还时间</label></td>
						<td><input type="date" name="borrowtime" required="required"
							/></td>
					</tr>
					<tr style="display: none">
						<td><input type="date" name="truetime" value="" /></td>
					</tr>
					<tr style="display: none">
						<td><input type="text" name="uid" 
							value="${user.id }" /></td>
					</tr>
					<tr style="display: none">
						<td><input type="text" name="state" value="未审批" /></td>
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
		<div id="update_win" class="easyui-window" title="设备借用修改"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="update_form" action="" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr style="display:none">
						<td><input type="text" name="id" value=""/></td>
					</tr>
					<tr>
						<td width="80" align="right"><label for="userName"><span
								class="x">*</span>设备名称</label></td>
						<td width="200"><input class="easyui-validatebox" 
							data-options="required:true,validType:'length[1,20]',missingMessage:'不能为空',invalidMessage:'字符在1~20之间'" 
							type="text" name="name"  /></td>
					</tr>
					<tr>
						<td align="right"><label for="password"><span
								class="x">*</span>类型规格</label></td>
						<td><input type="text" name="model" id="model" /></td>
					</tr>
					<tr>
						<td align="right"><label for="name"><span class="x">*</span>数量</label></td>
						<td><input type="number" name="num" id="num" /></td>
					</tr>
					<tr>
						<td align="right"><label for="sex"><span class="x">*</span>单位</label></td>
						<td><input type="text" name="unit" id="unit" /></td>
					</tr>
					<tr>
						<td align="right"><label for="borrower"><span
								class="x">*</span>借用人</label></td>
						<td><input type="text" name="borrower" id="borrower"
							value="${user.name }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"><label for="occupation"><span
								class="x">*</span>职业</label></td>
						<td><input type="text" name="occupation" id="occupation"
							value="${user.role==2?'老师':'学生' }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"><label for="state"><span
								class="x">*</span>是否通过</label></td>
						<td>
							<select name="state" >
								<option value="未审批" selected="selected">未审批</option>
								<option value="通过">通过</option>
								<option value="未通过">未通过</option>
							</select>
						</td>
					</tr>
					<tr style="display: none">
						<td align="right"><label for="return_status"><span
								class="x">*</span>设备状态</label></td>
						<td><input type="text" name="return_status" /></td>
					</tr>
					<tr>
						<td align="right"><label for="lendtime"><span
								class="x">*</span>借用时间</label></td>
						<td><input type="text" class="easyui-datebox" name="lendtime" required ="required" /></td>
					</tr>
					<tr>
						<td align="right"><label for="borrowtime"><span
								class="x">*</span>归还时间</label></td>
						<td><input type="text" name="borrowtime" id="borrowtime"
							value="" /></td>
					</tr>
					<tr style="display: none">
						<td><input type="date" name="truetime" id="truetime" value="" /></td>
					</tr>
					<tr style="display: none">
						<td><input type="text" name="uid" id="uid"
							value="${user.id }" /></td>
					</tr>
					
				</table>
			</form>
			<!-- 保存修改和取消按钮 -->
			<div class="windowButton" style="text-align:center;padding:5px">
				<a id="editUser_saveAndEdit" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-ok" href="javascript:void(0)"> 保存并修改</a>
			    <a id="editUser_cancel" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-cancel" href="javascript:void(0)">取消</a>
			</div>
		</div>
		<!-- 修改弹出框结束-->
		
	</div>

</body>
</html>
