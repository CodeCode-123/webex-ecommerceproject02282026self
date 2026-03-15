package com.code.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.service.IItemOrderDetailsService;
import com.code.api.service.IItemService;

import jakarta.validation.Valid;

import com.code.api.dto.OrderDetailsDTO;
import com.code.api.entity.*;
import com.code.api.exception.ResourceNotFoundException;

import java.util.*;

@RestController
@RequestMapping("/api/orderdetails")
public class OrderDetailsController {
	@Autowired
	private IItemService itemService; 
	@Autowired
	private IItemOrderDetailsService orderDetailsService;
	
	@GetMapping("/")
	public List<ItemOrderDetails> getAllOrderDetails() {
		return orderDetailsService.getAll();
	}
	@GetMapping("/{id}")
	public ItemOrderDetails getOrderDetailsById(@PathVariable("id") int id) {
		if (orderDetailsService.getById(id).isEmpty()) {
			throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailsId", String.valueOf(id));
		}
		return orderDetailsService.getById(id).get();
	}
	@PostMapping("/create")
	public ResponseEntity<ItemOrderDetails> createOrderDetails(@Valid @RequestBody OrderDetailsDTO orderDetailsDTO) {
		ItemOrderDetails orderDetails = generateOrderDetails(orderDetailsDTO);
		orderDetailsService.add(orderDetails);
		return ResponseEntity.ok(orderDetails);
	}
	@PutMapping("/edit")
	public ResponseEntity<ItemOrderDetails> editOrderDetails(@Valid @RequestBody OrderDetailsDTO orderDetailsDTO) {
		int orderDetailsId = orderDetailsDTO.getItemOrderDetailsId();
		if (orderDetailsService.getById(orderDetailsId).isEmpty()) {
			throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailsId", String.valueOf(orderDetailsId));
		}
		ItemOrderDetails orderDetails = generateOrderDetails(orderDetailsDTO);
		orderDetails.setItemOrderDetailsId(orderDetailsId);
		orderDetailsService.update(orderDetails);
		return ResponseEntity.ok(orderDetails);
	}
	@PatchMapping("/edit/{id}")
	public ResponseEntity<ItemOrderDetails> editOrderDetailsById(@PathVariable("id") int id, @Valid @RequestBody OrderDetailsDTO orderDetailsDTO) {
		Optional<ItemOrderDetails> dbItemOrderDetails = orderDetailsService.getById(id);
		if (dbItemOrderDetails.isEmpty()) {
			throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailsId", String.valueOf(id));
		}
		if (orderDetailsDTO.getItemOrder() != null && orderDetailsDTO.getItemOrder().getOrderId() > 0) {
			dbItemOrderDetails.get().setItemOrder(orderDetailsDTO.getItemOrder());
		}
		if (orderDetailsDTO.getItem() != null && orderDetailsDTO.getItem().getItemId() > 0) {
			Optional<Item> dbItem = itemService.getById(orderDetailsDTO.getItem().getItemId());
			if (dbItem.isEmpty()) {
				throw new ResourceNotFoundException("Item", "itemId", String.valueOf(orderDetailsDTO.getItem().getItemId()));
			}
			dbItemOrderDetails.get().setItem(orderDetailsDTO.getItem());
		}
		if (orderDetailsDTO.getQty() > 0) {
			dbItemOrderDetails.get().setQty(orderDetailsDTO.getQty());
		}
		int qty = dbItemOrderDetails.get().getQty();
		double price = dbItemOrderDetails.get().getItem().getItemPrice();
		dbItemOrderDetails.get().setItemValue(price * qty);
		if (orderDetailsDTO.getItemValue() > 0) {
			dbItemOrderDetails.get().setItemValue(orderDetailsDTO.getItemValue());
		}
		orderDetailsService.update(dbItemOrderDetails.get());
		return ResponseEntity.ok(dbItemOrderDetails.get());
	}
	@DeleteMapping("/delete/{id}")
	public String deleteOrderDetails(@PathVariable("id") int id) {
		if (orderDetailsService.getById(id).isEmpty()) {
			throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailsId", String.valueOf(id));
		}
		orderDetailsService.deleteById(id);
		return "Record is deleted successfully";
	}
	private ItemOrderDetails generateOrderDetails(OrderDetailsDTO orderDetailsDTO) {
		if (orderDetailsDTO.getItem() == null || orderDetailsDTO.getItem().getItemId() <= 0) {
			throw new ResourceNotFoundException("ItemOrderDetails", "item", orderDetailsDTO.getItem().toString());
		}
		int itemId = orderDetailsDTO.getItem().getItemId();
		if (itemService.getById(itemId).isEmpty()) {
			throw new ResourceNotFoundException("Item", "itemId", String.valueOf(itemId));
		}
		ItemOrderDetails tempItemOrderDetails = new ItemOrderDetails();
		Optional<Item> tempItem = itemService.getById(itemId);
		tempItemOrderDetails.setItem(tempItem.get());
		double price = itemService.getById(itemId).get().getItemPrice();
		int qty = orderDetailsDTO.getQty();
		tempItemOrderDetails.setQty(qty);
		tempItemOrderDetails.setItemValue(qty * price);
		return tempItemOrderDetails;
	}

}
