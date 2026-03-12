package com.code.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="item_order_details")
@Getter
@Setter
public class ItemOrderDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_order_details_id")
	private int itemOrderDetailsId;
	@OneToOne
	@JoinColumn(name="item_id")
	private Item item;
	@Column(name="qty")
	private int qty;
	@Column(name="item_value")
	private double itemValue;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, 
			CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="order_id")
	@JsonIgnore
	private ItemOrder itemOrder;
}
