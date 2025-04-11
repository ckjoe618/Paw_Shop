package com.topics.member.controller;

import org.springframework.web.bind.annotation.RestController;

import com.topics.member.model.bean.MemberBean;
import com.topics.member.model.service.MemberService;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class MemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping("/findAll")
	public List<MemberBean> selectMember() {
		return memberService.selectMember();
	}

	@PostMapping("/findType")
	public MemberBean selectMemberType(@RequestBody Map<String, Integer> data) {
		Integer memberId = data.get("memberId");
		return memberService.selectMemberById(memberId);
	}

//	@PostMapping("/findType")
//	public MemberBean selectMemberBy(@ModelAttribute MemberBean member) {
//
//		if (member.getMemberId() != null && member.getMemberId() > 0) {
//			return memberService.selectMemberById(member.getMemberId());
//
//		} else if (member.getAccount() != null && !member.getAccount().isEmpty()) {
//			return memberService.selectMemberByAccount(member.getAccount());
//
//		} else if (member.getIdno() != null && !member.getIdno().isEmpty()) {
//			return memberService.selectMemberByIdno(member.getIdno());
//		}
//		return null;
//	}

	@PostMapping("/update")
	public MemberBean updateMemberById(@ModelAttribute MemberBean member) {
		return memberService.updateMember(member);
	}

	@PostMapping("/delete")
	public MemberBean deleteMemberById(@RequestBody Map<String, Integer> data) {
		Integer memberId = data.get("memberId");
		return memberService.deleteMemberById(memberId);
	}

	@PostMapping("/add")
	public MemberBean insertMember(@ModelAttribute MemberBean member) {
		return memberService.insertMember(member);
	}

}
