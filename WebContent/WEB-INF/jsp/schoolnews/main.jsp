<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <!--Declare page as mobile friendly --> 
        <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
        <!-- Page Title -->
        <title>首页 | 生活在盐工</title>
        <!-- Stylesheet Load -->
        <link href='<c:url value="/css/style_1.css" />' rel="stylesheet" type="text/css">
        <link href='<c:url value="/css/framework-style.css" />' rel="stylesheet" type="text/css">
        <link href='<c:url value="/css/framework.css" />' rel="stylesheet" type="text/css">
        <link href='<c:url value="/css/coach.css" />' rel="stylesheet" type="text/css">
        <link href='<c:url value="/css/icons.css" />' rel="stylesheet" type="text/css">
        <link href='<c:url value="/css/retina.css" />' rel="stylesheet" type="text/css" media="only screen and (-webkit-min-device-pixel-ratio: 2)" />
    </head>
	<body>
		<div id="preloader">
            <div id="status">
                <p class="center-text">
                    (ง •̀_•́)ง&nbsp;加载中...
                    <em>加载速度取决于你的网速快慢!</em>
                </p>
            </div>
        </div>
        <div class="header">
            <a href="#" class="deploy-left-sidebar"></a>
            <a href='<c:url value="/" />' class="top-logo"><img src='<c:url value="/images/logo.png" />' class="replace-2x" width="125" alt="img"></a>
        </div>
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
        <div class="sidebar-left">
            <div class="sidebar-scroll-left">
                <div class="sidebar-header-left">
                    <a href="#" class="close-sidebar-left"></a>
                </div>
                <p class="sidebar-divider-text">导航菜单</p>
                <a href='<c:url value="/" />' class="nav-item home-nav">网站首页<em class="icon-active"></em></a>
                <a href="#" id="submenu-one" class="nav-item info-nav">新闻资讯<em class="icon-drop"></em></a>
                <div class="submenu submenu-one">
                    <a href='<c:url value="/list.jsp?smallid=28" />'><em></em>学校要闻</a>
                    <a href='<c:url value="/list.jsp?smallid=30" />'><em></em>综合新闻</a>
                    <a href='<c:url value="/list.jsp?smallid=35" />'><em></em>通知通告</a>
                    <a href='<c:url value="/list.jsp?smallid=27" />'><em></em>校外媒体</a>
                </div>
                <a href="#" id="submenu-two" class="nav-item folio-nav">教务图书<em class="icon-drop"></em></a>
                <div class="submenu submenu-two">
                    <a href="http://120.26.114.9/LivingInYcit/Login2.html"><em></em>教务系统</a>
                    <a href="http://120.26.114.9/LivingInYcit/dengji.html"><em></em>等级考试</a>
                    <a href="http://120.26.114.9/LivingInYcit/lib.html"><em></em>图书管理</a>
                </div>
                <a href="http://m.wsq.qq.com/251265174" class="nav-item text-nav">社区交流<em class="icon-page"></em></a>
                <a href="http://120.26.114.9/LivingInYcit/wxc.html" class="nav-item mail-nav">联系我们<em class="icon-page"></em></a>
                <div class="sidebar-decoration"></div>
                <p class="copyright-sidebar">生活在盐工</p>
            </div>
        </div>
	</body>
	<!--Page Scripts Load -->
    <script src='<c:url value="/js/jquery.min.js" />' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery-ui-min.js" />' type="text/javascript"></script>
    <script src='<c:url value="/js/hammer.js" />' type="text/javascript"></script>
    <script src='<c:url value="/js/swipe.js" />' type="text/javascript"></script>
    <script src='<c:url value="/js/custom.js" />' type="text/javascript"></script>
    <script src='<c:url value="/js/framework.js" />' type="text/javascript"></script>
</html>