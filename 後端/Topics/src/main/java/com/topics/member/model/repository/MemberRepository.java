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

	MemberBean findByPhone(String phone);

	Boolean existsByAccount(String account);

	Boolean existsByIdno(String idno);

	Boolean existsByEmail(String email);

	Boolean existsByPhone(String phone);

	@Query(value = """
			    SELECT FORMAT(create_account_date, 'yyyy-MM') AS month, COUNT(*) AS count
			    FROM member
			    WHERE create_account_date >= DATEADD(MONTH, -6, GETDATE())
			    GROUP BY FORMAT(create_account_date, 'yyyy-MM')
			    ORDER BY month
			""", nativeQuery = true)
	List<Object[]> countNewMembersByMonth();

	@Query(value = """
			    SELECT gender, COUNT(*) AS count
			    FROM member
			    WHERE gender IS NOT NULL
			    GROUP BY gender
			""", nativeQuery = true)
	List<Object[]> countByGender();

}
