package com.topics.order.model.service.back;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.repository.MemberRepository;
import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.repository.OrderRepository;

@Service
public class AdminOrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private MemberRepository memberRepository;

	// 查詢
	public List<OrderBean> findAllActiveOrders() {
		return orderRepository.findAllActiveOrder();
	};
	
	public List<OrderBean> findAllOrders(){
		return orderRepository.findAll();
	}

	// 新增
	@Transactional
	public OrderBean insertOrder(OrderBean insertBean) {
		insertBean.setTransactionTime(LocalDateTime.now().withNano(0));
		insertBean.setUpdateTime(LocalDateTime.now().withNano(0));
		return orderRepository.save(insertBean);
	};

	// 刪除
	@Transactional
	public void deleteOrderByOrderId(Integer orderId) {
		orderRepository.softDeleteById(orderId);
	};

	// 修改
	@Transactional
	public OrderBean findOrderByOrderId(Integer orderId) {
		Optional<OrderBean> op = orderRepository.findById(orderId);
		if (op.isPresent()) {
			OrderBean orderBean = op.get();
			orderBean.setTrackingNum(orderBean.getTrackingNum().trim());
			return orderBean;
		}
		return null;
	};
	@Transactional
	public OrderBean updateOrderByOrderId(OrderBean orderBean) {
		Integer memberId = orderBean.getMember().getMemberId();
		MemberBean member = memberRepository.findById(memberId).get();
		orderBean.setMember(member);
		orderBean.setUpdateTime(LocalDateTime.now().withNano(0));
		return orderRepository.save(orderBean);
	};

}
