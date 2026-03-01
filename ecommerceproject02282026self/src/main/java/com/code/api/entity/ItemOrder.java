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
@Table (name="item_order")
@Getter
@Setter
@NoArgsConstructor
public class ItemOrder {
	//member variable
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	int orderId;
	@Column(name="order_date")
	String orderDate;
	@Column(name="total_amount")
	double totalAmount;
	//@ManyToMany
	@ManyToOne
	@JoinColumn( name="user_id")
    Users users;
}
