package com.code.api.dto;

import java.util.List;

import com.code.api.entity.ItemOrderDetails;
import com.code.api.entity.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
	private int orderId;
	private String orderDate;
	private double totalAmount;
    private Users users;
    private List<ItemOrderDetails> itemOrderDetailsList;
    
    /*
    public void setTotalAmount() {
		double tempTotalAmount = 0;
		if (itemOrderDetailsList != null && itemOrderDetailsList.size() > 0) {
			for (ItemOrderDetails details: itemOrderDetailsList) {
				tempTotalAmount += details.getQty() * details.getItem().getItemPrice();
			}
		}
		this.totalAmount = tempTotalAmount;
	}
	
	public double getTotalAmount() {
		double tempTotalAmount = 0;
		if (itemOrderDetailsList != null && itemOrderDetailsList.size() > 0) {
			for (ItemOrderDetails details: itemOrderDetailsList) {
				tempTotalAmount += details.getQty() * details.getItem().getItemPrice();
			}
		}
		return tempTotalAmount;
	}*/
}
