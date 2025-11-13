package com.mycom.myapp.board.dto;

import java.util.List;

// 프론트 <-> 백 사이에서 게시판과 관련도니 모든 API 에 대한 응답 처리 표현
// 모든 작업 결과를 일관성있게 관리
public class BoardResultDto {
	private String result; // 작업 결과과에 대한 성공, 실패
	private List<BoardDto> list; // 목록
	private BoardDto dto; // 상세
	private int count; // 페이징 처리 - 총 건수
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<BoardDto> getList() {
		return list;
	}
	public void setList(List<BoardDto> list) {
		this.list = list;
	}
	public BoardDto getDto() {
		return dto;
	}
	public void setDto(BoardDto dto) {
		this.dto = dto;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "BoardResultDto [result=" + result + ", list=" + list + ", dto=" + dto + ", count=" + count + "]";
	}
}
