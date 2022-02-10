<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
<title>Insert title here</title>
<script>
	var sessionInfo = null;
		
	var board_no = ${data.boardVo.board_no};

	function doLike() {
		
		if(sessionInfo == null){
			var value = confirm("로그인 하셔야 이용 가능합니다. 로그인 페이지로 이동하시겠습니까?");
			if(value == true){
				location.href = "../member/loginPage";				
			}					
			return;
		}		
		
		//AJAX...호출...
		var xhr = new XMLHttpRequest();
		
		//응답 받을때...
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4 && xhr.status == 200){
				var data = JSON.parse(xhr.responseText);		
				
				refreshHeart();
				refreshTotalCount();
			}			
		};	
		
		xhr.open("post" , "./doLike" , true);
		xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xhr.send("board_no=" + board_no);
	}
	
	function refreshHeart() {		
		//AJAX...호출...
		var xhr = new XMLHttpRequest();
		
		//응답 받을때...
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4 && xhr.status == 200){
				var data = JSON.parse(xhr.responseText);
				
				if(data.result == 'error'){
					console.log(data.reason);
					return;
				}
				
				var heartBox = document.getElementById("heartBox");
				
				if(data.status == 'like'){
					heartBox.setAttribute("class" , "bi bi-heart-fill fs-1 text-danger")
				}else{
					heartBox.setAttribute("class" , "bi bi-heart fs-1 text-danger")
				}				
			}			
		};			
		xhr.open("get" , "./getMyLikeStatus?board_no=" + board_no , true);
		xhr.send();		
	}	

	function refreshTotalCount() {		
		//AJAX...호출...
		var xhr = new XMLHttpRequest();
		
		//응답 받을때...
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4 && xhr.status == 200){
				var data = JSON.parse(xhr.responseText);		
				
				var totalLikeCountBox = document.getElementById("totalLikeCountBox");
				totalLikeCountBox.innerText = data.totalLikeCount;
			}			
		};			
		xhr.open("get" , "./getTotalLikeCount?board_no=" + board_no , true);
		xhr.send();			
	}
	
	function getSessionInfo(){
		//AJAX...호출...
		var xhr = new XMLHttpRequest();
		
		//응답 받을때...
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4 && xhr.status == 200){
				var data = JSON.parse(xhr.responseText);	
				
				if(data.result != 'empty'){
					sessionInfo = {
							memberNo : data.memberNo,
							memberNick : data.memberNick
					};
				}
			}			
		}; 
		xhr.open("get" , "../member/getSessionInfo" , false); //마지막 인자... false 동기식인데.. 왠만하면 피하자...
		xhr.send();	
	}
	
	function writeComment() {
		
		if(sessionInfo == null){
			alert("로그인 후 이용 가능합니다.");
			return;			
		}
		
		var commentInput = document.getElementById("commentInput");
		var commentValue = commentInput.value;
		
		//AJAX...호출...
		var xhr = new XMLHttpRequest();
		
		//응답 받을때...(callback function) , 익숙해지기 전까지는 항상 아래처럼 작성해야 한다.
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4 && xhr.status == 200){
				var data = JSON.parse(xhr.responseText);					
				
				commentInput.value = "";
				
				refreshCommentList();
			}			
		}; 				
		xhr.open("post" , "./writeComment" , true);
		xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xhr.send("board_no=" + board_no + "&comment_content=" + commentValue);		
	}	
	
	function refreshCommentList() {
		
		//AJAX...호출...
		var xhr = new XMLHttpRequest();
		
		//응답 받을때...
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4 && xhr.status == 200){
				var data = JSON.parse(xhr.responseText);					
			
				//console.log(xhr.responseText);
				//console.log(data);
				//데이터를 이용해서 렌더링...
				
				var commentRoot = document.getElementById("commentRoot");
				commentRoot.innerHTML = "";
				
				for(commentData of data.commentList){
					//console.log(commentData.MEMBER_NICK);
					var rowBox = document.createElement("div");
					rowBox.setAttribute("class","row commentBox");
										
					var nicknameBox = document.createElement("div");
					nicknameBox.setAttribute("class","col-2 fw-bold bg-primary");
					nicknameBox.innerText = commentData.MEMBER_NICK;
					rowBox.appendChild(nicknameBox);
					
					var contentBox = document.createElement("div");
					contentBox.setAttribute("class","col-6 bg-secondary");
					contentBox.innerText = commentData.COMMENT_CONTENT;
					rowBox.appendChild(contentBox);

					//getMonth는 0월부터 시작이라 +1해줘야한다.					
					var dateBox = document.createElement("div");
					dateBox.setAttribute("class","col-2 bg-success");
					
					var commentWriteDate = new Date(commentData.COMMENT_WRITEDATE);
					
					dateBox.innerText = commentWriteDate.getFullYear() + "." 
						+ (commentWriteDate.getMonth() + 1) + "." 
						+ commentWriteDate.getDate();
					
					rowBox.appendChild(dateBox);
					
					if(sessionInfo != null && commentData.MEMBER_NO == sessionInfo.memberNo ){
						var deleteBox = document.createElement("div")
						deleteBox.setAttribute("class","col-1 bg-danger");
						deleteBox.setAttribute("onclick","deleteComment("+commentData.COMMENT_NO+")");
						deleteBox.innerText = "X";
						rowBox.appendChild(deleteBox);
						
						var updateBox = document.createElement("div");
						updateBox.setAttribute("class","col-1 bg-info");
						updateBox.setAttribute("onclick","updateCommentForm(this , "+commentData.COMMENT_NO+")");
						updateBox.innerText = "수정";
						rowBox.appendChild(updateBox);						
					}					
					
					commentRoot.appendChild(rowBox);
				}				
			}
			
			/*
			<div class="row coomentBox">
				<div class="col-2 fw-bold bg-primary">한조1</div>
				<div class="col-6 bg-secondary">내용.........</div>
				<div class="col-2 bg-success">22.2.9</div>
				<div class="col-1 bg-danger">X</div>
				<div class="col-1 bg-info">수정</div>
			</div>
			*/				
						
		}; 				
		xhr.open("get" , "./getCommentList?board_no=" + board_no , true);
		xhr.send();				
	}	
	
	function updateCommentForm(target , commentNo) {
		
		//console.log("asdfdsaf");
		var commentBox = target.closest(".commentBox");
		
		//...
		var commentValue = commentBox.children[1].innerText;
		
		commentBox.innerHTML = "";
		
		var textCol = document.createElement("div");
		textCol.setAttribute("class","col-8");
		var textBox = document.createElement("textarea");
		textBox.setAttribute("class","form-control");
		textBox.value = commentValue;
		textCol.appendChild(textBox);
		
		var inputButtonCol = document.createElement("div");
		inputButtonCol.setAttribute("class","col-2 d-grid");
		var inputButton = document.createElement("button");
		inputButton.setAttribute("class","btn btn-primary");
		inputButton.setAttribute("onclick","updateComment(this , "+commentNo+")");
		
		inputButton.innerText = "입력";
		inputButtonCol.appendChild(inputButton);
		
		var cancelUpdateFormCol = document.createElement("div");
		cancelUpdateFormCol.setAttribute("class","col-2 d-grid");
		var cancelButton = document.createElement("button");
		cancelButton.setAttribute("class","btn btn-primary");
		cancelButton.setAttribute("onclick","refreshCommentList()");
		cancelButton.innerText = "취소";
		cancelUpdateFormCol.appendChild(cancelButton);
		
		commentBox.appendChild(textCol);
		commentBox.appendChild(inputButtonCol);
		commentBox.appendChild(cancelUpdateFormCol);
		
		/*
		<div class="col-8"><textarea class="form-controll"></textarea></div>
		<div class="col-2 d-grid"><button class="btn btn-primary">입력</button></div>
		<div class="col-2 d-grid"><button class="btn btn-primary">취소</button></div>
		*/
				
	}

	function updateComment(target , commentNo) {
		
		var commentBox = target.closest(".commentBox");
		var commentValue = commentBox.querySelector("textarea").value;
		
		//AJAX...호출...
		var xhr = new XMLHttpRequest();
		
		//응답 받을때...
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4 && xhr.status == 200){
				var data = JSON.parse(xhr.responseText);	
			
				refreshCommentList();	
			}
			
		}; 

		xhr.open("post" , "./updateComment" , true);
		xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xhr.send("comment_no=" + commentNo + "&comment_content=" + commentValue);		
		
	}
	
	
	function deleteComment(commentNo) {
		//AJAX...호출...
		var xhr = new XMLHttpRequest();
		
		//응답 받을때...
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4 && xhr.status == 200){
				var data = JSON.parse(xhr.responseText);	
				
				refreshCommentList();				
			}
			
		}; 

		xhr.open("post" , "./deleteComment" , true);
		xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xhr.send("comment_no=" + commentNo);		
	}
	
	
	window.addEventListener("DOMContentLoaded", function(){
		//..여기 실행 시점...문서 로드 후... 사실장 시작 지점...
		getSessionInfo();
		refreshHeart();
		refreshTotalCount();	
		refreshCommentList();
		
		//setInterval(refreshCommentList , 10000);
	});	
	
