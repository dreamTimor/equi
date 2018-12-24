<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>教学视频</title>
	<script src="js/jquery.js"></script>	
	<script src="js/mediaelement-and-player.min.js"></script>	
	<link rel="stylesheet" href="css/mediaelementplayer.css" />
    <link rel="stylesheet" href="css/mejs-skins.css" />
	
</head>
<body>
	
	<div style="width:640px; height:360px; margin:50px auto; background-color:black" >
		<video class="mejs-wmp" width="640" height="360" src="${requestScope.videoPath }" type="video/mp4" 
			id="player1" controls="controls" preload="none"></video>
	</div>

	<script>
		$('audio,video').mediaelementplayer({
			success: function(player, node) {
				$('#' + node.id + '-mode').html('mode: ' + player.pluginType);
			}
		});
	</script>	
	
	
</body>
</html>