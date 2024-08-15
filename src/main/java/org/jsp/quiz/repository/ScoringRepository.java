package org.jsp.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.jsp.quiz.entity.Scoring;

@Repository
public interface ScoringRepository extends JpaRepository<Scoring, Long> {

}
