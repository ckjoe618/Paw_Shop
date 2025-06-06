package com.topics.member.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.topics.exception.NotFoundException;
import com.topics.exception.PasswordErrorException;
import com.topics.exception.RegisterException;
import com.topics.member.model.dto.AddressDto;
import com.topics.member.model.dto.AuthDto;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.AddressBean;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.repository.MemberRepository;
import com.topics.utils.DuplicateCheckUtil;

@Service
public class UserMemberService {

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

	public MemberDto updateMemberById(Integer memberId, MemberBean entity) {
		MemberBean member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("找不到該會員"));
		member.setEmail(entity.getEmail());
		member.setPhone(entity.getPhone());
		if (entity.getMemberPhoto() != null) {
			member.setMemberPhoto(entity.getMemberPhoto());
		}
		MemberBean memberNew = memberRepository.save(member);

		List<AddressBean> addressList = memberNew.getAddressList();
		AddressDto addressDefaul = null;
		if (addressList != null && !addressList.isEmpty()) {
			addressDefaul = memberNew.getAddressList().stream().filter(address -> address.isDefaultStatus()).findFirst()
					.map(address -> new AddressDto(address)).orElseGet(() -> new AddressDto(addressList.get(0)));
		}
		return new MemberDto(memberNew, addressDefaul);
	}

	public MemberDto findMemberById(Integer memberId) {
		MemberBean member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("找不到該會員"));
		return new MemberDto(member);
	}

	public MemberDto findMemberByEmail(String email) {
		MemberBean member = memberRepository.findByEmail(email);
		if (member == null) {
			throw new NotFoundException("找不到該信箱");
		}
		return new MemberDto(member);
	}

	public MemberDto resetPasswordByEmail(String email, String password) {
		MemberBean member = memberRepository.findByEmail(email);
		if (member == null) {
			throw new NotFoundException("找不到該信箱");
		}
		member.setPassword(password);
		MemberBean memebrNew = memberRepository.save(member);
		return new MemberDto(memebrNew);
	}

	public MemberDto updatePasswordByMemberId(Integer memberId, AuthDto entity) {
		MemberBean member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("找不到該會員"));
		if (!entity.getPassword().equals(member.getPassword())) {
			throw new PasswordErrorException("舊密碼錯誤");
		}
		member.setPassword(entity.getNewPassword());
		MemberBean memberNew = memberRepository.save(member);
		return new MemberDto(memberNew);
	}
}
