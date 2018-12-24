<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
		$(function(){
			// 菜单按钮触发菜单项
		    $('#mb').menubutton({    
			    iconCls: 'icon-reset',    
			    menu: '#mm'   
			}); 
		    // 点击退出登录触发的事件
			$("#exit").click(function(){
				window.location.href="${pageContext.request.contextPath }/LoginAction_loginOut";
			}) ;
			// 个人信息显示详细的窗口
			$("#me").click(function(){
				$("#showMe").window("open");
			});
			// 个人信息的取消按钮
			$("#showMe_cancel").on("click", function(){
				$("#showMe").window("close");
			});
			// 修改密码的弹出框
			$("#editPwd").click(function(){
				$("#update_win").window("open");
			});
			// 修改密码的保存按钮
			$("#editUser_saveAndEdit").click(function(){
				//进行表单校验
				var v = $("#update_form").form("validate");
				if(v){
					var v1 = $("#newPwd").val();
					var v2 = $("#rePwd").val();
					if(v1 == v2){
						var url = "${pageContext.request.contextPath }/ManageAction_updatePwd";
						$.post(url,{"password":v1},function(result){
							if(result){
								//修改密码成功
	                            $.messager.alert("提示信息","密码修改成功，请重新登录！","info");
	                            $("#update_form").form("clear");
							}else{
								//修改失败
	                            $.messager.alert("提示信息","密码修改失败！","warning");
							}
							 //关闭修改密码的窗口 
							$("#update_win").window("close");
						})
					}else{
						 //输入不一致，提示用户输入不一致
	                    $.messager.alert("提示信息","两次输入密码不一致！","warning");
					}
				}
			});
			$("#editUser_cancel").click(function(){
				$("#update_form").form("clear");
				$("#update_win").window("close");
			});
			
			// 上传文件的弹出框
			$("#upload").click(function(){
				$("#upload_win").window("open");
			});
			// 上传窗口的保存
			$("#upload_save").click(function(){
				//alert("上传成功");
				$("#upload_form").form("submit",{
					url : "file_upload",
					success : function(result){
						if(result[5]==1){
							alert("成功");
							$("#upload_win").window("close");
						}else{
							alert("上传失败");
						}
					}
				});
			});
			//上传窗口的取消键
			$("#upload_cancel").click(function(){
				$("#upload_win").window("close");
			});
		});
	</script>
