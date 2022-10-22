<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui">
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;chrome=1">
<title>选择发邮件服务器</title> 
<link rel="stylesheet" href="css/main.css" />
<script type="text/jscript" src="js/jquery-1.8.3.min.js"></script>

<script>
	$(function(){
		$("input[type='radio']").click(function(){
			$("#use").css("visibility","visible");
			$("#pa").css("visibility","visible");
			$("#lo").css("visibility","visible");
		});
	});

</script>
</head>
<body>

	<form method="post" action="ChooseServer">
		<table>
			<tr>
				<td>邮件服务器</td>
				<td>
					<label><input id="server"  name="server" type="radio" value="163.com" />163.com</label> 
					<label><input id="server" name="server" type="radio" value="qq.com" />qq.com </label> 
				</td>
			</tr>
			<tr id="use">
				<td>用户名：</td>
				<td><input name="sender" type="text" style="width: 200px;height: 20px;"/></td>
			</tr>
			<tr id ="pa">
				<td>密 码：</td>
				<td><input name="password" type="text" style="width: 200px;height: 20px;"/></td>
			</tr>
		</table>
		<input id="lo"  type="submit" value="登录" />
	</form>
</body>
</html>
