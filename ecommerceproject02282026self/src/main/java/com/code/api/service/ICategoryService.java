package com.code.api.service;

import java.util.List;
import java.util.Optional;

import com.code.api.entity.Category;

public interface ICategoryService {
	// declare the method that we want for the student
	public Category add(Category category);

	public Category update(Category category);

	public void delete(Category category);

	public void deleteById(int id);

	// create some method to get the student
	public List<Category> getAllCategories();

	public Optional<Category> getById(int id);

	public Optional<Category> getCategoryByName(String catname);

	public List<Category> search(String catname);
}
