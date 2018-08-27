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

    var salaryCountCharts = new Highcharts.Chart('salary-count-charts', {
        chart: {
            type: 'spline',
            height: 240
        },
        title: {
            text: '',
            x: -20
        },
        xAxis: {
            categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
        },
        yAxis: {
            gridLineWidth: 0,
            title: {
                text: ''
            },
            labels: {
                enabled: false
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            shared: true,
            formatter: function() {
                var s = '<b>' + this.x + '</b>';
                $.each(this.points, function() {
                    s += '<br/>' + this.series.name + ': ' + accounting.formatMoney(this.y, "¥", 2) + '元';
                });
                return s;
            }
        },
        legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'top',
            borderWidth: 0,
            enabled: false
        },
        series: []
    });

    // 查看某个月的工资详情
    window.showYearSalryDetail = function(date, eid, ename) {
        $("#salary-count-date-type").combobox('setValue', 'byMonth');
        $("#salary-count-date").val(date);
        loadTable("byMonth", date, eid, ename);
        loadChart("byMonth", date, eid, ename);
    };

    // 年薪资列表操作列格式化
    var yearTableHanleFormatter = function(value, row, index) {
        return '<span><a href="#" onclick="showYearSalryDetail(\'' + row.date + '\', ' + row.eid + ', \'' + row.ename + '\');">查看详情</a></span>';
    };

    // 加载表格
    var loadTable = function(dateType, date, eid, ename) {
        var title = '<span style="color: red">' + ename + '</span>' + date + '薪资详情如下：';
        if (dateType == 'byYear') {
            title = '<span style="color: red">' + ename + '</span>' + date + '年度薪资详情如下：';
        }
        if (dateType == 'byMonth') {
            $('#salary-count-table').datagrid({
                url: getBasePath() + '/salary/salary/getTableData.html?dateType=' + dateType + '&date=' + date + '&eid=' + eid,
                columns: [[
                    {field: 'date', title: '日期', align: 'center', width: 100},
                    {field: 'score', title: '工时记分', align: 'center', width: 70},
                    {field: 'daySalary', title: '日薪', align: 'center', width: 70},
                    {
                        field: 'extraSalary',
                        title: '额外薪资',
                        align: 'center',
                        width: 70,
                        formatter: function(value, row, index) {
                            return '<span title="' + row.extraDesc + '">' + row.extraSalary + '</span>'
                        }
                    },
                    {
                        field: 'lastModifyDate',
                        title: '当日总计薪资',
                        align: 'center',
                        formatter: function(value, row, index) {
                            return '<span style="color: red">' + ((parseFloat(row.daySalary) * row.score / 10) + parseFloat(row.extraSalary)) + '</span>';
                        },
                        width: 100
                    },
                    {field: 'worksiteName', title: '工地', align: 'center', width: 100},
                    {field: 'inputNickName', title: '录入人', align: 'center', width: 70},
                    {field: 'inputDate', title: '录入时间', align: 'center', width: 150},
                    {field: 'remark', title: '备注', align: 'center', width: 400}
                ]],
                title: title,
                singleSelect: true,
                rownumbers: true
            });
        } else if (dateType == 'byYear') {
            $('#salary-count-table').datagrid({
                url: getBasePath() + '/salary/salary/getTableData.html?dateType=' + dateType + '&date=' + date + '&eid=' + eid,
                columns: [[
                    {field: 'date', title: '日期', align: 'center', width: 160},
                    {
                        field: 'salary',
                        title: '该月薪资',
                        align: 'center',
                        width: 160,
                        formatter: function(value, row, index) {
                            return '<span style="color: red">' + row.salary + '</span>';
                        }
                    },
                    {
                        field: 'handle',
                        title: '操作',
                        align: 'center',
                        width: 640,
                        formatter: yearTableHanleFormatter
                    }
                ]],
                title: title,
                singleSelect: true,
                rownumbers: true
            });
        }
    };

    // 加载图表
    var loadChart = function(dateType, date, eid, ename) {
        $.ajax({
            url: getBasePath() + "/salary/salary/getChartsData.html",
            async: true,
            type: "GET",
            dataType: "json",
            data: {
                "dateType": dateType,
                "date": date,
                "eid": eid
            },
            success: function(result) {
                if (result.code != 200) {
                    $.messager.alert("系统提示！", '获取图表数据错误！', "error");
                    return;
                }
                var daySalary = [];
                var categories = [];
                var total = 0;
                for (var i = 0; i < result.data.categories.length; i++) {
                    daySalary.push(result.data.salary[i]);
                    categories.push(result.data.categories[i]);
                    total += result.data.salary[i];
                }
                var series = salaryCountCharts.series;
                while (series.length > 0) {
                    series[0].remove(false);
                }
                salaryCountCharts.xAxis[0].setCategories(categories);
                salaryCountCharts.addSeries({
                    name: '薪资',
                    data: daySalary,
                    color: '#08c1a2'
                }, false);
                var title;
                if (dateType == 'byYear') {
                    title = {
                        text: '<span style="color: red">' + ename + '</span>' + date + '年度薪资图表，总计薪资：<span style="color: blue">' + total + '</span>元。'
                    }
                } else {
                    title = {
                        text: '<span style="color: red">' + ename + '</span>' + date + '薪资图表，总计薪资：<span style="color: blue">' + total + '</span>元。'
                    }
                }
                salaryCountCharts.setTitle(title);
                salaryCountCharts.redraw(true);
            }
        });
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
