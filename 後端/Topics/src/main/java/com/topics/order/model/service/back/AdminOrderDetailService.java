package com.topics.order.model.service.back;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.bean.OrderDetailBean;
import com.topics.order.model.bean.OrderDetailRequestDTO;
import com.topics.order.model.repository.OrderDetailRepository;
import com.topics.order.model.repository.OrderRepository;
import com.topics.product.model.bean.ProductBean;
import com.topics.product.model.repository.ProductRepository;

@Service
public class AdminOrderDetailService {
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	//新增
	@Transactional
	public OrderDetailBean insertOrderDetail(OrderDetailRequestDTO requestDTO) {
		OrderBean order = orderRepository.findById(requestDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("找不到訂單"));
		ProductBean product = productRepository
			    .findById(requestDTO.getProductId())
			    .orElseThrow(() -> new RuntimeException("找不到商品"));
		
		OrderDetailBean orderDetail = new OrderDetailBean();
		orderDetail.setOrder(order);
	    orderDetail.setProduct(product);
	    orderDetail.setUnitPrice(requestDTO.getUnitPrice());
	    orderDetail.setQuantity(requestDTO.getQuantity());
	    orderDetail.setSubtotal(requestDTO.getUnitPrice() * requestDTO.getQuantity());
		
		// 扣庫存
		product.setProductStock(product.getProductStock() - requestDTO.getQuantity());
		productRepository.save(product);
	    
		return orderDetailRepository.save(orderDetail);
	}
	
	//刪除
	@Transactional
	public void deleteByOrderDetailId(Integer orderDetailId) {
		OrderDetailBean detail = orderDetailRepository.findById(orderDetailId).orElse(null);
	    if (detail != null && "active".equals(detail.getStatus())) {
	    	Integer productId = detail.getProduct().getProductId();

	        ProductBean product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("找不到對應商品"));

	        product.setProductStock(product.getProductStock() + detail.getQuantity());
	        productRepository.save(product);
	    }
		
		orderDetailRepository.softDeleteById(orderDetailId);
		
	}
	
	//查詢（依orderId）
	public List<OrderDetailBean> findByOrderId(Integer orderId){
		return orderDetailRepository.findByOrder_OrderId(orderId);
	}
	

}
