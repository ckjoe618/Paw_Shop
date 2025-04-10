package com.topics.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.topics.product.model.service.PurchasingOrderService;

@RestController
public class PurchasingOrderController {

	@Autowired
	private PurchasingOrderService purchasingOrderService;

	@PostMapping("/product/orders")
	public ResponseEntity<String> createOrder(@RequestParam Integer productId, @RequestParam Integer quantity) {
		purchasingOrderService.addPurchasingOrder(productId, quantity);
		return ResponseEntity.ok("新增進貨單成功！");
	}
}
