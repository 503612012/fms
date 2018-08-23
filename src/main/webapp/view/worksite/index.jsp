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
<script type="text/javascript" src="<%=basePath%>/view/worksite/js/index.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/js/common.js"></script>
<script type="text/javascript">
	function getBasePath(){
		return '<%=basePath%>';
	}
</script>
</head>
<body>
<table id="worksiteList" title="工地列表" class="easyui-datagrid" fitColumns="true" style="width:100%; height:100%" fit="true" border="false"
	iconCls="myIcon-worksite" pagination="true" url="<%=basePath%>/worksite/worksite/list.html" data-options="singleSelect:true" toolbar="#toolbar">
	<thead>
		<tr>
			<th field="id" width="20">ID</th>
			<th field="name" width="60">工地名</th>
			<th field="desc" width="60">工地描述</th>
			<th field="createNickName" width="60">创建人</th>
			<th field="createDate" width="60">创建时间</th>
		</tr>
	</thead>
</table>

<!-- 表格的工具条 -->
<div id="toolbar">
	<shiro:hasPermission name="D1_01_01">
		<a href="javascript:void(0)" class="easyui-linkbutton add_worksite_btn" iconCls="icon-add" plain="true">添加</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="D1_01_02">
		<a href="javascript:void(0)" class="easyui-linkbutton update_worksite_btn" iconCls="icon-edit" plain="true">修改</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="D1_01_03">
		<a href="javascript:void(0)" class="easyui-linkbutton delete_worksite_btn" iconCls="icon-remove" plain="true">删除</a>
	</shiro:hasPermission>
	<input name="keyword" value="${keyword}" style="line-height:18px; border:1px solid #ccc; border-radius: 4px">
	<a href="javascript:void(0)" class="easyui-linkbutton search_btn" plain="true" iconCls="icon-search">查询</a>
</div>

<!-- 添加和修改窗口 -->
<div id="worksite_manage_page" class="easyui-dialog" style="width:400px;height:300px;padding:10px 10px" closed="true" buttons="#dlg-buttons">
	<form id="worksiteForm" method="post">
		<input type="hidden" name="id" value="">
		<table cellspacing="10px;">
			<tr>
				<td>工地名：</td>
       			<td><input name="name" id="worksite-name" class="easyui-textbox" required="true" style="width: 170px;"></td>
       		</tr>
       		<tr>
       			<td>描述：</td>
       			<td><input class="easyui-textbox" id="worksite-desc" name="desc" data-options="multiline:true" style="height: 60px; width: 170px;"/></td>
       		</tr>
       	</table>
	</form>
</div>

<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton submit_btn" iconCls="icon-ok">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton cancel_btn" iconCls="icon-cancel" onclick="$('#worksite_manage_page').dialog('close')">关闭</a>
</div>

</body>
</html>