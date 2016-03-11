<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:forEach var="comment" items="${commentList}" varStatus="status">
	<div class="weui_media_box weui_media_text">
		<h4 class="weui_media_title">${status.index + 1}#：</h4>
		<p class="weui_media_desc">${comment.commentText }</p>
		<ul class="weui_media_info">
			<li class="weui_media_info_meta"><fmt:formatDate
					value="${comment.addTime}" pattern="yyyy年MM月dd hh点mm分ss秒" /></li>
		</ul>
	</div>
</c:forEach>