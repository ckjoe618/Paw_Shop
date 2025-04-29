package com.topics.utils;

import com.topics.exception.ForbiddenException;
import com.topics.exception.NotFoundException;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.AddressBean;
import com.topics.member.model.repository.AddressRepository;
import com.topics.security.AuthHolder;

public class SecurityUtil {

	// 判斷有無登入
	public static void checkLogin() {
		MemberDto member = AuthHolder.getMember();
		if (member == null) {
			throw new ForbiddenException("尚未登入");
		}
	}

	// 判斷有無登入且是否為管理者
	public static void checkAdminPermission() {
		MemberDto member = AuthHolder.getMember();
		if (member == null) {
			throw new ForbiddenException("尚未登入");
		}
		if (!"ADMIN".equals(member.getRole())) {
			throw new ForbiddenException("沒有權限");
		}
	}

	// 判斷有無登入且是否串改他人id
	public static void checkUserLogin(Integer memberId) {
		MemberDto member = AuthHolder.getMember();
		if (member == null) {
			throw new ForbiddenException("尚未登入");
		}
		if (!memberId.equals(member.getMemberId())) {
			throw new ForbiddenException("無權限修改其他會員的資料");
		}
	}

	// 判斷有無登入且是否串改他人地址
	public static void checkUserLoginForAddress(Integer addressId, AddressRepository addressRepository) {
		MemberDto member = AuthHolder.getMember();
		if (member == null) {
			throw new ForbiddenException("尚未登入");
		}

		AddressBean address = addressRepository.findById(addressId)
				.orElseThrow(() -> new NotFoundException("找不到該地址"));

		if (!address.getMember().getMemberId().equals(member.getMemberId())) {
			throw new ForbiddenException("無權限修改其他會員的地址");
		}
	}

}
