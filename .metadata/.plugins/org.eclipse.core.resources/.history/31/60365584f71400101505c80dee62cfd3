package com.topics.order.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.bean.OrderDetailBean;
import com.topics.order.model.repository.OrderDetailRepository;
import com.topics.order.model.repository.OrderRepository;

@Service
public class OrderDetailService {
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	//新增
	public OrderDetailBean insertOrderDetail(Integer orderId, OrderDetailBean orderDetailBean) {
		OrderBean getByorderid = orderRepository.findById(orderId).get();
		orderDetailBean.setOrder(getByorderid);
		
		Integer unitPrice = orderDetailBean.getUnitPrice();
		Integer quantity = orderDetailBean.getQuantity();
		orderDetailBean.setSubtotal(unitPrice*quantity);
		
		return orderDetailRepository.save(orderDetailBean);
	}
	
	//刪除
	@Transactional
	public void deleteByOrderDetailId(Integer orderDetailId) {
		orderDetailRepository.softDeleteById(orderDetailId);
	}
	
	//查詢（依orderId）
	public List<OrderDetailBean> findByOrderId(Integer orderId){
		return orderDetailRepository.findByOrder_OrderId(orderId);
	}
	

}
