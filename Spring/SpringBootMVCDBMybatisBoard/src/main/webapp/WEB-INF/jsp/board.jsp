<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycom.myapp.user.dto.UserDto" %>
<%
	UserDto userDto = (UserDto) session.getAttribute("userDto");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<title>게시판</title>
</head>
<body>

<nav class="navbar navbar-expand-lg bg-light">
  <div class="container">
    <a class="navbar-brand" href="#">
    	<img src="/assets/img/user/<%= userDto.getUserProfileImage()%>" style="width:32px; height:32px; border-radius:50%"></img>
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Link</a>
        </li>        
      </ul>
	  <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="/pages/logout">Logout</a>
        </li>        
      </ul>      
    </div>
  </div>
</nav>

<div class="container mt-4">

	<h4 class="text-center">게시판</h4>
	
	<div class="input-group">
	  <input type="text" class="form-control" placeholder="검색어를 입력하세요." id="inputSearchWord">
	  <button class="btn btn-success" type="button" id="btnSearchWord">검색</button>
	</div>
	
	<table class="table table-hover">
	  <thead>
	    <tr>
	      <th>#</th>
	      <th>제목</th>
	      <th>작성자</th>
	      <th>작성일시</th>
	      <th>조회수</th>
	    </tr>
	  </thead>
	  <tbody id="boardTbody">

	  </tbody>
	</table>
	
	<div id="paginationWrapper"></div>
	
	<!-- bootstrap 을 통한 모달창 띄우기는 static content 에 의미 -->
	<!-- 
	<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#insertBoardModal">글 쓰기</button>
	-->
	<button type="button" class="btn btn-primary" id="btnInsertPage">글 쓰기</button>
</div>




<!-- insert Modal -->
<div class="modal fade" id="insertBoardModal" tabindex="-1" aria-labelledby="insertBoardModal" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5">글 쓰기</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">

		<div class="mb-3">
		  <label for="titleInsert" class="form-label">제목</label>
		  <input type="text" class="form-control" id="titleInsert">
		</div>
		<div class="mb-3">
		  <label for="contentInsert" class="form-label">내용</label>
		  <textarea class="form-control" id="contentInsert" rows="10"></textarea>
		</div>

      </div>
      <div class="modal-footer">
      	<!-- 입력 유효성 검사 후 창이 자동으로 닫히지 않도록 data-bs-dismis 항목 제거 -->
        <button id="btnBoardInsert" type="button" class="btn btn-primary">등록</button>
      </div>
    </div>
  </div>
</div>

<!-- detail Modal -->
<div class="modal fade" id="detailBoardModal" tabindex="-1" aria-labelledby="detailBoardModal" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5">글 상세</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">

        <div class="table-responsive">
            <table class="table">
              <tbody>
                <tr><td>글번호</td><td id="boardIdDetail">#</td></tr>
                <tr><td>제목</td><td id="titleDetail">#</td></tr>
                <tr><td>내용</td><td id="contentDetail">#</td></tr>
                <tr><td>작성자</td><td id="userNameDetail">#</td></tr>
                <tr><td>작성일시</td><td id="regDtDetail">#</td></tr>
                <tr><td>조회수</td><td id="readCountDetail">#</td></tr>
              </tbody>
            </table>
     	</div> 

      </div>
      <div class="modal-footer" id="detailBoardModalFooter">
      	<!-- 입력 유효성 검사 후 창이 자동으로 닫히지 않도록 data-bs-dismis 항목 제거 -->
        <button id="btnBoardUpdatePage" type="button" class="btn btn-primary">글 수정하기</button>
        <button id="btnBoardDeleteConfirm" type="button" class="btn btn-warning">글 삭제하기</button>
      </div>
    </div>
  </div>
</div>

<!-- update Modal -->
<div class="modal fade" id="updateBoardModal" tabindex="-1" aria-labelledby="updateBoardModal" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5">글 수정</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">

		<div class="mb-3">
		  <label for="titleUpdate" class="form-label">제목</label>
		  <input type="text" class="form-control" id="titleUpdate">
		</div>
		<div class="mb-3">
		  <label for="contentUpdate" class="form-label">내용</label>
		  <textarea class="form-control" id="contentUpdate" rows="10"></textarea>
		</div>

      </div>
      <div class="modal-footer">
      	<!-- 입력 유효성 검사 후 창이 자동으로 닫히지 않도록 data-bs-dismis 항목 제거 -->
        <button id="btnBoardUpdate" type="button" class="btn btn-primary">수정</button>
      </div>
    </div>
  </div>
