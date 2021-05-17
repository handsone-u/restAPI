package com.handsone.restAPI.domain.member.service;

import com.handsone.restAPI.domain.member.domain.Member;
import com.handsone.restAPI.domain.member.dto.MemberDto;
import com.handsone.restAPI.infra.address.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.handsone.restAPI.domain.member.dto.MemberDto.toMemberDto;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void signUp() {
        Member member1 = new Member("hello", "1234", "myNick", new Address("si", "gu", "dong"));
        Member member2 = new Member("hello2", "1234", "myNick", new Address("si", "gu", "dong"));
        Member member3 = new Member("hello3", "1234", "i", new Address("si", "gu", "dong"));
        Member member4 = new Member("hello4", "1234", "will", new Address("si", "gu", "dong"));

        MemberDto memberDto1 = toMemberDto(member1);
        MemberDto memberDto2 = toMemberDto(member2);
        MemberDto memberDto3 = toMemberDto(member3);
        MemberDto memberDto4 = toMemberDto(member4);

        Member save1 = memberService.signUp(memberDto1);
        assertThat(save1).isNotNull();
        Member save2 = memberService.signUp(memberDto2);
        Member save3 = memberService.signUp(memberDto3);
        Member save4 = memberService.signUp(memberDto4);
        Member save5 = memberService.signUp(memberDto4);
        assertThat(save5).isNull();

        assertThat(save1.getUserId()).isEqualTo(member1.getUserId());
        assertThat(save2.getUserId()).isEqualTo(member2.getUserId());
        assertThat(save3.getUserId()).isEqualTo(member3.getUserId());
        assertThat(save4.getUserId()).isEqualTo(member4.getUserId());
    }

    @Test
    void logIn() {
        Member member1 = new Member("hello12", "1234", "myNick", new Address("si", "gu", "dong"));
        Member member2 = new Member("hello22", "1234", "myNick", new Address("si", "gu", "dong"));
        Member member3 = new Member("hello33", "1234", "i", new Address("si", "gu", "dong"));
        Member member4 = new Member("hello44", "1234", "will", new Address("si", "gu", "dong"));

        MemberDto memberDto1 = toMemberDto(member1);
        MemberDto memberDto2 = toMemberDto(member2);
        MemberDto memberDto3 = toMemberDto(member3);
        MemberDto memberDto4 = toMemberDto(member4);

        Member member11 = memberService.signUp(memberDto1);
        Member member22 = memberService.signUp(memberDto2);
        Member member33 = memberService.signUp(memberDto3);
        Member member44 = memberService.signUp(memberDto4);

        Member member = memberService.logIn(member11.getUserId(), member11.getPassword());
        assertThat(member).isNotNull();
        assertThat(member.getId()).isEqualTo(member11.getId());

        member = memberService.logIn(member11.getUserId(), "wrong");
        assertThat(member).isNull();

        member = memberService.logIn("noId", member11.getPassword());
        assertThat(member).isNull();
    }

    @Test
    void getOneAndUpdate() {
        Member member1 = new Member("userId!", "npt", "NotUpdated!", new Address("si", "gu", "dong"));
        memberService.signUp(toMemberDto(member1));
        Optional<Member> byId = memberService.findById(1L);
        assertThat(byId.isPresent()).isTrue();
        assertThat(byId.get().getId()).isEqualTo(1L);

        // test
        byId.get().changeNickName("change!");
        byId.get().changePassword("change!");
    }
}