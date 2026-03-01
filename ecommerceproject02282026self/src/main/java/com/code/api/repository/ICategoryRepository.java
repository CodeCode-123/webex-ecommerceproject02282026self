package com.code.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.code.api.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
	Optional<Category> findByCategoryName(String categoryName);
	List<Category> findByCategoryNameLike(String categoryName);
}
