package com.topics.utils;

import java.util.HashMap;
import java.util.Map;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.repository.MemberRepository;

public class DuplicateCheckUtil {
	
	// 比對資料庫是否重複
	public static Map<String, Boolean> checkDuplicateFields(MemberBean member, MemberRepository memberRepository) {
		Map<String, Boolean> result = new HashMap<>();
		result.put("idnoDuplicate", memberRepository.existsByIdno(member.getIdno()));
		result.put("emailDuplicate", memberRepository.existsByEmail(member.getEmail()));
		result.put("phoneDuplicate", memberRepository.existsByPhone(member.getPhone()));
		result.put("accountDuplicate", memberRepository.existsByAccount(member.getAccount()));
		return result;
	}

}
