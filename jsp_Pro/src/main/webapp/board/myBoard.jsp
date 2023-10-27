<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
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
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>작성일</th>
		<th>조회수</th>
	</tr>
	<c:forEach items="${list}" var="bvo">
		<tr>
			<td><a href="/brd/detail?bno=${bvo.bno}">${bvo.bno }</a></td>
			<td>
			<c:if test="${bvo.image_File ne '' && bvo.image_File ne null }">
				<img src="/_fileUpload/_th_${bvo.image_File }">
			</c:if>
			<a href="/brd/detail?bno=${bvo.bno}">${bvo.title }</a>
			</td>
			
			<td>${bvo.writer}</td>
			<td>${bvo.regdate}</td>
			<td>${bvo.readcount}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>