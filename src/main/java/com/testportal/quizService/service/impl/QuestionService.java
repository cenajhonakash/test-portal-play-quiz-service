package com.testportal.quizService.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.testportal.quizService.dto.QuestionDto;
import com.testportal.quizService.entity.Question;
import com.testportal.quizService.entity.Quiz;
import com.testportal.quizService.exception.MissingMandatoryAttribute;
import com.testportal.quizService.exception.ResourceNotFoundException;
import com.testportal.quizService.repository.QuestionRepo;
import com.testportal.quizService.service.helper.QuestionServiceHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuestionService {

	@Autowired
	private QuestionRepo questionRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private QuestionServiceHelper helper;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public QuestionDto addQuestion(QuestionDto question) {
		try {
			helper.validateQuestion(question, null);
		} catch (MissingMandatoryAttribute | ResourceNotFoundException e) {
			return null;
		}
		Question savedEntity = questionRepo.save(mapper.map(question, Question.class));
		return mapper.map(savedEntity, QuestionDto.class);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public QuestionDto updateQuestion(QuestionDto question, Long questionId) throws ResourceNotFoundException {
		try {
			helper.validateQuestion(question, questionId);
		} catch (MissingMandatoryAttribute | ResourceNotFoundException e) {
			return null;
		}
		Optional<Question> maybeQues = questionRepo.findById(questionId);
		if (maybeQues.isEmpty()) {
			log.error("question not found by id: {}, can't update!", questionId);
			throw new ResourceNotFoundException("question not found by id: " + questionId);
		}
		Question toUpdate = mapper.map(question, Question.class);
		toUpdate.setQuesId(questionId);
		return mapper.map(questionRepo.save(toUpdate), QuestionDto.class);
	}

	public List<QuestionDto> getAllQuestion() {
		List<Question> questionList = questionRepo.findAll();
		return questionList.stream().map(quest -> mapper.map(quest, QuestionDto.class)).collect(Collectors.toList());
	}

	public QuestionDto getQuestion(Long quesId) throws ResourceNotFoundException {
		Optional<Question> maybeQues = questionRepo.findById(quesId);
		if (maybeQues.isEmpty()) {
			log.error("question not found by id: {}", quesId);
			throw new ResourceNotFoundException("quiz not found by id: " + quesId);
		}
		return mapper.map(maybeQues.get(), QuestionDto.class);
	}

	public List<QuestionDto> getAllQuestionOfQuiz(Long quizId) {
		// scenario : if inside quiz we have questions > no_of_questions, then we have to show only max question no
		Quiz quiz = null;
		try {
			quiz = helper.getQuizByQuizId(quizId);
		} catch (ResourceNotFoundException e) {
			return null;
		}
		Set<Question> totalQues = quiz.getQuestions();
		// due to memory constraint we are converting set into list + we don't want to use contains() method frequently
		List<Question> totalQuestionsInQuiz = new ArrayList<Question>(totalQues);
		if (totalQuestionsInQuiz.size() > Integer.parseInt(quiz.getNo_of_questions())) {
			totalQuestionsInQuiz = totalQuestionsInQuiz.subList(0, Integer.parseInt(quiz.getNo_of_questions()) + 1);
		}

		Collections.shuffle(totalQuestionsInQuiz);

		return totalQuestionsInQuiz.stream().map(quest -> mapper.map(quest, QuestionDto.class)).collect(Collectors.toList());
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public void deleteQuestion(Long quesId) {
		questionRepo.deleteById(quesId);
	}

}
