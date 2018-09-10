// sourceURL=/view/paySalary/paySalary/js/index.js
$(function() {

    // 打开时间选择器
    $("#pay_salary_work_date").on("focus", function() {
        WdatePicker({dateFmt: 'yyyy-MM', readOnly: true});
    });

    // 打开时间选择器
    $("#pay_salary_pay_date").on("focus", function() {
        WdatePicker({dateFmt: 'yyyy-MM-dd', readOnly: true});
    });

    // 查询按钮点击事件
    $(".search_btn").on("click", function() {
        var workDate = $("#pay_salary_work_date").val();
        var payDate = $("#pay_salary_pay_date").val();
        var eid = $("#pay_salary_eid").combobox('getValue');
        $('#paySalaryList').datagrid('load', getBasePath() + '/paySalary/paySalary/list.html?workDate=' + workDate + '&payDate=' + payDate + '&eid=' + eid);
        $('#paySalaryList').datagrid('reloadFooter', getBasePath() + '/paySalary/paySalary/list.html?workDate=' + workDate + '&payDate=' + payDate + '&eid=' + eid);
    });

});

function formatDiffSalary(value, row, index) {
    if ((parseFloat(row.actual_pay_salary) - parseFloat(row.should_pay_salary)) != 0) {
        return '<span style="color: red;" title="' + row.remark + '">' + (parseFloat(row.actual_pay_salary) - parseFloat(row.should_pay_salary)) + '</span>';
    } else {
        return 0;
    }
}

function setSelected(data) {
    if (getEid() != '' && getEid() != null && getEid() != 'null') {
        $('#pay_salary_eid').combobox('setValue', getEid());
    }
}