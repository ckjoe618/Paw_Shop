package com.topics.member.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.topics.exception.NotFoundException;
import com.topics.exception.RegisterException;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.repository.AddressRepository;
import com.topics.member.model.repository.MemberRepository;
import com.topics.utils.DuplicateCheckUtil;

@Service
public class AdminMemberService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AddressRepository addressRepository;

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

	public Map<String, Object> memberGrowthAnalysis() {
		List<Object[]> rawData = memberRepository.countNewMembersByMonth();
		List<String> labels = new ArrayList<>();
		List<Integer> total = new ArrayList<>();

		for (Object[] row : rawData) {
			String month = (String) row[0];
			Integer data = ((Number) row[1]).intValue();
			String[] parts = month.split("-"); // 顯示為 "2025-04" → "4月"
			labels.add(parts[1] + "月");
			total.add(data);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("labels", labels);
		result.put("total", total);
		return result;
	}
	
	public Map<String, Object> memberGenderAnalysis() {
		List<Object[]> rawData = memberRepository.countByGender();
        List<String> labels = new ArrayList<>();
        List<Integer> total = new ArrayList<>();

        for (Object[] row : rawData) {
            String gender = (String) row[0];
            Integer data = ((Number) row[1]).intValue();
            labels.add(gender);
            total.add(data);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("labels", labels);
        result.put("total", total);
        return result;
	}
	
	public Map<String, Object> memberCityDistribution() {
		List<Object[]> rawData = addressRepository.countActiveMembersByCity();
        List<String> labels = new ArrayList<>();
        List<Integer> total = new ArrayList<>();

        for (Object[] row : rawData) {
            String city = (String) row[0];
            Integer data = ((Number) row[1]).intValue();
            labels.add(city);
            total.add(data);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("labels", labels);
        result.put("total", total);
        return result;
	}
}
