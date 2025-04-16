package com.topics.member.model.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.Member;
import com.topics.member.model.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public MemberDto insertMember(Member member) {
		Member memberNew = memberRepository.save(member);
		return new MemberDto(memberNew);
	}

	public MemberDto updateMember(Member member) {
		Member memberNew = memberRepository.save(member);
		return new MemberDto(memberNew);
	}

	public MemberDto deleteMemberById(Integer id) {
		Optional<Member> op = memberRepository.findById(id);
		Member member = op.get();
		member.setActiveStatus(false);
		Member memberNew = memberRepository.save(member);
		return new MemberDto(memberNew);
	}

	public MemberDto selectMemberById(Integer id) {
		Optional<Member> op = memberRepository.findById(id);
		if (op.isPresent()) {
			return new MemberDto(op.get());
		}
		return null;
	}

	public List<MemberDto> selectMember() {
		List<MemberDto> members = memberRepository.findByActive().stream()
				.map(m -> new MemberDto(m))
				.toList();
		return members;
	}

}
