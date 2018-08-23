<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>财务管理系统</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/source/css/login.css" />
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/themes/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/js/login.js"></script>
<script type="text/javascript">
	function getBasePath(){
		return '<%=basePath%>';
	}
</script>
</head>
<body>
<div class="login-box">
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="欢迎登陆财务管理系统" style="width:400px">
		<div style="padding:10px 60px 20px 60px">
		   	<table cellpadding="5">
		   		<tr>
		   			<td>用户名:</td>
		   			<td><input class="easyui-textbox" type="text" name="userName" data-options="required:true"/></td>
		   		</tr>
		   		<tr>
		   			<td>密&emsp;码:</td>
		   			<td><input class="easyui-textbox" type="password" name="password" data-options="required:true"/></td>
		   		</tr>
		   		<tr>
		   			<td>验证码:</td>
		   			<td class="captcha_td">
		   				<input class="easyui-textbox" type="text" name="captcha" data-options="required:true"/>
		   				<span><img src="<%=basePath%>/sys/getCaptcha.html" class="refresh_captcha"/></span>
		   			</td>
		   		</tr>
		   		<tr>
		   			<td colspan="2">
		   				<span class="red">${errorMsg}</span>
		   			</td>
		   		</tr>
		   	</table>
		    <div style="text-align:center;padding:5px">
		    	<a href="javascript:void(0)" class="easyui-linkbutton submit-btn">登录</a>
		    </div>
	    </div>
	</div>
</div>
</body>
</html>