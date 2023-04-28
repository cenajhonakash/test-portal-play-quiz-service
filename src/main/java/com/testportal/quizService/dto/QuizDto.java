package com.testportal.quizService.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuizDto {

	private Long qid;
	private boolean active;
	private String title;
	private String about;
	private String max_marks;
	private String no_of_questions;
	private Set<QuestionDto> questions;
	private CategoryDto category;
}
