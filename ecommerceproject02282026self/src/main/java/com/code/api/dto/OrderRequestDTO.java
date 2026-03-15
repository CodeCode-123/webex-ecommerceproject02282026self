package com.code.api.dto;

import java.util.List;

import com.code.api.entity.ItemOrderDetails;
import com.code.api.entity.Users;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
	private int orderId;
	private String orderDate;
	@Min(value=0, message="Total amount should not be negative")
	private double totalAmount;
    private Users users;
    private List<ItemOrderDetails> itemOrderDetailsList;
}
