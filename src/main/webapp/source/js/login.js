//@ sourceURL=source/js/login.js
$(function() {
	// 解决java后台跳出iframe的解决方法
	if (window != top) {
		top.location.href = location.href; 
	}
	
	// 刷新验证码
	$(".refresh_captcha").on("click", function() {
		$(this).attr("src", getBasePath() + "/sys/getCaptcha.html?random=" + new Date().getTime());
	});
	
	// 绑定回车事件
	$(document).keydown(function(e) { 
		var curKey = e.which; 
	    if (curKey == 13) { 
	    	$(".submit-btn").click(); 
	        return false; 
	    } 
    }); 
	
	// 绑定登录按钮事件
	$(".submit-btn").on("click", function() {
		var userName = $("input[name=userName]").val();
		if ($.trim(userName) == '') {
			$.messager.alert("系统提示！", "请输入用户名！", "error");
			return;
		}
		var password = $("input[name=password]").val();
		if ($.trim(password) == '') {
			$.messager.alert("系统提示！", "请输入密码！", "error");
			return;
		}
		var captcha = $("input[name=captcha]").val();
		if ($.trim(captcha) == '') {
			$.messager.alert("系统提示！", "请输入验证码！", "error");
			return ;
		}
		$.ajax({
			url: getBasePath() + "/sys/login.html",
			type: "POST",
			async : true,
			data: {
				"userName": userName,
				"password": password,
				"captcha": captcha
			},
			dataType: "json",
			success: function(result) {
				if (result.code != 200) {
					alert(result.data);
					$(".refresh_captcha").trigger("click");
	        		$("input[name=captcha]").val("");
					return;
				}
				location.href = getBasePath() + "/index.html";
			}
		});
	});
});