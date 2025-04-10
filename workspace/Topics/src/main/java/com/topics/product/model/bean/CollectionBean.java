package com.topics.product.model.bean;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "collection_table")
public class CollectionBean {

	@Id
	@Column(name = "member_id")
	private Integer memberId;
	
	@Id
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "is_deleted_status", nullable = false, columnDefinition = "BIT DEFAULT 0")
	private Boolean isDeleteStatus = false;
	
	// equals 和 hashCode (因為使用組合主鍵)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionBean that = (CollectionBean) o;
        return memberId == that.memberId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, productId);
    }
}
