package com.code.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="item_order_details")
@Getter
@Setter
@NoArgsConstructor
public class ItemOrderDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_order_id")
	int itemOrderId;
	@Column(name="product_name")
	String productName;
	@Column(name="category_name")
	String categoryName;
	@Column(name="price")
	double price;
	@Column(name="qty")
	int qty;
	@Column(name="item_value")
	double itemValue;
	@ManyToOne
	@JoinColumn(name="order_id")
	ItemOrder itemOrder;
}
