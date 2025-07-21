package com.core.backend_advanced.api.members.controller;

import com.core.backend_advanced.api.members.dto.LoginRequestDto;
import com.core.backend_advanced.api.members.dto.SignupRequestDto;
import com.core.backend_advanced.api.members.service.MemberService;
import com.core.backend_advanced.common.response.ApiResponse;
import com.core.backend_advanced.common.response.SuccessStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class MemberController {
    private final MemberService memberService;

    @PostMapping("auth/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@Valid @RequestBody SignupRequestDto request) {
        memberService.signupMember(request);

        return ApiResponse.successOnly(SuccessStatus.MEMBER_SIGNUP_SUCCESS);
    }

    @PostMapping("auth/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@Valid @RequestBody LoginRequestDto request) {
        Map<String, Object> result = memberService.loginMember(request);

        return ApiResponse.success(SuccessStatus.LOGIN_SUCCESS, result);
    }
}
