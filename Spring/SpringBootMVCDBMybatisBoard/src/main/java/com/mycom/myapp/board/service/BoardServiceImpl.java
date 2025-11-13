package com.mycom.myapp.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mycom.myapp.board.dao.BoardDao;
import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;
import com.mycom.myapp.board.dto.BoardResultDto;

@Service
public class BoardServiceImpl implements BoardService{

	private final BoardDao boardDao;
	
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Override
	public BoardResultDto listBoard(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			List<BoardDto> list = boardDao.listBoard(boardParamDto); // 목록
			int count = boardDao.listBoardTotalCount(); // 전체 건수
			boardResultDto.setList(list);
			boardResultDto.setCount(count);
			boardResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}

		return boardResultDto;
	}
	

	@Override
	public BoardResultDto listBoardSearchWord(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			List<BoardDto> list = boardDao.listBoardSearchWord(boardParamDto); // 검색어 목록
			int count = boardDao.listBoardSearchWordTotalCount(boardParamDto); // 검색어 전체 건수
			boardResultDto.setList(list);
			boardResultDto.setCount(count);
			boardResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}

		return boardResultDto;
	}	

	@Override
	public BoardResultDto detailBoard(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			BoardDto boardDto = boardDao.detailBoard(boardParamDto); // 게시글 1건 데이터
			// same user
			// boardDto.getUserSeq() <= 현재 게시글의 글 작성자 userSeq
			// boardParamDto.getUserSeq() <= 현재 게시글의 조회자 userSeq
			if( boardDto.getUserSeq() == boardParamDto.getUserSeq() ) { // 내가 쓴 글이라면
				boardDto.setSameUser(true);
			}else {
				boardDto.setSameUser(false);
			}
			boardResultDto.setDto(boardDto);
			boardResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}

		return boardResultDto;
	}
	
	
	@Override
	public BoardResultDto insertBoard(BoardDto boardDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			int ret = boardDao.insertBoard(boardDto);
			if( ret == 1 ) boardResultDto.setResult("success");
			else boardResultDto.setResult("fail");
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}
		
		return boardResultDto;
	}

	@Override
	public BoardResultDto updateBoard(BoardDto boardDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			int ret = boardDao.updateBoard(boardDto);
			if( ret == 1 ) boardResultDto.setResult("success");
			else boardResultDto.setResult("fail");
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}
		
		return boardResultDto;
	}

	@Override
	public BoardResultDto deleteBoard(int boardId) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			int ret = boardDao.deleteBoard(boardId);
			if( ret == 1 ) boardResultDto.setResult("success");
			else boardResultDto.setResult("fail");
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}
		
		return boardResultDto;
	}




}
