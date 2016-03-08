$("body").ready(function() {
	$("#loadingToast").css("display", "none");
});

$("#more_news_list").click(
		function() {
			$("#loadingToast").css("display", "block");
			var start = $("#more_news_list").attr("start");
			var max = $("#more_news_list").attr("max");
			var smallid = $("#more_news_list").attr("smallid");
			htmlobj = $.ajax({
				url : "getMoreNews?start=" + start + "&max=" + max
						+ "&smallid=" + smallid,
				async : false
			});
			// 显示新的条目
			$("#more_news_list").before(htmlobj.responseText);
			// 更改查询开始条目
			$("#more_news_list").attr("start", Number(start) + Number(max));
			// 加载动画消失
			$("#loadingToast").css("display", "none");

			return;
		});