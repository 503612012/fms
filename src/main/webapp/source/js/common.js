//@ sourceURL=source/js/common.js
$(function() {
	hookAjax({
		// 拦截回调
		onreadystatechange: function(xhr) {
			if (xhr.readyState == 4) { // 获取回传内容
				var obj = eval('(' + xhr.response + ')');
				if (obj.code == 3009 || obj.code == 3010) {
					location.href = getBasePath() + "/sys/loginPage.html";
				}
			}
		}
	});
});