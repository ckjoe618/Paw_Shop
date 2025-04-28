package com.topics.order.model.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailRequestDTO {
	
	private Integer orderId;
    private Integer productId;
    private Integer unitPrice;
    private Integer quantity; 

}
