package com.handsone.restAPI.domain.dogLost.repository;

import com.handsone.restAPI.domain.BoardStatus;
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
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.handsone.restAPI.global.request.DogDto.toDogDto;
import static com.handsone.restAPI.domain.dogLost.domain.DogLost.createDogLost;
import static com.handsone.restAPI.domain.member.dto.MemberDto.toMemberDto;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DogLostRepositoryTest {

    @Autowired
    private DogLostRepository dogLostRepository;
    @Autowired
    private DogLostSlicingRepository dogLostSlicingRepository;
    @Autowired
    private MemberService memberService;

    Member signed1;
    Member signed2;
    Member signed3;
    Member signed4;

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
        signed1 = memberService.signUp(memberDto1);
        signed2 = memberService.signUp(memberDto2);
        signed3 = memberService.signUp(memberDto3);
        signed4 = memberService.signUp(memberDto4);
        DogDto dogDto = new DogDto(signed1.getId(), signed1.getNickName(), "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogLost dogLost1 = createDogLost(signed1, dogDto);
        DogLost dogLost2 = createDogLost(signed2, dogDto);
        DogLost dogLost3 = createDogLost(signed3, dogDto);
        DogLost dogLost4 = createDogLost(signed4, dogDto);
        DogLost dogLost5 = createDogLost(signed1, dogDto);
        DogLost dogLost6 = createDogLost(signed2, dogDto);
        dogLostRepository.save(dogLost1);
        dogLostRepository.save(dogLost2);
        dogLostRepository.save(dogLost3);
        dogLostRepository.save(dogLost4);
        dogLostRepository.save(dogLost5);
        dogLostRepository.save(dogLost6);

    }

    @Test
    void saveAndPaging() {
        signed1 = memberService.findById(1L).get();
        signed2 = memberService.findById(2L).get();
        signed3 = memberService.findById(3L).get();
        List<DogLost> byMemberId = dogLostRepository.findAllByMemberId(signed1.getId());
        assertThat(byMemberId.size()).isEqualTo(2);
        for (DogLost dogLost : byMemberId) {
            System.out.println("dogLost = " + dogLost);
        }
        // paging
        PageRequest pageRequest = PageRequest.of(2, 4, Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
        Slice<DogLost> all = dogLostRepository.findAllByBoardStatus(BoardStatus.NORMAL, pageRequest);
        System.out.println("all.getSize() = " + all.getSize());
        System.out.println("all.getNumberOfElements() = " + all.getNumberOfElements());
        System.out.println("all.hasContent() = " + all.hasContent());
        System.out.println("all.hasNext() = " + all.hasNext());
        for (DogLost dogLost : all) {
            System.out.println("dogLost = " + dogLost);
        }
    }

    @Test
    void test2() {
        List<DogLost> byMemberId = dogLostRepository.findAllByMemberId(signed1.getId());
        List<DogLost> allByMember = dogLostRepository.findAllByMember(signed1);
        assertThat(allByMember.size()).isEqualTo(byMemberId.size());

        PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
        Slice<DogLost> all1 = dogLostRepository.findAllByBoardStatus(BoardStatus.NORMAL, pageRequest);
        assertThat(all1.hasContent()).isTrue();
        assertThat(all1.getNumberOfElements()).isEqualTo(4);

        Page<DogLost> all2 = dogLostRepository.findAll(pageRequest);
        System.out.println("all2.getTotalPages() = " + all2.getTotalPages());
        System.out.println("dogLostRepository.count() = " + dogLostRepository.count());
        
        Pageable next = pageRequest.next();
        System.out.println("next = " + next);
        next = next.next();
        all1 = dogLostRepository.findAllByBoardStatus(BoardStatus.NORMAL, next);
        assertThat(all1.hasContent()).isFalse();
    }
}