package com.topics.member.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.topics.member.model.entity.MemberBean;

public interface MemberRepository extends JpaRepository<MemberBean, Integer> {

	@Query("from MemberBean where activeStatus = true")
	List<MemberBean> findByActive();

	MemberBean findByAccount(String account);

	MemberBean findByEmail(String email);
}
