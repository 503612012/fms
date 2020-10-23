//@sourceURL=/js/user/user.js
requirejs.config({
    baseUrl: '/',
    paths: {
        jquery: 'easyui/jquery.min',
        layui: 'layui/layui.all',
        http: 'js/common/http',
        common: 'js/common/common'
    },
    shim: {
        "layui": {exports: "layui"}
    }
});

requirejs(['jquery', 'layui', 'http', 'common'], function($, layui, http, common) {

    var table = layui.table;

    /**
     * 重新加载表格
     */
    var reload = function() {
        var nickNameReload = $('#nickNameReload');
        var userNameReload = $('#userNameReload');
        var phoneReload = $('#phoneReload');
        // 执行重载
        table.reload('userReload', {
            page: {
                curr: 1 // 重新从第 1 页开始
            },
            where: {
                nickName: nickNameReload.val(),
                userName: userNameReload.val(),
                phone: phoneReload.val()
            }
        });
    };

    table.render({
        elem: '#user-list',
        url: '/user/getByPage/',
        toolbar: '#userListToolBar',
        id: 'userReload',
        even: true,
        cols: [[
            {type: 'numbers'},
            {field: 'userName', title: '用户名', sort: true},
            {field: 'nickName', title: '昵称'},
            {
                field: 'isOnline', title: '在线状态', templet: function(d) {
                    if (d.online) {
                        return '<button class="layui-btn layui-btn-xs force-logout" data-user-name="' + d.userName + '">在线</button>';
                    } else {
                        return '<button class="layui-btn layui-btn-primary layui-btn-xs">离线</button>';
                    }
                }
            },
            {field: 'age', title: '年龄'},
            {field: 'email', title: '邮箱'},
            {field: 'phone', title: '手机号'},
            {
                field: 'gender', title: '性别', templet: function(d) {
                    return d.gender == 1 ? '男' : '<span style="color: #F581B1;">女</span>';
                }
            },
            {field: 'createTime', title: '创建时间'},
            {field: 'createName', title: '创建人'},
            {field: 'lastModifyTime', title: '最后修改时间'},
            {field: 'lastModifyName', title: '最后修改人'},
            {
                field: 'status', title: '状态', templet: function(d) {
                    if (d.status == 1) {
                        return '<div><div class="layui-unselect layui-form-checkbox layui-form-checked user-status" data-id="' + d.id + '" data-status="' + d.status + '"><span>锁定</span><i class="layui-icon layui-icon-ok"></i></div></div>';
                    } else if (d.status == 0) {
                        return '<div><div class="layui-unselect layui-form-checkbox user-status" data-id="' + d.id + '" data-status="' + d.status + '"><span>锁定</span><i class="layui-icon layui-icon-ok"></i></div></div>';
                    }
                }
            },
            {title: '操作', width: 200, align: 'center', toolbar: '#userListBar'}
        ]],
        page: true
    });

    /**
     * 查询按钮点击事件绑定
     */
    $('.userTable .user-search').on('click', function() {
        reload();
    });

    /**
     * 重置按钮点击事件绑定
     */
    $('.userTable .user-reset').on('click', function() {
        $('#nickNameReload').val('');
        $('#userNameReload').val('');
        $('#phoneReload').val('');
        reload();
    });

    /**
     * 更改用户状态
     */
    var updateUserStatus = function(userId, status) {
        var params = {
            userId: userId,
            status: status
        };
        http.post('/user/updateStatus', params, function() {
            reload();
        });
    };

    /**
     * 强制退出
     */
    var forceLogout = function(userName) {
        http.post('/forceLogout', {userName: userName}, function() {
            reload();
        });
    };

    /**
     * 绑定强制退出点击事件
     */
    $("body").on("click", ".force-logout", function() {
        if (hasPermission(hasForceLogoutPermission)) {
            var userName = $(this).attr("data-user-name");
            layer.confirm('确认退出该用户吗？', {anim: 6}, function(index) {
                forceLogout(userName);
                layer.close(index);
            });
        }
    });

    /**
     * 绑定用户状态更改点击事件
     */
    $("body").on("click", ".user-status", function() {
        if (hasPermission(hasChangeUserStatusPermission)) {
            var id = $(this).attr("data-id");
            var status = $(this).attr("data-status");
            if (status == 0) {
                status = 1;
            } else if (status == 1) {
                status = 0;
            }
            if (status == 1) { // 锁定
                layer.confirm('确认锁定该用户吗？', {anim: 6}, function(index) {
                    updateUserStatus(id, 1);
                    layer.close(index);
                });
            }
            if (status == 0) { // 取消锁定
                layer.confirm('确认取消锁定该用户吗？', {anim: 6}, function(index) {
                    updateUserStatus(id, 0);
                    layer.close(index);
                });
            }
        }
    });

    /**
     * 设置角色复选框选中事件
     */
    $("body").on("click", ".user-set-role-checkbox", function() {
        $(this).parent().toggleClass("layui-form-checked");
    });

    /**
     * 设置用户角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    var setUserRole = function(userId, roleIds) {
        var params = {
            'userId': userId,
            'roleIds': roleIds
        };
        http.post('/user/setUserRole', params, function() {
            layer.closeAll();
        });
    };

    // 监听工具条
    table.on('tool(user-list)', function(obj) {
        var data = obj.data;
        var userid = data.id;
        if (obj.event == 'detail') {
            http.post('/user/getRoleByUserId', {id: userid}, function(data) {
                var html = '<div style="padding: 15px;">';
                for (var i = 0; i < data.length; i++) {
                    html += '<div style="margin-top: 7px;" data-role-id="' + data[i].role.id + '" class="layui-unselect layui-form-checkbox ' + (data[i].checked == true ? 'layui-form-checked' : '') + '" lay-skin="primary"><span>' + data[i].role.roleName + '</span><i class="layui-icon layui-icon-ok user-set-role-checkbox"></i></div>';
                }
                html += '</div>';
                layer.open({
                    title: '分配角色',
                    area: [$(window).width() <= 750 ? '60%' : '600px', '450px'],
                    btn: ['保存', '关闭'],
                    type: 1,
                    content: html,
                    yes: function() {
                        var roleIds = [];
                        $("body .layui-form-checked").each(function() {
                            roleIds.push($(this).attr("data-role-id"));
                        });
                        setUserRole(userid, roleIds.toString());
                    },
                    btn2: function() {
                        layer.closeAll();
                    }
                });
            });
        } else if (obj.event == 'del') {
            layer.confirm('真的删除此条记录么？', {anim: 6}, function(index) {
                http.post('/user/delete', {id: data.id}, function() {
                    layer.close(index);
                    reload();
                });
            });
        } else if (obj.event == 'edit') {
            window.parent.mainFrm.location.href = "/user/update?id=" + data.id;
        }
    });

    // 头工具栏事件
    table.on('toolbar(user-list)', function(obj) {
        if (obj.event == 'user-add-btn') {
            window.parent.mainFrm.location.href = "/user/add";
        }
    });

    common.cacheMousedown();

});
