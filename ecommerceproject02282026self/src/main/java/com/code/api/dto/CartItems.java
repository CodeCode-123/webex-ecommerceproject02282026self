package com.code.api.dto;

import com.code.api.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItems {
	private Integer itemId;
	private String itemName;
	private int itemPrice;
	private String fileName;
	private int qty;
	private Category category;
}
