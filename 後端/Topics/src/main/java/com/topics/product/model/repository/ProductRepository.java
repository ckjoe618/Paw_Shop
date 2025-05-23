package com.topics.product.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.topics.product.model.bean.ProductBean;

public interface ProductRepository extends JpaRepository<ProductBean, Integer> {

	// 查詢庫存數量不為 0 且未刪除的產品
	List<ProductBean> findByProductStockGreaterThanAndIsDeletedStatusFalse(int stock);
	
	// 查詢未刪除
	List<ProductBean> findByIsDeletedStatusFalse();

	// 查詢商品名稱(模糊)
	List<ProductBean> findByProductNameContaining(String keyword);

	// 查詢未被刪除的商品名稱 (模糊)
	List<ProductBean> findByProductNameContainingAndIsDeletedStatusFalse(String keyword);

	//顯示價格最高的前 4 筆商品
	List<ProductBean> findTop4ByOrderByProductPriceDesc();
}
