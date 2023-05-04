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
//board ��¤� �Ӽ��̸����� model ��ü�� ������ ����� ���ǿ��� ���ÿ� �����.
@SessionAttributes("board")
public class Boardcontroller {
	@RequestMapping(value = "/insertBoard.do")
	public String insertBoard(BoardVO vo, BoardDAO boardDAO) {
		System.out.println("�Խñ� ��� ó��");

		boardDAO.insertBoard(vo);

		return "redirect:getBoardList.do";
	}

	@RequestMapping(value = "/updateBoard.do")
	public String updateBoard(@ModelAttribute("board")BoardVO vo,BoardDAO boardDAO) {

		System.out.println("�Խñ� ���� ó��");

		boardDAO.updateBoard(vo);

		return "getBoardList.do";
	}

	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(BoardDAO boardDAO, BoardVO vo) {

		System.out.println("�Խñ� ���� ó��");

		boardDAO.deleteBoard(vo);

		return "getBoardList.do";
	}

	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		
		Map<String, String> conditionMap = new HashMap<>();
		
		
		//conditionMap.put("�ۼ���", "WRITER");
		conditionMap.put("����", "TITLE");
		conditionMap.put("����", "CONDITION");
		
		return conditionMap;

	}

	@RequestMapping(value = "/getBoardList.do")
	public String getBoardList(
			@RequestParam(value = "searchCondition", defaultValue = "TITLE", required = false) String condition,
			@RequestParam(value = "searchKeyword", defaultValue = "", required = false) String keyword,
			BoardDAO boardDAO, Model model) {

		System.out.println("�Խñ� ��� ��ȸ ó��");
		System.out.println("�˻� ����: " + condition);
		System.out.println("�˻���: " + keyword);

		List<BoardVO> boardList = boardDAO.getBoardList();

		// DB���� ��ȸ�� ������ ȭ�鿡 �����ϱ� ���� ���尴ü�� ����
		model.addAttribute("boardList", boardList);

		return "getBoardList.jsp";
	}

	/*
	 * �Է��Ķ����: model model - jsp ȭ������ ������ �����͸� ����
	 * 
	 */
	@RequestMapping(value = "/getBoard.do")
	public String getBoard(BoardDAO boardDAO, BoardVO vo, Model model) {

		System.out.println("�Խñ� �� ��ȸ");
		// �Խñ� ��ȣ �Է°� ����

		BoardDAO boardDao = new BoardDAO();
		BoardVO board = boardDao.getBoard(vo);

		// ���� ȭ�� ����
		model.addAttribute("board", board);

		return "getBoard.jsp";
	}
}