</div>

<script src="/assets/js/util.js"></script>
<script>
	// 페이지 요청과 데이터 요청을 구분해서 header 에 ajax:true 항목을 보내는 더 효율적인 코드는 
	//   직접 fetch 를 포함하는 사용자 정의 function() 만드는 방법
	//   이미 만들어진 axios 같은 통신 library 를 사용하는 방법 
	
	// 전역변수
	
	// 3개 모달
	const insertModal = new bootstrap.Modal( document.querySelector("#insertBoardModal") );
	const detailModal = new bootstrap.Modal( document.querySelector("#detailBoardModal") );
	const updateModal = new bootstrap.Modal( document.querySelector("#updateBoardModal") );
	
	// 페이징 처리, 검색어
	let LIST_ROW_COUNT = 10; 	// 화면에 게시글을 몇개 행으로 보여줄 것인가? == limit, Pagination Factor #1
	let OFFSET = 0;				// 몇개를 건너뛰고 보여줄 것인가?
	let SEARCH_WORD = '';		// 검색어
	
	let PAGE_LINK_COUNT = 10;	// 화면에 보여 줄 페이지 링크 1-2-3-4-5.. 몇 개, Pagination Factor #2
	let CURRENT_PAGE_INDEX = 1;	// 현재 화면에 보여줄 페이지 링크 인덱스 1-2-3-4-5.. 중 현재 몇 번째?, Pagination Factor #3
	let TOTAL_LIST_COUNT = 0;	// 현재 게시글 전체 수, Pagination Factor #4 <= 백엔드 제공
	
	window.onload = function(){
		// 글 목록
		listBoard();
		
		// 검색어 목록
		document.querySelector("#btnSearchWord").onclick = function(){
			// 아래 validation 포함된 코드는 검색 버튼이 검색어 전용일 때( 목록 버튼이 별도로 존재할 때 )
// 			let searchWord = document.querySelector("#inputSearchWord").value;
// 			if( searchWord == '' ){
// 				alert("검색어를 입력하세요.");
// 				return;
// 			}
// 			SEARCH_WORD = searchWord;
// 			listBoard();

			// 검색 버튼을 눌렀을 때, 검색어가 있으면 검색 목록, 없으면 전체 목록 한꺼번에 처리
			SEARCH_WORD = document.querySelector("#inputSearchWord").value;
			listBoard();
		}
		
		// 글 등록 모달 창 띄우기
		document.querySelector("#btnInsertPage").onclick = function(){
			// 입력 항목 정리
			document.querySelector("#titleInsert").value = '';
			document.querySelector("#contentInsert").value = '';
			// 모달 창 띄우기
// 			let modal = new bootstrap.Modal( document.querySelector("#insertBoardModal") );
			insertModal.show();
		}
		
		// 글 등록
		document.querySelector("#btnBoardInsert").onclick = function(){
			// validation check
			if( document.querySelector("#titleInsert").value == '' || document.querySelector("#contentInsert").value == '' ){
				alert("제목 또는 내용을 모두 입력하세요.");
				return;
			}
			// 등록
			insertBoard();
		}
		
		// 글 수정 모달 띄우기
		document.querySelector("#btnBoardUpdatePage").onclick = function(){
			let boardId = document.querySelector("#detailBoardModal").getAttribute("data-boardId"); // 상세 모달의 boardId 를 가져와서
			document.querySelector("#updateBoardModal").setAttribute("data-boardId", boardId) // 수정 모달의 boardId 를 담는다.
			// 상세 모달의 제목, 내용을 수정 모달의 제목, 내용으로 이동
			document.querySelector("#titleUpdate").value = document.querySelector("#titleDetail").innerHTML;
			document.querySelector("#contentUpdate").value = document.querySelector("#contentDetail").innerHTML;
			
			detailModal.hide();
			updateModal.show();
		}
		
		// 글 수정
		document.querySelector("#btnBoardUpdate").onclick = function(){
			// validation check
			if( document.querySelector("#titleUpdate").value == '' || document.querySelector("#contentUpdate").value == '' ){
				alert("제목 또는 내용을 모두 입력하세요.");
				return;
			}
			// 수정
			updateBoard();
		}	
		
		// 글 삭제 확인
		document.querySelector("#btnBoardDeleteConfirm").onclick = function(){
			// confirm
			if( confirm("이 글을 삭제할까요?") ){
				deleteBoard();
			}
		}
	}

	// 목록
	async function listBoard(){
		let fetchOptions = {
			headers: {
				ajax: "true"
			}	
		}
		
		let url = "/boards/list";
		let urlParams = "?limit=" + LIST_ROW_COUNT + "&offset=" + OFFSET + "&searchWord=" + SEARCH_WORD;
		let response = await fetch(url + urlParams, fetchOptions);
		let data = await response.json();
		
		console.log(data);		
		
		if( data.result == "success" ){			
			// data -> html
			makeListHtml(data.list);
			TOTAL_LIST_COUNT = data.count;
			addPagination();
		}else if( data.result == "fail" ){
			alert("글 목록 과정에서 오류가 발생했습니다.");
		}else if( data.result == "login" ){
			window.location.href = "/pages/login";
		}		
	}
	
	// Spring Boot 의 기본 Json Converter 는 Jackson library
	// 아래 코드는 Jackson 처리 결과에 대응하는 코드
	// 만약 Json Converter 를 Gson 등으로 변경하면 별도의 대응 코드를 작성
	// Gson 은 "2025-11-11T09:30:05" 포맷과 다른 형태로 json 을 만든다.
	// application.properties 에서 Json Converter 를 변경할 수 있다.
	function makeListHtml(list){
		let listHtml = ``;
		
		list.forEach( el => {
			let boardId = el.boardId;
			let userName = el.userName;
			let title = el.title;
			
			let regDt = new Date(el.regDt); // "2025-11-11T09:30:05" -> javascript Date 객체			
			let regDtStr = makeDateStr(regDt.getFullYear(), regDt.getMonth() + 1, regDt.getDate(), '.');
			
			let readCount = el.readCount;
			
// 			console.log(regDtStr);
			listHtml += `
				<tr style="cursor:pointer" data-boardId="\${boardId}">
					<td>\${boardId}</td>
					<td>\${title}</td>
					<td>\${userName}</td>
					<td>\${regDtStr}</td>
					<td>\${readCount}</td>
				</tr>
			`;
		} );
		
		document.querySelector("#boardTbody").innerHTML = listHtml;
		
		// 동적으로 추가한 <tr> 항목에 대한 click 이벤트 핸들러 작성
		document.querySelectorAll("#boardTbody tr").forEach( el => {
			el.onclick = function(){
				let boardId = this.getAttribute("data-boardId");
				detailBoard(boardId);
			}
		} ); // el 하나가 <tr> 하나
	}
	
	// Pagination UI 만들어야 한다.
	// 적지 않은 계산
	// UI 는 프론트에 의해 언제든지 자유롭게 변경 가능
	// Pagination 은 한 곳이 아닌 여러 곳에서 사용될 수 있다. (공통)
	//   => util.js 구현, 호출
	// 고려할 사항
	//   데이터 건수가 굉장히 큰 경우, 정확한 총 건수 계산대신 대략적인 건수 계산 테이블을 만든다.
	//   데이터 총 건수 테이블 생성, 충분한 시간 간격으로 한번씩 업데이트 <= 데이터의 성격이 최신 데이터 위주
	//   그 밖의 다양한 방식으로 대응 방법들이 많다.
	//   테이블 분리 : 최신 데이터 테이블과 이전 데이터 테이블 분리(이관) 이관 테이블 총건수는 미리 계산
	function addPagination(){
		console.log(LIST_ROW_COUNT, PAGE_LINK_COUNT, CURRENT_PAGE_INDEX, TOTAL_LIST_COUNT);
		makePaginationHtml(LIST_ROW_COUNT, PAGE_LINK_COUNT, CURRENT_PAGE_INDEX, TOTAL_LIST_COUNT, "paginationWrapper" );
	}
	
	// 9 를 누르면 movePage(9) 가 호출
	// 현재 페이지가 9 로 변경
	// 9 이전 1~8 페이지까지의 데이터를 skip
	function movePage(pageIndex){
		CURRENT_PAGE_INDEX = pageIndex;
		OFFSET = (pageIndex -1) * LIST_ROW_COUNT;
		listBoard();
	}
	
	// 상세
	async function detailBoard(boardId){
		let fetchOptions = {
			headers: {
				ajax: "true"
			}	
		}
		let url = "/boards/detail/" + boardId;
		let response = await fetch(url, fetchOptions);
		let data = await response.json();
		
		console.log(data);		
		
		if( data.result == "success" ){			
			// data -> html
			makeDetailHtml(data.dto);
		}else if( data.result == "fail" ){
			alert("글 상세 과정에서 오류가 발생했습니다.");
		}else if( data.result == "login" ){
			window.location.href = "/pages/login";
		}	
	}
    
	function makeDetailHtml(dto){
		let regDt = new Date(dto.regDt); // "2025-11-11T09:30:05" -> javascript Date 객체			
		let regDtStr = makeDateStr(regDt.getFullYear(), regDt.getMonth() + 1, regDt.getDate(), '/') + ' ' +
					   makeTimeStr(regDt.getHours(), regDt.getMinutes(), regDt.getSeconds(), ':');
		
		document.querySelector("#boardIdDetail").innerHTML = "#" + dto.boardId;
		document.querySelector("#titleDetail").innerHTML = dto.title;
		document.querySelector("#contentDetail").innerHTML = dto.content;
		document.querySelector("#userNameDetail").innerHTML = dto.userName;
		document.querySelector("#regDtDetail").innerHTML = regDtStr;
		document.querySelector("#readCountDetail").innerHTML = dto.readCount;
		// 수정, 삭제를 위해 boardId 를 Modal 에 속성으로 추가
		document.querySelector("#detailBoardModal").setAttribute("data-boardId", dto.boardId);
		
		// same user
		if( dto.sameUser ){
			document.querySelector("#detailBoardModalFooter").style.display = "block";
		}else{
			document.querySelector("#detailBoardModalFooter").style.display = "none";
		}
		
		detailModal.show();
	}
	
	// 등록
	// post, x-www-urlencoded ( UrlSearchParams 객체)
	async function insertBoard(){

		let urlParams = new URLSearchParams({
			title: document.querySelector("#titleInsert").value,
			content: document.querySelector("#contentInsert").value
		});
		
		let fetchOptions = {
			headers: {
				ajax: "true"
			},				
			method: "post",
			body: urlParams
		}
		
		let url = "/boards/insert"
		let response = await fetch(url, fetchOptions);
		let data = await response.json();
		
		console.log(data);
		
		if( data.result == "success" ){
			alert("글이 등록되었습니다.");
			// 작업이 성공했을 때에만 모달을 닫으려면 이곳에서 닫는다.
			
			// 목록
			listBoard();
		}else if( data.result == "fail" ){
			alert("글 등록 과정에서 오류가 발생했습니다.");
		}else if( data.result == "login" ){
			window.location.href = "/pages/login";
		}
		
		// 모달 창 닫기
		insertModal.hide();		
	}
	
	// 수정
	// post, x-www-urlencoded ( UrlSearchParams 객체)
	// 등록과 달리, boardId 전달
	async function updateBoard(){
		
		let boardId = document.querySelector("#updateBoardModal").getAttribute("data-boardId");
		
		let urlParams = new URLSearchParams({
			boardId: boardId,
			title: document.querySelector("#titleUpdate").value,
			content: document.querySelector("#contentUpdate").value
		});
		
		let fetchOptions = {
			headers: {
				ajax: "true"
			},				
			method: "post",
			body: urlParams
		}
		
		let url = "/boards/update"
		let response = await fetch(url, fetchOptions);
		let data = await response.json();
		
		console.log(data);
		
		if( data.result == "success" ){
			alert("글이 수정되었습니다.");
			// 작업이 성공했을 때에만 모달을 닫으려면 이곳에서 닫는다.
			
			// 목록
			listBoard();
		}else if( data.result == "fail" ){
			alert("글 수정 과정에서 오류가 발생했습니다.");
		}else if( data.result == "login" ){
			window.location.href = "/pages/login";
		}
		
		// 모달 창 닫기
		updateModal.hide();		
	}	
	
	// 삭제
	// get
	// boardId 전달
	async function deleteBoard(){
		let fetchOptions = {
				headers: {
					ajax: "true"
				}	
			}
		
		let boardId = document.querySelector("#detailBoardModal").getAttribute("data-boardId");

		let url = "/boards/delete/" + boardId
		let response = await fetch(url, fetchOptions);
		let data = await response.json();
		
		console.log(data);
		
		if( data.result == "success" ){
			alert("글이 삭제되었습니다.");
			// 작업이 성공했을 때에만 모달을 닫으려면 이곳에서 닫는다.
			
			// 목록
			listBoard();
		}else if( data.result == "fail" ){
			alert("글 삭제 과정에서 오류가 발생했습니다.");
		}else if( data.result == "login" ){
			window.location.href = "/pages/login";
		}
		
		// 모달 창 닫기
		detailModal.hide();		
	}		
</script>	
</body>
</html>







