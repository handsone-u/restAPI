package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.exception.ClientException;
import com.handsone.restAPI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.handsone.restAPI.error.ErrorCode.DUPLICATE_RESOURCE;
import static com.handsone.restAPI.error.ErrorCode.NOTFOUND_MEMBER;

@Service @Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false, rollbackFor = {RuntimeException.class, Error.class})
    public Member signUp(Member member) {
        memberRepository.findByUserId(member.getUserId()).ifPresent(m -> {
            throw new ClientException("Member's userId already exists. USERID : " + member.getUserId(), DUPLICATE_RESOURCE);});
        return memberRepository.save(member);
    }
    
    public Member logIn(String userId, String password) {
        return memberRepository.findByUserIdAndPassword(userId, password)
                .orElseThrow(() -> new ClientException("Cannot find Member's info.", NOTFOUND_MEMBER));
    }

    public Member findOne(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ClientException("Cannot find Member's info.", NOTFOUND_MEMBER));
    }

    public Page<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Slice<Member> findAllSlice(Pageable pageable) {
        return memberRepository.findAllBy(pageable);
    }
    
    public Boolean checkUserIdDuplicated(String userId) {
        return memberRepository.findByUserId(userId).isPresent();
    }

    public Long count() {
        return memberRepository.count();
    }
}
