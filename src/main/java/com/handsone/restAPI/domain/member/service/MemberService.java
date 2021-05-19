package com.handsone.restAPI.domain.member.service;

import com.handsone.restAPI.domain.member.domain.Member;
import com.handsone.restAPI.domain.member.dto.MemberDto;
import com.handsone.restAPI.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signUp(MemberDto memberDto) {
        if (userIdExists(memberDto.getUserId())) { return null; }
        Member savedMember = memberRepository.save(memberDto.toMember());
        return savedMember;
    }

    @Transactional(readOnly = true)
    public Member logIn(String userId, String password) {
        Optional<Member> byUserId = memberRepository.findByUserId(userId);
        if(byUserId.isEmpty()) return null;
        else if(byUserId.get().getPassword() != password) return null;
        else return byUserId.get();
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public boolean userIdExists(String userId) {
        Optional<Member> byUserId = memberRepository.findByUserId(userId);
        if(byUserId.isPresent()) return true;
        else return false;
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
