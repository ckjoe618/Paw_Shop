package com.topics.order.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.topics.order.model.bean.OrderBean;

public interface OrderRepository extends JpaRepository<OrderBean, Integer> {
	
	//查詢
	@Query("SELECT o FROM OrderBean o WHERE o.orderStatus != '訂單失效'")
	public List<OrderBean> findAllActiveOrder();
	
	public List<OrderBean> findByMemberId(Integer memberId);
	
	@Query("SELECT o FROM OrderBean o WHERE o.memberId = ?1 AND (transactionTime BETWEEN ?2 AND ?3)")
	public List<OrderBean> findByMemberIdAndTxnTime(Integer memberId, String startDate, String endDate);
	
	//刪除（假刪除）
	@Modifying
	@Query("UPDATE OrderBean SET orderStatus='訂單失效' WHERE orderId=?1")
	public void softDeleteById (Integer orderId);

}
