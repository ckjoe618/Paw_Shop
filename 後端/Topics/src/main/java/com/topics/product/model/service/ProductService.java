package com.topics.product.model.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.topics.product.model.bean.ProductBean;
import com.topics.product.model.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository pRepos;

	// 查詢單筆By id
	public ProductBean findProductById(Integer id) {
		Optional<ProductBean> op = pRepos.findById(id);

		if (op.isPresent()) {
			return op.get();
		}

		return null;
	}

	// 查詢未被刪除
	public List<ProductBean> findAvailableProducts() {
		return pRepos.findByIsDeletedStatusFalse();
	}

	// 查詢全部
	public List<ProductBean> findAllProducts() {
		return pRepos.findAll();
	}

	// 新增
	public ProductBean insertProduct(ProductBean product) {
		// 補上預設值，避免 NULL 寫入資料庫
	    if (product.getTotalStar() == null) {
	        product.setTotalStar(0);
	    }
	    if (product.getTotalReview() == null) {
	        product.setTotalReview(0);
	    }
	    if (product.getAverageRating() == null) {
	        product.setAverageRating(BigDecimal.valueOf(0.0));
	    }
		
		return pRepos.save(product);
	}

	// 修改
	public ProductBean partialUpdateProduct(Integer id, ProductBean updateData) {
		Optional<ProductBean> optional = pRepos.findById(id);
		if (optional.isPresent()) {
			ProductBean product = optional.get();

			if (updateData.getProductName() != null) {
				product.setProductName(updateData.getProductName());
			}
			if (updateData.getProductPrice() != null) {
				product.setProductPrice(updateData.getProductPrice());
			}
			if (updateData.getCategoryName() != null) {
				product.setCategoryName(updateData.getCategoryName());
			}
			if (updateData.getProductStock() != null) {
				product.setProductStock(updateData.getProductStock());
			}
			if (updateData.getProductDes() != null) {
				product.setProductDes(updateData.getProductDes());
			}
			if (updateData.getProductPhoto() != null) {
				product.setProductPhoto(updateData.getProductPhoto());
			}

			return pRepos.save(product);
		}
		return null;
	}

	// 軟刪除
	public boolean deleteProduct(Integer id) {
		Optional<ProductBean> optional = pRepos.findById(id);
		if (optional.isPresent()) {
			ProductBean product = optional.get();
			product.setIsDeletedStatus(true);
			pRepos.save(product);
			return true;
		}
		return false;
	}

	// 軟刪除復原
	public boolean restoreProduct(Integer id) {
		Optional<ProductBean> optional = pRepos.findById(id);
		if (optional.isPresent()) {
			ProductBean product = optional.get();
			product.setIsDeletedStatus(false); // 將狀態改為未刪除
			pRepos.save(product);
			return true;
		}
		return false;
	}

	// 查詢未被刪除的商品名稱 (模糊)
	public List<ProductBean> findByProductName(String keyword) {
		return pRepos.findByProductNameContainingAndIsDeletedStatusFalse(keyword);
	}
	
	public List<ProductBean> findAvailableStockProducts() {
	    return pRepos.findByProductStockGreaterThanAndIsDeletedStatusFalse(0);
	}
	
	public List<ProductBean> getTopExpensiveProducts() {
	    return pRepos.findTop4ByOrderByProductPriceDesc();
	}

}
