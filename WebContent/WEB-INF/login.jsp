<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인하이소^^</title>
</head>
<body>
	<form action="/bbs/login.bbs" method="post" id="loginForm">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<table>
			<tr><td><label for="id">I D : </td><td><input type="text" name="id" id="id"></label></td></tr>
			<tr><td><label for="pass">PASS : </td><td><input type="text" name="pass" id="pass"></label></td></tr>
		</table>
		<input type="submit" value="로그인">
		<input type="reset" value="취소">
		<input type="button" value="회원가입" onclick="document.location.href='/bbs/join.bbs'">	
	</form>
로그인 안됨
</body>
</html>