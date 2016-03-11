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
<title>${categoryTitle}| 生活在盐工</title>
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
	<div class="">
		<!-- 学校要闻 -->
		<div class="weui_cells_title">
			<h4>${categoryTitle}</h4>
		</div>
		<div class="weui_cells weui_cells_access weui_panel weui_panel_access"
			id="news_list">
			<c:forEach var="news" items="${schoolNewsList}">
				<a class="weui_cell"
					href="<%=ValueUtil.PROJECT_ROOT%>news?id=${news.id}&smallid=${news.smallid}">
					<div class="weui_cell_bd weui_cell_primary news_title">
						<c:out value="${news.title}" />
					</div>
					<div class="weui_cell_ft news_time">
						<fmt:formatDate value="${news.addtime}" pattern="MM-dd" />
					</div>
				</a>
			</c:forEach>
			<a class="weui_panel_ft" id="more_news_list" start="5" max="5"
				smallid="${smallid}">加载更多</a>
		</div>
		<div class="copy">&copy;2016&nbsp;生活在盐工</div>
	</div>
	<script
		src="<%=ValueUtil.PROJECT_ROOT%>js/jquery-2.2.0.min.js?v=<%=ValueUtil.PROJECT_VERSION%>"></script>
	<script
		src="<%=ValueUtil.PROJECT_ROOT%>js/script.js?v=<%=ValueUtil.PROJECT_VERSION%>"></script>
</body>
</html>