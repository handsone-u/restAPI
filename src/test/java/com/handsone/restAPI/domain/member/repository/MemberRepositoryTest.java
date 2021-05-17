package com.handsone.restAPI.domain.member.repository;

import com.handsone.restAPI.domain.member.domain.Member;
import com.handsone.restAPI.infra.address.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        Member member1 = new Member("hello", "1234", "myNick", new Address("si", "gu", "dong"));
        Member member2 = new Member("hello2", "1234", "myNick", new Address("si", "gu", "dong"));
        Member member3 = new Member("hello3", "1234", "i", new Address("si", "gu", "dong"));
        Member member4 = new Member("hello4", "1234", "will", new Address("si", "gu", "dong"));

        Member save1 = memberRepository.save(member1);
        Member save2 = memberRepository.save(member2);
        Member save3 = memberRepository.save(member3);
        Member save4 = memberRepository.save(member4);

        assertThat(save1.getId()).isEqualTo(member1.getId());
        assertThat(save1.getNickName()).isEqualTo(member1.getNickName());
        assertThat(save2.getId()).isEqualTo(member2.getId());
        assertThat(save3.getId()).isEqualTo(member3.getId());
        assertThat(save3.getNickName()).isEqualTo(member3.getNickName());
        assertThat(save4.getId()).isEqualTo(member4.getId());

        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(4);
    }
}