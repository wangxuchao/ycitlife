<%@page import="cn.wangxuchao.ycitz.util.ValueUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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