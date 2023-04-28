package com.testportal.quizService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testportal.quizService.entity.Quiz;

public interface QuizRepo extends JpaRepository<Quiz, Long> {

}
