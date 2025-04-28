package com.topics.product.model.repository;

import com.topics.product.model.bean.CollectionBean;
import com.topics.product.model.bean.CollectionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<CollectionBean, CollectionId> {
    List<CollectionBean> findByMemberIdAndIsDeletedStatusFalse(Integer memberId);
    Optional<CollectionBean> findByMemberIdAndProductId(Integer memberId, Integer productId);
}