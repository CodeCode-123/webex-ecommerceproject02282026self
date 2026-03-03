package com.code.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.service.IItemOrderDetailsService;
import com.code.api.dto.OrderDetailsDTO;
import com.code.api.entity.*;
import com.code.api.exception.ResourceNotFoundException;

import java.util.*;

@RestController
@RequestMapping("/api/orderdetails")
public class OrderDetailsController {
	@Autowired
	private IItemOrderDetailsService orderDetailsService;
	
	@GetMapping("/")
	public List<ItemOrderDetails> getAllOrderDetails() {
		return orderDetailsService.getAll();
	}
	@GetMapping("/{id}")
	public ItemOrderDetails getOrderDetailsById(@PathVariable("id") int id) {
		return orderDetailsService.getById(id);
	}
	@PostMapping("/create")
	public ResponseEntity<ItemOrderDetails> createOrderDetails(@RequestBody ItemOrderDetails itemOrderDetails) {
		int qty = itemOrderDetails.getQty();
		double price = itemOrderDetails.getItem().getItemPrice();
		itemOrderDetails.setItemValue(qty * price);
		orderDetailsService.add(itemOrderDetails);
		return ResponseEntity.ok(itemOrderDetails);
	}
	@PutMapping("/edit")
	public ResponseEntity<ItemOrderDetails> editOrderDetails(@RequestBody ItemOrderDetails itemOrderDetails) {
		int qty = itemOrderDetails.getQty();
		double price = itemOrderDetails.getItem().getItemPrice();
		itemOrderDetails.setItemValue(qty * price);
		orderDetailsService.update(itemOrderDetails);
		return ResponseEntity.ok(itemOrderDetails);
	}
	@PatchMapping("/edit/{id}")
	public ResponseEntity<ItemOrderDetails> editOrderDetailsById(@PathVariable("id") int id, @RequestBody OrderDetailsDTO orderDetailsDTO) {
		ItemOrderDetails dbItemOrderDetails = orderDetailsService.getById(id);
		if (dbItemOrderDetails == null) {
			throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailsId", String.valueOf(id));
		}
		if (orderDetailsDTO.getItem() != null && orderDetailsDTO.getItem().getItemId() > 0) {
			dbItemOrderDetails.setItem(orderDetailsDTO.getItem());
		}
		if (orderDetailsDTO.getQty() > 0) {
			dbItemOrderDetails.setQty(orderDetailsDTO.getQty());
		}
		if (orderDetailsDTO.getItemValue() > 0) {
			dbItemOrderDetails.setItemValue(orderDetailsDTO.getItemValue());
		}
		if (orderDetailsDTO.getItemOrder() != null && orderDetailsDTO.getItemOrder().getOrderId() > 0) {
			dbItemOrderDetails.setItemOrder(orderDetailsDTO.getItemOrder());
		}
		int qty = dbItemOrderDetails.getQty();
		double price = dbItemOrderDetails.getItem().getItemPrice();
		dbItemOrderDetails.setItemValue(qty * price);
		orderDetailsService.update(dbItemOrderDetails);
		return ResponseEntity.ok(dbItemOrderDetails);
	}
	@DeleteMapping("/delete/{id}")
	public String deleteOrderDetails(@PathVariable("id") int id) {
		return orderDetailsService.delete(id);
	}

}
