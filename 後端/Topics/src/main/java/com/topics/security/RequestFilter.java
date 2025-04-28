package com.topics.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.topics.exception.NotFoundException;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.repository.MemberRepository;
import com.topics.utils.JwtUtil;
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
		try {
			String header = request.getHeader("Authorization");
			if (header != null && header.startsWith("Bearer ")) {
				String token = header.substring(7);

				if (JwtUtil.isTokenValid(token)) {
					String memberId = JwtUtil.getSubject(token);
					MemberDto member = memberRepository.findById(Integer.parseInt(memberId))
							.map(m -> new MemberDto(m))
							.orElseThrow(() -> new NotFoundException("找不到使用者"));

					AuthHolder.setMember(member);
				}
			}
			chain.doFilter(request, response);
		} finally {
			AuthHolder.clear(); // 清除 ThreadLocal，避免記憶體洩漏
		}
	}
}
