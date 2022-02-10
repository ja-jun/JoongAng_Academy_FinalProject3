<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script>
	function joinMemberSubmit(){
		
		// 유효성 검사.... = 사용가 입력한 값이 정상적이냐?? 판단...
		// 정규표현식...
		var regExp = /^[a-z]+[a-z0-9]{3,19}$/g;	
				
		if(!regExp.test($("#inputId").val())){
			alert("아이디를 영문 소문자로 시작해야되고 총 4자리에서 20자 사이인 영문 숫자 조합으로 해야됩니다.");
			$("#inputId").focus();
			return;
		}
		
		/*
		var regExp = /^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{10,12}$/;		
				
		if(!regExp.test($("#inputPw").val())){
			alert("비밀번호는 최소 8자 최대 12자, 소문자와 대문자로 시작해야되고 특수문자 대소문자 숫자 조합해야됩니다.");
			$("#inputPw").focus();
			return;
		}
		*/
		
		if( $("#inputPw").val() != $("#inputPwConfirm").val() ){
			alert("비밀번호 확인이 일치하지 않습니다.");
			$("#inputPw").val("");
			$("#inputPwConfirm").val("");
			$("#inputPw").focus();
			return;		
		}
							
		if($("#inputBirth").val() == ""){
			alert("생일을 입력하세요.");
			$("#inputBirth").focus();
			return;
		}
		
		if(isIdConfirmed == false){
			alert("아이디 중복검사를 해야됩니다.");
			return;
		}
		
		$("#frm1").submit();
	}

	// 바닐라 JS
	function joinMemberSubmit2(){
		
		var inputId = document.getElementById("inputId");
		
		if(inputId.value == ""){
			alert("아이디 확인...")
			inputId.focus();
			return;
		}
		
		//....
		
		var frm1 = document.getElementById("frm1");
		frm1.submit();
	}
	
	var isIdConfirmed = false;
	
	function confirmId() {

		var idBox = document.getElementById("inputId");
		var idValue = idBox.value;		
		
		//AJAX...호출...
		
		var xhr = new XMLHttpRequest();
		
		//응답 받을때...
		xhr.onreadystatechange = function() { // callback function

			if(xhr.readyState == 4 && xhr.status == 200){
				//alert("ttt");
				//alert("서버로 부터 받은 데이터 : " + xhr.responseText);
				var data = JSON.parse(xhr.responseText);
				//alert(data.result);
				//여기부터가 헬(CSR)...DOM 조작 API... 미친듯이 잘 다뤄야 된다...
			
				//화면 조작... 핵심 DOM 조작
				var confirmAlertBox = document.getElementById("confirmAlertBox");
				if(data.result == true){
					//alert("이미 존재하는 아이디 입니다.");
					isIdConfirmed = false;
					confirmAlertBox.innerText = "이미 존재 하는 아이디 입니다.";			
					confirmAlertBox.style.color = "red";
				}else{
					//alert("사용 가능한 아이디 입니다.");
					isIdConfirmed = true;
					confirmAlertBox.innerText = "사용 가능한 아이디 입니다.";
					confirmAlertBox.style.color = "green";
				}
			}
		
		};
		
		xhr.open("GET" , "./isExistId?id=" + idValue , true);		
		xhr.send();
	}
	
	
</script>

</head>
<body>
	<h1>회원가입</h1>
	<form id="frm1" action="./joinMemberProcess" method="post">
		ID : <input id="inputId" type="text" name="member_id" onblur="confirmId()">
		<!--  <input type="button" value="중복확인" onclick="confirmId()"> -->		
		<br>
		<div id="confirmAlertBox"></div>
		
		PW : <input id="inputPw" type="password" name="member_pw"><br>
		PW Confirm : <input id="inputPwConfirm" type="password"><br>
		
		Nickname : <input type="text" name="member_nick"><br>
		gender : 
		<!-- 체크 , 라디오 , 셀렉트는 value값이 미리 지정되어 있어야됨 -->
		<input type="radio" name="member_gender" value="M" checked>남
		<input type="radio" name="member_gender" value="F">여<br>
		
		취미 : 
		<c:forEach items="${hobbyCategoryList }" var="abc">
			<input type="checkbox" name="hobby_category_no" value="${abc.hobby_category_no}">${abc.hobby_category_name }
		</c:forEach>
		
		<br>
		
		birth : <input id="inputBirth" type="date" name="member_birth"><br>
		phone : <input type="text" name="member_phone"><br>
		e-mail : <input type="text" name="member_email"><br>
		<input type="button" value="회원가입" onclick="joinMemberSubmit()">
	</form>
	
	
</body>
</html>