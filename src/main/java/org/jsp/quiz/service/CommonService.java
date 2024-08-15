package org.jsp.quiz.service;

import java.util.Optional;

import org.jsp.quiz.entity.Candidate;
import org.jsp.quiz.helper.ApplicationContant;
import org.jsp.quiz.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	public String saveCandidate(Candidate candidate) {
		Optional<Candidate> existing = candidateRepository.findByEmailAndDob(candidate.getEmail(), candidate.getDob());
		if(existing.isPresent()) {
			return ApplicationContant.USER_ALREADY_EXIST;
		}
		candidateRepository.save(candidate);
		return ApplicationContant.SUCCESS;
	}
	
	public Candidate retriveCandidate(String email, String dob) {
		Optional<Candidate> existing = candidateRepository.findByEmailAndDob(email, dob);
		if(existing.isPresent()) {
			return existing.get();
		}
		return null;
	}

}
