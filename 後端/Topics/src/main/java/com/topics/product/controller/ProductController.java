package com.topics.product.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.topics.product.model.bean.ProductBean;
import com.topics.product.model.service.ProductService;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService pService;

	// 查詢目前尚有庫存的商品（且未被刪除）
	@GetMapping("/stock/available")
	@ResponseBody
	public List<ProductBean> findAvailableStockProducts() {
	    return pService.findAvailableStockProducts();
	}

	// 查詢單筆
	@GetMapping("/{id}")
	@ResponseBody
	public ProductBean findProductById(@PathVariable Integer id) {
		return pService.findProductById(id);
	}

	// 查詢未被刪除
	@GetMapping("/available")
	@ResponseBody
	public List<ProductBean> findAvailableProducts() {
		return pService.findAvailableProducts();
	}

	// 查詢全部
	@GetMapping
	@ResponseBody
	public List<ProductBean> findAllProducts() {
		return pService.findAllProducts();
	}

	// 模糊查詢商品名稱（e.g. /product?keyword=貓）
	@GetMapping(params = "keyword")
	@ResponseBody
	public List<ProductBean> searchProducts(@RequestParam String keyword) {
		return pService.findByProductName(keyword);
	}

	// 新增
	@PostMapping
	@ResponseBody
	public ProductBean insertProduct(@RequestBody ProductBean product) {
		return pService.insertProduct(product);
	}

	// 修改
	@PutMapping("/{id}")
	@ResponseBody
	public ProductBean updateProduct(@PathVariable Integer id, @RequestBody ProductBean product) {
		return pService.partialUpdateProduct(id, product);
	}

	// 軟刪除
	@DeleteMapping("/{id}")
	@ResponseBody
	public String deleteProduct(@PathVariable Integer id) {
		boolean result = pService.deleteProduct(id);
		return result ? "刪除成功" : "查無資料";
	}

	// 軟刪除復原
	@PutMapping("/{id}/restore")
	@ResponseBody
	public String restoreProduct(@PathVariable Integer id) {
		boolean result = pService.restoreProduct(id);
		return result ? "產品已復原" : "查無此產品";
	}

}
