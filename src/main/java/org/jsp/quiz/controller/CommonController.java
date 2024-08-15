package org.jsp.quiz.controller;

import org.jsp.quiz.dto.Login;
import org.jsp.quiz.entity.Candidate;
import org.jsp.quiz.entity.Question;
import org.jsp.quiz.helper.ApplicationContant;
import org.jsp.quiz.service.CommonService;
import org.jsp.quiz.service.QuestionsService;
import org.jsp.quiz.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class CommonController {
	
	private final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private QuestionsService questionService;
	
	@Autowired
	private QuizService quizService;

	@RequestMapping(path = {"/", "/login"}, method = RequestMethod.GET)
	public String loadHome() {
		return "index";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, ModelMap map) {
		session.invalidate();
		map.put("pass", "Logout Success");
		return "index";

	}	
	
	@PostMapping("/start-quiz")
	public String quiz(@ModelAttribute Candidate candidate, Model model, HttpSession session) {
		logger.info("Logged In User : {}", candidate.getName());
		String response = commonService.saveCandidate(candidate);
		if(response.equals(ApplicationContant.USER_ALREADY_EXIST)) {
			model.addAttribute("error", response);
			return "register";
		}
		model.addAttribute("name", candidate.getName());
		
		Question randomQuestion = questionService.getRandomQuestions("logical_reasoning", "easy");
		model.addAttribute("question", randomQuestion);
		session.setAttribute("candidate", candidate);
		return "quiz";
	}
	
	@PostMapping("/view-Result")
	public String postMethodName(@ModelAttribute Login candidate, Model model, HttpSession session) {
		Candidate retrivedCandidate = commonService.retriveCandidate(candidate.getEmail(), candidate.getDob()); 
		if(retrivedCandidate == null) {
			model.addAttribute("error", "No User Exist!");
			return "register";
		}
		model.addAttribute("totalScore", quizService.calclulateAndUpdateScore(retrivedCandidate));
		session.setAttribute("candidate", retrivedCandidate);
		return "success";
	}
	
	
	
	
	

}
