//@ sourceURL=view/sys/log/js/index.js
$(function() {
	// 展开查看详情
	$('#logList').datagrid({
		view: detailview,
		detailFormatter: function(index, row) {
			return '<div class="ddv" style="padding:5px 0">' + row.content + '</div>';
		}
	});
	
	// 查询按钮点击事件
	$(".search_btn").on("click", function() {
		var keyword = $("input[name=keyword]").val();
		$('#logList').datagrid('load', getBasePath() + '/sys/log/list.html?keyword=' + keyword);
		$('#logList').datagrid('reloadFooter', getBasePath() + '/sys/log/list.html?keyword=' + keyword);
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
 * 鼠标悬停显示
 */
function showTitle(val, row) {
	return '<span title="' + val + '">' + val + '</span>';
}