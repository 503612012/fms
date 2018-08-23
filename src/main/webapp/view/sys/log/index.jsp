<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="<%=basePath%>/view/sys/log/js/index.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/datagrid-detailview.js"></script>
<script type="text/javascript">
	function getBasePath(){
		return '<%=basePath%>';
	}
	function g(val, row) {
		return "<span tital='"+val+"'>"+val+"</span>";
	}
</script>
</head>
<body>
<table id="logList" title="日志列表" class="easyui-datagrid" fitColumns="true" style="width:100%; height:100%" fit="true" border="false"
	iconCls="myIcon-log" pagination="true" url="<%=basePath%>/sys/log/list.html" data-options="singleSelect:true" toolbar="#toolbar">
	<thead>
		<tr>
			<th field="id" width="10">ID</th>
			<th field="title" width="15" sortable="true">标题</th>
			<th field="content" width="60" title="content" formatter="showTitle">内容</th>
			<th field="nickName" width="15" sortable="true">操作人</th>
			<th field="ip" width="15">操作IP</th>
			<th field="createTime" width="25">操作时间</th>
		</tr>
	</thead>
</table>

<!-- 表格的工具条 -->
<div id="toolbar">
	<input name="keyword" value="${keyword}" style="line-height:18px; border:1px solid #ccc; border-radius: 4px">
	<a href="javascript:void(0)" class="easyui-linkbutton search_btn" plain="true" iconCls="icon-search">查询</a>
</div>

</body>
</html>