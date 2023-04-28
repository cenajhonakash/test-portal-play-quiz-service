package com.testportal.quizService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testportal.quizService.dto.QuizDto;
import com.testportal.quizService.exception.ResourceNotFoundException;
import com.testportal.quizService.service.impl.QuizService;

@RestController
@RequestMapping("/v1/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@PostMapping("/add")
	public ResponseEntity<QuizDto> addQuiz(@RequestBody QuizDto quizDto) {
		return ResponseEntity.ok(this.quizService.addQuiz(quizDto));
	}

	@PutMapping("/update")
	public ResponseEntity<QuizDto> updateQuiz(@RequestBody QuizDto quizDto, @RequestParam(required = true, name = "quizId") Long quizId) {
		return ResponseEntity.ok(this.quizService.updateQuiz(quizDto, quizId));
	}

	@GetMapping("/fetch")
	public ResponseEntity<List<QuizDto>> getAllQuiz() {
		return ResponseEntity.ok(this.quizService.getAllQuiz());
	}

	@GetMapping("/")
	public ResponseEntity<QuizDto> getQuiz(@RequestParam(required = true, name = "quizId") Long quizId) {
		try {
			return ResponseEntity.ok(this.quizService.getQuiz(quizId));
		} catch (ResourceNotFoundException e) {
			return null;
		}
	}

	@DeleteMapping("/delete/{qid}")
	public void deleteQuiz(@PathVariable("qid") Long quizId) {
		this.quizService.deleteQuiz(quizId);
	}

	@GetMapping("/search/{query}")
	public ResponseEntity<List<QuizDto>> searchQuiz(@PathVariable("query") String query) {
		List<QuizDto> list = this.quizService.findByNameContainingAndQuiz(query);
		return ResponseEntity.ok(list);
	}
}
