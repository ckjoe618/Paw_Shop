package com.topics.member.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.topics.member.exception.NotFoundException;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.repository.MemberRepository;

@Service
public class AdminMemberService {

	@Autowired
	private MemberRepository memberRepository;

	public MemberDto insertMember(MemberBean member) {
		MemberBean memberNew = memberRepository.save(member);
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

	public void deleteMemberById(Integer id) {
		MemberBean member = memberRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("找不到該會員"));
		member.setActiveStatus(false);
		memberRepository.save(member);
	}

	public List<MemberDto> findMemberAll() {
		List<MemberDto> memberList = memberRepository.findAll().stream()
				.map(m -> new MemberDto(m))
				.toList();
		return memberList;
	}

}
