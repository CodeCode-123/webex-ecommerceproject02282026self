package com.code.api.service;

import java.util.List;
import java.util.Optional;

import com.code.api.entity.Payment;

public interface IPaymentService {
	Payment createPayment(Payment payment);
	Optional<Payment> getPaymentById(int id);
	List<Payment> getAllPayments();
}
