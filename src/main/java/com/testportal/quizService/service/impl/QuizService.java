package com.testportal.quizService.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.testportal.quizService.dto.QuizDto;
import com.testportal.quizService.entity.Quiz;
import com.testportal.quizService.exception.MissingMandatoryAttribute;
import com.testportal.quizService.exception.ResourceNotFoundException;
import com.testportal.quizService.repository.QuizRepo;
import com.testportal.quizService.service.helper.QuizServiceHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuizService {

	@Autowired
	private QuizRepo quizRepo;
	@Autowired
	private QuizServiceHelper helper;
	@Autowired
	private ModelMapper mapper;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public QuizDto addQuiz(QuizDto quizDto) {
		try {
			helper.validateQuiz(quizDto, null);
		} catch (MissingMandatoryAttribute e) {
			return null;
		} catch (ResourceNotFoundException e) {
			return null;
		}
		return mapper.map(quizRepo.save(mapper.map(quizDto, Quiz.class)), QuizDto.class);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public QuizDto updateQuiz(QuizDto quizDto, Long quizId) {
		try {
			helper.validateQuiz(quizDto, quizId);
		} catch (MissingMandatoryAttribute e) {
			return null;
		} catch (ResourceNotFoundException e) {
			return null;
		}
		return mapper.map(quizRepo.save(mapper.map(quizDto, Quiz.class)), QuizDto.class);
	}

	public List<QuizDto> getAllQuiz() {
		List<Quiz> allQuiz = quizRepo.findAll();
		return allQuiz.stream().map(quiz -> mapper.map(quiz, QuizDto.class)).collect(Collectors.toList());
	}

	public QuizDto getQuiz(Long quizId) throws ResourceNotFoundException {
		Optional<Quiz> maybeQuiz = quizRepo.findById(quizId);
		if (maybeQuiz.isEmpty()) {
			log.warn("no quiz found for id: {}", quizId);
			throw new ResourceNotFoundException("no quiz found for id: " + quizId);
		}
		return mapper.map(maybeQuiz.get(), QuizDto.class);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public void deleteQuiz(Long quizId) {
		quizRepo.deleteById(quizId);
	}

	public List<QuizDto> findByNameContainingAndQuiz(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
