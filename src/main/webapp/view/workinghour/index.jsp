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
<script type="text/javascript" src="<%=basePath%>/source/js/ajaxhook.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/themes/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/view/workinghour/js/index.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/js/common.js"></script>
<script type="text/javascript">
	function getBasePath(){
		return '<%=basePath%>';
	}
    var buttons = $.extend([], $.fn.datebox.defaults.buttons);
    buttons.splice(1, 0, {
        text: '清空',
        handler: function(target) {
            $("#workinghour-work-date").combo("setValue", "").combo("setText", ""); // 设置空值
            $("#workinghour-work-date").combo("hidePanel"); // 点击清空按钮之后关闭日期选择面板
        }
    });
</script>
</head>
<body>
<table id="workinghourList" title="工时列表" class="easyui-datagrid" fitColumns="true" style="width:100%; height:100%" fit="true" border="false"
	iconCls="myIcon-time" pagination="true" url="<%=basePath%>/workinghour/workinghour/list.html?date=${date}" data-options="singleSelect:true" toolbar="#toolbar">
	<thead>
		<tr>
			<th field="id" width="20">ID</th>
			<th field="ename" width="50">员工姓名</th>
			<th field="score" width="30">工时记分</th>
			<th field="date" width="50">工作时间</th>
			<th field="daySalary" width="30">日薪</th>
			<th field="extraSalary" width="30" data-options="formatter:formatExtraSalary">额外薪资</th>
			<th field="worksiteName" width="60">工地</th>
			<th field="inputNickName" width="50">录入人员</th>
			<th field="inputDate" width="70">录入时间</th>
			<th field="remark" width="100">备注</th>
		</tr>
	</thead>
</table>

<!-- 表格的工具条 -->
<div id="toolbar">
	工作时间：<input class="easyui-datebox" data-options="buttons:buttons" id="workinghour-work-date" value="${date}" type="text" name="date" editable="false" style="line-height:18px;width: 200px;"/>
	关键字：<input name="keyword" value="${keyword}" style="line-height:18px; border:1px solid #ccc; border-radius: 4px">
	<a href="javascript:void(0)" class="easyui-linkbutton search_btn" plain="true" iconCls="icon-search">查询</a>
</div>

</body>
</html>