package org.jsp.quiz.controller;

import org.jsp.quiz.dto.InterviewResponse;
import org.jsp.quiz.entity.Candidate;
import org.jsp.quiz.entity.Question;
import org.jsp.quiz.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/quiz")
@Controller
public class QuizController {

	private final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private QuizService service;

	
	@PostMapping("/next-quiz")
	public String quiz(@ModelAttribute InterviewResponse response,  HttpSession session, Model model) {
		Candidate candidate = null;
		Object attribute = session.getAttribute("candidate");
		if(attribute instanceof Candidate) {
			candidate = (Candidate) attribute;
		} else {
			return "index";
		}
		response.setCandidateId(""+candidate.getId());
		service.processCandidateResponse(response);
		long questionsAnswered = service.getAnsweredQuestionCount(candidate);
		if(questionsAnswered  == 20) {
			model.addAttribute("totalScore", service.calclulateAndUpdateScore(candidate));
			return "success";
		}
		
		Question nextQuestion = service.nextQuestion(candidate);
		model.addAttribute("question", nextQuestion);
		return "quiz";
	}


}
