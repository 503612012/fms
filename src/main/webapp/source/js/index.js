//@ sourceURL=source/js/index.js
$(function() {
	// 加载系统菜单
	$('#menu_tree').tree({
		url: getBasePath() + "/sys/getMenu.html",
		method: "get",
		animate: true,
		lines: true,
		onClick: function(node) {
			if (node.state == 'closed' && (!$("#menu_tree").tree('isLeaf', node.target))) { // 状态为关闭而且非叶子节点
				$(this).tree('expand', node.target); // 点击文字展开菜单
				if (node.attributes && node.attributes.url) {
					openPanel(node.attributes.url, node.text);
				}
			} else {
				if ($("#menu_tree").tree('isLeaf', node.target)) { // 状态为打开而且为叶子节点
					if (node.attributes && node.attributes.url) {
						openPanel(node.attributes.url, node.text);
					}
				} else {
					$(this).tree('collapse', node.target); // 点击文字关闭菜单
				}
			}     
		}
	});

	// 打开一个panel
	var openPanel = function(url, title) {
		if ($('#panel_box').tabs('exists', title)) {
			$('#panel_box').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0" src="' + getBasePath() + url + '" style="width:100%;height:100%;"></iframe>';
			$('#panel_box').tabs('add', {
				title: title,
				content: content,
				closable: true
			});
		}
	};
	
	// 退出按钮点击事件
	$(".logout").on("click", function() {
		$.messager.confirm("系统提示！", "确认退出？", function(r) {
			if (r) {
				$.ajax({
					url: getBasePath() + "/sys/logout.html",
					type: "POST",
					async : true,
					data: {},
					dataType: "json",
					success: function(result) {
						if (result.code != 200) {
							$.messager.alert("系统提示！", result.data, "error");
							return;
						}
						location.href = getBasePath() + "/sys/loginPage.html";
					}
				});
			}
		});
	});
});
