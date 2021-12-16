package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.dto.RequestAI;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.Response;
import com.handsone.restAPI.service.DogLostService;
import com.handsone.restAPI.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dog-lost")
@Slf4j
public class DogLostAPIController {

    private final DogLostService dogLostService;
    private final ImageFileService imageFileService;
    private final ModelMapper modelMapper;

    private final AiModelClient aiModelClient;
    private final String downLostPath = "/download-image-file/lost/";

    @PostMapping("")
    public ResponseEntity<? extends Response> postDogLost(
            @ModelAttribute DogDto dogDto, @RequestParam("files")MultipartFile files) throws IOException {
        log.debug("dogBreed=[{}]", dogDto.getDogBreed());
        log.debug("dogName=[{}]", dogDto.getDogName());
        log.debug("gender=[{}]", dogDto.getGender());

        ArrayList<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(files);
        DogLost upload = dogLostService.upload(dogDto);
        imageFileService.lostUpload(upload, multipartFiles);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downLostPath)
                .path(upload.getId().toString())
                .toUriString();

        DogDto dto = modelMapper.map(upload, DogDto.class)
                .setMemberAndFileProperties(baseUri);

        aiModelClient.getDogFoundByDogBreed(new RequestAI(dto.getImageFileList().get(0).getFilePath(),
                dto.getId(), dto.getDogBreed(), "lost"));

        log.info("Created DogId=[{}]", dto.getId());
        return ResponseEntity.ok().body(
                new CommonResponse<>(dto.getId()));
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<? extends Response> getDogLost(@PathVariable Long dogId) {
        DogLost dogLost = dogLostService.findById(dogId);
        DogDto map = modelMapper.map(dogLost, DogDto.class);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downLostPath)
                .toUriString();

        map.setMemberAndFileProperties(baseUri + map.getId());

        return ResponseEntity.ok()
                .body(new CommonResponse<>(map));
    }

    @GetMapping("/dog-breed")
    public ResponseEntity<? extends Response> getDogLostByDogBreed(String dogBreed) {
        List<DogLost> result = dogLostService.findAllByDogBreed(dogBreed);

        return null;
    }

    @GetMapping("/dog-losts")
    public ResponseEntity<Page> getAllDogLost(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<DogLost> all = dogLostService.findAll(pageable);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downLostPath)
                .toUriString();

        return ResponseEntity.ok()
                .body(all.map(d -> modelMapper.map(d, DogDto.class)
                        .setMemberAndFileProperties(baseUri + d.getId())));
    }
}
