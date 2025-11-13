package com.mycom.myapp.board.service;

import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;
import com.mycom.myapp.board.dto.BoardResultDto;

public interface BoardService {
	// 목록 
	// 전체 건수를 가져오는 메소드는 Service 에 없다.
	// Controller -> Service -> Dao 2번 요청보다는
	// Controller -> Service 1번, Service -> Dao 2번 이 더 효율적
	// Service 에서 BoardResultDto 객체의 count 까지 마무리하고 return
	BoardResultDto listBoard(BoardParamDto boardParamDto); // limit, offset
	BoardResultDto listBoardSearchWord(BoardParamDto boardParamDto); // limit, offset, searchWord
	
	// 상세
	BoardResultDto detailBoard(BoardParamDto boardParamDto); // boardId, userSeq
		
	// 등록, 수정, 삭제
	BoardResultDto insertBoard(BoardDto boardDto);
	BoardResultDto updateBoard(BoardDto boardDto);
	BoardResultDto deleteBoard(int boardId);
}
