package com.topics.order.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.topics.order.model.bean.OrderBean;

public interface OrderRepository extends JpaRepository<OrderBean, Integer> {

	// 查詢
	@Query("SELECT o FROM OrderBean o WHERE o.orderStatus != '訂單失效'")
	public List<OrderBean> findAllActiveOrder();
	
	//前台會員訂單查詢
	public List<OrderBean> findByMember_MemberId(Integer memberId);


	// 刪除（假刪除）
	@Modifying
	@Query("UPDATE OrderBean SET orderStatus='訂單失效' WHERE orderId=?1")
	public void softDeleteById(Integer orderId);

}
