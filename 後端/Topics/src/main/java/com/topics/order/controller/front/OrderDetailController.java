package com.topics.order.controller.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topics.order.model.bean.OrderDetailBean;
import com.topics.order.model.service.front.OrderDetailService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/orderdetail")
public class OrderDetailController {
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	//新增
	@PostMapping("path")
	public String addOrderdetailsFromCart(@RequestBody List<OrderDetailBean> orderDetails) {
		for(OrderDetailBean o : orderDetails) {
			orderDetailService.addOrderdetailsFromCart(o);
		}
		return "ok";
	}
	
	
	//查詢
	@GetMapping("/{orderId}")
	public List<OrderDetailBean> getOrderDetails(@PathVariable Integer orderId) {
		return orderDetailService.getAllDetails(orderId);
	}
	
	

}
