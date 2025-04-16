package com.topics.member.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.topics.member.model.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	@Query("from Member where activeStatus = true")
	List<Member> findByActive();

	Member findByAccount(String account);

}
