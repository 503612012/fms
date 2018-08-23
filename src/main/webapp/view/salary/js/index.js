// sourceURL=/view/salary/js/index.js
$(function() {

    // 打开时间选择器
    $("#salary-count-date").on("focus", function() {
        var that = $(this);
        var dateType = $("#salary-count-date-type").datebox('getValue');
        var dateFmt = 'yyyy-MM-dd';
        if (dateType == "byYear") // 按年
            dateFmt = 'yyyy';
        if (dateType == "byMonth") // 按月
            dateFmt = 'yyyy-MM';
        WdatePicker({dateFmt: dateFmt, readOnly: true});
    });

    // 加载表格
    var loadTable = function(dateType, date, eid, ename) {
        if (dateType == 'byMonth') {
            $('#salary-count-table').datagrid({
                url: getBasePath() + '/salary/salary/getTableData.html?dateType=' + dateType + '&date=' + date + '&eid=' + eid,
                columns: [[
                    {field: 'date', title: '日期', align: 'center'},
                    {field: 'score', title: '工时记分', align: 'center'},
                    {field: 'daySalary', title: '日薪', align: 'right'},
                    {field: 'extraSalary', title: '额外薪资', align: 'right'},
                    {
                        field: 'lastModifyDate', title: '当日总计薪资', align: 'right', formatter: function(value, row, index) {
                            return '<span style="color: red">' + row.daySalary + row.extraSalary + '</span>';
                        }
                    },
                    {field: 'worksiteName', title: '工地', align: 'center'},
                    {field: 'inputNickName', title: '录入人', align: 'center'},
                    {field: 'inputDate', title: '录入时间', align: 'center'},
                    {field: 'remark', title: '备注', align: 'center'}
                ]],
                title: '<span style="color: red">' + ename + '</span>' + date + '薪资详情如下：'
            });
        } else if (dateType == 'byYear') {
            // TODO
        }
    };

    // 加载图标
    var loadChart = function(dateType, date, eid, ename) {
        // TODO
    };

    // 查询
    $("#salary-count-search-btn").on("click", function() {
        var dateType = $("#salary-count-date-type").datebox('getValue');
        var date = $("#salary-count-date").val();
        var eid = $('#salary-count-eid').combobox('getValue');
        var ename = $('#salary-count-eid').combobox('getText');
        if (date == null || date == '') {
            $.messager.alert("系统提示！", "请选择日期！", "error");
            return;
        }
        loadTable(dateType, date, eid, ename);
        loadChart(dateType, date, eid, ename);
    });

});