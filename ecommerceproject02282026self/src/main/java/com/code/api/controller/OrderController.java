package com.code.api.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.code.api.service.*;
import com.code.api.dto.OrderRequestDTO;
import com.code.api.entity.*;
import com.code.api.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	private IItemOrderService itemOrderService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IItemOrderDetailsService itemOrderDetailsService;
	
	
	@GetMapping("/")
	public List<ItemOrder> getAllOrders() {
		return itemOrderService.getAll();
	}
	@GetMapping("/{id}")
	public ItemOrder getOrderById(@PathVariable("id") int id) {
		if (itemOrderService.getById(id) == null) {
			throw new ResourceNotFoundException("ItemOrder", "itemOrderId", String.valueOf(id));
		}
		return itemOrderService.getById(id).get();
	}
	@PostMapping("/placeorder") // create an order with items and users
	public ResponseEntity<ItemOrder> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
		Users users = null;
		if (orderRequestDTO.getUsers() != null && orderRequestDTO.getUsers().getId() > 0) {
			int userId = orderRequestDTO.getUsers().getId();
			users = userService.getUserById(userId);
			if (users != null) {
				System.out.println("User id: " + userId);
			}
		}
		ItemOrder order = new ItemOrder();
		if (users == null) {
			return ResponseEntity.ok(order);
		}
		order.setUsers(users);
		LocalDateTime nowTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = nowTime.format(formatter);
		order.setOrderDate(formattedDate);
		System.out.println("Order date: " + order.getOrderDate());
		//order.setTotalAmount();
		itemOrderService.add(order);
		if (orderRequestDTO.getItemOrderDetailsList() != null 
				&& orderRequestDTO.getItemOrderDetailsList().size() > 0) {
			List<ItemOrderDetails> itemOrderDetailsList = new ArrayList<>();
			int tempId = 0;
			Optional<ItemOrderDetails> tempDetails = null;
			int tempQty = 0;
			double tempPrice = 0;
			int tempTotal = 0;
			for (ItemOrderDetails details: orderRequestDTO.getItemOrderDetailsList()) {
				tempId = details.getItemOrderDetailsId();
				if (tempId == 0) {
					throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailsId", String.valueOf(tempId));
				}
				tempDetails = itemOrderDetailsService.getById(tempId);
				if (tempDetails != null) {
					tempDetails.get().setItemOrder(order);
					itemOrderDetailsList.add(tempDetails.get());
					tempQty = tempDetails.get().getQty();
					tempPrice = tempDetails.get().getItem().getItemPrice();
					tempTotal += tempQty * tempPrice;
				}
			}
			order.setItemOrderDetailsList(itemOrderDetailsList);
			order.setTotalAmount(tempTotal);
			itemOrderService.update(order);
		}
		return ResponseEntity.ok(order);
	}
	/*
	@PutMapping("/edit")
	public ItemOrder editOrder(@RequestBody ItemOrder itemOrder) {
		return itemOrderService.update(itemOrder);
	}*/
	@DeleteMapping("/delete/{id}")
	public String deleteOrder(@PathVariable("id") int id) {
		if (itemOrderService.getById(id) == null) {
			throw new ResourceNotFoundException("ItemOrder", "itemOrderId", String.valueOf(id));
		}
		return itemOrderService.delete(id);
	}
    /*
	@PostMapping("/placeorder") // create an order with items
	public ResponseEntity<ItemOrder> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
		System.out.println("User id: " + orderRequestDTO.getUserId());
		int userId = orderRequestDTO.getUserId();
		Users users = userService.getUserById(userId);
		ItemOrder order = new ItemOrder();
		if (users == null) {
			return ResponseEntity.ok(order);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = order.getOrderDate().formatted(formatter);
		order.setOrderDate(formattedDate);
		order.setTotalAmount(orderRequestDTO.getTotalAmount());
		System.out.println("Order date: " + order.getOrderDate());
		order.setUsers(users);
		itemOrderService.add(order);
		ItemOrderDetails detail = null;
		for (CartItems item:orderRequestDTO.getItems()) {
			detail = new ItemOrderDetails();
			detail.setProductName(item.getItemName());
			detail.setCategoryName(item.getCategory().getCategoryName());
			detail.setPrice(item.getItemPrice());
			detail.setQty(item.getQty());
			detail.setItemValue(item.getItemPrice());
			detail.setItemOrder(order);
			itemOrderDetailsService.add(detail);
		}
		return ResponseEntity.ok(order);
	}*/
}
