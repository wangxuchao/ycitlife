<%@page import="cn.wangxuchao.ycitz.util.ValueUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>${schoolNewsDetail.getSchoolNews().getTitle() }|生活在盐工</title>
<link rel="stylesheet"
	href="<%=ValueUtil.PROJECT_ROOT%>css/weui.min.css" />
<link rel="stylesheet" href="<%=ValueUtil.PROJECT_ROOT%>css/style.css" />
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
	<div class="weui_panel">
		<div class="weui_panel_bd">
			<div class="weui_media_box weui_media_text">
				<h4 class="weui_media_title">${schoolNewsDetail.getSchoolNews().getTitle()}</h4>
				<ul class="weui_media_info">
					<li class="weui_media_info_meta">作者：${schoolNewsDetail.getSchoolNews().getAuthor()}</li>
					<li class="weui_media_info_meta">来源：${schoolNewsDetail.getSchoolNews().getCom()}</li>
					<li class="weui_media_info_meta">时间：<fmt:formatDate
							value="${schoolNewsDetail.getSchoolNews().getAddtime()}"
							pattern="yyyy-MM-dd hh:mm:ss" /></li>
				</ul>
			</div>
		</div>
		<article class="weui_article">
			${schoolNewsDetail.getSchoolNewsInfo().getNewsinfo()}</article>
		<div class="weui_panel_hd alignright">责任编辑：${schoolNewsDetail.getSchoolNewsInfo().getZeren()}</div>

		<div class="copy">&copy;2016&nbsp;生活在盐工</div>
	</div>
	<script src="<%=ValueUtil.PROJECT_ROOT%>js/jquery-2.2.0.min.js" /></script>
	<script src="<%=ValueUtil.PROJECT_ROOT%>js/script.js" /></script>
</body>
</html>