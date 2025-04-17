package com.topics.member.security;

import com.topics.member.model.dto.MemberDto;

/**
 * 這裡可以放登入的使用者是誰
 */
public class AuthHolder {

	private static final ThreadLocal<MemberDto> THREAD_LOCAL = new ThreadLocal<>();

	public static void setMember(MemberDto memberDto) {
		THREAD_LOCAL.set(memberDto);
	}

	public static MemberDto getMember() {
		return THREAD_LOCAL.get();
	}

	public static void clear() {
		THREAD_LOCAL.remove();
	}

}
