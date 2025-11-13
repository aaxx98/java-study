package com.mycom.myapp.user.dto;

// 사용자 관련 표준 응답
// 등록, 수정, 삭제 : int -> result, success
// 목록, 상세 : List<Dto> Dto
// 위 모든 처리를 하나의 ResultDto 처리
public class UserResultDto {
	private String result; // "success", "fail" <= Emun, 코드....

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
