package com.topics.order.model.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topics.order.model.bean.OrderDetailBean;
import com.topics.order.model.repository.OrderDetailRepository;

@Service
public class OrderDetailService {
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	//新增
	public OrderDetailBean addOrderdetailsFromCart(OrderDetailBean orderDetailBean){
		return orderDetailRepository.save(orderDetailBean);
	}
	
	//查詢（依orderId）
	public List<OrderDetailBean> getAllDetails (Integer orderId){
		return orderDetailRepository.findByOrder_OrderId(orderId);
	}
	

}
