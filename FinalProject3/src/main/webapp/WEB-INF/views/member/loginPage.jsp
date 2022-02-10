<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Insert title here</title>
</head>
<body>
	<div style="width:1200px;margin : 0 auto">
		<div class="container-fluid">
			<div class="row">
				<div class="col"><!-- 글로벌 nav -->
					<jsp:include page="../commons/global_nav.jsp"></jsp:include>
				</div>
			</div>
		
			<div class="row mt-5">
				<div class="col"></div>
				<div class="col-4">
					<form action="./loginProcess" method="post">
					<div class="row mt-3">
						<div class="col text-center fs-1 fw-bold">로그인</div>
					</div>
					<div class="row mt-3">
						<div class="col">
							<input name="member_id" type="text" class="form-control" placeholder="아이디를 입력하세요">
						</div>
					</div>
					<div class="row mt-2">
						<div class="col">
							<input name="member_pw" type="password" class="form-control" placeholder="비밀번호를 입력하세요">
						</div>
					</div>
					<div class="row mt-3">
						<div class="col d-grid">
							<input type="submit" value="로그인" class="btn btn-primary">
						</div>
					</div>
					<div class="row mt-1">
						<div class="col">
							<a href="./joinMemberPage">회원가입</a>
						</div>
					</div>
					</form>
				</div>
				<div class="col"></div>
			</div>
		</div>
	</div>
	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>	
</body>
</html>