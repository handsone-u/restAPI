package com.handsone.restAPI.domain.dogLost.service;

import com.handsone.restAPI.domain.Dog;
import com.handsone.restAPI.domain.file.domain.File;
import com.handsone.restAPI.domain.file.service.FileService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
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
    private Member signed1, signed2, signed3, signed4;

    @BeforeEach
    void before() throws IOException {
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

        DogDto dogDto1 = new DogDto(signed1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto2 = new DogDto(signed1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto3 = new DogDto(signed1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto4 = new DogDto(signed1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto5 = new DogDto(signed1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto6 = new DogDto(signed1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto7 = new DogDto(signed1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto8 = new DogDto(signed1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));

        List<MultipartFile> files = new ArrayList<>();

        dogLost = dogLostService.upload(dogDto1, files);
        DogLost up2 = dogLostService.upload(dogDto2, files);
        DogLost up3 = dogLostService.upload(dogDto3, files);
        DogLost up4 = dogLostService.upload(dogDto4, files);
        DogLost up5 = dogLostService.upload(dogDto5, files);
        DogLost up6 = dogLostService.upload(dogDto6, files);
        DogLost up7 = dogLostService.upload(dogDto7, files);
        DogLost up8 = dogLostService.upload(dogDto8, files);

        System.out.println("DogLostServiceTest.before\n");
    }

    @Test
    void getPosts() {
        Long cnt = dogLostService.count();

        List<DogLost> all = dogLostService.findAllByMemberId(signed1.getId());
        assertThat(new Long(all.size())).isEqualTo(cnt);

        List<DogLost> results = dogLostService.findAll();
        assertThat(new Long(results.size())).isEqualTo(dogLostService.count());

        Optional<DogLost> byId = dogLostService.findById(dogLost.getId());
        assertThat(byId.isPresent()).isTrue();
        assertThat(byId.get().getId()).isEqualTo(dogLost.getId());
        assertThat(byId.get().getMember().getId()).isEqualTo(dogLost.getMember().getId());

        byId = dogLostService.findById(1000L);
        assertThat(byId.isEmpty()).isTrue();
    }

    @Test
    void pagingTest() throws IOException{
        System.out.println("DogLostServiceTest.pagingTest");
        int page = 0, size =3;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("regDate").descending());

        Slice<DogLost> results = dogLostService.findAllByBoardStatusNormal(pageRequest);
        assertThat(results.hasContent()).isTrue();
        assertThat(results.getNumberOfElements()).isEqualTo(size);
        assertThat(results.isFirst()).isTrue();
        assertThat(results.hasNext()).isTrue();
        System.out.println("pageRequest = " + pageRequest);
        System.out.println("results = " + results);
        for (DogLost result : results) {
            System.out.println("result = " + result);
        }
        System.out.println();

        while (results.hasNext()) {
            pageRequest = (PageRequest) pageRequest.next();
            results = dogLostService.findAllByBoardStatusNormal(pageRequest);
            System.out.println("pageRequest = " + pageRequest);
            for (DogLost result : results) {
                System.out.println("result = " + result);
            }
            System.out.println();
        }
        assertThat(results.isLast()).isTrue();

        pageRequest = (PageRequest) pageRequest.next();
        results = dogLostService.findAllByBoardStatusNormal(pageRequest);
        assertThat(results.hasNext()).isFalse();
        assertThat(results.hasContent()).isFalse();
        assertThat(results.getNumberOfElements()).isZero();
    }

}