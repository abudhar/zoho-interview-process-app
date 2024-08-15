package org.jsp.quiz.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.jsp.quiz.controller.CommonController;
import org.jsp.quiz.dto.InterviewResponse;
import org.jsp.quiz.entity.Candidate;
import org.jsp.quiz.entity.CandidateResponse;
import org.jsp.quiz.entity.Question;
import org.jsp.quiz.entity.Scoring;
import org.jsp.quiz.repository.CandidateRepository;
import org.jsp.quiz.repository.CandidateResponseRepository;
import org.jsp.quiz.repository.QuestionRepository;
import org.jsp.quiz.repository.ScoringRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
	
	@Autowired
	private QuestionsService questionService;

	@Autowired
	private CandidateRepository candidateRepo;
	
	@Autowired
	private QuestionRepository questionRepo;
	
	@Autowired
	private CandidateResponseRepository candidateRespRepo;
	
	@Autowired
	private ScoringRepository scoringRepository;

	private final Logger logger = LoggerFactory.getLogger(CommonController.class);

	
	public void processCandidateResponse(InterviewResponse response) {
		CandidateResponse candidateResponse = new CandidateResponse();
		Optional<Candidate> candidate = candidateRepo.findById(Long.valueOf(response.getCandidateId()));
		Optional<Question> question = questionRepo.findById(Long.valueOf(response.getQuestionId()));
		candidateResponse.setCandidate(candidate.get());
		candidateResponse.setQuestion(question.get());
		candidateResponse.setCorrect(question.get().getCorrectOption().equals(response.getAnswer()));
		candidateResponse.setSelectedOption(response.getAnswer());
		candidateRespRepo.save(candidateResponse);
	}
	

    public Question nextQuestion(Candidate candidate) {
        List<CandidateResponse> responses = candidateRespRepo.findByCandidate(candidate);
        long totalLogicalResponseCount = responses.stream().filter(response -> "logical_reasoning".equals(response.getQuestion().getSection())).count();
        long totalPsychometricEvaluationCount = responses.stream().filter(response -> "psychometric_evaluation".equals(response.getQuestion().getSection())).count();
        CandidateResponse latestResponse = getLatestResponse(responses);

        long logicalReasoningCount = countResponsesForSection(responses, "logical_reasoning", getDifficulty(latestResponse));
        long psychometricEvaluationCount = countResponsesForSection(responses, "psychometric_evaluation", getDifficulty(latestResponse));
        
        logger.info("logicalReasoningCount => {}", logicalReasoningCount);
        logger.info("psychometricEvaluationCount => {}", logicalReasoningCount);
        
        String logicalReasoningDifficulty = determineNextDifficulty(logicalReasoningCount, getDifficulty(latestResponse));
        String psychometricEvaluationDifficulty = determineNextDifficulty(psychometricEvaluationCount, totalPsychometricEvaluationCount == 0 ? "easy" : getDifficulty(latestResponse));

        logger.info("logicalReasoningDifficulty => {}", logicalReasoningDifficulty);
        logger.info("psychometricEvaluationDifficulty => {}", psychometricEvaluationDifficulty);

        if (totalLogicalResponseCount < 10) {
        	logger.info("Genearting RandomQuestions for logical_reasoning ...");
            return questionService.getRandomQuestions("logical_reasoning", logicalReasoningDifficulty);
        } 
        logger.info("Genearting RandomQuestions for psychometric_evaluation ...");
        return questionService.getRandomQuestions("psychometric_evaluation", psychometricEvaluationDifficulty);
    }

    private CandidateResponse getLatestResponse(List<CandidateResponse> responses) {
        return responses.stream()
                .max(Comparator.comparing(CandidateResponse::getId))
                .orElseThrow(() -> new RuntimeException("No responses found for candidate"));
    }

    private long countResponsesForSection(List<CandidateResponse> responses, String section, String difficulty) {
        return responses.stream()
                .filter(response -> section.equals(response.getQuestion().getSection()))
                .filter(response -> difficulty.equals(response.getQuestion().getDifficulty()))
                .filter(CandidateResponse::isCorrect)
                .count();
    }

    private String getDifficulty(CandidateResponse response) {
        return response.getQuestion().getDifficulty();
    }

    public static String determineNextDifficulty(long count, String currentDifficulty) {
        if (count >= 2) {
            switch (currentDifficulty) {
                case "easy":
                    return "moderate";
                case "moderate":
                    return "hard";
                default:
                    return currentDifficulty;
            }
        }
        return currentDifficulty;
    }

    public double calculateAndUpdateScore(Candidate candidate) {
        List<CandidateResponse> responses = candidateRespRepo.findByCandidate(candidate);

        double logicalScore = calculateSectionScore(responses, "logical_reasoning");
        double psychometricScore = calculateSectionScore(responses, "psychometric_evaluation");

        scoringRepository.save(new Scoring(candidate, "logical_reasoning", logicalScore));
        scoringRepository.save(new Scoring(candidate, "psychometric_evaluation", psychometricScore));

        return logicalScore + psychometricScore;
    }

    private double calculateSectionScore(List<CandidateResponse> responses, String section) {
        return responses.stream()
                .filter(response -> section.equals(response.getQuestion().getSection()))
                .mapToDouble(response -> Double.parseDouble(response.getQuestion().getScore()))
                .sum();
    }

    public long getAnsweredQuestionCount(Candidate candidate) {
        return candidateRespRepo.countByCandidate(candidate);
    }
    
    public double calclulateAndUpdateScore(Candidate candidate) {
		List<CandidateResponse> candidateScores = candidateRespRepo.findByCandidate(candidate);
		double logicalScore = candidateScores
				.stream()
				.filter(candidateScore -> candidateScore.getQuestion().getSection().equals("logical_reasoning"))
				.mapToDouble(value -> Double.valueOf(value.getQuestion().getScore())).sum();
		scoringRepository.save(new Scoring(candidate, "logical_reasoning", logicalScore));

		double psychometricScore = candidateScores
				.stream()
				.filter(candidateScore -> candidateScore.getQuestion().getSection().equals("psychometric_evaluation"))
				.mapToDouble(value -> Double.valueOf(value.getQuestion().getScore())).sum();
		scoringRepository.save(new Scoring(candidate, "psychometric_evaluation", psychometricScore));
		candidate.setScore(""+ (logicalScore + psychometricScore));
		candidateRepo.save(candidate);
		return logicalScore + psychometricScore;
	}


}
