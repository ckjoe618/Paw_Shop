package com.topics.order.model.service.front;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topics.exception.NotFoundException;
import com.topics.member.model.entity.MemberBean;
import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.bean.OrderDetailBean;
import com.topics.order.model.repository.OrderDetailRepository;
import com.topics.order.model.repository.OrderRepository;
import com.topics.product.model.bean.ProductBean;
import com.topics.product.model.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ProductRepository productRepository;

	
	//新增
	@Transactional
	public OrderBean createOrder(OrderBean orderBean, Integer memberId) {
		orderBean.setOrderStatus("訂單已成立");
		orderBean.setPaymentStatus("待付款");
		orderBean.setTransactionTime(LocalDateTime.now().withNano(0));
		orderBean.setUpdateTime(LocalDateTime.now().withNano(0));
		
		MemberBean m = new MemberBean();
		m.setMemberId(memberId);
		orderBean.setMember(m);
		
		if (orderBean.getOrderDetails() != null) {
	        for (OrderDetailBean detail : orderBean.getOrderDetails()) {
	            detail.setOrder(orderBean);
	            
	            ProductBean product = productRepository.findById(detail.getProduct().getProductId())
	            	    .orElseThrow(() -> new RuntimeException("找不到商品"));
	            
	            detail.setProduct(product);
	            detail.setUnitPrice(product.getProductPrice());
	            detail.setSubtotal(product.getProductPrice() * detail.getQuantity());
	            
	            product.setProductStock(product.getProductStock() - detail.getQuantity());
	    		productRepository.save(product);
	        }
	    }
		
		return orderRepository.save(orderBean);
	}
	
	//修改（付款成功）
	@Transactional
	public OrderBean updateOrder(OrderBean orderBean) {
		orderBean.setPaymentStatus("已付款");
		orderBean.setOrderStatus("備貨中");
		orderBean.setUpdateTime(LocalDateTime.now().withNano(0));
		
		return orderRepository.save(orderBean);
	}
	
	//查詢
	public List<OrderBean> getOrdersByMemberId(Integer memberId){
		return orderRepository.findByMember_MemberId(memberId);
	}
	
	//取消訂單
	@Transactional
	public void cancelOrder(Integer orderid) {
		OrderBean cancelingOrder = orderRepository.findById(orderid)
				.orElseThrow(() -> new NotFoundException("找不到訂單"));
		
			if (!"訂單取消".equals(cancelingOrder.getOrderStatus())) {
				cancelingOrder.setOrderStatus("訂單取消");
				orderRepository.save(cancelingOrder);
				
				List<OrderDetailBean> orderDetails = orderDetailRepository.findByOrder_OrderId(orderid);
				for(OrderDetailBean o : orderDetails) {
					Integer productId = o.getProduct().getProductId();
					Integer quantity = o.getQuantity();
					
					Optional<ProductBean> productOp = productRepository.findById(productId);
					if (productOp.isPresent()) {
					    ProductBean product = productOp.get();
					    product.setProductStock(product.getProductStock() + quantity);
					    productRepository.save(product);
					}
				}
			}
		
	}

}
