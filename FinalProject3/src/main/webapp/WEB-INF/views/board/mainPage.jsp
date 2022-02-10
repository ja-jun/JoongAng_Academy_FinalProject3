<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Insert title here</title>
</head>
<body>
	
	<div style="width:1200px; margin: 0 auto">
		<div class="container-fluid">
			<div class="row">
				<div class="col"><!-- 글로벌 nav -->
					<jsp:include page="../commons/global_nav.jsp"></jsp:include>
				</div>
			</div>
			<div class="row">
				<div class="col"></div>
				<div class="col-10">
				
					<div class="row my-3"><!-- 배너.. -->
						<div class="col">
							<img src="../resources/img/banner.jpg" class="img-fluid">
						</div>
					</div>
					
					<form action="./mainPage" method="get">
					<div class="row mt-3"><!-- 검색 -->
						
						<div class="col">
							<select name="searchOption" class="form-select">
								<option value="title">제목</option>
								<option value="content">내용</option>
								<option value="nick">닉네임</option>
							</select>
						</div>
						<div class="col-8">
							<input name="searchWord" type="text" class="form-control" placeholder="검색할 단어를 입력하세요">
						</div>
						<div class="col d-grid">
							<input type="submit" value="검색" class="btn btn-primary">
						</div>
						
					</div>
					</form>
					
					<div class="row mt-3">
						<div class="col">
							<table class="table">
							  <thead>
							    <tr>
							      <th scope="col">글 번호</th>
							      <th scope="col">제목</th>
							      <th scope="col">작성자</th>
							      <th scope="col">작성일</th>
							      <th scope="col">조회수</th>
							    </tr>
							  </thead>
							  <tbody>
							  <c:forEach items="${dataList }" var="data">
							    <tr>
							      <th scope="row">${data.boardVo.board_no }</th>
							      <td>
							      	<a href="./readContentPage?board_no=${data.boardVo.board_no }">${data.boardVo.board_title }</a>
							      	<c:if test="${!empty data.newKeyword }">
							      		<span class="badge bg-danger">new</span>
							      	</c:if>
							      </td>
							      <td>${data.memberVo.member_nick }</td>
							      <td><fmt:formatDate value="${data.boardVo.board_writedate }" pattern="yy년MM월dd일" /></td>
							      <td>${data.boardVo.board_readcount }</td>
							    </tr>
							  </c:forEach>
							  </tbody>
							</table>
						</div>
					</div>					
					<div class="row mt-1">
						<div class="col-3"></div>
						<div class="col">
							<nav aria-label="...">
							  <ul class="pagination mb-0">
							  <c:choose>
							  	<c:when test="${startPage <= 1}">
								    <li class="page-item disabled">
								      <a class="page-link">&lt;</a>
								    </li>
							  	</c:when>
							  	<c:otherwise>
								    <li class="page-item">
								      <a class="page-link" href="./mainPage?pageNum=${startPage-1 }${additionalParam}">&lt;</a>
								    </li>
							  	</c:otherwise>
							  </c:choose>
							    <c:forEach begin="${startPage}" end="${endPage}" var="i">
							    	<c:choose>
							    		<c:when test="${currentPage == i}">
									    	<li class="page-item active">
									    		<a class="page-link" href="./mainPage?pageNum=${i}${additionalParam}">${i}</a>
									    	</li>
							    		</c:when>
							    		<c:otherwise>
									    	<li class="page-item">
									    		<a class="page-link" href="./mainPage?pageNum=${i}${additionalParam}">${i}</a>
									    	</li>
							    		</c:otherwise>
							    	</c:choose>
							    
							    </c:forEach>
							    <c:choose>
							    	<c:when test="${endPage >= totalPageCount}">
									    <li class="page-item disabled">
									      <a class="page-link">&gt;</a>
									    </li>
							    	</c:when>
							    	<c:otherwise>
									    <li class="page-item">
									      <a class="page-link" href="./mainPage?pageNum=${endPage+1 }${additionalParam}">&gt;</a>
									    </li>
							    	</c:otherwise>							    
							    </c:choose>
							    
							    
							  </ul>
							</nav>							
						</div>
						<div class="col-2 d-grid">
						<c:if test="${!empty sessionUser }">
							<a href="./writeContentPage" class="btn btn-primary">글쓰기</a>
						</c:if>
						</div>
					</div>
					
					
				</div>
				<div class="col"></div>
			</div>
		</div>
	</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>