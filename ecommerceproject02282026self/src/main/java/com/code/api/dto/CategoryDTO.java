package com.code.api.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
	@Size(min=1, message="Category name should be at least 1 character")
	String categoryName;
	String categoryDesc;
}
