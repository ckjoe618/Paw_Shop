package com.topics.product.model.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topics.order.model.bean.OrderDetailBean;
import com.topics.order.model.bean.ShoppingcartItemBean;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "product")
public class ProductBean {
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_des")
	private String productDes;

	@Column(name = "product_price")
	private Integer productPrice;

	@Column(name = "product_stock")
	private Integer productStock;

	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "product_photo")
	private String productPhoto;

	@Column(name = "total_star")
	private Integer totalStar;

	@Column(name = "total_review")
	private Integer totalReview;

	@Column(name = "average_rating")
	private BigDecimal averageRating;

	@Column(name = "is_deleted_status", nullable = false, columnDefinition = "BIT DEFAULT 0")
	private Boolean isDeletedStatus = false; // SQL Server 的 BIT 可用 Boolean
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrderDetailBean> orderDetailBean;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ShoppingcartItemBean> shoppingcartItem;

	// ✅ 雙向關聯：一個產品對應多筆進貨單
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchasingOrderBean> purchasingOrders = new ArrayList<>();

	// ✅ 輔助方法：加入一筆進貨單，並同步維護雙向關聯與庫存
	public void addPurchasingOrder(PurchasingOrderBean order) {
		purchasingOrders.add(order);
		order.setProduct(this);
		this.productStock += order.getPurchasingQuantity();
	}

	// ✅ 輔助方法：移除一筆進貨單（可選，並更新庫存）
	public void removePurchasingOrder(PurchasingOrderBean order) {
		purchasingOrders.remove(order);
		order.setProduct(null);
		this.productStock -= order.getPurchasingQuantity();
	}
}
