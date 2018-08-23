//@ sourceURL=view/worksite/js/index.js
$(function() {
	
	// 添加工地页面
	$(".add_worksite_btn").on("click", function() {
		$("#worksite_manage_page").dialog('open').dialog('setTitle', '添加工地');
		$('#worksiteForm').form('clear');
	});
	
	// 修改工地页面
	$(".update_worksite_btn").on("click", function() {
		var row = $('#worksiteList').datagrid('getSelected');
		if (row) {
			$("#worksite_manage_page").dialog('open').dialog('setTitle', '修改工地');
            $("#worksite-name").textbox('setValue', row.name);
            $("#worksite-desc").textbox('setValue', row.desc);
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
            $.messager.alert("系统提示！", "请输入工地名称！", "error");
            return;
        }
        var desc = $("#worksite-desc").textbox("getValue");
        if (desc == '') {
            $.messager.alert("系统提示！", "请输入工地描述！", "error");
            return;
        }
        var url;
        if (id == null || id == '') {
            url = getBasePath() + "/worksite/worksite/add.html";
        } else {
            url = getBasePath() + "/worksite/worksite/update.html";
        }
		$.ajax({
			url: url,
			type: "POST",
			async : true,
			dataType: "json",
			data: {
				"name": name,
				"desc": desc,
				"id": id
			},
			success: function(result) {
				if (result.code != 200) {
					$.messager.alert("系统提示！", result.data, "error");
				} else {
					$.messager.alert("系统提示！", "操作成功");
					$('#worksite_manage_page').dialog('close');
					$("#worksiteList").datagrid("reload");
				}
			}
		});
	});
	
	// 删除工地
	$(".delete_worksite_btn").on("click", function() {
		var row = $('#worksiteList').datagrid('getSelected');
		if (row) {
			$.messager.confirm("系统提示！", "您确定要删除这条记录吗？", function(r) {
				if (r) {
					$.post(getBasePath() + "/worksite/worksite/delete.html", {
						id: row.id
					}, function(result) {
						if (result.code != 200) {
							$.messager.alert("系统提示！", result.data, "error");
							return;
						}
						$.messager.alert("系统提示！", "删除成功！");
						$("#worksiteList").datagrid("reload");
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
		$('#worksiteList').datagrid('load', getBasePath() + '/worksite/worksite/list.html?keyword=' + keyword);
		$('#worksiteList').datagrid('reloadFooter', getBasePath() + '/worksite/worksite/list.html?keyword=' + keyword);
	});
});
