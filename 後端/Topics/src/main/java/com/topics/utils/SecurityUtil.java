package com.topics.utils;

import com.topics.exception.ForbiddenException;
import com.topics.member.model.dto.MemberDto;
import com.topics.security.AuthHolder;

public class SecurityUtil {

	public static void checkAdminPermission() {
		MemberDto member = AuthHolder.getMember();
		if (member == null) {
			throw new ForbiddenException("尚未登入");
		}
		if (!"ADMIN".equals(member.getRole())) {
			throw new ForbiddenException("沒有權限");
		}
	}

	public static void checkUserLogin(Integer id) {
		MemberDto member = AuthHolder.getMember();
		if (member == null) {
			throw new ForbiddenException("尚未登入");
		}
		if(!id.equals(member.getMemberId())) {
			throw new ForbiddenException("無權限修改其他會員的資料");
		}
	}

}
