package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.MemberDto;
import com.handsone.restAPI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public Member signUp(MemberDto memberDto) {
        if (userIdExists(memberDto.getUserId())) { return null; }
        Member savedMember = memberRepository.save(memberDto.toMember());
        return savedMember;
    }

    public Member logIn(String userId, String password) {
        Optional<Member> byUserId = memberRepository.findByUserId(userId);
        if(byUserId.isEmpty()) return null;
        else if(!byUserId.get().getPassword().equals(password)) return null;
        else return byUserId.get();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public boolean userIdExists(String userId) {
        Optional<Member> byUserId = memberRepository.findByUserId(userId);
        if(byUserId.isPresent()) return true;
        else return false;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
