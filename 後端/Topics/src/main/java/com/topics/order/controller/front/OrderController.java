package com.topics.order.controller.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.topics.member.model.dto.MemberDto;
import com.topics.member.security.AuthHolder;
import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.service.front.OrderService;

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
	public OrderBean createOrder(@RequestBody OrderBean orderBean) {
		Integer memberId = getmemberId();
		return orderService.createOrder(orderBean, memberId);
	}
	
	//修改(付款成功）
	@PutMapping
	public OrderBean updateOrder(@RequestBody OrderBean orderBean) {
		orderBean.setPaymentStatus("已付款");
		orderBean.setOrderStatus("備貨中");
		return orderService.updateOrder(orderBean);
	}
	
	//查詢
	@GetMapping
	public List<OrderBean> getOrdersByMemberId(){
		Integer memberId = getmemberId();
		
		return orderService.getOrdersByMemberId(memberId);
	}
	
	//取消訂單
	@PutMapping("/{orderId}")
	public void cancelOrder(@PathVariable Integer orderId) {
		orderService.cancelOrder(orderId);
	}

}
