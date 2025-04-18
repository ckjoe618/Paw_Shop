package com.topics.member.model.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public MemberDto insertMember(MemberBean member) {
		MemberBean memberNew = memberRepository.save(member);
		return new MemberDto(memberNew);
	}

	public MemberDto updateMember(MemberBean member) {
		MemberBean memberNew = memberRepository.save(member);
		return new MemberDto(memberNew);
	}

	public MemberDto deleteMemberById(Integer id) {
		Optional<MemberBean> op = memberRepository.findById(id);
		if (op.isEmpty()) {
			throw new IllegalArgumentException("查無此會員");
		}
		MemberBean member = op.get();
		member.setActiveStatus(false);
		MemberBean memberNew = memberRepository.save(member);
		return new MemberDto(memberNew);
	}

	public MemberDto selectMemberById(Integer id) {
		Optional<MemberBean> op = memberRepository.findById(id);
		if (op.isPresent()) {
			return new MemberDto(op.get());
		}
		return null;
	}
	
	public MemberBean selectMembeEntityrById(Integer id) {
		Optional<MemberBean> op = memberRepository.findById(id);
		if (op.isPresent()) {
			return op.get();
		}
		return null;
	}

	public List<MemberDto> selectMember() {
		List<MemberDto> members = memberRepository.findAll().stream()
				.map(m -> new MemberDto(m))
				.toList();
		return members;
	}

}
