package com.handsone.restAPI.domain.member.controller;

import com.handsone.restAPI.domain.member.domain.Member;
import com.handsone.restAPI.domain.member.dto.MemberDto;
import com.handsone.restAPI.domain.member.service.MemberService;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.ErrorResponse;
import com.handsone.restAPI.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.handsone.restAPI.domain.member.dto.MemberDto.toMemberDto;

@RestController
@RequiredArgsConstructor
public class MemberAPIController {

    private final MemberService memberService;

    @PostMapping("member")
    public ResponseEntity<? extends Response> signUp(@RequestBody MemberDto memberDto) {
        System.out.println("memberDto = " + memberDto);
        Member member = memberService.signUp(memberDto);
        if (member == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("이미 있는 아이디"));
        else return ResponseEntity.ok().body(new CommonResponse<Long>(member.getId()));
    }

    @PostMapping("member/login")
    public ResponseEntity<? extends Response> logIn(@RequestBody MemberDto memberDto) {
        System.out.println("memberDto = " + memberDto);
        Member member = memberService.logIn(memberDto.getUserId(), memberDto.getPassword());
        if(member == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("로그인 실패"));
        else return ResponseEntity.ok().body(new CommonResponse<Long>(member.getId()));
    }

    @GetMapping("member/{memberId}")
    public ResponseEntity<? extends Response> getOne(@PathVariable Long memberId) {
        Optional<Member> byId = memberService.findById(memberId);
        if(byId.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("로그인 만료"));
        else return ResponseEntity.ok().body(new CommonResponse<MemberDto>(toMemberDto(byId.get())));
    }
}
