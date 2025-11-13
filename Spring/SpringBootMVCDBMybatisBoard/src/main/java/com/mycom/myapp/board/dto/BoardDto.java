package com.mycom.myapp.board.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class BoardDto {
	private int boardId;
	private int userSeq;
	private String userName; // join 을 통해 글쓴 사용자의 이름
	private String userProfileImage; // 선택 사항
	private String title;
	private String content;
	private LocalDateTime regDt;
	private int readCount;
	
	private boolean sameUser; // 로그인한 사용자와 현재 조회하는 글 작성자가 일치하는 지 표현 ( 수정, 삭제 버튼 보이기 제어 사용 )
	
	public BoardDto() {}
	public BoardDto(int userSeq, String title, String content) {
		this.userSeq = userSeq;
		this.title = title;
		this.content = content;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserProfileImage() {
		return userProfileImage;
	}
	public void setUserProfileImage(String userProfileImage) {
		this.userProfileImage = userProfileImage;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getRegDt() {
		return regDt;
	}
	// mybatis Date Mapping 처리할 때, LocalDateTime 바로 처리 X
	public void setRegDt(Date regDt) {
		this.regDt = LocalDateTime.ofInstant(regDt.toInstant(), ZoneId.systemDefault());
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public boolean isSameUser() {
		return sameUser;
	}
	public void setSameUser(boolean sameUser) {
		this.sameUser = sameUser;
	}
	
	@Override
	public String toString() {
		return "BoardDto [boardId=" + boardId + ", userSeq=" + userSeq + ", userName=" + userName
				+ ", userProfileImage=" + userProfileImage + ", title=" + title + ", content=" + content + ", regDt="
				+ regDt + ", readCount=" + readCount + ", sameUser=" + sameUser + "]";
	}
}
