package com.handsone.restAPI.infra.controller;

import com.handsone.restAPI.global.request.DogDto;
import com.handsone.restAPI.domain.Gender;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.service.DogLostService;
import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.MemberDto;
import com.handsone.restAPI.service.MemberService;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.Response;
import com.handsone.restAPI.global.response.TestResponse;
import com.handsone.restAPI.infra.address.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.handsone.restAPI.dto.MemberDto.toMemberDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestAPIController {

    private final MemberService memberService;
    private final DogLostService dogLostService;
    @GetMapping("test/create")
    public String createDate() {
        Member member1 = new Member("hello", "1234", "myNick", new Address("si", "gu", "dong"));
        Member member2 = new Member("hello2", "1234", "myNick", new Address("si", "gu", "dong"));
        Member member3 = new Member("hello3", "1234", "i", new Address("si", "gu", "dong"));
        Member member4 = new Member("hello4", "1234", "will", new Address("si", "gu", "dong"));
        MemberDto memberDto1 = toMemberDto(member1);
        MemberDto memberDto2 = toMemberDto(member2);
        MemberDto memberDto3 = toMemberDto(member3);
        MemberDto memberDto4 = toMemberDto(member4);

        member1 = memberService.signUp(memberDto1);
        member2 = memberService.signUp(memberDto2);
        member3 = memberService.signUp(memberDto3);
        member4 = memberService.signUp(memberDto4);

        DogDto dogDto1 = new DogDto(member1.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto2 = new DogDto(member2.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto3 = new DogDto(member3.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
        DogDto dogDto4 = new DogDto(member4.getId(), "asd", "title", "content hello", "Dogmeat",
                Gender.UNIDENTIFIED, new Address("city", "distict", "dong"));
//        DogLost up1 = dogLostService.upload(dogDto1);
//        DogLost up2 = dogLostService.upload(dogDto2);
//        DogLost up3 = dogLostService.upload(dogDto3);
//        DogLost up4 = dogLostService.upload(dogDto4);

        return "Ok";
    }

    @GetMapping("test/1")
    public ResponseEntity<? extends Response> pages() {
        PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
        return ResponseEntity.ok().body(new CommonResponse<PageRequest>(pageRequest));
    }

    @PostMapping("test/1")
    public String gets(@RequestParam("page") int page, @RequestParam("size") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
        Pageable next = pageRequest.next();
        System.out.println("next = " + next);
        Slice<DogLost> all = dogLostService.findAllByBoardStatusNormal((PageRequest) next);
        if (all.hasContent()) return "ok";
        else return "no";
    }

    @PostMapping("test/2")
    public Slice<DogLost> gets2(@RequestBody com.handsone.restAPI.global.request.PageRequest pageRequest) {
        System.out.println("pageRequest = " + pageRequest);
        return dogLostService.findAllByBoardStatusNormal(pageRequest.of());
    }

    @GetMapping("test-raw")
    public List<MemberDto> getMemberDtoRaw() {
        List<Member> all = memberService.findAll();
        return all.stream()
                .map(MemberDto::toMemberDto)
                .collect(Collectors.toList());
    }

    @GetMapping("test-json")
    public ResponseEntity<? extends Response> getMemberDto() {
        List<Member> all = memberService.findAll();
        List<MemberDto> dtos = all.stream().map(a -> toMemberDto(a)).collect(Collectors.toList());
        TestResponse<List<MemberDto>> dtosResponse = new TestResponse<>(dtos);
        dtosResponse.setSuccess(true);
        dtosResponse.setMessage("test-json");
        return ResponseEntity.ok(dtosResponse);
    }

    @PostMapping("test/jsons")
    public String ttt(@RequestBody String m, @RequestBody String b) {
        log.info(m);
        log.info(b);
        return "hello";
    }

    @PostMapping("test/login")
    public LoggingResponse signTest(@RequestBody Logging a) {
        log.info(a.toString());
        return new LoggingResponse(1L);
    }

    @GetMapping("test/hogi")
    public TestNow testing() {
        log.info("testing()");
        TestNow testNow = new TestNow();
        testNow.setNow(10);
        testNow.setCnt(100);
        return testNow;
    }

    @GetMapping("test/look")
    public ResponseEntity<? extends Response> look() {
        MemberDto mem1 = new MemberDto(1L, "123", "124", "!23", null);
        MemberDto mem2 = new MemberDto(2L, "123", "124", "!23", null);
        ArrayList<MemberDto> dtos = new ArrayList<MemberDto>();
        dtos.add(mem1);
        dtos.add(mem2);
        return ResponseEntity.ok().body(new CommonResponse<>(mem1));
//        return ResponseEntity.ok().body(new CommonResponse<>(dtos));
    }

    @Data
    static class TestNow {
        String message = "hello world~!";
        int now;
        int cnt;
    }

    @Data
    static class Logging {
        String user_id;
        String user_password;
    }

    @Data @AllArgsConstructor
    static class LoggingResponse {
        Long id;
    }
}
