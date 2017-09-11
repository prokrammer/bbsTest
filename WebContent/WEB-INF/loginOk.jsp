<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
${id }님...환영합니다<br/>

<!-- <form action="/bbs/logout.bbs" method="post">
<!-- 	<input type="button" value="로그아웃"> -->
<!-- </form> -->

<form action="/bbs/logout.bbs" method="post">
	<input type="hidden" name="pageNum" value="${pageNum}">
	<button id = "logout">로그아웃</button>
	<!-- 버튼태그가 form태그안에 선언되면 해당되는 폼의 submit이 선언된다. -->
</form>
</body>
</html>