package org.jsp.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.jsp.quiz.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
	Optional<Candidate> findByEmailAndDob(String email, String dob);
}
