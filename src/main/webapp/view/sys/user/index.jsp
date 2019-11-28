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
<link rel="stylesheet" type="text/css" href="<%=basePath%>/view/sys/user/css/index.css?v=1.0.0" />
<script type="text/javascript" src="<%=basePath%>/source/js/ajaxhook.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/themes/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/view/sys/user/js/index.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/js/common.js"></script>
<script type="text/javascript">
	function getBasePath(){
		return '<%=basePath%>';
	}
	function hasUpdateUserStatusPermission() {
		return '<%=AuthUtils.hasPermission("A1_01_04")%>';
	}
</script>
</head>
<body>
<table id="userList" title="用户列表" class="easyui-datagrid" fitColumns="true" style="width:100%; height:100%" fit="true" border="false"
	iconCls="myIcon-user" pagination="true" url="<%=basePath%>/sys/user/list.html" data-options="singleSelect:true" toolbar="#toolbar">
	<thead>
		<tr>
			<th field="id" width="20">ID</th>
			<th field="userName" width="60">用户名</th>
			<th field="nickName" width="60">昵称</th>
			<th field="contact" width="60">联系方式</th>
			<th field="createTime" width="60">创建时间</th>
			<th field="modifyTime" width="60">最后修改时间</th>
			<th field="status" width="60" formatter="formatterStatus">
				<span>状态</span>
				<shiro:hasPermission name="A1_01_04">
					<img class="notice" src="<%=basePath%>/file/images/icon/notice.png" title="点击可更改用户状态！" alt="">
				</shiro:hasPermission>
			</th>
		</tr>
	</thead>
</table>

<!-- 表格的工具条 -->
<div id="toolbar">
	<shiro:hasPermission name="A1_01_01">
		<a href="javascript:void(0)" class="easyui-linkbutton add_user_btn" iconCls="icon-add" plain="true">添加</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="A1_01_02">
		<a href="javascript:void(0)" class="easyui-linkbutton update_user_btn" iconCls="icon-edit" plain="true">修改</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="A1_01_03">
		<a href="javascript:void(0)" class="easyui-linkbutton delete_user_btn" iconCls="icon-remove" plain="true">删除</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="A1_01_05">
		<a href="javascript:void(0)" class="easyui-linkbutton config_permisson_btn" iconCls="myIcon-cog" plain="true">配置权限</a>
	</shiro:hasPermission>
	<input name="keyword" value="${keyword}" style="line-height:18px; border:1px solid #ccc; border-radius: 4px">
	<a href="javascript:void(0)" class="easyui-linkbutton search_btn" plain="true" iconCls="icon-search">查询</a>
</div>

<!-- 添加和修改窗口 -->
<div id="user_manage_page" class="easyui-dialog" style="width:400px;height:300px;padding:10px 10px" closed="true" buttons="#dlg-buttons">
	<form id="userForm" method="post">
		<input type="hidden" name="id" value="">
		<table cellspacing="10px;">
			<tr>
				<td>用户名：</td>
       			<td><input name="userName" id="user-name" class="easyui-textbox" required="true" style="width: 200px;"></td>
       		</tr>
       		<tr>
       			<td>密码：</td>
       			<td><input name="password" id="user-password" type="password" class="easyui-textbox" required="true" style="width: 200px;"></td>
       		</tr>
       		<tr>
       			<td>重复密码：</td>
       			<td><input name="confirmPassword" id="user-confirmPassword" type="password" class="easyui-textbox" required="true" style="width: 200px;"></td>
       		</tr>
       		<tr>
       			<td>昵称：</td>
       			<td><input name="nickName" id="user-nickName" type="text" class="easyui-textbox" required="true" style="width: 200px;"></td>
       		</tr>
       		<tr>
       			<td>联系方式：</td>
       			<td><input name="contact" id="user-contact" type="text" class="easyui-textbox" required="true" style="width: 200px;"></td>
       		</tr>
       	</table>
	</form>
</div>

<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton submit_btn" iconCls="icon-ok">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton cancel_btn" iconCls="icon-cancel" onclick="$('#user_manage_page').dialog('close')">关闭</a>
</div>

<!-- 模态窗体 -->
<div id="w" class="easyui-window" title="配置权限" data-options="modal:true,minimizable:false,collapsible:false,closed:true,iconCls:'myIcon-cog'" style="width:800px;height:600px;padding:10px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'" style="padding:10px;">
			<input type="hidden" name="id" id="setPermissionUserId" value="">
			<ul id="permission_tree" class="easyui-tree" data-options="checkbox:true, cascadeCheck: false"></ul>
		</div>
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="configPermissionSave();">确定</a>
            <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="configPermissionCancel();">关闭</a>
        </div>
	</div>
</div>

</body>
</html>