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
<c:if test="${bvo.image_File ne '' && bvo.image_File ne null}">
	<div>
		<img src="/_fileUpload/${bvo.image_File }" alt="No Image!">
	</div>
</c:if>
<table class="table table-hover">
	<tr>
		<th>번호</th>
		<td>${bvo.bno }</td>
	</tr>
	<tr>
		<th>제목</th>
		<td>${bvo.title }</td>
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
		<th>수정일</th>
		<td>${bvo.moddate }</td>
	</tr>
	<tr>
		<th>내용</th>
		<td>${bvo.content }</td>
	</tr>
	<tr>
		<th>조회수</th>
		<td>${bvo.readcount }</td>
	</tr>
</table>
<c:if test="${ses.id eq bvo.writer }">
<a href="/brd/modify?bno=${bvo.bno }"><button class="btn btn-outline-primary">수정</button></a>
<a href="/brd/remove?bno=${bvo.bno }"><button class="btn btn-outline-danger">삭제</button></a>
</c:if>
<a href="/brd/pageList"><button class="btn btn-outline-secondary">list</button></a>

<hr>
<!-- 댓글 등록 -->
<div>
<input type="text" id="cmtWriter" value="${ses.id }" readonly="readonly"><br>
<input type="text" id="cmtText" placeholder="Add Commnet"><br>
<button type="button" id="cmtAddBtn" class="btn btn-outline-success">등록</button><br>
</div>
<br>
<!-- 댓글 표시 -->
<div class="accordion" id="accordionExample">
  <div class="accordion-item">
    <h2 class="accordion-header">
      <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
        cno, writer, reg_date
      </button>
    </h2>
    <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        content
      </div>
    </div>
  </div>
</div>  
<!-- c:out : 서버측 변수를 script에 할당, 서버의 bno를 bnoVal에 저장 -->
<script type="text/javascript">
const bnoVal = `<c:out value="${bvo.bno}" />`;
</script>
<script src="/resources/board_detail.js"></script>
</body>
<script type="text/javascript">
printCommentList(bnoVal);
</script>
</html>