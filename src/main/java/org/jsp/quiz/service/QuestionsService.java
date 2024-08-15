package org.jsp.quiz.service;

import org.jsp.quiz.entity.Question;
import org.jsp.quiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionsService {
	
	@Autowired
	private QuestionRepository questions;

	public Question getRandomQuestions(String section, String difficulty){
		return questions.findBySectionAndDifficulty(section, difficulty);
	}
	
	
	
}
