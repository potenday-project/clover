package com.clover.habbittracker.domain.member.api;

import static com.clover.habbittracker.global.dto.ResponseType.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clover.habbittracker.domain.member.dto.MemberRequest;
import com.clover.habbittracker.domain.member.dto.MemberResponse;
import com.clover.habbittracker.domain.member.service.MemberService;
import com.clover.habbittracker.global.dto.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/me")
	ResponseEntity<BaseResponse<MemberResponse>> getMyProfile(Authentication authentication) {
		Long memberId = (Long)authentication.getPrincipal();
		MemberResponse data = memberService.getProfile(memberId);
		return new ResponseEntity<>(BaseResponse.of(data, MEMBER_READ), HttpStatus.OK);
	}

	@PutMapping("/me")
	ResponseEntity<BaseResponse<MemberResponse>> updateMyProfile(Authentication authentication,
		@RequestBody MemberRequest request) {
		Long memberId = (Long)authentication.getPrincipal();
		MemberResponse data = memberService.updateProfile(memberId, request);
		return new ResponseEntity<>(BaseResponse.of(data, MEMBER_UPDATE), HttpStatus.OK);
	}

	@DeleteMapping("/me")
	ResponseEntity<BaseResponse<Void>> deleteMember(Authentication authentication) {
		memberService.deleteProfile((Long)authentication.getPrincipal());
		return new ResponseEntity<>(BaseResponse.of(null, MEMBER_DELETE), HttpStatus.NO_CONTENT);
	}
}
