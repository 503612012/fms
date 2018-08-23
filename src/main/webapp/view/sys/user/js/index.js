//@ sourceURL=view/sys/user/js/index.js
$(function() {
	// 改变用户状态
	$("body").on("click", ".datagrid-cell-c1-status > font", function() {
		// 这里需要判断权限
		var flag = hasUpdateUserStatusPermission();
		if (flag == 'false') {
			return;
		}
		var status = $(this).text();
		var msg = "";
		if (status == "可用") {
			status = '2';
			msg = "您确定要禁用该用户吗？";
		} else {
			status = '1';
			msg = "您确定要启用该用户吗？";
		}
		var id = $(this).parent().parent().parent().find("td[field=id]>div").html();
		$.messager.confirm("系统提示！", msg, function(r) {
			if (r) {
				$.ajax({
					url: getBasePath() + "/sys/user/updateStatus.html",
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
							$("#userList").datagrid("reload");
						}
					}
				});
			}
		});
	});
	
	// 添加用户页面
	$(".add_user_btn").on("click", function() {
		$("#user_manage_page").dialog('open').dialog('setTitle', '添加用户');
		$('#userForm').form('clear');
		$("input[name=userName]").attr("disabled", false);
	});
	
	// 修改用户页面
	$(".update_user_btn").on("click", function() {
		var row = $('#userList').datagrid('getSelected');
		if (row) {
			$("#user_manage_page").dialog('open').dialog('setTitle', '修改用户');
            $('#user-name').textbox('textbox').attr('readonly', true);
			$("#user-name").textbox('setValue', row.userName);
			$("#user-password").textbox('setValue', '');
			$("#user-confirmPassword").textbox('setValue', '');
			$("#user-nickName").textbox('setValue', row.nickName);
			$("#user-contact").textbox('setValue', row.contact);
			$("input[name=id]").val(row.id);
		} else {
			$.messager.alert("系统提示！", "请选择一条记录！", "error");
		}
	});
	
	// 提交
	$(".submit_btn").on("click", function() {
		var id = $("input[name=id]").val();
		var password = $("input[name=password]").val();
		var confirmPassword = $("input[name=confirmPassword]").val();
		if (password != confirmPassword) {
			$.messager.alert("系统提示！", "密码不一致，请重新输入！", "error");
			return;
		}
		if (password == '') {
			$.messager.alert("系统提示！", "请输入密码！", "error");
			return;
		}
		var userName = $("input[name=userName]").val();
		if (userName == '') {
			$.messager.alert("系统提示！", "请输入用户名！", "error");
			return;
		}
		var nickName = $("input[name=nickName]").val();
		if (nickName == '') {
			$.messager.alert("系统提示！", "请输入昵称！", "error");
			return;
		}
		var contact = $("input[name=contact]").val();
		if (contact == '') {
			$.messager.alert("系统提示！", "请输入联系方式！", "error");
			return;
		}
		var url = '';
		if (id == null || id == '') {
			url = getBasePath() + "/sys/user/add.html";
		} else {
			url = getBasePath() + "/sys/user/update.html";
		}
		$.ajax({
			url: url,
			type: "POST",
			async : true,
			dataType: "json",
			data: {
				"userName": userName,
				"password": password,
				"nickName": nickName,
				"contact": contact,
				"id": id
			},
			success: function(result) {
				if (result.code != 200) {
					$.messager.alert("系统提示！", result.data, "error");
				} else {
					$.messager.alert("系统提示！", "操作成功");
					$('#user_manage_page').dialog('close');
					$("#userList").datagrid("reload");
				}
			}
		});
	});
	
	// 删除用户
	$(".delete_user_btn").on("click", function() {
		var row = $('#userList').datagrid('getSelected');
		if (row) {
			$.messager.confirm("系统提示！", "您确定要删除这条记录吗？", function(r) {
				if (r) {
					$.post(getBasePath() + "/sys/user/delete.html", {
						id: row.id
					}, function(result) {
						if (result.code != 200) {
							$.messager.alert("系统提示！", result.data, "error");
							return;
						}
						$.messager.alert("系统提示！", "删除成功！");
						$("#userList").datagrid("reload");
					}, 'json');
				}
			});
		} else {
			$.messager.alert("系统提示！", "请选择一条记录！", "error");
		}
	});

	// 配置权限
	$(".config_permisson_btn").on("click", function() {
		var row = $('#userList').datagrid('getSelected');
		if (row) {
			$("#setPermissionUserId").val(row.id);
			$('#permission_tree').tree({
				url: getBasePath() + "/sys/menu/getMenuTree.html?userId=" + row.id,
				method: "GET",
				animate: true,
				lines: true,
				onClick: function(node) {
					if (node.state == 'closed' && (!$("#permission_tree").tree('isLeaf', node.target))) { // 状态为关闭而且非叶子节点
						$(this).tree('expand', node.target); // 点击文字展开菜单
						if (node.attributes && node.attributes.url) {
							openPanel(node.attributes.url, node.text);
						}
					} else {
						if ($("#permission_tree").tree('isLeaf', node.target)) { // 状态为打开而且为叶子节点
							if (node.attributes && node.attributes.url) {
								openPanel(node.attributes.url, node.text);
							}
						} else {
							$(this).tree('collapse', node.target); // 点击文字关闭菜单
						}
					}
				},
				onCheck: function(node, checked) {
					var childList = $(this).tree('getChildren', node.target);
					if (childList.length > 0) { // 有子菜单，点击的时候级联子菜单
						var checkedTrue = function() {
							childList.map(function(currentValue) {
								$("#" + currentValue.domId).parent().find(".tree-checkbox").removeClass("tree-checkbox0").removeClass("tree-checkbox2").addClass("tree-checkbox1");
							});
						};
						var checkedFalse = function() {
							$.each(childList, function(index, currentValue) {
								$("#" + currentValue.domId).parent().find(".tree-checkbox").removeClass("tree-checkbox1").removeClass("tree-checkbox2").addClass("tree-checkbox0");
							});
						};
						var checkChangeProperties = checked == true ? checkedTrue() : checkedFalse();
					} else { // 没有子菜单，即为叶子节点，点击的时候级联父节点
						var parentNode = $('#permission_tree').tree('getParent', node.target); //得到父节点
						if (checked == true) { // 选中子节点
							var checkBoxList = $("#" + node.domId).parent().parent().find(".tree-checkbox");
							for (var i=0; i<checkBoxList.length; i++) {
								if ($(checkBoxList[i]).hasClass("tree-checkbox0")) {
									$("#" + parentNode.domId).find(".tree-checkbox").removeClass("tree-checkbox0").removeClass("tree-checkbox1").addClass("tree-checkbox2");
									return;
								}
							}
							$("#" + parentNode.domId).find(".tree-checkbox").removeClass("tree-checkbox0").removeClass("tree-checkbox2").addClass("tree-checkbox1");
						} else { // 取消选中子节点
							var checkBoxList = $("#" + node.domId).parent().parent().find(".tree-checkbox");
							for (var i=0; i<checkBoxList.length; i++) {
								if ($(checkBoxList[i]).hasClass("tree-checkbox1")) {
									$("#" + parentNode.domId).find(".tree-checkbox").removeClass("tree-checkbox0").removeClass("tree-checkbox1").addClass("tree-checkbox2");
									return;
								}
							}
						}
					}
				}
			});
			$('#w').window('open');
		} else {
			$.messager.alert("系统提示！", "请选择一条记录！", "error");
		}
	});
	
	// 查询按钮点击事件
	$(".search_btn").on("click", function() {
		var keyword = $("input[name=keyword]").val();
		$('#userList').datagrid('load', getBasePath() + '/sys/user/list.html?keyword=' + keyword);
		$('#userList').datagrid('reloadFooter', getBasePath() + '/sys/user/list.html?keyword=' + keyword);
	});
});

