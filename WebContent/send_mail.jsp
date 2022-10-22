<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui">
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;chrome=1">
<title>发送邮件</title> 
<link rel="stylesheet" href="css/main.css" />
<script type="text/jscript" src="js/jquery-1.8.3.min.js"></script>
</head>

 <body> 	
 	<!-- enctype="multipart/form-data" -->
	<form method="post"  action="MailSendServlet" enctype="multipart/form-data" >
	    <table>
	      <tr>
	        <td style="width: 80;">
	          	收件人:
	        </td>
	        <td><input id="receiver" type="text" name="receiver" /></td>
	      </tr>
	      <tr>
	        <td style="width: 80;">
				主题:
	        </td>
	        <td>
	           <input id="subject" type="text" name="subject" />
	        </td>
	      </tr>
	      <tr>
	      	<td style="width: 80;">
				添加附件:
	        </td>
	        
	        <td>
	          <input id="attach" type="file" name="attach" value="添加附件"/>
	        </td>
	      </tr>
	        
	      <tr> 
	      	<td style="width: 80;">
	      		正文 :
	      	</td>
	      	<td>
	      		<textarea id="context"  name="context" ></textarea>
	      	</td>
 		  </tr>
	    </table>
	    <input id="send"  type="submit" value="发送邮件" />
  </form>
      <!-- 不同状态 -->
    <c:if test="${requestScope.state == 200 }">
        <div >
	     	发送成功！
	    </div>
    </c:if>
    
     <!-- 不同状态 -->
    <c:if test="${requestScope.state == 400 }">
        <div >
	     	发送失败！
	    </div>
    </c:if>
 </body>
</html>



