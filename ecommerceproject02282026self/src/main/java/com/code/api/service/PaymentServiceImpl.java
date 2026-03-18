package com.code.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code.api.entity.Payment;
import com.code.api.repository.IPaymentRepository;

@Service
public class PaymentServiceImpl implements IPaymentService {
	@Autowired
	private IPaymentRepository paymentRepository;

	@Override
	public Payment createPayment(Payment payment) {
		return paymentRepository.save(payment); 
	}

	@Override
	public Optional<Payment> getPaymentById(int id) {
		return paymentRepository.findById(id);
	}

	@Override
	public List<Payment> getAllPayments() {
		return paymentRepository.findAll();
	}
}
