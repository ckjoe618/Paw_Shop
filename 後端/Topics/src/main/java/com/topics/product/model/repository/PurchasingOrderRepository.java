package com.topics.product.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.topics.product.model.bean.PurchasingOrderBean;

public interface PurchasingOrderRepository extends JpaRepository<PurchasingOrderBean, Integer> {

}
