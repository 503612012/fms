//@ sourceURL=view/workinghour/js/index.js
$(function() {

    // 查询按钮点击事件
    $(".search_btn").on("click", function() {
        var keyword = $("input[name=keyword]").val();
        var date = $("#workinghour-work-date").datebox("getValue");
        $('#workinghourList').datagrid('load', getBasePath() + '/workinghour/workinghour/list.html?keyword=' + keyword + "&date=" + date);
        $('#workinghourList').datagrid('reloadFooter', getBasePath() + '/workinghour/workinghour/list.html?keyword=' + keyword + "&date=" + date);
    });

});

// 格式化额外薪资
function formatExtraSalary(value, row, index) {
    return '<span title="' + row.extraDesc + '">' + row.extraSalary + '</span>';
}