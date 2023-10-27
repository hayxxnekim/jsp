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
<form action="/mem/update" method="post">
아이디 : <input type="text" name="id" value="${ses.id }" readonly="readonly"><br>
비밀번호 : <input type="text" name="pwd" value="${ses.pwd }"><br>
이메일 : <input type="text" name="email" value="${ses.email }"><br>
나이 : <input type="text" name="age" value="${ses.age }"><br>
<button type="submit" class="btn btn-outline-primary">수정</button>
</form>
<a href="/mem/remove"><button class="btn btn-outline-danger">탈퇴</button></a>
</body>
</html>