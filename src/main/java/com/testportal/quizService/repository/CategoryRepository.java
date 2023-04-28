package com.testportal.quizService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testportal.quizService.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
