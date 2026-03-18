package com.code.api.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="payment")
@Getter
@Setter
public class Payment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="razorpay_order_id")
    private String razorpayOrderId;
	@Column(name="razorpay_payment_id")
	private String razorpayPaymentId;
	@Column(name="amount")
	private double amount;
	@Column(name="status")
	private String status;
	@Column(name="paid_at")
	private LocalDateTime paidAt = LocalDateTime.now();
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, 
			CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="order_id")
	@JsonIgnore
	private ItemOrder order;
}
