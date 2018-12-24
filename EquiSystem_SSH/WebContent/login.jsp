<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="${pageContext.request.contextPath }/css/bootstrap.css"
		rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath }/css/login.css"
		rel="stylesheet" type="text/css" />
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
<script>
        function formchecker() {
            var inputUsername = document.getElementById("inputUsername").value;
            var inputPassword = document.getElementById("inputPassword").value;
            var validate = document.getElementById("validate").value;
            if(inputUsername.replace(/^\s+|\s+$/g,"").length == 0) {
                alert("登录名为必填字段");
                login_form.username.focus();
                return false;
            }
            /*验证登录名的长度是否合法		//如果需要的话，就去掉注释再改改
             else if(inputUsername.length < 3) {
             alert("登录名至少3位");
             login_form.username.focus();
             return false;
             }*/
            else if(inputPassword.replace(/^\s+|\s+$/g,"").length == 0) {
                alert("密码为必填字段");
                login_form.password.focus();
                return false;
            }
            else if(validate.replace(/^\s+|\s+$/g,"").length == 0) {
                alert("验证码为必填字段");
                login_form.validate.focus();
                return false;
            }
            /*验证密码的长度是否合法
             else if(pwd1.length < 6) {
             alert("密码至少6位");
             login_form.password1.focus();
             return false;
             }*/
            else {
                return true;
            }
            
        }
    </script>
</head>
<body class="easyui-layout">
	<div class="all">
		<!-- 头部开始 -->
		<div class="header">
			<div class="title">
				<h1><span>实验室设备</span>管理系统</h1>
				<short>Laboratory Equiment Management System</short>
			</div>
		</div>
		<!-- 头部结束 -->

		<!-- 中间内容开始 -->
		<div class="center img-responsive" style="background-color: #63c6f0">
			<!-- 登录开始 -->
			<div class="container col-md-12 login">
				<!-- <div class="title">
					<h1>
						<span>实验室设备</span>管理系统
					</h1>
					<short>Laboratory Equiment Management System</short>
				</div> -->
				<div class="box col-md-4 col-md-offset-7 img-rounded"
					style="margin-top: 0px; background-color: white;">
					<span><label class="h3">登录 </label>&nbsp&nbspUSER LOGIN</span>
					<hr />
					<!-- form表格 -->
					<form class="form-horizontal" style="margin-top: 10px;"
						action="LoginAction_login" method="post" name="login_form"
						onsubmit="return formchecker()">
						<div class="form-group">
							<label class="col-sm-3 control-label"
								style="padding-right: 10px;"><span
								class="glyphicon glyphicon-user"></span></label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="username"
									id="inputUsername" placeholder="学号/教职工编号" required>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"
								style="padding-right: 10px;"><span
								class="glyphicon glyphicon-lock"></span></label>
							<div class="col-sm-7">
								<input type="password" class="form-control" name="password"
									id="inputPassword" placeholder="密码" required>
							</div>
						</div>
						<div class="form-group" style="margin-bottom: 0;">
							<label class="col-sm-3 control-label"
								style="padding-right: 10px;"><span
								class="glyphicon glyphicon-indent-left"></span></label>
							<div class="col-sm-3">
								<input type="text" class="form-control" name="validate"
									id="inputValidate" placeholder="验证码" required>
							</div>
							<div class="" style="margin-top: 5px;">
								<input type="text" id="checkCode" class="code"
									style="width: 55px" /> <a href="#" class="click">看不清楚</a><br />
								<br />
							</div>
						</div>
						<div class="form-group" style="margin-top: 15px">
							<div class="col-sm-8 col-sm-offset-2">
								<button type="submit" class="btn btn-primary btn-block">登录</button>
							</div>
						</div>
						<div style="text-align: right">
							<a href="file_getListSession" target="_Blank">规章制度</a><!-- target打开新窗口 -->
							<a href="file_getVideoListSession" target="_Blank">教学视频</a>
						</div>
					</form>
				</div>
			</div>
			<!-- 登录结束 -->
		</div>
		<!-- 中间内容开始 -->

		<!-- 底部开始 -->
		<div class="footer">
			<p>版权所有：华南农业大学珠江学院 粤ICP备10075462号-1</p>
			<p>地址：广州市从化区广从北路72号 电 话：020-87979913 传 真：020-87979913
				招生电话：40000-12623 邮 编：510900</p>
		</div>
		<!-- 底部结束 -->
	</div>
	
	
	
	<!-- 添加弹出框 -->
		<div id="add_win" class="easyui-window" title="规章制度文件"
			data-options="iconCls:'icon-save',closed:true"
			style="width: 450px; height: 320px; padding: 5px;">
			<form id="add_form" method="post">

				<s:if test="#ccc != null">
					成功
				</s:if>
						
			</form>
		</div>
		<!-- 添加弹出框结束-->
	
	
	
</body>