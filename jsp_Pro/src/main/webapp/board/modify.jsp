<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
</head>
<body>
<!-- 입력한 데이터를 /brd/edit(BoardController)으로 post 방식 전송 -->
<form action="/brd/edit" method="post" enctype="multipart/form-data">
<table class="table table-hover">
	<tr>
		<th>번호</th>
		<td><input type="text" name="bno" value="${bvo.bno }" readonly="readonly"></td>
	</tr>
	<tr>
		<th>제목</th>
		<td><input type="text" name="title" value="${bvo.title }"></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>${bvo.writer }</td>
	</tr>
	<tr>
		<th>작성일</th>
		<td>${bvo.regdate }</td>
	</tr>
	<tr>
		<th>내용</th>
		<td><textarea rows="3" cols="30" name="content">${bvo.content }</textarea></td>
	</tr>
	<tr>
		<th>첨부 파일</th>
		<td>
			<input type="hidden" name="image_file" value="${bvo.image_File }">
			<input type="file" name="new_file" accept="image/png, image/jpg, image/gif">
		</td>
	</tr>
</table>
<button type="submit" class="btn btn-outline-success">수정</button>
</form>
</body>
</html>