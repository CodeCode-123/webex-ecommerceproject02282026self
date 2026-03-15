package com.code.api.dto;

import com.code.api.entity.Item;
import com.code.api.entity.ItemOrder;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsDTO {
	private int itemOrderDetailsId;
	private Item item;
	@Min(value=1, message="Quantity should be at least 1")
	private int qty;
	@Min(value=0, message="Item value should not be negative")
	private double itemValue;
	private ItemOrder itemOrder;
}
