<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>文件下载界面</title>
	<script>
		function down(test){
			var tr = test.parentNode.parentNode.getElementsByTagName("td")[0].innerHTML;
			test.href="DownLoadAction_play?videoName="+encodeURI(encodeURI(tr));
		};
	</script>
</head>
<body>
	
	<s:if test="#request.uploads != null">
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<td>文件名</td>
				<td>上传人</td>
				<td>上传时间</td>
				<td></td>
			</tr>
			
			<s:iterator value="#request.uploads">
				<tr>
					<td>${filename }</td>
					<td>${username }</td>
					<td>${uploadTime }</td>
					<td><a href="#" onclick="down(this)">播放</a></td>
				</tr>		
			</s:iterator>
		</table>
	</s:if>
	<s:else>
		<h5>没有文件可下载</h5>
	</s:else>
	
</body>
</html>