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
<style>
	.space-between {
		display: flex;
		justify-content: flex-end;
	}
</style>
<script type="text/javascript" src="<%=basePath%>/source/js/ajaxhook.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/themes/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/view/workinghour/js/add.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/js/common.js"></script>
<script type="text/javascript">
	function getBasePath() {
		return '<%=basePath%>';
	}
</script>
</head>
<body>

<form id="addWorkinghourForm" method="post">
	<table cellpadding="5">
		<tr>
			<td class="space-between">员工：</td>
			<td><input class="easyui-combobox" id="workinghour-eid" name="eid" data-options="
						url: '<%=basePath%>/employee/employee/findAll.html',
						valueField: 'id',
						textField: 'text',
						onLoadSuccess: workinghourEidLoadSuccess,
						onSelect: workinghourEidChange,
						pannelHeight: 'auto'" style="width: 200px;"/></td>
		</tr>
		<tr>
			<td class="space-between">工作时间：</td>
			<td><input class="easyui-datebox" id="workinghour-date" type="text" name="date" editable="false" data-options="required:true" style="width: 200px;"/></td>
		</tr>
		<tr>
			<td class="space-between">员工日薪：</td>
			<td><input name="daySalary" id="workinghour-day-salary" type="text" class="easyui-numberbox" data-options="min:0, precision:2" required="true" style="width: 200px;"></td>
		</tr>
		<tr>
			<td class="space-between">工地：</td>
			<td><input class="easyui-combobox" id="workinghour-worksite" name="worksite" data-options="
						url: '<%=basePath%>/worksite/worksite/findAll.html',
						valueField: 'id',
						textField: 'text',
						pannelHeight: 'auto'" style="width: 200px;"/></td>
		</tr>
		<tr>
			<td class="space-between">工时记分：</td>
			<td><input name="score" id="workinghour-score" type="text" class="easyui-numberbox" data-options="min:0, precision:2" required="true" style="width: 200px;"></td>
		</tr>
		<tr>
			<td class="space-between">额外薪资：</td>
			<td><input name="extraSalary" id="workinghour-extra-salary" type="text" class="easyui-numberbox" data-options="precision:2" style="width: 200px;"></td>
		</tr>
		<tr>
			<td class="space-between">备注：</td>
			<td><input class="easyui-textbox" id="workinghour-remark" name="remark" data-options="multiline:true" required="true" style="height:60px; width: 200px;"/></td>
		</tr>
		<tr>
			<td class="space-between">额外薪资说明：</td>
			<td><input class="easyui-textbox" id="workinghour-extra-salary-desc" name="extraSalaryDesc" data-options="multiline:true" style="height:60px; width: 200px;"/></td>
		</tr>
	</table>
</form>

<div style="padding: 5px; margin-left: 127px">
	<a href="javascript:void(0)" class="easyui-linkbutton" id="workinghour-submit-btn">提交</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" id="workinghour-clear-btn">清空</a>
</div>

</body>
</html>