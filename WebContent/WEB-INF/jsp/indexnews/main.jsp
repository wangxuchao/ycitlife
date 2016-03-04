<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
        <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
        <title>首页 | 生活在盐工</title>
    </head>
	<body>
        <div class="content-box shadow">
            <div class="content">
                <div class="container no-bottom" align="center">
                    <MARQUEE loop="-1" scrollAmount="2" scrollDelay="4" width="90%" align="left"><p>感谢使用“生活在盐工”微信公众平台！</p></MARQUEE>
                </div>
                <div class="decoration"></div>
                <div class="container no-bottom">
                    <div class="column no-bottom">
                        <div>
                            <h4 class="uppercase"><a href="list.jsp?smallid=28">学校要闻</a></h4>
                            <table>
                            	<c:forEach var="news" items="${indexNewsList}" begin="0" end="5">
                            		<tr>
                                    	<td><a href='<c:out value="${news.newsUrl}" />'><c:out value="${news.newsName}" />...</a></td>
                                    	<td style="width: 25%"><c:out value="${news.pubTime}" /></td>
                                	</tr>
                            	</c:forEach>
                            </table>
                        </div>
                        <div>
                            <h4 class="uppercase"><a href="list.jsp?smallid=30">综合新闻</a></h4>
                            <table>
                                <c:forEach var="news" items="${indexNewsList}" begin="6" end="11">
                            		<tr>
                                    	<td><a href='<c:out value="${news.newsUrl}" />'><c:out value="${news.newsName}" />...</a></td>
                                    	<td style="width: 25%"><c:out value="${news.pubTime}" /></td>
                                	</tr>
                            	</c:forEach>
                            </table>
                        </div>
                        <div>
                            <h4 class="uppercase"><a href="list.jsp?smallid=35">通知通告</a></h4>
                            <table>
                                <c:forEach var="news" items="${indexNewsList}" begin="12" end="17">
                            		<tr>
                                    	<td><a href='<c:out value="${news.newsUrl}" />'><c:out value="${news.newsName}" />...</a></td>
                                    	<td style="width: 25%"><c:out value="${news.pubTime}" /></td>
                                	</tr>
                            	</c:forEach>
                            </table>
                        </div>
                        <div>
                            <h4 class="uppercase"><a href="list.jsp?smallid=27">校外媒体</a></h4>
                            <table>
                                <c:forEach var="news" items="${indexNewsList}" begin="18" end="23">
                            		<tr>
                                    	<td><a href='<c:out value="${news.newsUrl}" />'><c:out value="${news.newsName}" />...</a></td>
                                    	<td style="width: 25%"><c:out value="${news.pubTime}" /></td>
                                	</tr>
                            	</c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="decoration"></div>
                <p class="center-text uppercase footer-text"><a href='<c:url value="/" />'>生活在盐工</a> Copyright © 2014 - 2016</p>
            </div>
        </div>
	</body>
</html>