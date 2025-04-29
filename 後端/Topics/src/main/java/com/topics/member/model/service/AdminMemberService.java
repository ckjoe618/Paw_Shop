package com.topics.member.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.topics.exception.NotFoundException;
import com.topics.exception.RegisterException;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.repository.MemberRepository;
import com.topics.utils.DuplicateCheckUtil;

@Service
public class AdminMemberService {

	@Autowired
	private MemberRepository memberRepository;

	public MemberDto insertMember(MemberBean entity) {
		Map<String, Boolean> check = DuplicateCheckUtil.checkDuplicateFields(entity, memberRepository);

		List<String> errorMsg = new ArrayList<>();
		if (check.get("idnoDuplicate")) {
			errorMsg.add("身分證已被註冊");
		}
		if (check.get("emailDuplicate")) {
			errorMsg.add("Email 已被註冊");
		}
		if (check.get("phoneDuplicate")) {
			errorMsg.add("手機號碼已被註冊");
		}
		if (check.get("accountDuplicate")) {
			errorMsg.add("帳號已被註冊");
		}
		if (!errorMsg.isEmpty()) {
	        throw new RegisterException(String.join("、", errorMsg));
	    }
		
		MemberBean memberNew = memberRepository.save(entity);
		return new MemberDto(memberNew);
	}

	public MemberDto updateMemberById(Integer id, MemberBean entity) {
		MemberBean member = memberRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("找不到該會員"));
		member.setMemberName(entity.getMemberName());
		member.setEmail(entity.getEmail());
		member.setPhone(entity.getPhone());
		member.setRole(entity.getRole());
		member.setActiveStatus(entity.isActiveStatus());
		MemberBean memberNew = memberRepository.save(member);
		return new MemberDto(memberNew);
	}

	public MemberDto deleteMemberById(Integer id) {
		MemberBean member = memberRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("找不到該會員"));
		member.setActiveStatus(false);
		MemberBean memberNew = memberRepository.save(member);
		return new MemberDto(memberNew);
	}

	public List<MemberDto> findMemberAll() {
		List<MemberDto> memberList = memberRepository.findAll().stream()
				.map(m -> new MemberDto(m))
				.toList();
		return memberList;
	}

}
