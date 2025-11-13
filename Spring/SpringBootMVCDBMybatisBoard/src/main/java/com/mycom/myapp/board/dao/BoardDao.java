package com.mycom.myapp.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;

@Mapper
public interface BoardDao {

	// 목록, 목록별 전체 건수
	List<BoardDto> listBoard(BoardParamDto boardParamDto); // 전체 목록 (limit, offset)
	int listBoardTotalCount(); // parameter X
	
	List<BoardDto> listBoardSearchWord(BoardParamDto boardParamDto); // 검색어 목록 (limit, offset, searchWord)
	int listBoardSearchWordTotalCount(BoardParamDto boardParamDto); // searchWord
	
	// 상세
	BoardDto detailBoard(BoardParamDto boardParamDto);
	
	// 등록, 수정, 삭제
	int insertBoard(BoardDto boardDto);
	int updateBoard(BoardDto boardDto);
	int deleteBoard(int boardId);
}
