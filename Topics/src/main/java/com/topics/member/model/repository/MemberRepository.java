package com.topics.member.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.topics.member.model.bean.MemberBean;

public interface MemberRepository extends JpaRepository<MemberBean, Integer> {
	
	@Query("from MemberBean where isActive = true")
	List<MemberBean> findByActive();
}
