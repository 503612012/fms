<%@ page import="com.oven.util.AuthUtils"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/view/employee/employee/css/index.css?v=1.0.0" />
<script type="text/javascript" src="<%=basePath%>/source/js/ajaxhook.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/themes/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/view/employee/employee/js/index.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/js/common.js"></script>
<script type="text/javascript">
	function getBasePath(){
		return '<%=basePath%>';
	}
	function hasUpdateEmployeeStatusPermission() {
		return '<%=AuthUtils.hasPermission("B1_01_04")%>';
	}
	function hasShowMeneyPermission(perCode) {
		return '<%=AuthUtils.hasPermission("B1_01_05")%>';
	}
</script>
</head>
<body>
<table id="employeeList" title="员工列表" class="easyui-datagrid" fitColumns="true" style="width:100%; height:100%" fit="true" border="false"
	iconCls="myIcon-employee" pagination="true" url="<%=basePath%>/employee/employee/list.html" data-options="singleSelect:true" toolbar="#toolbar">
	<thead>
		<tr>
			<th field="id" width="20">ID</th>
			<th field="name" width="60">姓名</th>
			<th field="age" width="60">年龄</th>
			<th field="gender" width="60" formatter="formatterGender">性别</th>
			<th field="address" width="60">住址</th>
			<th field="contact" width="60">联系方式</th>
			<th field="daySalary" width="60" formatter="formatterDaySalary">日薪</th>
			<th field="monthSalary" width="60" formatter="formatterMonthSalary">月薪</th>
			<th field="createTime" width="60">创建时间</th>
			<th field="modifyTime" width="60">最后修改时间</th>
			<th field="status" width="60" formatter="formatterStatus">
				<span>状态</span>
				<shiro:hasPermission name="B1_01_04">
					<img class="notice" src="<%=basePath%>/file/images/icon/notice.png" title="点击可更改用户状态！">
				</shiro:hasPermission>
			</th>
		</tr>
	</thead>
</table>

<!-- 表格的工具条 -->
<div id="toolbar">
	<shiro:hasPermission name="B1_01_01">
		<a href="javascript:void(0)" class="easyui-linkbutton add_employee_btn" iconCls="icon-add" plain="true">添加</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="B1_01_02">
		<a href="javascript:void(0)" class="easyui-linkbutton update_employee_btn" iconCls="icon-edit" plain="true">修改</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="B1_01_03">
		<a href="javascript:void(0)" class="easyui-linkbutton delete_employee_btn" iconCls="icon-remove" plain="true">删除</a>
	</shiro:hasPermission>
	<input name="keyword" value="${keyword}" style="line-height:18px; border:1px solid #ccc; border-radius: 4px">
	<a href="javascript:void(0)" class="easyui-linkbutton search_btn" plain="true" iconCls="icon-search">查询</a>
</div>

<!-- 添加和修改窗口 -->
<div id="employee_manage_page" class="easyui-dialog" style="width:500px;height:400px;padding:10px 10px" closed="true" buttons="#dlg-buttons">
	<form id="employeeForm" method="post">
		<input type="hidden" name="id" value="">
		<table cellspacing="10px;">
			<tr>
				<td>姓名：</td>
       			<td><input name="name" id="employee-name" class="easyui-textbox" required="true" style="width: 200px;"></td>
       		</tr>
       		<tr>
       			<td>年龄：</td>
       			<td><input name="age" id="age" type="text" class="easyui-numberbox" required="true" style="width: 200px;"></td>
       		</tr>
       		<tr>
       			<td>性别：</td>
       			<td>
       				<input type="radio" value="1" id="gender_1" name="gender">男
       				<input type="radio" value="0" id="gender_0" name="gender">女
       			</td>
       		</tr>
       		<tr>
       			<td>住址：</td>
       			<td>
					<input class="easyui-textbox" id="employee-address" name="address" data-options="multiline:true" style="height: 60px; width: 200px;"/>
       			</td>
       		</tr>
       		<tr>
       			<td>联系方式：</td>
       			<td><input name="contact" id="employee-contact" type="text" class="easyui-textbox" required="true" style="width: 200px;"></td>
       		</tr>
       		<tr>
       			<td>日薪：</td>
       			<td><input name="daySalary" id="daySalary" type="text" class="easyui-numberbox" data-options="min:0, precision:2" required="true" style="width: 200px;"></td>
       		</tr>
       		<tr>
       			<td>月薪：</td>
       			<td><input name="monthSalary" id="monthSalary" type="text" class="easyui-numberbox" data-options="min:0, precision:2" required="true" style="width: 200px;"></td>
       		</tr>
       	</table>
	</form>
</div>

<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton submit_btn" iconCls="icon-ok">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton cancel_btn" iconCls="icon-cancel" onclick="javascript:$('#employee_manage_page').dialog('close')">关闭</a>
</div>

</body>
</html>