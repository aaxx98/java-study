<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<title>로그인</title>
</head>
<body>
	<div class="container">
		<div class="mb-3 mt-5 d-flex justify-content-center">
			<h1 class="display-4">삐따기 세상</h1>		
		</div>
		
		<div class="mb-3">
			<h2>로그인</h2>		
		</div>
		
		<form novalidate><!-- 브라우저의 기본 유효성 검사 제외 -->
			<div class="mb-3">
			  <label for="userEmail" class="form-label">User Email</label>
			  <input type="email" class="form-control" id="userEmail" placeholder="이메일을 입력하세요." value="dskim@dskim.com">	  
			</div>		
			<div class="mb-3">
			  <label for="userPassword" class="form-label">User Password</label>
			  <input type="password" class="form-control" id="userPassword" placeholder="비밀번호를 입력하세요." value="1234">		  
			</div>
		</form>
		<button id="btnLogin" class="btn btn-success">로그인</button>
	</div>
	
<script>
	window.onload = function(){
		// btnLogin
		document.querySelector("#btnLogin").onclick = function(){
			// validation check
			if( document.querySelector("#userEmail").value == '' || document.querySelector("#userPassword").value == '' ){
				alert("입력이 올바르지 않습니다.");
				return;
			}
			
			login();
		}
	}

	async function login(){
		// 사용자 입력값
		let userEmail = document.querySelector("#userEmail").value;
		let userPassword = document.querySelector("#userPassword").value;
		
		
		// post x-www-form-urlencoded
		let urlParams = new URLSearchParams({
			userEmail: userEmail,
			userPassword: userPassword,
		});
		
		// fetch options
		let fetchOptions = {
			method: "post",
			body: urlParams
		}
		
		let response = await fetch("/auth/login", fetchOptions);
		let data = await response.json();
		
		if( data.result == "success" ){
			window.location.href="/pages/board";
		}else{
			alert("이메일 또는 비밀번호가 올바르지 않습니다. 다시 시도해 주세요.");
		}
	}
</script>
</body>
</html>