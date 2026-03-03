package com.code.api.dto;

import java.util.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
	private String orderDate;
	private double totalAmount;
    private int userId;
    private List<CartItems> items;
}
