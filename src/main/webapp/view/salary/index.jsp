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

</body>
</html>