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
<c:if test="${ses.id eq null }">
<form action="/mem/login" method="get">
ID : <input type="text" name="id">
PWD : <input type="text" name="pwd">
<button type="submit" class="btn btn-outline-success">login</button>
</form>
<br>
<a href="/mem/join"><button class="btn btn-outline-primary">join</button></a>
<a href="/brd/pageList"><button class="btn btn-outline-secondary">list</button></a>
</c:if>
<!-- membercontroller의 ses 속성 사용 가능 -->
<div>
<c:if test="${ses.id ne null && ses.id ne 'admin'}">
${ses.id }님 환영합니다<br>
계정 생성일 : ${ses.regdate }<br>
마지막 접속 : ${ses.lastlogin }<br>
<br>
<a href="/mem/logout"><button class="btn btn-outline-danger">로그아웃</button></a>
<a href="/mem/modify"><button class="btn btn-outline-warning">회원 정보 수정</button></a>
<br><br><a href="/brd/register"><button class="btn btn-outline-success">게시글 등록</button></a>
<a href="/brd/pageList"><button class="btn btn-outline-secondary">게시글 목록</button></a>
<a href="/brd/myBoard"><button class="btn btn-outline-info">내가 쓴 게시글</button></a>
</c:if>

<c:if test="${ses.id eq 'admin'}">
    <script type="text/javascript">
        alert("관리자로 로그인했습니다");
    </script>
    <a href="/mem/logout"><button class="btn btn-outline-danger">로그아웃</button></a>
    <a href="/mem/list"><button class="btn btn-outline-primary">회원 목록</button></a>
    <a href="/brd/pageList"><button class="btn btn-outline-secondary">게시글 목록</button></a>
</c:if>


</div>
<!-- c:out : 데이터 출력시 사용  -->
<script type="text/javascript">
const msg_login = `<c:out value="${msg_login}" />`;
if(msg_login === '0') {
	alert("로그인 정보가 일치하지 않습니다");
}
</script>
</body>
</html>