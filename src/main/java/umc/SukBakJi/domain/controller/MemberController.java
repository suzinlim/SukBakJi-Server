package umc.SukBakJi.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.SukBakJi.domain.model.dto.member.MemberRequestDto;
import umc.SukBakJi.domain.model.dto.member.MemberResponseDto;
import umc.SukBakJi.domain.service.MemberService;
import umc.SukBakJi.global.apiPayload.ApiResponse;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/user")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/profile")
    @Operation(summary = "프로필 설정", description = "회원가입 이후 진행되는 프로필 설정입니다.")
    public ApiResponse<?> setMemberProfile(@RequestHeader("Authorization") String token,
                                           @RequestBody MemberRequestDto.ProfileDto profileDto) {
        String jwtToken = token.substring(7);
        MemberResponseDto.ProfileResultDto responseDto = memberService.setMemberProfile(jwtToken, profileDto);
        return ApiResponse.onSuccess(responseDto);
    }

    @PutMapping("/profile")
    @Operation(summary = "프로필 수정", description = "설정에서 사용자 정보를 수정합니다.")
    public ApiResponse<?> modifyMemberProfile(@RequestHeader("Authorization") String token,
                                              @RequestBody MemberRequestDto.ModifyProfileDto profileDto) {
        String jwtToken = token.substring(7);
        MemberResponseDto.ProfileResultDto responseDto = memberService.modifyMemberProfile(jwtToken, profileDto);
        return ApiResponse.onSuccess(responseDto);
    }

    @GetMapping("/mypage")
    @Operation(summary = "마이페이지", description = "사용자가 설정한 프로필 정보를 확인합니다.")
    public ApiResponse<?> getMyPage(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        MemberResponseDto.ProfileResultDto responseDto = memberService.getMemberProfile(jwtToken);
        return ApiResponse.onSuccess(responseDto);
    }
}