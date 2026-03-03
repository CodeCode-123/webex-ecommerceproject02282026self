package com.code.api.entity;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table (name="item_order")
@Getter
@Setter
@NoArgsConstructor
public class ItemOrder {
	//member variable
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	private int orderId;
	@Column(name="order_date")
	private String orderDate;
	@Column(name="total_amount")
	private double totalAmount;
	//@ManyToMany
	@ManyToOne
	@JoinColumn(name="user_id")
    private Users users;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="itemOrder", cascade=CascadeType.ALL)
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
	}
	// add a convenient method to add the itemOrderDetails
	public void addItemOrderDetails(ItemOrderDetails itemOrderDetails) {
		this.itemOrderDetailsList.add(itemOrderDetails);
		this.totalAmount = getTotalAmount();
	}
	// add a convenient method to remove the itemOrderDetails
	public void deleteItemOrderDetailsById(int id) {
		if (itemOrderDetailsList != null && itemOrderDetailsList.size() > 0) {
			for (ItemOrderDetails details: itemOrderDetailsList) {
				if (details.getItemOrderDetailsId() == id) {
					itemOrderDetailsList.remove(details);
					break;
				}
			}
		}
		this.totalAmount = getTotalAmount();
	}*/
}
