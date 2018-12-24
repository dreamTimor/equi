<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath }/css/bootstrap.css"
	rel="stylesheet" type="text/css" />
<style>
.bg {
	width: 100%;
	height: 800px;
	padding:80px 0;
	margin: 0 auto;
	background:url(image/bg1.jpg) no-repeat;
	background-size:100%;
}
</style>
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
<body style="background:#55c1f2;">
	<div class="container col-md-12">
		<div class="bg">
			<div class="box col-md-4 col-md-offset-7 img-rounded"
				style="margin-top: 100px; background-color: white;">
				<span><label class="h3">登录 </label>&nbsp&nbspUSER LOGIN</span>
				<!-- form表格 -->
				<form class="form-horizontal" style="margin-top: 20px;" action="LoginAction_login" method="post" name="login_form" onsubmit="return formchecker()">
					<div class="form-group">
						<label class="col-sm-3 control-label" style="padding-right: 10px;"><span class="glyphicon glyphicon-user"></span></label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="username" id="inputUsername" placeholder="学号/教职工编号" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" style="padding-right: 10px;"><span class="glyphicon glyphicon-lock"></span></label>
						<div class="col-sm-7">
							<input type="password" class="form-control" name="password" id="inputPassword" placeholder="密码" required>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 0;">
						<label class="col-sm-3 control-label" style="padding-right: 10px;"><span class="glyphicon glyphicon-indent-left"></span></label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="validate" id="inputValidate" placeholder="验证码" required>
						</div>
						<div class="" style="margin-top: 5px;">
							<input type="text" id="checkCode" class="code" style="width: 55px" /> <a href="#" class="click">看不清楚</a><br />
							<br />
						</div>
					</div>
					<div class="form-group" style="">
						<div class="col-sm-offset-3 col-sm-10">
							<div class="checkbox">
								<label> <input type="checkbox">自动登录&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp </label> <label>
									<input type="checkbox"> 记住用户名 
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-8 col-sm-offset-2">
							<button type="submit" class="btn btn-primary btn-block">登录</button>
						</div>
					</div>
				</form>
				
			</div>
		</div>
</body>