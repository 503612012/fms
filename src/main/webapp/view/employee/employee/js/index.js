//@ sourceURL=view/employee/employee/js/index.js
$(function() {
	// 改变员工状态
	$("body").on("click", ".datagrid-cell-c1-status > font", function() {
		// 这里需要判断权限
		var flag = hasUpdateEmployeeStatusPermission();
		if (flag == 'false') {
			return;
		}
		var status = $(this).text();
		var msg = "";
		if (status == "可用") {
			status = '2';
			msg = "您确定要禁用该员工吗？";
		} else {
			status = '1';
			msg = "您确定要启用该员工吗？";
		}
		var id = $(this).parent().parent().parent().find("td[field=id]>div").html();
		$.messager.confirm("系统提示！", msg, function(r) {
			if (r) {
				$.ajax({
					url: getBasePath() + "/employee/employee/updateStatus.html",
					type: "POST",
					async : true,
					dataType: "json",
					data: {
						"id": id,
						"status": status
					},
					success: function(result) {
						if (result.code != 200) {
							$.messager.alert("系统提示！", result.data, "error");
						} else {
							$("#employeeList").datagrid("reload");
						}
					}
				});
			}
		});
	});
	
	// 显示日薪
	$("body").on("click", ".datagrid-cell-c1-daySalary > span, .datagrid-cell-c1-monthSalary > span", function() {
		// 这里需要判断权限
		var flag = hasShowMeneyPermission();
		if (flag == 'false') {
			return;
		}
		$(this).parent().find("span.hide").removeClass("hide");
		$(this).addClass("hide");
	});
	
	// 添加员工页面
	$(".add_employee_btn").on("click", function() {
		$("#employee_manage_page").dialog('open').dialog('setTitle', '添加员工');
		$('#employeeForm').form('clear');
	});
	
	// 修改员工页面
	$(".update_employee_btn").on("click", function() {
		var row = $('#employeeList').datagrid('getSelected');
		if (row) {
			$("#employee_manage_page").dialog('open').dialog('setTitle', '修改员工');
            $("#employee-name").textbox('setValue', row.name);
			$("#age").numberbox('setValue', row.age);
			$("#employee-address").textbox('setValue', row.address);
			if (row.gender == 1) {
				$("#gender_1").attr("checked", "checked");
			} else if (row.gender == 0) {
				$("#gender_0").attr("checked", "checked");
			}
            $("#employee-contact").textbox('setValue', row.contact);
			$("#daySalary").numberbox('setValue', row.daySalary);
			$("#monthSalary").numberbox('setValue', row.monthSalary);
			$("input[name=id]").val(row.id);
		} else {
			$.messager.alert("系统提示！", "请选择一条记录！", "error");
		}
	});
	
	// 提交
	$(".submit_btn").on("click", function() {
		var id = $("input[name=id]").val();
		var name = $("input[name=name]").val();
		if (name == '') {
			$.messager.alert("系统提示！", "请输入员工姓名！", "error");
			return;
		}
		var age = $("#age").numberbox('getValue');
		if (age == '') {
			$.messager.alert("系统提示！", "请输入员工年龄！", "error");
			return;
		}
		var gender = $("input[name=gender]:checked").val();
		if (gender == '' || gender == undefined) {
			$.messager.alert("系统提示！", "请选择性别！", "error");
			return;
		}
		var address = $("#employee-address").textbox('getValue');
		if (address == '') {
			$.messager.alert("系统提示！", "请输入员工住址！", "error");
			return;
		}
		var contact = $("input[name=contact]").val();
		if (contact == '') {
			$.messager.alert("系统提示！", "请输入联系方式！", "error");
			return;
		}
		var daySalary = $("#daySalary").numberbox('getValue');
		if (daySalary == '') {
			$.messager.alert("系统提示！", "请输入日薪！", "error");
			return;
		}
		var monthSalary = $("#monthSalary").numberbox('getValue');
		if (monthSalary == '') {
			$.messager.alert("系统提示！", "请输入月薪！", "error");
			return;
		}
		var url = '';
		if (id == null || id == '') {
			url = getBasePath() + "/employee/employee/add.html";
		} else {
			url = getBasePath() + "/employee/employee/update.html";
		}
		$.ajax({
			url: url,
			type: "POST",
			async : true,
			dataType: "json",
			data: {
				"name": name,
				"age": age,
				"gender": gender,
				"address": address,
				"contact": contact,
				"daySalary": daySalary,
				"monthSalary": monthSalary,
				"id": id
			},
			success: function(result) {
				if (result.code != 200) {
					$.messager.alert("系统提示！", result.data, "error");
				} else {
					$.messager.alert("系统提示！", "操作成功");
					$('#employee_manage_page').dialog('close');
					$("#employeeList").datagrid("reload");
				}
			}
		});
	});
	
	// 删除员工
	$(".delete_employee_btn").on("click", function() {
		var row = $('#employeeList').datagrid('getSelected');
		if (row) {
			$.messager.confirm("系统提示！", "您确定要删除这条记录吗？", function(r) {
				if (r) {
					$.post(getBasePath() + "/employee/employee/delete.html", {
						id: row.id
					}, function(result) {
						if (result.code != 200) {
							$.messager.alert("系统提示！", result.data, "error");
							return;
						}
						$.messager.alert("系统提示！", "删除成功！");
						$("#employeeList").datagrid("reload");
					}, 'json');
				}
			});
		} else {
			$.messager.alert("系统提示！", "请选择一条记录！", "error");
		}
	});
	
	// 查询按钮点击事件
	$(".search_btn").on("click", function() {
		var keyword = $("input[name=keyword]").val();
		$('#employeeList').datagrid('load', getBasePath() + '/employee/employee/list.html?keyword=' + keyword);
		$('#employeeList').datagrid('reloadFooter', getBasePath() + '/employee/employee/list.html?keyword=' + keyword);
	});
});

/**
 * 格式化员工状态
 */
function formatterStatus(val, row) {
	if (val == 1) {
		return '<font color=blue>可用</font>';
	} else {
		return '<font color=red>禁用</font>';
	}
}

/**
 * 格式化员工性别
 */
function formatterGender(val, row) {
	if (val == 1) {
		return '男';
	} else if (val == 0) {
		return '女';
	} else {
		return '数据错误！';
	}
}

/**
 * 格式化员工日薪
 */
function formatterDaySalary(val, row, index) {
	return '<span class="hide daySalary_money">' + row.daySalary + '</span><span class="daySalary_hide">***</span>';
}

/**
 * 格式化员工月薪
 */
function formatterMonthSalary(val, row, index) {
	return '<span class="hide monthSalary_money">' + row.monthSalary + '</span><span class="monthSalary_hide">***</span>';
}