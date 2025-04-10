package com.topics.order.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.topics.order.model.bean.ShoppingcartItemBean;

public interface ShoppingcartItemRepository extends JpaRepository<ShoppingcartItemBean, Integer> {

}
