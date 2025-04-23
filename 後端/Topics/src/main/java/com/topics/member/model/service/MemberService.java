package com.topics.member.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.topics.member.exception.NotFoundException;
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

	public MemberDto updateMemberById(Integer id, MemberBean entity) {
		MemberBean member = findMemberEntityById(id);
		member.setMemberName(entity.getMemberName());
		member.setEmail(entity.getEmail());
		member.setPhone(entity.getPhone());
		member.setRole(entity.getRole());
		member.setActiveStatus(entity.isActiveStatus());
		
		if (entity.getMemberPhoto() != null) {
	        member.setMemberPhoto(entity.getMemberPhoto());
	    }
		MemberBean memberNew = memberRepository.save(member);
		return new MemberDto(memberNew);
	}

	public MemberDto deleteMemberById(Integer id) {
		MemberBean member = memberRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("找不到該會員"));
		member.setActiveStatus(false);
		MemberBean save = memberRepository.save(member);
		return new MemberDto(save);
	}

	public MemberDto findMemberById(Integer id) {
		MemberBean member = memberRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("找不到該會員"));
		return new MemberDto(member);
	}

	public MemberBean findMemberEntityById(Integer id) {
		return memberRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("找不到該會員"));
	}

	public List<MemberDto> findMember() {
		List<MemberDto> members = memberRepository.findAll().stream()
				.map(m -> new MemberDto(m))
				.toList();
		return members;
	}

}
