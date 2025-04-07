package com.topics.product.model.service;

import com.topics.product.model.bean.ProductBean;
import com.topics.product.model.bean.PurchasingOrderBean;
import com.topics.product.model.repository.ProductRepository;
import com.topics.product.model.repository.PurchasingOrderRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PurchasingOrderService {

    private final ProductRepository productRepository;
    private final PurchasingOrderRepository purchasingOrderRepository;

    public PurchasingOrderService(ProductRepository productRepository, PurchasingOrderRepository purchasingOrderRepository) {
        this.productRepository = productRepository;
        this.purchasingOrderRepository = purchasingOrderRepository;
    }

    @Transactional
    public void addPurchasingOrder(Integer productId, Integer quantity) {
        // 查找產品
        ProductBean product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("找不到產品，ID: " + productId));

        // 建立進貨單
        PurchasingOrderBean order = new PurchasingOrderBean();
        order.setPurchasingQuantity(quantity);
        // purchasingDate 自動處理 via @PrePersist

        // 加入商品（會自動更新庫存與關聯）
        product.addPurchasingOrder(order);

        // 儲存（CascadeType.ALL 會自動儲存 order）
        productRepository.save(product);
    }
}
