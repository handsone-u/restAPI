package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.MemberDto;
import com.handsone.restAPI.service.MemberService;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.ErrorResponse;
import com.handsone.restAPI.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberAPIController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @PostMapping("/check-duplicated")
    public Boolean checkDuplicated(@RequestBody String userId) {
        return memberService.checkUserIdDuplicated(userId);
    }

    @PostMapping("")
    public ResponseEntity<? extends Response> signUp(@RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(new CommonResponse<Long>
                (memberService.signUp(memberDto).getId()));
    }

    @PostMapping("/login")
    public ResponseEntity<? extends Response> logIn(@RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(new CommonResponse<Long>
                (memberService.logIn(memberDto.getUserId(), memberDto.getPassword()).getId()));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<? extends Response> getOne(@PathVariable Long memberId) {
        Member one = memberService.findOne(memberId);
        return ResponseEntity.ok(new CommonResponse<MemberDto>(modelMapper.map(one, MemberDto.class)));
    }
}