</script>

</head>
<body>
	
	<h1>${data.boardVo.board_title }</h1>
	
	작성자 : ${data.memberVo.member_nick }<br>
	작성일 :  <fmt:formatDate value="${data.boardVo.board_writedate }" pattern="yyyy.MM.dd HH:mm"/><br>	
	조회수 : ${data.boardVo.board_readcount }<br>
	
	이미지 : <br>
	<c:forEach items="${data.boardImageVoList }" var="boardImageVo">
		<img src="/upload/${boardImageVo.image_url }"><br>	
	</c:forEach>
		
	내용 : <br>
	${data.boardVo.board_content }<br>
	
	<br>
	<i id="heartBox" class="bi bi-heart fs-1 text-danger" onclick="doLike()"></i>			
	<br>
	좋아요 수 : <span id="totalLikeCountBox"></span>
	<br>
	
	<a href="./mainPage">목록으로</a>
	
	<c:if test="${!empty sessionUser && sessionUser.member_no == data.boardVo.member_no }">
		<!-- ?는 링크에 파라미터를 보내기 위한 방법을 의미한다. -->
		<a href="./deleteContentProcess?board_no=${data.boardVo.board_no }">삭제</a>	
		
	    <!-- 수정은 글쓰기와 글보기가 같이 써져있어야 한다. -->	
		<a href="./updateContentPage?board_no=${data.boardVo.board_no }">수정</a> 
	</c:if>	
	
	<div class="container-fluid">
		<div class="row">
			<div class="col-8">
				<textarea id="commentInput" class="form-control" placeholder="댓글 입력하세요"></textarea>
			</div>
			<div class="col d-grid">
				<button class="btn btn-primary" onclick="writeComment()">입력</button>
			</div>
		</div>	
		<div class="row"><!-- 댓글 리스트 루트 -->
			<div class="col" id="commentRoot">
				<div class="row coomentBox">
					<div class="col-2 fw-bold bg-primary">한조1</div>
					<div class="col-6 bg-secondary">내용.........</div>
					<div class="col-2 bg-success">22.2.9</div>
					<div class="col-1 bg-danger">X</div>
					<div class="col-1 bg-info">수정</div>
				</div>				
			</div>
		</div>	
	
	</div>
	
			
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>