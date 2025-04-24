package com.topics.product.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class CollectionId implements Serializable {
    private Integer memberId;
    private Integer productId;

    public CollectionId() {}

    public CollectionId(Integer memberId, Integer productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionId)) return false;
        CollectionId that = (CollectionId) o;
        return Objects.equals(memberId, that.memberId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, productId);
    }
}
