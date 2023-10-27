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
<!-- 데이터를 /brd/insert로 post 방식 전송 -->
<form action="/brd/insert" method="post" enctype="multipart/form-data">
	제목 : <input type="text" name="title"><br>
	작성자 : <input type="text" name="writer" value="${ses.id }" readonly="readonly"><br>
	내용 : <textarea rows="3" cols="30" name="content"></textarea><br>
	첨부 파일 : <input type="file" name="image_file"
	accept="image/png, image/jpg, image/jpeg, image/gif"><br>
	<button type="submit" class="btn btn-outline-success">등록</button>
</form>
</body>
</html>