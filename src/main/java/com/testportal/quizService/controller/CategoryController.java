package com.testportal.quizService.controller;

import java.util.Set;

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

import com.testportal.quizService.dto.CategoryDto;
import com.testportal.quizService.exception.ResourceNotFoundException;
import com.testportal.quizService.service.impl.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/category")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/*
	 * API only for admin & super Admin
	 */
	@PostMapping("/add")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto dto) {
		String methodname = "addCategory()";
		log.info(methodname + " called");
		return new ResponseEntity<CategoryDto>(categoryService.addCategory(dto), HttpStatus.CREATED);
	}

	@GetMapping("/{cid}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("cid") Long cid) {

		String methodname = "getCategory()";
		log.info(methodname + " called");

		try {
			return new ResponseEntity<CategoryDto>(categoryService.getCategory(cid), HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			log.info(e.getMessage());
		}
		return null;
	}

	@GetMapping("/fetch")
	public ResponseEntity<Set<CategoryDto>> getAllCategory() {

		String methodname = "getAllCategory()";
		log.info(methodname + " called");

		return new ResponseEntity<Set<CategoryDto>>(categoryService.getAllCategories(), HttpStatus.OK);
	}

	/*
	 * API only for admin & super Admin
	 */
	@PutMapping("/update")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto c, @RequestParam(required = true, name = "categoryId") Long categoryId) {

		String methodname = "updateCategory()";
		log.info(methodname + " called");

		return new ResponseEntity<CategoryDto>(categoryService.updateCategory(c, categoryId), HttpStatus.OK);
	}

	/*
	 * API only for admin & super Admin
	 */
	@DeleteMapping("/{cid}")
	public void deleteCategory(@PathVariable("cid") Long cid) {

		String methodname = "deleteCategory()";
		log.info(methodname + " called");

		this.categoryService.deleteCategory(cid);
	}
}
