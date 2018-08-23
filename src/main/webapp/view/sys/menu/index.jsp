<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%@ page import="com.skyer.util.AuthUtils"%>
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
<link rel="stylesheet" type="text/css" href="<%=basePath%>/view/sys/menu/css/index.css?v=1.0.0" />
<script type="text/javascript" src="<%=basePath%>/source/js/ajaxhook.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/themes/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/view/sys/menu/js/index.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/js/common.js"></script>
<script type="text/javascript">
	function getBasePath(){
		return '<%=basePath%>';
	}
	function hasUpdateMenuStatusPermission() {
		return '<%=AuthUtils.hasPermission("A1_02_02")%>';
	}
</script>
</head>
<body>
<table id="menuList" class="easyui-treegrid" title="菜单管理" style="width:700px;height:250px" fit="true" border="false"
	iconCls="myIcon-menu" url="<%=basePath%>/sys/menu/list.html" rownumbers="true" animate="true" fitColumns="true" 
	idField="id" treeField="name" toolbar="#toolbar">
	<thead>
		<tr>
			<th data-options="field:'name',width:180,editor:'text'">菜单名称</th>
			<th data-options="field:'menuCode',width:80">菜单编码</th>
			<th data-options="field:'type',formatter:formatType,width:80">菜单编码</th>
			<th data-options="field:'modifyTime',width:80">最后修改日期</th>
			<th data-options="field:'status',width:80,formatter:formatStatus">
				<span>状态</span>
				<shiro:hasPermission name="A1_02_02">
					<img class="notice" src="<%=basePath%>/file/images/icon/notice.png" title="点击可更改菜单状态！">
				</shiro:hasPermission>
			</th>
		</tr>
	</thead>
</table>

<!-- 表格的工具条 -->
<div id="toolbar">
	<shiro:hasPermission name="A1_02_01">
		<a href="javascript:void(0)" class="easyui-linkbutton update_menu_btn" iconCls="icon-edit" plain="true">修改</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="A1_02_01">
		<a href="javascript:void(0)" class="easyui-linkbutton update_save_btn" iconCls="icon-save" plain="true">保存</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="A1_02_01">
		<a href="javascript:void(0)" class="easyui-linkbutton update_cancel_btn" iconCls="icon-cancel" plain="true">取消</a>
	</shiro:hasPermission>
	<input name="keyword" value="${keyword}" style="line-height:18px; border:1px solid #ccc; border-radius: 4px">
	<a href="javascript:void(0)" class="easyui-linkbutton search_btn" plain="true" iconCls="icon-search">查询</a>
</div>
</body>
</html>