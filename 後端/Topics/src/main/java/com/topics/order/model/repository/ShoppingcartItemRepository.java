package com.topics.order.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.topics.order.model.bean.ShoppingcartItemBean;

public interface ShoppingcartItemRepository extends JpaRepository<ShoppingcartItemBean, Integer> {
	
	//查詢
	public List<ShoppingcartItemBean> findByMember_MemberId(Integer memberId);

}
