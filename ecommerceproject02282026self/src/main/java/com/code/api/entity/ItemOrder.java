package com.code.api.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table (name="item_order")
@Getter
@Setter
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
	@OneToOne(mappedBy="order", cascade = {CascadeType.PERSIST, CascadeType.DETACH, 
			CascadeType.MERGE, CascadeType.REFRESH})
	@JsonManagedReference
	private Payment payment;
}
