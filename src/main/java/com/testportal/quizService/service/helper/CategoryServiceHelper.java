package com.testportal.quizService.service.helper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testportal.quizService.dto.CategoryDto;
import com.testportal.quizService.entity.Category;
import com.testportal.quizService.exception.MissingMandatoryAttribute;
import com.testportal.quizService.exception.ResourceNotFoundException;
import com.testportal.quizService.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceHelper {
	@Autowired
	private CategoryRepository categoryRepository;

	public void validateCategory(CategoryDto dto, Long categoryId) throws ResourceNotFoundException, MissingMandatoryAttribute {
		if (!validateMandatoryFields(dto)) {
			log.info("mandatory fields are missing in dto: {}", dto.toString());
			throw new MissingMandatoryAttribute();
		}
		if (categoryId != null) {
			Optional<Category> maybeCategory = categoryRepository.findById(categoryId);
			if (maybeCategory.isEmpty()) {
				log.warn("Resource not found with id: {}", categoryId);
				throw new ResourceNotFoundException("Resource not found with id: " + categoryId);
			}
			dto.setCid(maybeCategory.get().getCid());
		}
	}

	private boolean validateMandatoryFields(CategoryDto dto) {
		if (dto.getTitle() == null || dto.getTitle().trim().length() == 0 || dto.getAbout() == null || dto.getAbout().trim().length() == 0) {
			return false;
		}
		return true;
	}

}
