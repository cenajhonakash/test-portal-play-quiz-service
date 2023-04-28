package com.testportal.quizService.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long quesId;

	@Column(length = 3000, nullable = false)
	private String content;

	private String image;

	@Column(nullable = false)
	private String option1;
	@Column(nullable = false)
	private String option2;
	@Column(nullable = false)
	private String option3;
	@Column(nullable = false)
	private String option4;
	@Column(nullable = false)
	private String answer;

	@ManyToOne(fetch = FetchType.EAGER)
	private Quiz quiz;
}
