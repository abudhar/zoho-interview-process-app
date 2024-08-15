package org.jsp.quiz.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CandidateResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Candidate candidate;

	@ManyToOne
	private Question question;

	@Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
	
	private String selectedOption;
	private boolean isCorrect;
	
	@PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
