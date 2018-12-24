<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*,java.util.*,net.sf.json.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>书架</title>
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
					url : 'BookAction_getList',
					striped : true,  //斑马线效果
					singleSelect : true,  // 开启单选
					nowrap : true,  
					fitColumns : true,
					rownumbers : true,
					pagination : true,
					pageSize : 20,
					pageList : [20,40,60],
					showPageList : false,
					remoteSort : false,
					loadMsg : '正在加载数据......',
					columns : [ [ {
						field : 'bookname',
						title : '图书名称',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'author',
						title : '作者',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'isbn',
						title : 'ISBN图书编号',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'publish',
						title : '出版社',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'pubtime',
						title : '出版时间',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'price',
						title : '单价',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'batch',
						title : '批次',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'total',
						title : '总数量',
						width : 100,
						halign : "center",
						align : "center",
						sortable : true
					}, {
						field : 'residueNum',
						title : '剩余数量',
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
		// 添加
		$("#save").on("click", function() {
			$("#add_win").window("open");
		});
		// 修改
		$("#update").on("click",function() {
			var data = $('#tt').datagrid('getSelected');
			if(data!=null) {
				alert("若是修改总数量，请对应修改剩余数量");
				$("#update_win").window("open");
				$("#update_form").form("load",data);
			}else{
				$.messager.alert('提示消息','请选中一条记录');
			}
		});
		// 删除
		$("#delete").on("click",function() {
			//$.messager.alert("提示", "delete1", "info");
			var data = $("#tt").datagrid("getSelections");// 获取表格选中的数据
			if (data.length > 0 && data != null) { // 选中
				$.messager.confirm("提示","确实要删除吗？",function(r) {
					if (r) { // 如同 r==true
						//alert("目前禁止删除");
						var id = data[0].id; // 获取数据的 id,即idField : 'id',
						$.post("BookAction_delete",{id : id},function(result) { // action 返回的参数
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
		// 借书按键
		$("#borrow").on("click",function() {
			var data = $('#tt').datagrid('getSelected');
			if(data!=null) {
				$("#borrow_win").window("open");
				$("#borrow_win").form("load",data);
			}else{
				$.messager.alert('提示消息','请选中一本图书');
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
		// 添加的取消按钮
		$("#addUser_cancel").on("click", function(){
			$("#add_form").form("clear");
			$("#add_win").window("close");
		});
		// 修改的取消按钮
		$("#editUser_cancel").on("click", function(){
			$("#update_form").form("clear");
			$("#update_win").window("close");
		});
		// 借书的取消按钮
		$("#borrow_cancel").on("click", function(){
			$("#borrow_win").window("close");
		});
		// 添加的保存按钮
	    $("#addUser_saveAndAdd").on("click",function(){
	   		$("#add_form").form("submit",{
	   			url:"BookAction_add",
	   			success:function(result){
	   				//alert("result:"+result);
	   				if(result){
	   					//alert("result.success"+result.success);
	   					$("#add_form").form("clear"); // 清空表单数据
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
				url:"BookAction_update",
				success:function(result){
					if(result){
						$("#update_win").window("close");
						$("#tt").datagrid("reload"); // 修改成功后刷新页面
						$("#update_form").form("clear");
					}else{
						$.messager.alert("提示", "修改失败","info");
					}
				}
			});
		});
		//借书的确定按钮
		$("#borrow_save").on("click",function(){
			$('#borrow_form').form('submit',{
				url:"BookBorrowAction_add",
				success:function(result){
					if(result[5]==1){
						$('#borrow_win').window('close');
						$('#tt').datagrid('reload');
					}else{
						$.messager.alert("提示","剩余数量不足","info");
					}
				}
			});
		});
		
		// 查询按钮
		$("#query").click(function(){
			$('#tt').datagrid('load',{"queryData" : $("#condition").val()});
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
		             <!--  <a  href="javascript:void(0)" id="reset" class="easyui-linkbutton my-search-button" iconCls="icon-reset" plain="true" >重置</a> --> 
		          </td>
		        </tr>
		      </table>
		    </div>
		    <span id="openOrClo --%>se"></span> 
		</div>

		<!-- 菜单工具栏 -->
		<div id="tt_btn">
			
			<c:if test="${user.role==1 }">
				<a href="javascript:void(0)" id="save" class="easyui-linkbutton"
					iconCls="icon-add" plain="true">添加图书</a>
				<a href="javascript:void(0)" id="update" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true">修改图书</a>
				<a href="javascript:void(0)" id="delete" class="easyui-linkbutton"
					 iconCls="icon-remove" plain="true">删除</a>
			</c:if>
			<a href="javascript:void(0)" id="borrow" class="easyui-linkbutton" iconCls="icon-add" plain="true">图书借阅申请</a>
		</div>
		
		
		<!-- 表格栏 -->
		<table id="tt" style="table-layout: fixed;"></table>

		<!-- 添加弹出框 -->
		<div id="add_win" class="easyui-window" title="设备借用申请"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 350px; padding: 5px;">
			<form id="add_form" action="" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr>
						<td width="80" align="right"><label for="bookname"><span class="x">*</span>图书名称</label></td>
						<td width="200"><input class="easyui-validatebox" type="text" name="bookname" 
								class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="author"><span class="x">*</span>作者</label></td>
						<td><input type="text" name="author" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="isbn"><span class="x">*</span>ISBN</label></td>
						<td><input name="isbn" required="required" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="publish"><span class="x">*</span>出版社</label></td>
						<td><input type="text" name="publish" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="pubtime"><span class="x">*</span>出版时间</label></td>
						<td><input type="date" name="pubtime" class="easyui-datebox" required="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="price"><span class="x">*</span>单价</label></td>
						<td><input type="num" name="price" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="batch"><span class="x">*</span>批次</label></td>
						<td><input type="text" name="batch" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="total"><span class="x">*</span>总数量</label></td>
						<td><input type="num" name="total" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="residuNum"><span class="x">*</span>剩余数量</label></td>
						<td><input type="num" name="residuNum" class="easyui-validatebox" data-options="required:true"/></td>
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
			style="width: 450px; height: 350px; padding: 5px;">
			<form id="update_form" action="" name="update_form" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr style="display:none">
						<td><input type="text" name="id" value=""/></td>
					</tr>
					<tr>
						<td width="80" align="right"><label for="bookname"><span class="x">*</span>图书名称</label></td>
						<td width="200"><input class="easyui-validatebox" type="text" name="bookname" readonly="readonly"/></td>
					</tr>
					<tr>
						<td align="right"><label for="author"><span class="x">*</span>作者</label></td>
						<td><input type="text" name="author" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="isbn"><span class="x">*</span>ISBN</label></td>
						<td><input name="isbn" required="required" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="publish"><span class="x">*</span>出版社</label></td>
						<td><input type="text" name="publish" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="pubtime"><span class="x">*</span>出版时间</label></td>
						<td><input type="date" name="pubtime" class="easyui-datebox" required="required"/></td>
					</tr>
					<tr>
						<td align="right"><label for="price"><span class="x">*</span>单价</label></td>
						<td><input type="num" name="price" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="batch"><span class="x">*</span>批次</label></td>
						<td><input type="text" name="batch" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td align="right"><label for="total"><span class="x">*</span>总数量</label></td>
						<td><input type="num" name="total" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right"><label for="residueNum"><span class="x">*</span>剩余数量</label></td>
						<td><input type="num" name="residueNum" class="easyui-validatebox" data-options="required:true"/></td>
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
		
		
		<!-- 借书弹出框开始-->
		<div id="borrow_win" class="easyui-window" title="图书借阅申请"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="borrow_form" action="" name="update_form" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr style="display:none">
						<td><input type="text" name="id" value=""/></td>
					</tr>
					<tr>															
						<td width="80" align="right"><label for="username"><span class="x">*</span>借阅者</label></td>
						<td><input type="text" name="username" value="${user.name }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td width="80" align="right"><label for="bookname"><span class="x">*</span>图书名称</label></td>
						<td width="200"><input class="easyui-validatebox" type="text" name="bookname" readonly="readonly"/></td>
					</tr>
					<tr>
						<td align="right"><label for="booknum"><span class="x">*</span>数量</label></td>
						<td><input type="text" name="booknum" class="easyui-validatebox" required="required"/></td>
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
						<td><input type="text" name="is_succeed" id="is_succeed" value="未审批"/></td>
					</tr>
				</table>
			</form>
			<!-- 保存和取消按钮 -->
			<div class="windowButton" style="text-align:center;padding:5px">
				<a id="borrow_save" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-ok" href="javascript:void(0)"> 保存</a>
			    <a id="borrow_cancel" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-cancel" href="javascript:void(0)">取消</a>
			</div>
		</div>		
		<!-- 借书弹出框结束-->
		
		
	</div>

</body>
</html>
