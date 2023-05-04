package com.ezen.view.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ezen.biz.dao.BoardDAO;
import com.ezen.biz.dto.BoardVO;

@Controller
//board 라는ㄴ 속성이름으로 model 객체에 데이터 저장시 세션에도 동시에 저장됨.
@SessionAttributes("board")
public class Boardcontroller {
	@RequestMapping(value = "/insertBoard.do")
	public String insertBoard(BoardVO vo, BoardDAO boardDAO) {
		System.out.println("게시글 등록 처리");

		boardDAO.insertBoard(vo);

		return "redirect:getBoardList.do";
	}

	@RequestMapping(value = "/updateBoard.do")
	public String updateBoard(@ModelAttribute("board")BoardVO vo,BoardDAO boardDAO) {

		System.out.println("게시글 수정 처리");

		boardDAO.updateBoard(vo);

		return "getBoardList.do";
	}

	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(BoardDAO boardDAO, BoardVO vo) {

		System.out.println("게시글 삭제 처리");

		boardDAO.deleteBoard(vo);

		return "getBoardList.do";
	}

	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		
		Map<String, String> conditionMap = new HashMap<>();
		
		
		//conditionMap.put("작성자", "WRITER");
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONDITION");
		
		return conditionMap;

	}

	@RequestMapping(value = "/getBoardList.do")
	public String getBoardList(
			@RequestParam(value = "searchCondition", defaultValue = "TITLE", required = false) String condition,
			@RequestParam(value = "searchKeyword", defaultValue = "", required = false) String keyword,
			BoardDAO boardDAO, Model model) {

		System.out.println("게시글 목록 조회 처리");
		System.out.println("검색 조건: " + condition);
		System.out.println("검색어: " + keyword);

		List<BoardVO> boardList = boardDAO.getBoardList();

		// DB에서 조회한 내용을 화면에 전달하기 위해 내장객체에 저장
		model.addAttribute("boardList", boardList);

		return "getBoardList.jsp";
	}

	/*
	 * 입력파라미터: model model - jsp 화면으로 전달할 데이터를 저장
	 * 
	 */
	@RequestMapping(value = "/getBoard.do")
	public String getBoard(BoardDAO boardDAO, BoardVO vo, Model model) {

		System.out.println("게시글 상세 조회");
		// 게시글 번호 입력값 추출

		BoardDAO boardDao = new BoardDAO();
		BoardVO board = boardDao.getBoard(vo);

		// 응답 화면 구성
		model.addAttribute("board", board);

		return "getBoard.jsp";
	}
}
