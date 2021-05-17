package com.handsone.restAPI.domain.dogLost.service;

import com.handsone.restAPI.global.request.DogDto;
import com.handsone.restAPI.domain.Gender;
import com.handsone.restAPI.domain.dogLost.domain.DogLost;
import com.handsone.restAPI.domain.member.domain.Member;
import com.handsone.restAPI.domain.member.dto.MemberDto;
import com.handsone.restAPI.domain.member.service.MemberService;
import com.handsone.restAPI.infra.address.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.handsone.restAPI.domain.member.dto.MemberDto.toMemberDto;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class DogLostServiceTest {

    @Autowired
    private DogLostService dogLostService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private EntityManager entityManager;

    private DogLost dogLost;

    @BeforeEach
    void before() {
        Member member1 = new Member("hello", "1234", "myNick", new Address("si", "gu", "dong"));
        Member member2 = new Member("hello2", "1234", "myNick", new Address("si", "gu", "dong"));
        Member member3 = new Member("hello3", "1234", "i", new Address("si", "gu", "dong"));
        Member member4 = new Member("hello4", "1234", "will", new Address("si", "gu", "dong"));

        MemberDto memberDto1 = toMemberDto(member1);
        MemberDto memberDto2 = toMemberDto(member2);
        MemberDto memberDto3 = toMemberDto(member3);
        MemberDto memberDto4 = toMemberDto(member4);

        Member signed1 = memberService.signUp(memberDto1);
        Member signed2 = memberService.signUp(memberDto2);
        Member signed3 = memberService.signUp(memberDto3);
        Member signed4 = memberService.signUp(memberDto4);
        DogDto dogDto = new DogDto(signed1.getId(), signed1.getNickName(), "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));

//        dogLost = dogLostService.upload(dogDto);
    }

    @Test
    void save() {
        assertThat(dogLost).isNotNull();
        System.out.println("dogLost = " + dogLost);

        Optional<DogLost> byId = dogLostService.findById(dogLost.getId());
        assertThat(byId.isPresent()).isTrue();
    }

    @Test
    void getByMemberId() {

        Member member1 = new Member("hello1234", "1234", "myNick", new Address("si", "gu", "dong"));
        member1 = memberService.signUp(toMemberDto(member1));

        DogDto dogDto1 = new DogDto(member1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto2 = new DogDto(member1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto3 = new DogDto(member1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto4 = new DogDto(member1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));

//        DogLost up1 = dogLostService.upload(dogDto1);
//        DogLost up2 = dogLostService.upload(dogDto2);
//        DogLost up3 = dogLostService.upload(dogDto3);
//        DogLost up4 = dogLostService.upload(dogDto4);

        List<DogLost> all = dogLostService.findAllByMemberId(member1.getId());
        assertThat(all.size()).isEqualTo(4);
    }

}