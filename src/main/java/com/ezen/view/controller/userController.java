package com.ezen.view.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ezen.biz.dao.UserDAO;
import com.ezen.biz.dto.UserVO;

@Controller
@SessionAttributes("loginUser")
public class userController {
	
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String loginView(@ModelAttribute("user") UserVO vo) {
		System.out.println("로그인 화면으로 이동");
		
		vo.setId("kto5294");
		vo.setPassword("1234");
		
		return "login.jsp";
	}
	
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(UserDAO userDAO, UserVO vo, HttpSession session, Model model) {
		System.out.println("로그인 처리");

		UserVO user = userDAO.getUser(vo);

		if (user != null) {
			model.addAttribute("loginUser",user);
			session.setAttribute("username", user.getName());
			return "getBoardList.do";
		} 
		else {
			return "login.jsp";
		}
	}
	
	@RequestMapping("/logout.do")
	public String handleRequest() {
		
		System.out.println("로그아웃 처리");

		return "login.do";
	}
}
