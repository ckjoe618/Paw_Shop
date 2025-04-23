package com.topics.order.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.topics.order.model.bean.OrderDetailBean;

public interface OrderDetailRepository extends JpaRepository<OrderDetailBean, Integer> {

	// 刪除（假刪除）
	@Modifying
	@Query("UPDATE OrderDetailBean SET status='disabled' WHERE orderDetailId=?1")
	public void softDeleteById(Integer orderDetailId);

	// 查詢（依orderId）
	public List<OrderDetailBean> findByOrder_OrderId(Integer orderId);

	//查詢某產品所有評價用
	List<OrderDetailBean> findByProductProductId(Integer productId);
}
