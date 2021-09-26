package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.MemberDto;
import com.handsone.restAPI.exception.ClientException;
import com.handsone.restAPI.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {
    @Autowired private MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired private EntityManager em;

    @Test @Transactional(readOnly = true)
    public void jpaTest() {
        Member member1 = em.find(Member.class, 1L);
        Member member2 = em.find(Member.class, 1L);

        System.out.println("member1 = " + member1);
        System.out.println("member2 = " + member2);
        assertThat(member1 == member2).isTrue();
        assertThat(member1).isEqualTo(member2);
        assertThat(member1).isSameAs(member2);
    }

    @BeforeEach
    public void before() {
        System.out.println("MemberServiceTest.before");
        MemberDto memberDto1 = new MemberDto(null, "id1", "password1", "nick1", null);
        MemberDto memberDto2 = new MemberDto(null, "id2", "password2", "nick2", null);
        MemberDto memberDto3 = new MemberDto(null, "id3", "password3", "nick3", null);
        memberService.signUp(memberDto1);
        memberService.signUp(memberDto2);
        memberService.signUp(memberDto3);
        assertThat(memberRepository.count()).isEqualTo(3);
    }

    @AfterEach
    public void after() {
        System.out.println("MemberServiceTest.after");
        memberRepository.deleteAll();
    }

    @Test
    public void testSignUp() {
        // given
        long count = memberService.count();
        MemberDto memberDto = new MemberDto(0l, "idTest", "password1", "nick1", null);
        // when
        Member member = memberService.signUp(memberDto);
        // then 1
        Member findMember = em.find(Member.class, member.getId());
        assertThat(findMember.getId())
                .isNotNull()
                .isEqualTo(member.getId());
        // then 2
        Member findMember2 = memberRepository.findByUserId(memberDto.getUserId()).get();
        assertThat(findMember2.getId())
                .isNotNull()
                .isEqualTo(member.getId());
        // then 3
        assertThat(memberService.count()).isEqualTo(count + 1);
        assertThat(findMember.getId()).isEqualTo(findMember2.getId());
    }

    @Test
    public void testDuplicatedUserId() {
        // given
        long count = memberService.count();
        MemberDto already = new MemberDto(null, "id3", "password3", "nick3", null);
        // when
        assertThatThrownBy(() -> memberService.signUp(already))
                .isInstanceOf(ClientException.class)
                .hasMessage("Member's userId already exists. ".concat(already.getUserId()));
        // then
        assertThat(memberRepository.findByUserId(already.getUserId())).isPresent();
        assertThat(memberService.count()).isEqualTo(3);
    }

    @Test
    public void logInSuccess() {
        // given
        MemberDto memberDto = new MemberDto(null, "idLogin", "password1", "nick1", null);
        Member sign = memberService.signUp(memberDto);
        // when
        Member member = memberService.logIn(memberDto.getUserId(), memberDto.getPassword());
        // then
        assertThat(sign.getId()).isEqualTo(member.getId());
    }

    @Test
    public void logInFail() {
        // given
        MemberDto memberDto = new MemberDto(null, "idLogin", "password1", "nick1", null);
        Member sign = memberService.signUp(memberDto);
        // when & then
        assertThatThrownBy(() -> memberService.logIn(memberDto.getUserId(), "wrong"))
                .isInstanceOf(ClientException.class)
                .hasMessage("Cannot find Member's info.");
        assertThatThrownBy(() -> memberService.logIn("wrong", memberDto.getPassword()))
                .isInstanceOf(ClientException.class);
        assertThatThrownBy(() -> memberService.logIn("wrong", "wrong"))
                .isInstanceOf(ClientException.class);
    }
}