package com.testportal.quizService.service.helper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testportal.quizService.dto.QuizDto;
import com.testportal.quizService.entity.Quiz;
import com.testportal.quizService.exception.MissingMandatoryAttribute;
import com.testportal.quizService.exception.ResourceNotFoundException;
import com.testportal.quizService.repository.QuizRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuizServiceHelper {

	@Autowired
	private QuizRepo quizRepo;

	public void validateQuiz(QuizDto quiz, Long quizId) throws MissingMandatoryAttribute, ResourceNotFoundException {
		if (!ValidateMandatoryAttributes(quiz)) {
			log.info("mandatory fields are missing in dto: {}", quiz.toString());
			throw new MissingMandatoryAttribute();
		}
		if (quizId != null) {
			Optional<Quiz> maybeQuiz = quizRepo.findById(quizId);
			if (maybeQuiz.isEmpty()) {
				log.warn("no quiz found for id: {}", quizId);
				throw new ResourceNotFoundException("no quiz found for id: " + quizId);
			}
			quiz.setQid(maybeQuiz.get().getQid());
		}
	}

	private boolean ValidateMandatoryAttributes(QuizDto quiz) {
		if (quiz.getTitle() == null || quiz.getTitle().trim().length() == 0 || quiz.getAbout() == null || quiz.getAbout().trim().length() == 0) {
			return false;
		}
		if (quiz.getNo_of_questions() == null || quiz.getNo_of_questions().trim().length() == 0 || quiz.getMax_marks() == null || quiz.getMax_marks().trim().length() == 0) {
			return false;
		}
		if (quiz.getCategory() == null || quiz.getCategory().getCid() == null) {
			return false;
		}
		return true;
	}
}
