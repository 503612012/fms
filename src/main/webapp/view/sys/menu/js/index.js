//@ sourceURL=view/sys/menu/js/index.js
$(function() {
	// 改变菜单状态
	$("body").on("click", ".datagrid-cell-c1-status > font", function() {
		// 这里需要判断权限
		var flag = hasUpdateMenuStatusPermission();
		if (flag == 'false') {
			return;
		}
		var status = $(this).text();
		var msg = "";
		if (status == "可用") {
			status = '2';
			msg = "您确定要禁用该菜单吗？";
		} else {
			status = '1';
			msg = "您确定要启用该菜单吗？";
		}
		var menuCode = $(this).parent().parent().parent().find("td[field=menuCode]>div").html();
		$.messager.confirm("系统提示！", msg, function(r) {
			if (r) {
				$.ajax({
					url: getBasePath() + "/sys/menu/updateStatus.html",
					type: "POST",
					async : true,
					dataType: "json",
					data: {
						"menuCode": menuCode,
						"status": status
					},
					success: function(result) {
						if (result.code != 200) {
							$.messager.alert("系统提示！", result.data, "error");
						} else {
							$("#menuList").treegrid("reload");
						}
					}
				});
			}
		});
	});
	
	// 查询按钮点击事件
	$(".search_btn").on("click", function() {
		var keyword = $("input[name=keyword]").val();
		$('#menuList').treegrid('load', {keyword: keyword});
	});
	
	var editingId; // 当前编辑的行
	// 编辑
	$(".update_menu_btn").on("click", function() {
		if (editingId != undefined) {
            $('#menuList').treegrid('select', editingId);
            return;
        }
        var row = $('#menuList').treegrid('getSelected');
        if (row.type == 2) {
        	$.messager.alert("系统提示！", '按钮名称不允许修改！', "info");
        	return;
        }
        if (row) {
            editingId = row.id;
            $('#menuList').treegrid('beginEdit', editingId);
        }
	});

	// 保存
	$(".update_save_btn").on("click", function() {
		if (editingId != undefined) {
            var tg = $('#menuList');
            tg.treegrid('endEdit', editingId);
            editingId = undefined;
            var row = tg.treegrid('getSelected');
            $.ajax({
            	url: getBasePath() + "/sys/menu/update.html",
            	type: "POST",
            	data: {
            		"id": row.id,
            		"menuName": row.name
            	},
            	dataType: "json",
            	success: function(result) {
            		if (result.code != 200) {
            			$.messager.alert("系统提示！", result.data, "error");
            		}
            	}
            });
        }
	});
	
	// 取消
	$(".update_cancel_btn").on("click", function() {
		if (editingId != undefined) {
			$('#menuList').treegrid('cancelEdit', editingId);
            editingId = undefined;
		}
	});
	
});

/**
 * 格式化菜单类型
 */
function formatType(val, row) {
	if (val == 1) {
		return '<font color=blue>目录</font>';
	} else {
		return '<font color=red>按钮</font>';
	}
}

/**
 * 格式化菜单状态
 */
function formatStatus(val, row) {
	if (val == 1) {
		return '<font color=blue>可用</font>';
	} else {
		return '<font color=red>禁用</font>';
	}
}
