package com.mycom.myapp.board.dto;

// front -> back 전달 파라미터 표준화
public class BoardParamDto {
	// 목록
	private int limit; 	// pagination
	private int offset; // pagination
	private String searchWord; // 제목 검색
	
	// 상세
	private int boardId; // 게시글 key
	private int userSeq; // 작성자와 보는자 일치 확인에 필요
	
	// 생성자는 생략
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	
	@Override
	public String toString() {
		return "BoardParamDto [limit=" + limit + ", offset=" + offset + ", searchWord=" + searchWord + ", boardId="
				+ boardId + ", userSeq=" + userSeq + "]";
	}
}
