<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
</head>
<body>
<table class="table table-hover">
	<tr>
		<th>아이디</th>
		<th>비밀번호</th>
		<th>이메일</th>
		<th>나이</th>
		<th>가입일자</th>
		<th>최근 로그인</th>	
		<th>탈퇴</th>
	</tr>
	<c:forEach items="${list }" var="mvo">
	<tr>
		<th>${mvo.id }</th>
		<th>${mvo.pwd }</th>
		<th>${mvo.email }</th>
		<th>${mvo.age }</th>
		<th>${mvo.regdate }</th>
		<th>${mvo.lastlogin }</th>
		<th>
			<c:if test="${mvo.id ne 'admin'}">
 			<a href="/mem/removeAdVer?id=${mvo.id}" onclick="return confirm('${mvo.id } 회원을 탈퇴하시겠습니까?');">
        	<button class="btn btn-outline-danger">탈퇴</button>
    		</a>
    		</c:if>
		</th>
	</tr>
	</c:forEach>
</table>
</body>
</html>