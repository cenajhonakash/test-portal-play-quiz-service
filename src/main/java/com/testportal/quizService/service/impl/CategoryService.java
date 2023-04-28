package com.testportal.quizService.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.testportal.quizService.dto.CategoryDto;
import com.testportal.quizService.entity.Category;
import com.testportal.quizService.exception.MissingMandatoryAttribute;
import com.testportal.quizService.exception.ResourceNotFoundException;
import com.testportal.quizService.repository.CategoryRepository;
import com.testportal.quizService.service.helper.CategoryServiceHelper;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryServiceHelper categoryServiceHelper;
	@Autowired
	private ModelMapper mapper;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public CategoryDto addCategory(CategoryDto dto) {
		try {
			categoryServiceHelper.validateCategory(dto, null);
		} catch (ResourceNotFoundException e) {
			return null;
		} catch (MissingMandatoryAttribute e) {
			return null;
		}
		Category toAdd = mapper.map(dto, Category.class);
		return mapper.map(categoryRepository.save(toAdd), CategoryDto.class);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public CategoryDto updateCategory(CategoryDto dto, Long categoryId) {
		try {
			categoryServiceHelper.validateCategory(dto, categoryId);
		} catch (ResourceNotFoundException e) {
			return null;
		} catch (MissingMandatoryAttribute e) {
			return null;
		}
		Category toUpdate = mapper.map(dto, Category.class);
		return mapper.map(categoryRepository.save(toUpdate), CategoryDto.class);
	}

	public CategoryDto getCategory(Long cid) throws ResourceNotFoundException {
		Optional<Category> optionalCategory = categoryRepository.findById(cid);
		if (optionalCategory.isEmpty()) {
			throw new ResourceNotFoundException("Category not found with id: " + cid);
		}
		return mapper.map(optionalCategory.get(), CategoryDto.class);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public void deleteCategory(Long cid) {
		categoryRepository.deleteById(cid);
	}

	public Set<CategoryDto> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().map(category -> mapper.map(category, CategoryDto.class)).collect(Collectors.toSet());
	}

}
