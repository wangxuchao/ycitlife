<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OAuth2.0网页授权</title>
<meta name="viewport" content="width=device-width,user-scalable=0">
<style type="text/css">
* {
	margin: 0;
	padding: 0
}

table {
	border: 1px dashed #B9B9DD;
	font-size: 12pt
}

td {
	border: 1px dashed #B9B9DD;
	word-break: break-all;
	word-wrap: break-word;
}
</style>
</head>
<body>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td width="20%">属性</td>
			<td width="80%">值</td>
		</tr>
		<tr>
			<td>OpenID</td>
			<td>${snsUserInfo.openId}</td>
		</tr>
		<tr>
			<td>昵称</td>
			<td>${snsUserInfo.nickname}</td>
		</tr>
		<tr>
			<td>性别</td>
			<td>${snsUserInfo.sex}</td>
		</tr>
		<tr>
			<td>国家</td>
			<td>${snsUserInfo.country}</td>
		</tr>
		<tr>
			<td>省份</td>
			<td>${snsUserInfo.province}</td>
		</tr>
		<tr>
			<td>城市</td>
			<td>${snsUserInfo.city}</td>
		</tr>
		<tr>
			<td>头像</td>
			<td>${snsUserInfo.headImgUrl}</td>
		</tr>
		<tr>
			<td>特权</td>
			<td>${snsUserInfo.privilegeList}</td>
		</tr>
		<tr>
			<td>expiresIn</td>
			<td>${expiresIn}</td>
		</tr>
	</table>
</body>
</html>