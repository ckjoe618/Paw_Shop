package com.topics.member.model.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.topics.exception.ForbiddenException;
import com.topics.exception.LoginFailException;
import com.topics.member.model.dto.AddressDto;
import com.topics.member.model.dto.AuthDto;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.AddressBean;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.repository.AddressRepository;
import com.topics.member.model.repository.MemberRepository;

@Service
public class AuthService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AddressRepository addressRepository;

	public MemberDto login(AuthDto info) {
		MemberBean member = memberRepository.findByAccount(info.getLoginId());
		if (member == null) {
			member = memberRepository.findByEmail(info.getLoginId());
			if (member == null) {
				member = memberRepository.findByPhone(info.getLoginId());
			}
		}
		if (member == null) {
			throw new LoginFailException("登入失敗，帳號或 email 錯誤");
		}
		if (!info.getPassword().equals(member.getPassword())) {
			throw new LoginFailException("登入失敗，密碼錯誤");
		}
		if(!member.isActiveStatus()) {
			throw new ForbiddenException("帳號已被停用，請聯繫客服");
		}

		AddressBean address = addressRepository.findActiveAddressByMemberId(member.getMemberId());
		if (address == null) {
			return new MemberDto(member);
		}
		return new MemberDto(member, new AddressDto(address));
	}

	@SuppressWarnings("rawtypes")
	public MemberDto loginByGoogle(String code, String clientId, String clientSecret, String redirectUri) {
		RestTemplate restTemplate = new RestTemplate();

		// 換取 access_token
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", clientId);
		params.add("client_secret", clientSecret);
		params.add("code", code);
		params.add("redirect_uri", redirectUri);
		params.add("grant_type", "authorization_code");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

		ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
				"https://oauth2.googleapis.com/token", requestEntity, Map.class);

		String accessToken = tokenResponse.getBody().get("access_token").toString();

		// 用 access_token 拿 userinfo
		HttpHeaders userHeaders = new HttpHeaders();
		userHeaders.setBearerAuth(accessToken);
		HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);

		ResponseEntity<Map> userResponse = restTemplate.exchange(
				"https://www.googleapis.com/oauth2/v3/userinfo",
				HttpMethod.GET,
				userRequest,
				Map.class);

		Map userInfo = userResponse.getBody();
		String email = (String) userInfo.get("email");
		String name = (String) userInfo.get("name");

		MemberBean member = memberRepository.findByEmail(email);

		if (member == null) {
			MemberBean memberNew = new MemberBean();
			memberNew.setMemberName(name);
			memberNew.setEmail(email);
			memberNew.setAccount(email.split("@")[0] + "_google");
			memberNew.setPassword(UUID.randomUUID().toString());
			memberNew.setGender("女");
			memberNew.setIdno("A2" + String.format("%08d", new Random().nextInt(100000000)));
			memberNew.setPhone("09" + String.format("%08d", new Random().nextInt(100000000)));
			memberNew.setBirthDate(LocalDate.of(2000, 1, 1));

			MemberBean saved = memberRepository.save(memberNew);
			return new MemberDto(saved);
		}
		return new MemberDto(member);
	}
}
