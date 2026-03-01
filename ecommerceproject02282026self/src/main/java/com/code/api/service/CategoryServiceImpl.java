package com.code.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.repository.*;
import com.code.api.entity.Category;
//Add transaction and Service
@Service
@Transactional //will create the bean for the session.beginTranction ----- session.commit
public class CategoryServiceImpl implements ICategoryService {
	//Inject CategoryDAO
	@Autowired
	ICategoryRepository categoryRepository;
	@Override
	public Category add(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category update(Category category) {
		// TODO Auto-generated method stub
	return categoryRepository.save(category);
	}

	@Override
	public String delete(Category category) {
		// TODO Auto-generated method stub
	    categoryRepository.delete(category);
	    return "Record is deleted successfully";
	}

	@Override
	public String delete(int id) {
		// TODO Auto-generated method stub
		// Get the user with the id
		Optional<Category> categoryOptional = categoryRepository.findById(id);
		if (categoryOptional.isPresent()) {
			// get the object and delete it
			categoryRepository.delete(categoryOptional.get());
			return "Record is deleted successfully";
		}
		return "Category with Id " + id + " not found";
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category getById(int id) {
		// TODO Auto-generated method stub
	    Optional<Category> categoryOptional = categoryRepository.findById(id);
	    Category category = null;
	    // check if the category is present or not 
	    if (categoryOptional.isPresent()) {
	    	// get the category object and return;
	    	category = categoryOptional.get();
	    } 
	    return category;
	}

	@Override
	public Category getCategoryByName(String catname)
	{
		// TODO Auto-generated method stub
		// check if the category object is present or not
		Optional<Category> categoryOptional = categoryRepository.findByCategoryName(catname);
		Category category = null;
		// check if the category object is present or not
		if (categoryOptional.isPresent()) {
			// get the category object and return
			category = categoryOptional.get();
		}
		return category;
	}

	@Override
	public List<Category> search(String catname) {
		// TODO Auto-generated method stub
		// return a List of Category with a category name pattern
		return categoryRepository.findByCategoryNameLike("%" + catname + "%");
	}

}
