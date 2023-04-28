package com.testportal.quizService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.testportal.quizService.dto.QuestionDto;
import com.testportal.quizService.exception.ResourceNotFoundException;
import com.testportal.quizService.service.impl.QuestionService;

@RestController
@RequestMapping("/v1/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	/*
	 * only allowed for ADMIN & Super ADMIN
	 */
	@PostMapping("/")
	public ResponseEntity<QuestionDto> addQuestion(@RequestBody QuestionDto question) {
		return ResponseEntity.ok(questionService.addQuestion(question));
	}

	/*
	 * only allowed for ADMIN & Super ADMIN
	 */
	@PutMapping("/")
	public ResponseEntity<QuestionDto> updateQuestion(@RequestBody QuestionDto question, @RequestParam(required = true, name = "questionId") Long questionId) {
		try {
			return ResponseEntity.ok(questionService.updateQuestion(question, questionId));
		} catch (ResourceNotFoundException e) {
			return null;
		}
	}

	/*
	 * only allowed for ADMIN & Super ADMIN
	 */
	@GetMapping("/")
	public ResponseEntity<List<QuestionDto>> getAllQuestion() {
		return ResponseEntity.ok(questionService.getAllQuestion());
	}

	@GetMapping("/{quesId}")
	public ResponseEntity<QuestionDto> getQuestionById(@PathVariable("quesId") Long quesId) {
		try {
			return new ResponseEntity<QuestionDto>(questionService.getQuestion(quesId), HttpStatus.FOUND);
		} catch (ResourceNotFoundException e) {
			return null;
		}
	}

	@GetMapping("/quiz/{qid}")
	public ResponseEntity<List<QuestionDto>> getAllQuestionOfQuiz(@PathVariable("qid") Long qid) {

		return ResponseEntity.ok(questionService.getAllQuestionOfQuiz(qid));
	}
//
//	@GetMapping("/quiz/admin-all/{qid}")
//	public ResponseEntity<?> getAllQuestionOfQuiz4Admin(@PathVariable("qid") Long qid) {
//
//		String methodname = "getAllQuestionOfQuiz4Admin()";
//		log.info(methodname + " called");
//
//		// scenario : if inside quiz we have questions > no_of_questions, then we have to show only max question no
//		Quiz q = questionService.getQuiz(qid);
//		Set<Questions> totalQues = q.getQuest();
//		// due to memory constraint we are converting set into list + we don't want to use contains() method frequently
//		List<Questions> toQs = new ArrayList<Questions>(totalQues);
//
//		// Collections.shuffle(toQs);
//
//		return ResponseEntity.ok(toQs);
//	}

	@DeleteMapping("/{quesId}")
	public void DeleteQuestionsById(@PathVariable("quesId") Long quesId) {
		questionService.deleteQuestion(quesId);
	}

}
