package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.MemberDto;
import com.handsone.restAPI.exception.ClientException;
import com.handsone.restAPI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.handsone.restAPI.error.ErrorCode.*;

@Service @Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = false, rollbackFor = {RuntimeException.class, Error.class})
    public Member signUp(MemberDto memberDto) {
        memberRepository.findByUserId(memberDto.getUserId()).ifPresent(m -> {
            throw new ClientException("Member's userId already exists. USERID : " + memberDto.getUserId(), DUPLICATE_RESOURCE);});
        return memberRepository.save(memberDto.toEntity());
    }
    
    public Member logIn(String userId, String password) throws ClientException {
        return memberRepository.findByUserIdAndPassword(userId, password)
                .orElseThrow(() -> new ClientException("Cannot find Member's info.", NOTFOUND_MEMBER));
    }

    public Member findOne(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ClientException("Cannot find Member's info.", NOTFOUND_MEMBER));
    }

    /**
     * true : userId already Exists.
     */
    public Boolean checkUserIdDuplicated(String userId) {
        return memberRepository.findByUserId(userId).isPresent();
    }

    public Long count() {
        return memberRepository.count();
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
