<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*,java.util.*,net.sf.json.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图书借阅管理</title>
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
					url : 'BookBorrowAction_getList',
					striped : true,  //斑马线效果
					singleSelect : true,  // 开启单选
					nowrap : true,  
					fitColumns : true,
					rownumbers : true,
					pageSize: 20,//每页显示的记录条数，默认为5
					pageList:[10,20,30,40],//每页显示记录条数
					remoteSort : false,
					showPageList : false,
					loadMsg : '正在加载数据......',
					columns : [ [ {
						field : 'bookname',
						title : '图书名称',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'username',
						title : '借阅者',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'booknum',
						title : '数量',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'borrowtime',
						title : '借书日期',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'returntime',
						title : '预计还书日期',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'actualtime',
						title : '实际还书日期',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'is_succeed',
						title : '申请情况',
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
		// 修改通过
		$("#update_yes").on("click",function() {
			//获取选中行的数据
			var data = $('#tt').datagrid('getSelected');
			if(data!=null) {
				//如果选中行的数据是未审批,用form表单发送post请求,成功后返回1
				if(data.is_succeed=="未审批"){
					$("#update_form").form("load",data);
					$("#update_form").form("submit",{
						url : "BookBorrowAction_update_yes",
						success : function(result){
							if(result[5]==1){
								$("#tt").datagrid("reload");
							}else{
								alert("失败");
							}
						}
					});
				}else{
					alert("申请已处理");
				}
			}else{
				$.messager.alert('提示消息','请选中一条记录');
			}
		});
		// 修改未通过
		$("#update_no").on("click",function() {
			//获取选中行的数据
			var data = $('#tt').datagrid('getSelected');
			if(data!=null) {
				//如果选中行的数据是未审批,用form表单发送post请求,成功后返回1
				if(data.is_succeed=="未审批"){
					$("#update_form").form("load",data);
					$("#update_form").form("submit",{
						url : "BookBorrowAction_update_no",
						success : function(result){
							if(result[5]==1){
								$("#tt").datagrid("reload");
							}else{
								alert("失败");
							}
						}
					});
				}else{
					alert("申请已处理");
				}
			}else{
				$.messager.alert('提示消息','请选中一条记录');
			}
		});
		// 确认还书
		$("#update_return").on("click",function() {
			//获取选中行的数据
			var data = $('#tt').datagrid('getSelected');
			if(data!=null) {
				//选中行的数据是通过申请的
				if(data.is_succeed=="通过"){
					//确认是否已还书：发送form表单的post请求，添加实际还书日期
					if(data.actualtime==null || data.actualtime==""){
						$("#update_form").form("load",data);
						$("#update_form").form("submit",{
							url : "BookBorrowAction_update_return",
							success : function(result){
								//成功刷新界面，失败返回
								if(result[5]==1){
									$("#tt").datagrid("reload");
								}else{
									alert("失败");
								}
							}
						});
					}else{
						var test="数据: ";
						jQuery.each(data, function(i, val){
							test = test + "Key:" + i + "，Value:" + val+"        ";
						});
						alert(test);
					}
				}else{
					alert("该申请未通过");
				}
			}else{
				$.messager.alert('提示消息','请选中一条记录');
			}
		});
		// 删除
		$("#delete").on("click",function() {
			var data = $("#tt").datagrid("getSelections");// 获取表格选中的数据
			if (data.length > 0 && data != null) { // 选中
				$.messager.confirm("提示","确实要删除吗？",function(r) {
					if (r) { // 如同 r==true
						var id = data[0].id; // 获取数据的 id,即idField : 'id',
						$.post("BookBorrowAction_delete",{id : id}, // 将 id 传到 action
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
		
		// 查询按钮
		$("#query").click(function(){
			$("#tt").datagrid("load",{"Data":$("#condition").val()});
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
		              <a  href="javascript:void(0)" id="reset" class="easyui-linkbutton my-search-button" iconCls="icon-reset" plain="true" >重置</a> 
		          </td>
		        </tr>
		      </table>
		    </div>
		    <span id="openOrClo --%>se"></span> 
		</div>

		<!-- 菜单工具栏 -->
		<div id="tt_btn">
			<c:if test="${user.role==1 }">
				<!-- <a href="javascript:void(0)" id="save" class="easyui-linkbutton"
					iconCls="icon-add" plain="true">添加图书借阅记录</a> -->
				<a href="javascript:void(0)" id="update_yes" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true">通过</a>
				<a href="javascript:void(0)" id="update_no" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true">未通过</a>
				<a href="javascript:void(0)" id="update_return" class="easyui-linkbutton"
					iconCls="icon-redo" plain="true">确认还书</a>
				<a href="javascript:void(0)" id="delete" class="easyui-linkbutton"
					 iconCls="icon-remove" plain="true">删除</a>
			</c:if>
		</div>
		
		<!-- 表格栏 -->
		<table id="tt" style="table-layout: fixed;"></table>

		<%-- <!-- 添加弹出框 -->
		<div id="add_win" class="easyui-window" title="图书借阅申请"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="add_form" action="" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr>															
						<td width="80" align="right"><label for="username"><span class="x">*</span>借阅者</label></td>
						<td><input type="text" name="username" value="${user.name }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"><label for="bookname"><span class="x">*</span>图书名称</label></td>
						<td><input type="text" name="bookname" /></td>
					</tr>
					<tr>
						<td align="right"><label for="booknum"><span class="x">*</span>数量</label></td>
						<td><input type="text" name="booknum" /></td>
					</tr>
					<tr>
						<td align="right"><label for="borrowtime"><span class="x">*</span>借书日期</label></td>
						<td><input type="text" name="borrowtime" class= "easyui-datebox" required ="required"/></td>
					</tr>
					<tr>
						<td width="120" align="right"><label for="returntime"><span class="x">*</span>预计还书日期</label></td>
						<td><input type="text" name="returntime" class= "easyui-datebox" required ="required"/></td>
					</tr>
					<tr style="display:none">
						<td><input type="text" name="user" id="user"></input></td>
					</tr>
					<tr style="display:none">
						<td><input type="text" name="book" id="book"></input></td>
					</tr>
					<tr style="display:none">
						<td><input type="text" name="is_succeed" id="is_succeed" value="未审批"/></td>
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
		<!-- 添加弹出框结束--> --%>
		
		<!-- 修改弹出框开始-->
		<div id="update_win" class="easyui-window" title="设备借用修改"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="update_form" action="" name="update_form" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr>
						<td width="80" align="right"><label for="username"><span class="x">*</span>借阅者</label></td>
						<td><input type="text" name="username" value="${user.name }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"><label for="bookname"><span class="x">*</span>图书名称</label></td>
						<td><input type="text" name="bookname" /></td>
					</tr>
					<tr>
						<td align="right"><label for="booknum"><span class="x">*</span>数量</label></td>
						<td><input type="text" name="booknum" /></td>
					</tr>
					<tr>
						<td align="right"><label for="borrowtime"><span class="x">*</span>借书日期</label></td>
						<td><input type="text" name="borrowtime" class= "easyui-datebox" required ="required"/></td>
					</tr>
					<tr>
						<td width="120" align="right"><label for="returntime"><span class="x">*</span>预计还书日期</label></td>
						<td><input type="text" name="returntime" class= "easyui-datebox" required ="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="is_succeed"><span class="x">*</span>申请审批:</label></td>
						<td>
							<select name="is_succeed">
								<option value="未审批">未审批</option>
								<option value="通过">通过</option>
								<option value="未通过">未通过</option>
							</select>
						</td>
					</tr>
					<tr style="display:none">
						<td><input type="text" name="id" id="id"></input></td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 修改弹出框结束-->
		
	</div>

</body>
</html>
