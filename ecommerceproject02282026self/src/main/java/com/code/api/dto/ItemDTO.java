package com.code.api.dto;

import com.code.api.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
	private String itemName;
	private int itemPrice;
	private byte[] imageData;
	private Category category;
}
