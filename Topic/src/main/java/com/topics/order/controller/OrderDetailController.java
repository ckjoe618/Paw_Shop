package com.topics.order.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.topics.order.model.bean.OrderDetailBean;
import com.topics.order.model.service.OrderDetailService;

@RestController
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;

	//新增
	@PostMapping("/orderdetail/add")
	public OrderDetailBean insertOrderDetail(@RequestBody OrderDetailBean orderDetailBean) {	
		 return orderDetailService.insertOrderDetail(orderDetailBean);
		
	}
	
	//刪除
	@GetMapping("/orderdetail/delete")
	public void deleteOrderDetail(@RequestParam Integer orderDetailId) {
		if (orderDetailId != null) {
			orderDetailService.deleteByOrderDetailId(orderDetailId);
		}
	}
	
	
	//查詢
	@GetMapping("/orderdetail/allorderdetails")
	public List<OrderDetailBean> getOrderDetails(@RequestParam Integer orderId){
		System.out.println(orderDetailService.findByOrderId(orderId));
		return orderDetailService.findByOrderId(orderId);
	}

}
