package com.topics.member.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.topics.member.model.entity.AddressBean;

public interface AddressRepository extends JpaRepository<AddressBean, Integer> {

	@Query("from AddressBean WHERE member.memberId = :memberId AND activeStatus = true ORDER BY defaultStatus DESC, addressId ASC")
	List<AddressBean> findAddressByMemberId(@Param("memberId") Integer id);

	@Query("from AddressBean WHERE member.memberId = :memberId AND defaultStatus = true AND activeStatus = true")
	AddressBean findActiveAddressByMemberId(@Param("memberId") Integer memberId);
	
	@Modifying
	@Transactional
	@Query("UPDATE AddressBean SET defaultStatus = false WHERE member.memberId = :memberId")
	void clearDefaultByMemberId(@Param("memberId") Integer memberId);

}
