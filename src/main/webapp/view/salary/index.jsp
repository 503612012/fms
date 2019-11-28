<%@ page import="com.oven.util.AuthUtils" %>
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
<script type="text/javascript" src="<%=basePath%>/source/js/ajaxhook.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/themes/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/js/accounting.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/highcharts5/highcharts.src.js"></script>
<script type="text/javascript" src="<%=basePath%>/view/salary/js/index.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/js/common.js"></script>
<script type="text/javascript">
	function getBasePath(){
		return '<%=basePath%>';
	}
	function hasPaySalaryPermission() {
		return '<%=AuthUtils.hasPermission("E1_01_01")%>';
    }
</script>
</head>
<body>

<div id="salary-count" class="easyui-panel" title="工资统计" data-options="iconCls:'myIcon-calculator', fit: true, border: false, tools: '#salary-count-tools'">
	<div style="padding: 10px; font-size: 16px;">
		<select class="easyui-combobox" name="salary-count-date-type" id="salary-count-date-type">
			<option value="byMonth">按月统计</option>
			<option value="byYear">按年统计</option>
		</select>
		<input class="Wdate" id="salary-count-date" name="date" type="text" style="border: 1px solid #95B8E7; border-radius: 5px 5px 5px 5px;"/>
		<input class="easyui-combobox" id="salary-count-eid" name="eid" data-options="
						url: '<%=basePath%>/employee/employee/findAll.html',
						valueField: 'id',
						textField: 'text',
						pannelHeight: 'auto'" style="width: 100px;"/>
		<a href="javascript:void(0)" class="easyui-linkbutton" id="salary-count-search-btn" iconCls="icon-search">查询</a>
	</div>
	<div>
		<div id="salary-count-charts"></div>
	</div>
	<div>
		<table id="salary-count-table" style="width: 100%;"></table>
	</div>
</div>

<!-- 工资发放模态窗口 -->
<div id="pay_salary_dialog" class="easyui-dialog" style="width:500px;height:400px;padding:10px 10px" closed="true" buttons="#pay_salary_dialog_buttons">
	<form id="pay_salary_dialog_form" method="post">
		<input type="hidden" name="eid" value="">
		<table cellspacing="10px;">
			<tr>
				<td>员工姓名：</td>
				<td><input name="ename" id="ename" class="easyui-textbox" required="true" style="width: 200px;"></td>
			</tr>
			<tr>
				<td>工作月份：</td>
				<td><input name="workDate" id="workDate" type="text" class="easyui-textbox" required="true" style="width: 200px;"></td>
			</tr>
			<tr>
				<td>应发工资：</td>
				<td>
					<input name="shouldPaySalary" id="shouldPaySalary" type="text" class="easyui-numberbox" data-options="precision:2" required="true" style="width: 200px;">
				</td>
			</tr>
			<tr>
				<td>实发工资：</td>
				<td>
					<input name="actualPaySalary" id="actualPaySalary" type="text" class="easyui-numberbox" data-options="precision:2" required="true" style="width: 200px;">
				</td>
			</tr>
			<tr>
				<td>备注：</td>
				<td><input class="easyui-textbox" id="remark" name="remark" data-options="multiline:true" style="height:60px; width: 200px;"/></td>
			</tr>
		</table>
	</form>
</div>

<div id="pay_salary_dialog_buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton pay_salary_dialog_buttons_submit_btn" iconCls="icon-ok">确认</a>
	<a href="javascript:void(0)" class="easyui-linkbutton pay_salary_dialog_buttons_cancel_btn" iconCls="icon-cancel" onclick="javascript:$('#pay_salary_dialog').dialog('close')">关闭</a>
</div>

</body>
</html>