<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.wangxuchao.ycitz.util.ValueUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>${categoryTitle}|生活在盐工</title>
<link rel="stylesheet"
	href="<%=ValueUtil.PROJECT_ROOT%>css/weui.min.css?v=<%=ValueUtil.PROJECT_VERSION%>" />
<link rel="stylesheet"
	href="<%=ValueUtil.PROJECT_ROOT%>css/style.css?v=<%=ValueUtil.PROJECT_VERSION%>" />
</head>
<body ontouchstart>
	<!-- 加载动画 -->
	<div id="loadingToast" class="weui_loading_toast">
		<div class="weui_mask_transparent"></div>
		<div class="weui_toast">
			<div class="weui_loading">
				<div class="weui_loading_leaf weui_loading_leaf_0"></div>
				<div class="weui_loading_leaf weui_loading_leaf_1"></div>
				<div class="weui_loading_leaf weui_loading_leaf_2"></div>
				<div class="weui_loading_leaf weui_loading_leaf_3"></div>
				<div class="weui_loading_leaf weui_loading_leaf_4"></div>
				<div class="weui_loading_leaf weui_loading_leaf_5"></div>
				<div class="weui_loading_leaf weui_loading_leaf_6"></div>
				<div class="weui_loading_leaf weui_loading_leaf_7"></div>
				<div class="weui_loading_leaf weui_loading_leaf_8"></div>
				<div class="weui_loading_leaf weui_loading_leaf_9"></div>
				<div class="weui_loading_leaf weui_loading_leaf_10"></div>
				<div class="weui_loading_leaf weui_loading_leaf_11"></div>
			</div>
			<p class="weui_toast_content">(ง •̀_•́)ง&nbsp;加载中...</p>
		</div>
	</div>
	<div class="weui_msg">
		<div class="weui_icon_area">
			<i class="${msg_icon_class} weui_icon_msg"></i>
		</div>
		<div class="weui_text_area">
			<h2 class="weui_msg_title">${msg_title}</h2>
			<p class="weui_msg_desc">${msg_desc}</p>
		</div>
		<div class="weui_extra_area">
			<a href="<%=ValueUtil.PROJECT_ROOT%>">返回首页</a>
		</div>
	</div>
	<script
		src="<%=ValueUtil.PROJECT_ROOT%>js/jquery-2.2.0.min.js?v=<%=ValueUtil.PROJECT_VERSION%>"></script>
	<script
		src="<%=ValueUtil.PROJECT_ROOT%>js/script.js?v=<%=ValueUtil.PROJECT_VERSION%>"></script>
</body>
</html>