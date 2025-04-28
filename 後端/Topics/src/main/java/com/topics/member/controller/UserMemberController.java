package com.topics.member.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.topics.member.model.dto.AddressDto;
import com.topics.member.model.dto.AuthDto;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.AddressBean;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.service.AddressService;
import com.topics.member.model.service.UserMemberService;
import com.topics.utils.ResponseUtil;
import com.topics.utils.SecurityUtil;

@RestController
@RequestMapping("/api/user")
public class UserMemberController {

	@Autowired
	private UserMemberService userMemberService;

	@Autowired
	private AddressService addressService;

	@PostMapping("/member")
	public ResponseEntity<?> insertMember(@RequestBody MemberBean entity) {
		MemberDto member = userMemberService.insertMember(entity);
		return ResponseUtil.created(member);
	}

	@PutMapping("/member/{memberId}")
	public ResponseEntity<?> updateMemberById(@PathVariable Integer memberId,
			@RequestPart("data") MemberBean entity,
			@RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
		SecurityUtil.checkUserLogin(memberId);
		MemberDto member = userMemberService.findMemberById(memberId);
		String oldPhoto = member.getMemberPhoto();

		if (file != null && !file.isEmpty()) {

			if (oldPhoto != null && oldPhoto.contains("/images/uploads/")) {
				int idx = oldPhoto.lastIndexOf("/images/uploads/");
				String oldFileName = oldPhoto.substring(idx + "/images/uploads/".length());
				Path deletePath = Paths.get("uploads/images/", oldFileName);
				Files.deleteIfExists(deletePath);
			}

			String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
			Path uploadPath = Paths.get("uploads/images/");
			Files.createDirectories(uploadPath);
			Path filePath = uploadPath.resolve(filename);
			Files.write(filePath, file.getBytes());

			String photoPath = "/images/uploads/" + filename;
			entity.setMemberPhoto(photoPath);
		}

		MemberDto memberNew = userMemberService.updateMemberById(memberId, entity);
		return ResponseUtil.success(memberNew);
	}

	@PutMapping("/member/json/{memberId}")
	public ResponseEntity<?> updateMemberByIdWithJson(@PathVariable Integer memberId, @RequestBody MemberBean entity) {
		SecurityUtil.checkUserLogin(memberId);
		MemberDto member = userMemberService.updateMemberById(memberId, entity);
		return ResponseUtil.success(member);
	}

	@GetMapping("/address/{memberId}")
	public ResponseEntity<?> findAddressByMemberId(@PathVariable Integer memberId) {
		SecurityUtil.checkUserLogin(memberId);
		List<AddressDto> addressList = addressService.findAddressByMemberId(memberId);
		return ResponseUtil.success(addressList);
	}

	@PostMapping("/address")
	public ResponseEntity<?> insertAddressByMemberId(@RequestBody AddressDto entity) {
		SecurityUtil.checkUserLogin(entity.getMemberId());
		AddressDto address = addressService.insertAddress(entity);
		return ResponseUtil.created(address);
	}

	@PutMapping("/address/{memberId}")
	public ResponseEntity<?> updateAddressById(@PathVariable Integer memberId, @RequestBody AddressBean entity) {
		SecurityUtil.checkUserLogin(memberId);
		AddressDto address = addressService.updateAddressByAddressId(entity);
		return ResponseUtil.success(address);
	}

	@PutMapping("/address/default/{addressId}")
	public ResponseEntity<?> updateDefaultAddress(@PathVariable Integer addressId) {
		SecurityUtil.checkUserLoginForAddress(addressId);
		MemberDto member = addressService.updateDefaultAddress(addressId);
		return ResponseUtil.success(member);
	}

	@DeleteMapping("/address/{addressId}")
	public ResponseEntity<?> updateAddressById(@PathVariable Integer addressId) {
		SecurityUtil.checkUserLoginForAddress(addressId);
		addressService.deleteAddressById(addressId);
		return ResponseUtil.success("刪除地址成功");
	}

	@PutMapping("/password/{memberId}")
	public ResponseEntity<?> putMethodName(@PathVariable Integer memberId, @RequestBody AuthDto entity) {
		SecurityUtil.checkUserLogin(memberId);
		userMemberService.updatePasswordByMemberId(memberId, entity);
		return ResponseUtil.success("修改密碼成功");
	}
}
