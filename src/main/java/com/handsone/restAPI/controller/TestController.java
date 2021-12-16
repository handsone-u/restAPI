package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.Gender;
import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.dto.MemberDto;
import com.handsone.restAPI.dto.RequestAI;
import com.handsone.restAPI.infra.address.Address;
import com.handsone.restAPI.service.DogFoundService;
import com.handsone.restAPI.service.DogLostService;
import com.handsone.restAPI.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final MemberService memberService;
    private final DogLostService dogLostService;
    private final DogFoundService dogFoundService;
    private static ArrayList<DogDto> arr = new ArrayList<>();

    @PostMapping("/ai-model")
    public RequestAI requestAI(@RequestBody RequestAI requestAI) {
        log.info("receive=[{}]", requestAI);
        return requestAI;
    }

    @GetMapping(value = "/test-api/members")
    public Long insertMemberData(@RequestParam int size) {
        log.info("size = {}", size);
        String userId = "id";
        String password = "pass";
        String nickName = "nick";
        Address address = new Address("Gu");

        for (int i = 1; i <= size; i++)
            memberService.signUp(new Member(null, userId + i, password + i, nickName + i, address));

        return memberService.count();
    }

    @RequestMapping("/test-api/losts")
    public Long insertLostData(@RequestParam(name = "size") int size,
                               @RequestParam(name = "files") List<MultipartFile> files) throws IOException {
        for (int i = 1; i <= size; i++) {
            DogDto dog = DogDto.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .memberId(1L)
                    .dogName("dogName" + i)
                    .gender(Gender.MALE)
                    .dogBreed("otterhound")
                    .build();

            dogLostService.upload(dog);
        }

        return dogLostService.count();
    }

    @RequestMapping("/test-api/founds")
    public Long insertFoundData(@RequestParam int size,
                                @RequestParam List<MultipartFile> files) throws IOException {
        for (int i = 1; i <= size; i++) {
            DogDto dog = DogDto.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .memberId(1L)
                    .dogName("dogName" + i)
                    .gender(Gender.MALE)
                    .build();
            dogFoundService.upload(dog);
        }

        return dogFoundService.count();
    }

    @RequestMapping("/hogi")
    public Boolean hogi(RequestEntity request) {
        log.info(request.getUrl().toString());
        return true;
    }

    @GetMapping("/image")
    public ResponseEntity<Resource> getOneImage() throws MalformedURLException {
        Path path = Paths.get("./").toAbsolutePath()
                .resolve("test-data")
                .resolve("hongik.jpg")
                .normalize();
        log.info(path.toUri().toString());
        UrlResource urlResource = new UrlResource(path.toUri());
        if(urlResource.exists()) {
            log.info(urlResource.getFilename());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(urlResource);
        }
        else {
            log.error("Cannot find path {}",path);
            return null;
        }
    }

    @GetMapping("/download-image-file/{id}")
    public ResponseEntity<Resource> downOne(@PathVariable int id) {
        Resource res = new ClassPathResource(id+".jpg");
        if(!res.exists()) log.error("resource not exist.");

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(res);
    }

    @GetMapping("/prepare-lost")
    public ResponseEntity<Page> prepareDogLosts(@PageableDefault(size = 10,page = 0) Pageable pageable) {
        for(int i=0;i<10;i++){
            DogDto dto = DogDto.builder()
                    .nickName("회원 닉네임" + i)
                    .dogName("강아지 이름" + i)
                    .title("제목" + i)
                    .address(new Address("마포구"))
                    .content("내용" + i)
                    .regDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download-image-file/")
                    .toUriString();
            dto.getFileUris().add(baseUri + i);

            arr.add(dto);
        }

        Page<DogDto> page = new PageImpl<DogDto>(arr, pageable, 10);

        return ResponseEntity.ok()
                .body(page);
    }
}
