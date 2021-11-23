package com.handsone.restAPI.repository;

import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.infra.address.Address;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// 각각의 TEST 는 자동으로 Roll Back 됨.
@DataJpaTest
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ActiveProfiles(profiles = "dev")
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;
    static List<Member> members = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        System.out.println("MemberRepositoryTest.beforeAll");

        members.add(new Member("id", "pass1", "nick1", new Address("gu")));
        members.add(new Member("id2", "pass1", "nick4", new Address("gu")));
        members.add(new Member("id3", "pass1", "nick5", new Address("gu")));
    }

    @AfterAll
    static void afterAll() {
        System.out.println("MemberRepositoryTest.afterAll");
    }

    @Test
    void save() {
        saveMember();
    }

    @Test
    void findByUserId() {
        saveMember();
        // FAIL
        Optional<Member> fail = memberRepository.findByUserId("no");
        assertThat(fail.isEmpty()).isTrue();

        // SUCCESS
        Optional<Member> success = memberRepository.findByUserId(members.get(0).getUserId());
        assertThat(success.isPresent()).isTrue();
        Member member = success.get();
        assertThat(member).isNotNull();
        assertMember(members.get(0), member);
    }

    @Test
    void findByUserIdAndPassword() {
        saveMember();
        Member failMember = new Member("fail", "fail", "nuck", null);
        Member successMember = members.get(0);
        // FAIL
        Optional<Member> fail = memberRepository.findByUserIdAndPassword(failMember.getUserId(), failMember.getPassword());
        assertThat(fail.isEmpty()).isTrue();

        // SUCCESS
        Optional<Member> success = memberRepository.findByUserIdAndPassword(successMember.getUserId(), successMember.getPassword());
        assertThat(success.isPresent()).isTrue();

        Member successResult = success.get();
        assertThat(successResult).isNotNull();
        assertMember(successMember, successResult);
    }

    private void saveMember() {
        for (Member member : members) {
            Member save = memberRepository.save(member);
            assertMember(member, save);
        }
    }

    private void assertMember(Member actual, Member expect) {
        assertThat(actual.getUserId()).isEqualTo(expect.getUserId());
        assertThat(actual.getAddress().getGu()).isEqualTo(expect.getAddress().getGu());
        assertThat(actual.getPassword()).isEqualTo(expect.getPassword());
        assertThat(actual.getNickName()).isEqualTo(expect.getNickName());
    }
}