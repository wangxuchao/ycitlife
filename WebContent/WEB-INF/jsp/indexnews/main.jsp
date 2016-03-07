<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>生活在盐工</title>
<link rel="stylesheet" href="css/weui.min.css" />
<link rel="stylesheet" href="css/style.css" />
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
	<div align="center">
		<MARQUEE loop="-1" scrollAmount="2" scrollDelay="4" width="90%"
			align="left">
			<p>感谢使用“生活在盐工”微信公众平台！</p>
		</MARQUEE>
	</div>
	<div class="">
		<!-- 学校要闻 -->
		<div class="weui_cells_title">
			<h4><a href="list.jsp?smallid=28">学校要闻</a></h4>
		</div>
		<div class="weui_cells weui_cells_access weui_panel weui_panel_access">
			<c:forEach var="news" items="${indexNewsList}" begin="0" end="5">
				<a class="weui_cell" href='<c:out value="${news.newsUrl}" />'>
					<div class="weui_cell_bd weui_cell_primary news_title">
						<c:out value="${news.newsName}" />
					</div>
					<div class="weui_cell_ft news_time">
						<c:out value="${news.pubTime}" />
					</div>
				</a>
			</c:forEach>
			<a class="weui_panel_ft" href="list.jsp?smallid=28">查看更多</a>
		</div>

		<!-- 综合新闻 -->
		<div class="weui_cells_title">
			<h4><a href="list.jsp?smallid=30">综合新闻</a></h4>
		</div>
		<div class="weui_cells weui_cells_access weui_panel weui_panel_access">
			<c:forEach var="news" items="${indexNewsList}" begin="6" end="11">
				<a class="weui_cell" href='<c:out value="${news.newsUrl}" />'>
					<div class="weui_cell_bd weui_cell_primary news_title">
						<c:out value="${news.newsName}" />
					</div>
					<div class="weui_cell_ft news_time">
						<c:out value="${news.pubTime}" />
					</div>
				</a>
			</c:forEach>
			<a class="weui_panel_ft" href="list.jsp?smallid=30">查看更多</a>
		</div>

		<!-- 通知通告 -->
		<div class="weui_cells_title">
			<h4><a href="list.jsp?smallid=35">通知通告</a></h4>
		</div>
		<div class="weui_cells weui_cells_access weui_panel weui_panel_access">
			<c:forEach var="news" items="${indexNewsList}" begin="12" end="17">
				<a class="weui_cell" href='<c:out value="${news.newsUrl}" />'>
					<div class="weui_cell_bd weui_cell_primary news_title">
						<c:out value="${news.newsName}" />
					</div>
					<div class="weui_cell_ft news_time">
						<c:out value="${news.pubTime}" />
					</div>
				</a>
			</c:forEach>
			<a class="weui_panel_ft" href="list.jsp?smallid=35">查看更多</a>
		</div>

		<!-- 校外媒体 -->
		<div class="weui_cells_title">
			<h4><a href="list.jsp?smallid=27">校外媒体</a></h4>
		</div>
		<div class="weui_cells weui_cells_access weui_panel weui_panel_access">
			<c:forEach var="news" items="${indexNewsList}" begin="18" end="23">
				<a class="weui_cell" href='<c:out value="${news.newsUrl}" />'>
					<div class="weui_cell_bd weui_cell_primary news_title">
						<c:out value="${news.newsName}" />
					</div>
					<div class="weui_cell_ft news_time">
						<c:out value="${news.pubTime}" />
					</div>
				</a>
			</c:forEach>
			<a class="weui_panel_ft" href="list.jsp?smallid=27">查看更多</a>
		</div>

		<div class="copy">&copy;2016&nbsp;生活在盐工</div>
	</div>
	<script src="js/jquery-2.2.0.min.js"></script>
	<script src="js/script.js"></script>
</body>
</html>