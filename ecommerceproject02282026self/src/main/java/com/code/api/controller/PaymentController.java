package com.code.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.dto.OrderRequestDTO;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.entity.Users;
import com.code.api.exception.ResourceNotFoundException;
import com.code.api.service.IItemOrderDetailsService;
import com.code.api.service.IItemOrderService;
import com.code.api.service.IUserService;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

@RestController
@RequestMapping("/api/payment/")
public class PaymentController {
	@Value("${razorpay.key.id}")
	private String keyId;
	@Value("${razorpay.key.secret}")
	private String keySecret;
	@Autowired
	private IItemOrderDetailsService itemOrderDetailsService;
	@Autowired
	private IUserService userService;
	
	@PostMapping("/createorder")
	public String create(@RequestBody OrderRequestDTO orderRequestDTO) throws Exception {
		RazorpayClient client = new RazorpayClient(keyId, keySecret);
		JSONObject options = new JSONObject();
		Users users = null;
		if (orderRequestDTO.getUsers() != null && orderRequestDTO.getUsers().getId() > 0) {
			int userId = orderRequestDTO.getUsers().getId();
			Optional<Users> tempUsers = userService.getUserById(userId);
			if (!tempUsers.isEmpty()) {
				users = tempUsers.get();
				System.out.println("User id: " + userId);
			}
		}
		ItemOrder order = new ItemOrder();
		order.setUsers(users);
		int total = 0;
		if (orderRequestDTO.getItemOrderDetailsList() != null 
				&& orderRequestDTO.getItemOrderDetailsList().size() > 0) {
			List<ItemOrderDetails> itemOrderDetailsList = new ArrayList<>();
			int tempId = 0;
			Optional<ItemOrderDetails> tempDetails;
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
			total = tempTotal;
		}
		options.put("amount", (int)(total * 100));
		options.put("currency", "USD");
		options.put("receipt", "txn_" + System.currentTimeMillis());
		com.razorpay.Order razorOrder = client.orders.create(options);
		return razorOrder.toString();
	}
	
	@PostMapping("/confirmpayment")
	public ResponseEntity<String> confirmPayment(@RequestBody Map<String, String> data) {
		String secret = keySecret;
		String orderId = data.get("razorpay_order_id");
		String paymentId = data.get("razorpay_payment_id");
		String signature = data.get("razorpay_signature");
		try {
			JSONObject options = new JSONObject();
			options.put("razorpay_order_id", orderId);
			options.put("razorpay_payment_id", paymentId);
			options.put("razorpay_signature", signature);
			boolean isValid = Utils.verifyPaymentSignature(options, secret);
			if (isValid) {
				return ResponseEntity.ok("Payment Successful");
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification Failed");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Signature");
	}
}
