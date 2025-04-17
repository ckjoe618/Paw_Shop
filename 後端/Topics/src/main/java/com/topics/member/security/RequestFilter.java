package com.topics.member.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.repository.MemberRepository;
import com.topics.member.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");
		if (header != null) {
			String token = header.substring(7);
			String memberId = JwtUtil.getSubject(token);
			
			MemberDto member = memberRepository.findById(Integer.parseInt(memberId))
					.map(m -> new MemberDto(m))
					.orElseThrow(()-> new RuntimeException("找不到使用者"));
			
			AuthHolder.setMember(member);
		}

		chain.doFilter(request, response);
	}

}
