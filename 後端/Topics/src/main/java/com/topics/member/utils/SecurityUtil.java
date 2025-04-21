package com.topics.member.utils;

import com.topics.member.exception.ForbiddenException;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.security.AuthHolder;

public class SecurityUtil {

	public static void checkAdminPermission() {
        MemberDto member = AuthHolder.getMember();
        if (member == null) throw new ForbiddenException("尚未登入");
        if (!"ADMIN".equals(member.getRole())) throw new ForbiddenException("沒有權限");
    }
	
	public static void checkUserLogin() {
		if (AuthHolder.getMember() == null) {
			throw new ForbiddenException("尚未登入");
		}
	}
}
