package com.topics.order.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	// 查詢
	@Transactional
	public OrderBean findOrderByOrderId(Integer orderId) {
		Optional<OrderBean> op = orderRepository.findById(orderId);
		if (op.isPresent()) {
			return op.get();
		}
		return null;
	};

//	public List<OrderBean> findOrderByMemberId(Integer memberId) {
//		return orderRepository.findByMemberId(memberId);
//		
//	};

	public List<OrderBean> findAllOrders() {
		return orderRepository.findAllActiveOrder();
	};

	// 新增
	public OrderBean insertOrder(OrderBean insertBean) {
		return orderRepository.save(insertBean);
	};

	// 刪除
	@Transactional
	public void deleteOrderByOrderId(Integer orderId) {
		orderRepository.softDeleteById(orderId);
	};

	// 修改
	public OrderBean updateOrderByOrderId(OrderBean orderBean) {
		return orderRepository.save(orderBean);
	};

}