/**
 * 格式化用户状态
 */
function formatterStatus(val, row) {
	if (val == 1) {
		return '<font color=blue>可用</font>';
	} else {
		return '<font color=red>禁用</font>';
	}
}

/**
 * 关闭配置权限的模态窗口
 */
function configPermissionCancel() {
	$("#w").panel('close');
}

/**
 * 提交配置权限
 */
function configPermissionSave() {
	var list = $('#permission_tree').find(".tree-checkbox"); // 不是获取选中的，而是获取所有节点
	var data = new Array();
	var userId = $("#setPermissionUserId").val();
	for (var i=0; i<list.length; i++) {
		var menuCode = $("#permission_tree").tree("getNode", $(list[i]).parent()).id;

		var checked = false;
		if ($(list[i]).hasClass("tree-checkbox2")) {
			checked = true;
		} else if ($(list[i]).hasClass("tree-checkbox1")) {
			checked = true;
		}
		data.push({"menuCode": menuCode, "checked": checked});
	}
	$.ajax({
		url: getBasePath() + '/sys/user/setPermission.html',
		type: 'POST',
		async: true,
		data: {
			"data": JSON.stringify(data),
			"userId": userId
		},
		dataType: "json",
		success: function(result) {
			if (result.code != 200) {
				$.messager.alert("系统提示！", result.data, "error");
				return;
			}
			$("#w").panel('close');
			$.messager.alert("系统提示！", "设置成功", "info");
		}
	});
}