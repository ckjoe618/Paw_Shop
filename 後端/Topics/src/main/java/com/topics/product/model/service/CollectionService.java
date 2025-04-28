package com.topics.product.model.service;

import com.topics.product.model.bean.CollectionBean;
import com.topics.product.model.repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;

    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public CollectionBean addToCollection(Integer memberId, Integer productId) {
        CollectionBean collection = new CollectionBean();
        collection.setMemberId(memberId);
        collection.setProductId(productId);
        collection.setIsDeletedStatus(false);
        return collectionRepository.save(collection);
    }

    public boolean removeFromCollection(Integer memberId, Integer productId) {
        Optional<CollectionBean> optional = collectionRepository.findByMemberIdAndProductId(memberId, productId);
        if (optional.isPresent()) {
            CollectionBean bean = optional.get();
            bean.setIsDeletedStatus(true);
            collectionRepository.save(bean);
            return true;
        }
        return false;
    }

    public List<CollectionBean> getCollectionsByMemberId(Integer memberId) {
        return collectionRepository.findByMemberIdAndIsDeletedStatusFalse(memberId);
    }
}
