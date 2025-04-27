package com.topics.product.model.dto;

import com.topics.product.model.bean.CollectionBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionDto {
    private Integer productId;

    public CollectionDto(CollectionBean bean) {
        this.productId = bean.getProductId();
    }
}
