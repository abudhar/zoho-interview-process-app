package org.jsp.quiz.repository;

import org.jsp.quiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	@Query(value = "SELECT * FROM question A WHERE A.section = :section AND A.difficulty = :difficulty ORDER BY RAND() LIMIT 1", nativeQuery = true)
	Question findBySectionAndDifficulty(String section, String difficulty);
}
