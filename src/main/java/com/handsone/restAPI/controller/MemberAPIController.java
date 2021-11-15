package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.MemberDto;
import com.handsone.restAPI.service.MemberService;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberAPIController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @PostMapping("/check-duplicated")
    public Boolean checkDuplicated(@RequestBody String userId) {
        return memberService.checkUserIdDuplicated(userId);
    }

    @PostMapping("")
    public ResponseEntity<? extends Response> signUp(@RequestBody MemberDto memberDto) {
        Member member = memberDto.toEntity();
        return ResponseEntity.ok(new CommonResponse<>
                (memberService.signUp(member).getId()));
    }

    @PostMapping("/login")
    public ResponseEntity<? extends Response> logIn(@RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(new CommonResponse<>
                (memberService.logIn(memberDto.getUserId(), memberDto.getPassword()).getId()));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<? extends Response> getOne(@PathVariable Long memberId) {
        Member one = memberService.findOne(memberId);
        return ResponseEntity.ok(new CommonResponse<>(modelMapper.map(one, MemberDto.class)));
    }

    @GetMapping("/members")
    public Page<MemberDto> getAllMembers(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Member> all = memberService.findAll(pageable);
        return all.map(m -> modelMapper.map(m, MemberDto.class));
    }

    @GetMapping("/members-slice")
    public Slice<MemberDto> getAllMembersSlice(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Slice<Member> all = memberService.findAllSlice(pageable);
        return all.map(m -> modelMapper.map(m, MemberDto.class));
    }
}
