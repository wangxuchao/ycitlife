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

$("#comment_text .weui_textarea").change(
		function() {
			var count = $(this).val().length;
			$("#comment_text span").text(count);
			if (count > 200 || count == 0) {
				$("#comment_text span").css("color", "red");
				$(".comment_btn").attr("disabled", true).addClass(
						"weui_btn_disabled");
				$("#comment_tips").css("display", "");
			} else {
				$("#comment_text span").css("color", "");
				$(".comment_btn").attr("disabled", false).removeClass(
						"weui_btn_disabled");
				$("#comment_tips").css("display", "none");
			}
		});

$('.comment_btn').click(function() {
	var count = $("#comment_textarea").val().length;
	if (count > 200 || count == 0) {
		$("#comment_text span").css("color", "red");
		$(".comment_btn").attr("disabled", true).addClass("weui_btn_disabled");
		$("#comment_tips").css("display", "");
		return false;
	}
	$.post('add_comment', {
		newsid : $('#newsid').val(),
		comment_text : $('#comment_textarea').val()
	}, function(data) // 回传函数
	{
		if (data == 'success') {
			// 显示成功
			$("#toast").fadeIn().delay(500).fadeOut();
			$('#comment_textarea').val("")
			// 更新评论列表
			getComments($('#newsid').val());
		}
	});
});

function getComments(newsid) {
	htmlobj = $.ajax({
		url : "get_comment?newsid=" + newsid,
		async : false
	});
	$("#comment_body").html(htmlobj.responseText);
}