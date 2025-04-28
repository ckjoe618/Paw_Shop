package com.topics.order.controller.front;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.topics.member.model.dto.MemberDto;
import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.service.front.OrderService;
import com.topics.security.AuthHolder;
import com.topics.utils.ResponseUtil;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	private Integer getmemberId() {
		MemberDto member = AuthHolder.getMember();
	    if (member == null) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "未登入");
	    }
		return member.getMemberId();
	}
	
	//新增
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestBody OrderBean orderBean) {
		Integer memberId = getmemberId();
		OrderBean order = orderService.createOrder(orderBean, memberId);
		return ResponseUtil.success(order);
	}
	
	//修改(付款成功）
	@PutMapping
	public ResponseEntity<?> updateOrder(@RequestBody OrderBean orderBean) {
		orderBean.setPaymentStatus("已付款");
		orderBean.setOrderStatus("備貨中");
		OrderBean updateOrder = orderService.updateOrder(orderBean);
		return ResponseUtil.success(updateOrder);
		
	}
	
	//查詢
	@GetMapping
	public ResponseEntity<?> getOrdersByMemberId(){
		Integer memberId = getmemberId();
		
		List<OrderBean> orders = orderService.getOrdersByMemberId(memberId);
		return ResponseUtil.success(orders);
	}
	
	//取消訂單
	@PutMapping("/{orderId}")
	public void cancelOrder(@PathVariable Integer orderId) {
		orderService.cancelOrder(orderId);
	}

}
