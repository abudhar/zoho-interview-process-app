package org.jsp.quiz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Scoring {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Candidate candidate;

	private String section;

	private double score;

	public Scoring(Candidate candidate, String section, double score) {
		super();
		this.candidate = candidate;
		this.section = section;
		this.score = score;
	}

}
