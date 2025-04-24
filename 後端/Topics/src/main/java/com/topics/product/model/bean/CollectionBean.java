package com.topics.product.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "collection_table")
@IdClass(CollectionId.class)
public class CollectionBean {

    @Id
    @Column(name = "member_id")
    private Integer memberId;

    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "is_deleted_status")
    private Boolean isDeletedStatus = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonIgnore
    private ProductBean product;
}
