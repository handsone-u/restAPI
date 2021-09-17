package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.MemberDto;
import com.handsone.restAPI.service.MemberService;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.ErrorResponse;
import com.handsone.restAPI.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberAPIController {

    private final MemberService memberService;

    @PostMapping("/duplicate")
    public ResponseEntity<Boolean> checkDuplicate(@RequestParam String userId) {
        return null;
    }

    @PostMapping("")
    public ResponseEntity<? extends Response> signUp(@RequestBody MemberDto memberDto) {
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<? extends Response> logIn(@RequestBody MemberDto memberDto) {
        return null;
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<? extends Response> getOne(@PathVariable Long memberId) {
        return null;
    }
}
