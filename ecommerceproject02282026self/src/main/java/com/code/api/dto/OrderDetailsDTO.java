package com.code.api.dto;

import com.code.api.entity.Item;
import com.code.api.entity.ItemOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsDTO {
	private Item item;
	private int qty;
	private double itemValue;
	private ItemOrder itemOrder;
	
	public void setItemValue() {
		this.itemValue = qty * item.getItemPrice();
	}
	public double getItemValue() {
		return qty * item.getItemPrice();
	}
}
