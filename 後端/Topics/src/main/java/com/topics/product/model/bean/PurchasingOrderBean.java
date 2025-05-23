package com.topics.product.model.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "purchasing_order")
public class PurchasingOrderBean {

	@Id
	@Column(name = "purchasing_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer purchasingId;

	@Column(name = "purchasing_quantity")
	private Integer purchasingQuantity;

	@Column(name = "purchasing_date")
	private LocalDateTime purchasingDate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private ProductBean product;

	@PrePersist
	protected void onCreate() {
		if (this.purchasingDate == null) {
			this.purchasingDate = LocalDateTime.now();
		}
	}
}
