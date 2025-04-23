package com.topics.order.model.service.front;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topics.order.model.bean.OrderDetailBean;
import com.topics.order.model.repository.OrderDetailRepository;
import com.topics.product.model.bean.ProductBean;
import com.topics.product.model.repository.ProductRepository;

@Service
public class OrderDetailService {
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ProductRepository productRepository;
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
	

	@Transactional
    public void updateProductRatingStats(Integer productId) {
        List<OrderDetailBean> details = orderDetailRepository.findByProductProductId(productId);

        int totalStar = details.stream()
            .filter(d -> d.getRating() != null)
            .mapToInt(OrderDetailBean::getRating)
            .sum();

        int totalReview = (int) details.stream()
            .filter(d -> d.getRating() != null)
            .count();

        BigDecimal avg = totalReview > 0
        	    ? BigDecimal.valueOf((double) totalStar / totalReview).setScale(1, RoundingMode.HALF_UP)
        	    : BigDecimal.ZERO;

        ProductBean product = productRepository.findById(productId).orElseThrow();
        product.setTotalStar(totalStar);
        product.setTotalReview(totalReview);
        product.setAverageRating(avg);

        productRepository.save(product);
    }

    // 可選：初始化所有產品評價（部署時用）
    public void initializeAllProductRatings() {
        productRepository.findAll().forEach(p -> updateProductRatingStats(p.getProductId()));
    }
}
