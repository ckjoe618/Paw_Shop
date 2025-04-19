package com.topics.member.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.topics.member.model.entity.AddressBean;

public interface AddressRepository extends JpaRepository<AddressBean, Integer> {

	@Query("from AddressBean WHERE member.memberId = :id")
	List<AddressBean> findAddressById(@Param("id") Integer id);
	
}
