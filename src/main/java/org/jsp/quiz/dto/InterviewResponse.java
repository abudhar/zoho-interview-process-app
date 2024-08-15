package org.jsp.quiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewResponse {
	private String questionId;
	private String answer;
	private String isCorrect;
	private String candidateId;
}
