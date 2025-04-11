package com.topics.member.model.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topics.member.model.bean.MemberBean;
import com.topics.member.model.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public MemberBean insertMember(MemberBean member) {
		return memberRepository.save(member);
	}

	public MemberBean updateMember(MemberBean member) {
		return memberRepository.save(member);
	}

	public MemberBean deleteMemberById(Integer id) {
		Optional<MemberBean> op = memberRepository.findById(id);
		MemberBean member = op.get();
		member.setActive(false);
		return memberRepository.save(member);
	}

	public MemberBean selectMemberById(Integer id) {
		Optional<MemberBean> op = memberRepository.findById(id);
		if (op.isPresent()) {
			return op.get();
		}
		return null;
	}

	public List<MemberBean> selectMember() {
		return memberRepository.findByActive();
	}
}
