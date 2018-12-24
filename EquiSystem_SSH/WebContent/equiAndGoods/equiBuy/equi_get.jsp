<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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

<!-- jsp页面，在登记的时候，谁来登记，写谁的领取人名字，哪个管理员或者老师在线，就写哪个人的发放人，获取当前时间，老师无法删除登记，可以修改登记 -->
$(function() {
	$("#tt").datagrid(
			{
				height : $("#body").height()
						- $('#search_area').height() - 5,
				width : $("#body").width(),
				idField : 'id',		
				url : 'EquiGetAction_equiGetList',		
				striped : true,//斑马线效果
				singleSelect : true,//设置只能选择单选 
				nowrap : true,	//设置为true，当数据长度超出列宽时将会自动截取
				pagination : true,		//启动底部分页工具栏
				fitColumns : true, //列宽的自动适应
				rownumbers : true, //显示第几行
				pageSize: 20,//每页显示的记录条数，默认为5
				pageList:[10,20,30,40],//每页显示记录条数
				showPageList : false,
				remoteSort : false,//排序
				columns : [ [ {
					field : 'name',
					title : '设备名称',
					width : 100,
					halign : "center",
					align : "center",
					sortable : true
				}, {
					field : 'model',
					title : '型号规格',
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
					field : 'keeper',
					title : '发放人',
					width : 100,
					halign : "center",
					align : "center",
					sortable : true
				}, {
					field : 'receiver',
					title : '领取人',
					width : 100,
					halign : "center",
					align : "center",
					sortable : true
				}, {
					field : 'innertime',
					title : '领取时间',
					width : 100,
					halign : "center",
					align : "center",
					sortable : true
				}, {
					field : 'remark',
					title : '备注',
					width : 100,
					halign : "center",
					align : "center",
					sortable : true
				} ] ],
				toolbar : '#tt_btn',  //绑定工具栏
				
				onDblClickRow : function(rowIndex, rowData) {  //双击点击显示数据详细信息
					viewDetail(rowData.id);
				}
			
			});
	
			// 领取登记
			$("#save").on("click", function() {
				$("#add_win").window("open");//点击打开窗口
			});
						
			// 领取登记的保存按钮
		    $("#addpaylist").on("click",function(){
		   		$("#add_form").form("submit",{    //表单提交
		   			url:"EquiGetAction_addEquiGet",
		   			success:function(result){
		   				if(result){
		   					$("#add_win").window("close");  //添加后关闭页面
		   					$("#tt").datagrid("reload"); // 添加成功后刷新页面
		   				}else{
		   					$.messager.alert("提示", "添加失败","info");
		   				}
		   			}
		   		});
		   	});
			
			 // 领取登记的取消按钮
			$("#addpaylist_cancel").on("click", function(){
				$("#add_form").form("clear"); // 清空表单数据
				$("#add_win").window("close");//点击关闭窗口
			});
			 
			// 修改
			$("#update").on("click",function() {
				var data = $('#tt').datagrid('getSelected');  //判断是否选中一行
				if(data!=null) {
					$("#update_win").window("open");
					$("#update_form").form("load",data);
				}else{
					$.messager.alert('提示消息','请选择数据');
				}
			});
			
			// 修改的保存按钮
			$("#paylist_saveAndEdit").on("click",function(){
				$("#update_form").form("submit",{
					url:"EquiGetAction_updateEquiGet",
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
			
			// 修改的取消按钮
			$("#paylist_cancel").on("click", function(){
				$("#update_form").form("clear");
				$("#update_win").window("close");
			});
			 
			// 删除
			$("#delete").on("click",function() {
				//$.messager.alert("提示", "delete1", "info");
				var data = $("#tt").datagrid(
						"getSelections");// 获取表格选中的数据
				if (data.length > 0 && data != null) { // 判断是否选中
					$.messager.confirm("提示","确实要删除吗？",function(r) {
						if (r) { // 如同 r==true
							var id = data[0].id; // 获取数据的 id,即idField : 'id',
							$.post("EquiGetAction_deleteEquiGet",{id : id}, // 将 id 传到 action,在action中根据id获取对象
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
			
			//模糊查询：获取查询条件，修改显示的请求url，换成查询
			$("#query").on("click",function(){
				var queryData = $("#querydata").val();
				$('#tt').datagrid('options').url = "EquiGetAction_equiGetList?queryData="+encodeURI(encodeURI(queryData));
				$('#tt').datagrid('reload');
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
		          <td ><input name="querydata" id="querydata" /></td>
		          <td>
		              <a  href="javascript:void(0)" id="query" class="easyui-linkbutton my-search-button" iconCls="icon-search" plain="true">查询</a>
		          </td>
		        </tr>
		      </table>
		    </div>
		    <span id="openOrClo --%>se"></span> 
		</div>
		
		<!-- 菜单工具栏 -->
		<div id="tt_btn">
			<a href="javascript:void(0)" id="save" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">领取登记
			</a> 
			 
				<a href="javascript:void(0)" id="update" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true">修改
				</a> 
			<c:if test="${user.role==1}">   <!-- 判断是否角色=1，否则不显示  -->
				<a href="javascript:void(0)"
					id="delete" class="easyui-linkbutton" iconCls="icon-remove"
					plain="true">删除
				</a>
			</c:if> 
		</div>
		
		<!-- 表格栏 -->
		<table id="tt" style="table-layout: fixed;"></table>
		
		
		<!-- 添加的弹出框 -->
		<div id="add_win" class="easyui-window" title="领取登记"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="add_form" action="" method="post">
				<table width="280" border="0" align="center" cellpadding="3">
					<tr>
						<td width="80" align="right"><label for="name"><span
								class="x">*</span>设备名称</label></td>
						<td width="200"><input class="easyui-validatebox" 
							data-options="required:true,validType:'length[1,20]',missingMessage:'不能为空',invalidMessage:'字符在1~20之间'" 
							type="text" name="name" id="name" /></td>
					</tr>
					<tr>
						<td align="right"><label for="model"><span
								class="x">*</span>类型规格</label></td>
						<td><input type="text" name="model" id="model" /></td>
					</tr>
					<tr>
						<td align="right"><label for="num"><span class="x">*</span>数量</label></td>
						<td><input type="number" name="num" id="num" /></td>
					</tr>
					<tr>
						<td align="right"><label for="unit"><span class="x">*</span>单位</label></td>
						<td><input type="text" name="unit" id="unit" /></td>
					</tr>
					<tr>
						<td align="right"><label for="applicant"><span
								class="x">*</span>发放人</label></td>
						<td><input type="text" name="keeper" id="keeper"
							value="${user.name }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"><label for="unit"><span class="x">*</span>领取人</label></td>
						<td><input type="text" name="receiver" id="receiver" /></td>
					</tr>
					
					<tr>
						<td align="right"><label for="applydate"><span
								class="x">*</span>领取时间</label></td>
						<td><input type="date" name="innertime" id="innertime" readonly="readonly"
						 value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>" /></td>
					</tr>
					<tr>
						<td align="right"><label for="remark"><span
								class="x">*</span>备注</label></td>
						<td><input type="text" name="remark" id="remark"></td>
					</tr>

				</table>
			</form>
			<!-- 添加和取消按钮 -->
			<div class="windowButton" style="text-align:center;padding:5px">
				<a id="addpaylist" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-ok" href="javascript:void(0)"> 保存并新增</a>
			    <a id="addpaylist_cancel" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-cancel" href="javascript:void(0)">取消</a>
			</div>
		</div>
		
		<!-- 修改弹出框开始-->
		<div id="update_win" class="easyui-window" title="预采购申请修改"
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
							type="text" name="name" id="name" /></td>
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
						<td align="right"><label for="applicant"><span
								class="x">*</span>发放人</label></td>
						<td><input type="text" name="keeper" id="keeper"
							value="${user.name }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"><label for="sex"><span class="x">*</span>领取人</label></td>
						<td><input type="text" name="receiver" id="receiver" /></td>
					</tr>
					<tr>
						<td align="right"><label for="applydate"><span
								class="x">*</span>领取时间</label></td>
						<td><input type="text" name="innertime" id="innertime" readonly="readonly" value="innertime" /></td>
					</tr>

					<tr style="display: none">
						<td align="right"><label for="remark"><span
								class="x">*</span>备注</label></td>
						<td><input type="text" name="remark" /></td>
					</tr>
					<tr style="display: none"><!-- 不显示出来 -->
						<td><input type="text" name="uid" id="uid"
							value="${user.id }" /></td>
					</tr>

				</table>
			</form>
			<!-- 保存修改和取消按钮 -->
			<div class="windowButton" style="text-align:center;padding:5px">
				<a id="paylist_saveAndEdit" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-ok" href="javascript:void(0)"> 保存并修改</a>
			    <a id="paylist_cancel" class="easyui-linkbutton my-dialog-button"
					plain="true" icon="icon-cancel" href="javascript:void(0)">取消</a>
			</div>
		</div>

		<div id="dlg" class="easyui-dialog"
			style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
			buttons="#dlg-buttons">
		</div>
	</div>

	
</body>
</html>

