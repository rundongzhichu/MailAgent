<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>主页</title>
<link rel="stylesheet" href="css/main.css" />
<script type="text/jscript" src="js/jquery-1.8.3.min.js"></script>
<script>
	$(function(){
		$("#send").click(function(){
			window.location.href="send_mail.jsp";
		});
		$("#download").click(function(){
			window.location.href="DownloadEmail";
		});
	});
</script>
</head>
<body>
<div><input id="send" type="button" value="发送邮件"></input></div>
<div><input id="download" type="button" value="下载邮件"></input></div>
</body>
</html>