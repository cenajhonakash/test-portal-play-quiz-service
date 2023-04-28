package com.testportal.quizService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testportal.quizService.entity.Question;

public interface QuestionRepo extends JpaRepository<Question, Long> {

}
