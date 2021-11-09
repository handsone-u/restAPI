package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.service.DogLostService;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.Response;
import com.handsone.restAPI.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor @Slf4j
@RequestMapping("/dog-lost")
public class DogLostAPIController {

    private final DogLostService dogLostService;
    private final ImageFileService imageFileService;
    private final ModelMapper modelMapper;
    private final String downLostPath = "/download-image-file/lost/";

    @PostMapping("")
    public ResponseEntity<? extends Response> postDogLost(
            @ModelAttribute DogDto dogDto, BindingResult bindingResult,
            @RequestParam("files") List<MultipartFile> files) throws Exception {
        DogLost upload = dogLostService.upload(dogDto);
        imageFileService.lostUpload(upload, files);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downLostPath)
                .path(upload.getId().toString())
                .toUriString();

        return ResponseEntity.ok().body(
                new CommonResponse<>(modelMapper.map(upload, DogDto.class)
                        .setMemberAndFileProperties(baseUri)));
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<? extends Response> getDogLost(@PathVariable Long dogId) {
        DogLost dogLost = dogLostService.findById(dogId);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(modelMapper.map(dogLost, DogDto.class)));
    }

    //TODO[추가] 업로드 시, 인공지능 모델 Server 에 POST 전송
    @GetMapping("/dog-losts")
    public ResponseEntity<Page> getAllDogLost(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        log.debug("currentRequestUri = [{}]", ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        log.debug("pageable.PageNumber[{}]", pageable.getPageNumber());
        log.debug("pageable.PageSize[{}]", pageable.getPageSize());
        log.debug("pageable.PageOffset[{}]", pageable.getOffset());
        Page<DogLost> all = dogLostService.findAll(pageable);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downLostPath)
                .toUriString();

        return ResponseEntity.ok()
                .body(all.map(d -> modelMapper.map(d, DogDto.class)
                        .setMemberAndFileProperties(baseUri + d.getId())));
    }
}
