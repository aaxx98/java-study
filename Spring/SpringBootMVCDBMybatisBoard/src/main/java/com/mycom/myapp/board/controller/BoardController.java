package com.mycom.myapp.board.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;
import com.mycom.myapp.board.dto.BoardResultDto;
import com.mycom.myapp.board.service.BoardService;
import com.mycom.myapp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/boards")
public class BoardController {

	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	// 목록
	// 파라미터에 searchWord 가 포함되어 있으면 검색을 통한 목록, 없으면 전체 목록
	// 프론트가 편해 진다.
	// 전체 목록도 파라미터를 통한 처리 ( limit, offset )
	@GetMapping("/list")
	public BoardResultDto listBoard(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = null;
		
		if( Strings.isEmpty(boardParamDto.getSearchWord()) ){ // isEmpty : null, "", isBlank : null, "", " ", "/t"..
			boardResultDto = boardService.listBoard(boardParamDto); // limit, offset
		}else {
			boardResultDto = boardService.listBoardSearchWord(boardParamDto); // limit, offset, searchWord
		}
		return boardResultDto;
	}
	
	// 상세
	@GetMapping("/detail/{boardId}")
	public BoardResultDto detailBoard(@PathVariable("boardId") Integer boardId, HttpSession session) {
		BoardParamDto boardParamDto = new BoardParamDto();
		
		boardParamDto.setBoardId(boardId);
		int userSeq = ((UserDto) session.getAttribute("userDto")).getUserSeq();
		boardParamDto.setUserSeq(userSeq);
		
		return boardService.detailBoard(boardParamDto);
	}
	
	// 등록
	// parameter boardDto 객체는 title, content 만 자동 처리
	@PostMapping("/insert")
	public BoardResultDto insertBoard(BoardDto boardDto, HttpSession session) {
		int userSeq = ((UserDto) session.getAttribute("userDto")).getUserSeq(); // 현재 로그인하고 게시글을 작성하는 사용자의 userSeq
		boardDto.setUserSeq(userSeq);
		return boardService.insertBoard(boardDto);
	}
	
	// 수정
	// session 에서 userSeq 를 얻어 사용하지 않는 이유는 이미 same user 에 의해 같은 사용자임이 확인되었기 때문에 
	// 수정이 가능하고 이를 신뢰
	@PostMapping("/update")
	public BoardResultDto updateBoard(BoardDto boardDto) {
		return boardService.updateBoard(boardDto);
	}
	
	// 삭제
	@GetMapping("/delete/{boardId}")
	public BoardResultDto deleteBoard(@PathVariable("boardId") Integer boardId) {
		return boardService.deleteBoard(boardId);
	}	
}
