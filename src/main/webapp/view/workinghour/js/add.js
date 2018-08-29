// sourceURL=view/workinghour/js/add.js
$(function() {
    var basePath = getBasePath();

    $("#workinghour-clear-btn").on("click", function() {
        $('#addWorkinghourForm').form('clear');
    });

    // 提交
    $("#workinghour-submit-btn").on("click", function() {
        var data = {};
        data.eid = $('#workinghour-eid').combobox('getValue');
        data.ename = $('#workinghour-eid').combobox('getText');
        data.date = $('#workinghour-date').datebox("getValue");
        data.daySalary = $('#workinghour-day-salary').numberbox('getValue');
        data.worksiteId = $('#workinghour-worksite').combobox('getValue');
        data.score = $("#workinghour-score").numberbox('getValue');
        if (parseFloat(data.score) < 0 || parseFloat(data.score) > 10) {
            $.messager.alert("系统提示！", "工时记分只能在0-10之间！", "error");
            return;
        }
        data.extraSalary = $("#workinghour-extra-salary").numberbox('getValue');
        data.remark = $('#workinghour-remark').textbox('getValue');
        data.extraSalaryDesc = $('#workinghour-extra-salary-desc').textbox('getValue');
        if (data.extraSalary != null && data.extraSalary != '') {
            if (data.extraSalaryDesc == null || data.extraSalaryDesc == '') {
                $.messager.alert("系统提示！", "请输入额外薪资说明！", "error");
                return;
            }
        }
        if (data.extraSalary == null || data.extraSalary == '') {
            data.extraSalary = 0;
        }
        var check = {};
        $.ajax({
            url: basePath + "/workinghour/workinghour/isInput.html",
            type: "GET",
            data: {
                eid: data.eid,
                date: data.date
            },
            dataType: "json",
            async: false,
            success: function(result) {
                check = result.data;
            }
        });
        if (check.isPaySalary) {
            $.messager.alert("系统提示！", "员工[" + data.ename + "]在[" + data.date.substring(0, 7) + "]工资已经发放，禁止录入！", "error");
            return;
        }
        if (isInput) {
            $.messager.confirm('警告', '员工[' + data.ename + ']在[' + data.date + ']已经录入工时，重复录入会覆盖原有工时，是否重复录入？', function(r) {
                if (r) {
                    inputWorkinghour(data);
                }
            });
        } else {
            inputWorkinghour(data);
        }
    });

    var inputWorkinghour = function(data) {
        $.ajax({
            url: basePath + "/workinghour/workinghour/insert.html",
            type: "POST",
            data: {
                eid: data.eid,
                ename: data.ename,
                date: data.date,
                score: data.score,
                daySalary: data.daySalary,
                extraSalary: data.extraSalary,
                worksiteId: data.worksiteId,
                extraDesc: data.extraSalaryDesc,
                remark: data.remark
            },
            dataType: "json",
            success: function(result) {
                if (result.code != 200) {
                    $.messager.alert("系统提示！", result.data, "error");
                } else {
                    $.messager.alert("系统提示！", "录入成功！");
                    $('#addWorkinghourForm').form('clear');
                }
            }
        });
    }

});

function workinghourEidChange(data) {
    loadWorkinghourDaySalary(data.id);
}

function workinghourEidLoadSuccess(date) {
    var eid = $('#workinghour-eid').combobox('getValue');
    loadWorkinghourDaySalary(eid);
}

function loadWorkinghourDaySalary(eid) {
    if (eid != '' && eid != null) {
        $.ajax({
            url: getBasePath() + "/employee/employee/getDaySalaryById.html",
            type: 'GET',
            data: {
                eid: eid
            },
            dataType: 'json',
            success: function(result) {
                $("#workinghour-day-salary").numberbox('setValue', result.data);
            }
        });
    }
}
