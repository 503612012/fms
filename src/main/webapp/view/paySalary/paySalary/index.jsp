<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
Integer eid = (Integer) request.getAttribute("eid");
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
<script type="text/javascript" src="<%=basePath%>/view/paySalary/paySalary/js/index.js"></script>
<script type="text/javascript" src="<%=basePath%>/source/js/common.js"></script>
<script type="text/javascript">
	function getBasePath(){
		return '<%=basePath%>';
	}
	function getEid() {
		return '<%=eid%>';
    }
</script>
</head>
<body>

<table id="paySalaryList" title="发薪记录" class="easyui-datagrid" fitColumns="true" style="width:100%; height:100%" fit="true" border="false"
	   iconCls="myIcon-money" pagination="true" url="<%=basePath%>/paySalary/paySalary/list.html?eid=${eid}&workDate=${date}"
	   data-options="singleSelect:true,rownumbers:true" toolbar="#toolbar">
	<thead>
		<tr>
			<th field="ename" width="40" align="center">员工姓名</th>
			<th field="work_date" width="50" align="center">工作日期</th>
			<th field="should_pay_salary" width="40" align="center">应发薪资</th>
			<th field="actual_pay_salary" width="40" align="center">实发薪资</th>
			<th field="diffSalary" width="40" align="center" data-options="formatter:formatDiffSalary">薪资差异</th>
			<th field="pay_date" width="60" align="center">发薪日期</th>
			<th field="payName" width="50" align="center">发薪人</th>
			<th field="remark" width="100" align="center">备注</th>
		</tr>
	</thead>
</table>

<!-- 表格的工具条 -->
<div id="toolbar">
	<span>工作时间：</span><input value="${date}" class="Wdate" id="pay_salary_work_date" name="pay_salary_work_date" type="text" style="border: 1px solid #95B8E7; border-radius: 5px 5px 5px 5px;"/>
	<span>发薪时间：</span><input class="Wdate" id="pay_salary_pay_date" name="pay_salary_pay_date" type="text" style="border: 1px solid #95B8E7; border-radius: 5px 5px 5px 5px;"/>
	<span>员工：</span><input class="easyui-combobox" id="pay_salary_eid" name="eid" data-options="
						url: '<%=basePath%>/employee/employee/findAllWithNoDefault.html',
						valueField: 'id',
						textField: 'text',
						pannelHeight: 'auto',
						onLoadSuccess: setSelected" style="width: 100px;"/>
	<a href="javascript:void(0)" class="easyui-linkbutton search_btn" plain="true" iconCls="icon-search">查询</a>
</div>

</body>
</html>