<div data-options="region:'north',border:false"
	style="height: 60px; padding: 5px; background: #F3F3F3">
	<a href="#"><span class="northTitle">实验室设备管理系统</span></a>
	<span class="loginInfo">&nbsp;&nbsp;&nbsp;&nbsp;
		<%-- <a href="${pageContext.request.contextPath }/LoginAction_loginOut">退出登录</a> --%>
		<a href="javascript:void(0)" id="mb">${user.username }</a>
		<div id="mm" style="width:80px">   
			<div data-options="iconCls:'icon-search'" id="me">个人信息</div>
		    <div data-options="iconCls:'icon-edit'" id="editPwd">修改密码</div>   
			<div class="menu-sep"></div>
			<c:if test="${user.role != 3}">
			    <div id="upload">上传文件</div>
			    <div class="menu-sep"></div>
			</c:if>
		    <div data-options="iconCls:'icon-no'" id="exit">退出登录</div>   
		</div>   
	</span>
	
	<!-- 个人信息弹出框开始-->
	<div id="showMe" class="easyui-window" title="详细"
		data-options="iconCls:'icon-save',closed:true"
		style="width: 350px; height: 250px; padding: 5px; ">
		<table width="280" border="0" align="center" cellpadding="3">
			<!-- 154行已经自动填写表单的值，id会自动发送，不用给值 -->
			<tr style="display: none">
				<td><input type="text" name="id" id="id"/></td>
			</tr>
			<tr>
				<td align="right"><label for="username">账号</label></td>
				<td><input type="text" name="username" value="${user.username}" readonly /></td>
			</tr>
			<tr>
				<td align="right"><label for="password">密码</label></td>
				<td><input type="text" name="password"  value="${user.password}" readonly /></td>
			</tr>
			<tr>
				<td align="right"><label for="name">姓名</label></td>
				<td><input type="text" name="name" value="${user.name }" readonly /></td>
			</tr>
			<tr>
				<td align="right"><label for="sex">性别</label></td>
				<td><input type="text" name="sex" value="${user.sex }" readonly /></td>
			</tr>
			<c:if test="${user.role==3 }">
				<tr>
					<td align="right"><label for="academy">院系</label></td>
					<td><input type="text" name="academy" value="${user.academy }" readonly /></td>
				</tr>
				<tr>
					<td align="right"><label for="cla">班级</label></td>
					<td><input type="text" name="cla" value="${user.cla }" readonly /></td>
				</tr>
			</c:if>
			<tr>
				<td align="right"><label for="phone">电话</label></td>
				<td><input type="text" name="phone" value="${user.phone }" readonly /></td>
			</tr>
			<tr id="update_claInput" style="display : none">
				<td align="right"><label for="update_cla"><span class="x">*</span>班级</label></td>
				<td><input type="text" name="cla" id="update_cla"/></td>
			</tr>
		</table>
		<!-- 取消按钮 -->
		<div class="windowButton" style="text-align:center;padding:5px">
		    <a id="showMe_cancel" class="easyui-linkbutton my-dialog-button"
				plain="true" icon="icon-cancel" href="javascript:void(0)">取消</a>
		</div>
	</div>
	<!-- 个人信息弹出框结束-->
	
	<!-- 修改密码弹出框开始-->
	<div id="update_win" class="easyui-window" title="修改密码"
		data-options="iconCls:'icon-save',closed:true"
		style="width: 300px; height: 150px; padding: 5px;">
		<form id="update_form" action="" method="post">
			<table width="280" border="0" align="center" cellpadding="3">
				<tr style="display:none">
					<td><input type="text" name="id" value=""/></td>
				</tr>
				<tr>
					<td width="80" align="right"><label for="userName"><span
							class="x">*</span>新密码</label></td>
					<td width="200"><input id="newPwd" class="easyui-validatebox" 
						data-options="required:true,validType:'length[6,10]',missingMessage:'不能为空',invalidMessage:'字符在6~10之间'" 
						type="password" name="name"  /></td>
				</tr>
				<tr>
					<td align="right"><label for="password"><span
						class="x">*</span>确认密码</label></td>
					<td><input id="rePwd" class="easyui-validatebox" 
						data-options="required:true,validType:'length[6,10]',missingMessage:'不能为空',invalidMessage:'字符在6~10之间'"
						type="password"  /></td>
				</tr>
			</table>
		</form>
		<!-- 保存修改和取消按钮 -->
		<div class="windowButton" style="text-align:center;padding:5px">
			<a id="editUser_saveAndEdit" class="easyui-linkbutton my-dialog-button"
				plain="true" icon="icon-ok" href="javascript:void(0)">确定</a>
		    <a id="editUser_cancel" class="easyui-linkbutton my-dialog-button"
				plain="true" icon="icon-cancel" href="javascript:void(0)">取消</a>
		</div>
	</div>
	<!-- 修改密码弹出框结束-->
		
	<!-- 上传文件弹出框开始-->
	<div id="upload_win" class="easyui-window" title="上传"
		data-options="iconCls:'icon-save',closed:true"
		style="width: 400px; height: 150px; padding: 5px;">
		<form id="upload_form" action="" method="post" enctype="multipart/form-data">
			<table width="280" border="0" align="center" cellpadding="3">
				<tr>
					<td align="right"><label for="icon"><span class="x">*</span>选择文件</label></td>
					<td><input type="file" name="icon"></td>
				</tr>
				<tr>
					<td align="right"><label for="day"><span class="x">*</span>上传文件类型:</label></td>
					<td>
						<select name="fileType">
							<option value="1">文档</option>
							<option value="2">视频(仅支持MP4)</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<!-- 保存修改和取消按钮 -->
		<div class="windowButton" style="text-align:center;padding:5px">
			<a id="upload_save" class="easyui-linkbutton my-dialog-button"
				plain="true" icon="icon-ok" href="javascript:void(0)">确定上传</a>
		    <a id="upload_cancel" class="easyui-linkbutton my-dialog-button"
				plain="true" icon="icon-cancel" href="javascript:void(0)">取消</a>
		</div>
	</div>
	<!-- 上传文件弹出框结束-->
	
	
</div>

		