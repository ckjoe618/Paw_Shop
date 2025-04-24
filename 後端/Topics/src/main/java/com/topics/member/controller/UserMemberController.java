package com.topics.member.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.service.UserMemberService;
import com.topics.member.utils.ResponseUtil;
import com.topics.member.utils.SecurityUtil;

@RestController
@RequestMapping("/api/user")
public class UserMemberController {

	@Autowired
	private UserMemberService userMemberService;

	@PutMapping("/member/{id}")
	public ResponseEntity<?> updateMemberById(@PathVariable Integer id,
			@RequestPart("data") MemberBean entity,
			@RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
		SecurityUtil.checkUserLogin(id);
		MemberDto member = userMemberService.findMemberById(id);
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

		MemberDto memberNew = userMemberService.updateMemberById(id, entity);
		return ResponseUtil.success(memberNew);
	}

	@PutMapping("/member/json/{id}")
	public ResponseEntity<?> updateMemberByIdWithJson(@PathVariable Integer id, @RequestBody MemberBean entity) {
		MemberDto member = userMemberService.updateMemberById(id, entity);
		return ResponseUtil.success(member);
	}

	@PostMapping("/member")
	public ResponseEntity<?> insertMember(@RequestBody MemberBean entity) {
		MemberDto member = userMemberService.insertMember(entity);
		return ResponseUtil.created(member);
	}

}
