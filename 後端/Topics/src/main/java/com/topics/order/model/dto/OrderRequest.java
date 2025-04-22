package com.topics.order.model.dto;

import lombok.Data;

@Data
public class OrderRequest {
	private Integer orderId;
    private Integer amount;
    private String itemName;
}
