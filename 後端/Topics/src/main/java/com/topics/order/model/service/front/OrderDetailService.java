package com.topics.order.model.service.front;

import java.util.List;
import java.util.Optional;

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
	
	//修改（評論）
	public OrderDetailBean updateOrderdetailComment(Integer orderDetailId, Integer rating, String comment) {
		Optional<OrderDetailBean> op = orderDetailRepository.findById(orderDetailId);
		if (op.isPresent()) {
			OrderDetailBean orderDetailBean = op.get();
			orderDetailBean.setRating(rating);
			orderDetailBean.setComment(comment);
			orderDetailRepository.save(orderDetailBean);
			return orderDetailBean;
		}
		return null;
	}
	

}
