<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${schoolNewsDetail.getSchoolNews().getTitle() }</title>
</head>
<body>
id：${schoolNewsDetail.getSchoolNews().getId() }<br/>
smallid：${schoolNewsDetail.getSchoolNews().getSmallid() }<br/>
title：${schoolNewsDetail.getSchoolNews().getTitle() }<br/>
author：${schoolNewsDetail.getSchoolNews().getAuthor() }<br/>
com：${schoolNewsDetail.getSchoolNews().getCom() }<br/>
addtime：${schoolNewsDetail.getSchoolNews().getAddtime() }<br/>
newsinfo：${schoolNewsDetail.getSchoolNewsInfo().getNewsinfo() }<br/>
zeren：${schoolNewsDetail.getSchoolNewsInfo().getZeren() }<br/>
</body>
</html>