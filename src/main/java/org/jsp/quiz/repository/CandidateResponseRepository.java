package org.jsp.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.jsp.quiz.entity.CandidateResponse;
import java.util.List;
import org.jsp.quiz.entity.Candidate;
import org.jsp.quiz.entity.Question;



@Repository
public interface CandidateResponseRepository extends JpaRepository<CandidateResponse, Long> {
	
	public List<CandidateResponse> findByCandidate(Candidate candidate);
	
	long countByCandidate(Candidate candidate);
	
	long countByQuestion(Question question);
	
}
