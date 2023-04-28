package com.testportal.quizService.service.helper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testportal.quizService.dto.QuestionDto;
import com.testportal.quizService.entity.Question;
import com.testportal.quizService.entity.Quiz;
import com.testportal.quizService.exception.MissingMandatoryAttribute;
import com.testportal.quizService.exception.ResourceNotFoundException;
import com.testportal.quizService.repository.QuestionRepo;
import com.testportal.quizService.repository.QuizRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuestionServiceHelper {

	@Autowired
	private QuizRepo quizRepo;
	@Autowired
	private QuestionRepo questionRepo;

	public Quiz getQuizByQuizId(Long quizId) throws ResourceNotFoundException {
		Optional<Quiz> maybeQuiz = quizRepo.findById(quizId);
		if (maybeQuiz.isEmpty()) {
			log.error("quiz not found by id: {}", quizId);
			throw new ResourceNotFoundException("quiz not found by id: " + quizId);
		}
		return maybeQuiz.get();
	}

	public void validateQuestion(QuestionDto question, Long questionId) throws MissingMandatoryAttribute, ResourceNotFoundException {
		if (!validateMandatoryAttributes(question)) {
			log.info("mandatory fields are missing in dto: {}", question.toString());
			throw new MissingMandatoryAttribute();
		}
		if (questionId != null) {
			Optional<Question> maybeQues = questionRepo.findById(questionId);
			if (maybeQues.isEmpty()) {
				log.error("question not found by id: {}", questionId);
				throw new ResourceNotFoundException("quiz not found by id: " + questionId);
			}
		}
	}

	private boolean validateMandatoryAttributes(QuestionDto question) {
		if (question.getContent() == null || question.getContent().trim().length() == 0 || question.getAnswer() == null || question.getAnswer().trim().length() == 0) {
			return false;
		}
		if (question.getOption1() == null || question.getOption1().trim().length() == 0 || question.getOption2() == null || question.getOption2().trim().length() == 0) {
			return false;
		}
		if (question.getOption3() == null || question.getOption3().trim().length() == 0 || question.getOption4() == null || question.getOption4().trim().length() == 0) {
			return false;
		}
		if (question.getQuiz() == null || question.getQuiz().getQid() == null) {
			return false;
		}
		return false;
	}

}
