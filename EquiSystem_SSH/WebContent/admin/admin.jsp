<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理员</title>
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/css/default.css">
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.5/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.5/themes/icon.css" />
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/js/JQuery-zTree-v3.5.15/css/zTreeStyle/zTreeStyle.css">
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.5/jquery-1.10.2.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/JQuery-zTree-v3.5.15/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/admin.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/extends.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common.js"></script>
</head>

<body class="easyui-layout">
	<!-- 头部标题 -->
	<jsp:include page="/public/header.jsp"></jsp:include>

	<!-- 左侧导航 -->
	<jsp:include page="/public/nav.jsp"></jsp:include>

	<!-- 内容tabs -->
	<jsp:include page="/public/tabs.jsp"></jsp:include>

	<!-- 用于弹出框 -->
	<div id="parent_win"></div>

	<!-- 页脚信息 -->
	<jsp:include page="/public/footer.jsp"></jsp:include>
</body>

</html>
