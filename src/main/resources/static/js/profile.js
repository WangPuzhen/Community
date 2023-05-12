$(function(){
	$(".follow-btn").click(follow);
});

function follow() {
	var btn = this;
	if($(btn).hasClass("btn-info")) {
		// 关注TA
		$.post(
			CONTEXT_PATH + "/follow",
			{"entityType":3, "entityId":$(btn).prev().val()}, // 把前端获取得到的参数传给后端方法
			function (data){ // 处理返回结果data
				data = $.parseJSON(data);
				if(data.code == 0){
					window.location.reload(); // 刷新页面
				}else{
					alert(data.msg);
				}
			}
		);
		// $(btn).text("已关注").removeClass("btn-info").addClass("btn-secondary"); // 样式
	} else {
		// 取消关注
		// 关注TA
		$.post(
			CONTEXT_PATH + "/unfollow",
			{"entityType":3, "entityId":$(btn).prev().val()}, // 把前端获取得到的参数传给后端方法
			function (data){ // 处理返回结果data
				data = $.parseJSON(data);
				if(data.code == 0){
					window.location.reload(); // 刷新页面
				}else{
					alert(data.msg);
				}
			}
		);
		// $(btn).text("关注TA").removeClass("btn-secondary").addClass("btn-info");
	}
}