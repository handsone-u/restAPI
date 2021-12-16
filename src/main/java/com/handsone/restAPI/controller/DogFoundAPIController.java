package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.dto.RequestAI;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.Response;
import com.handsone.restAPI.service.DogFoundService;
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

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dog-found")
@Slf4j
public class DogFoundAPIController {

    private final DogFoundService dogFoundService;
    private final ImageFileService imageFileService;
    private final ModelMapper modelMapper;

    private final AiModelClient aiModelClient;
    private final String downFoundPath = "/download-image-file/found/";

    @PostMapping("")
    public ResponseEntity<? extends Response> postDogFound(
            @ModelAttribute DogDto dogDto, @RequestParam("files")MultipartFile files) throws Exception{
        ArrayList<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(files);
        DogFound upload = dogFoundService.upload(dogDto);
        imageFileService.foundUpload(upload, multipartFiles);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downFoundPath)
                .path(upload.getId().toString())
                .toUriString();

        DogDto dto = modelMapper.map(upload, DogDto.class)
                .setMemberAndFileProperties(baseUri);

        aiModelClient.getDogLostAndUpdateDogBreed(new RequestAI(dto.getImageFileList().get(0).getFilePath(),
                dto.getId(), dto.getDogBreed(), "found"));

        log.info("createdDogId=[{}]", dto.getId());
        return ResponseEntity.ok()
                .body(new CommonResponse<>(dto.getId()));
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<? extends Response> getDogFound(@PathVariable Long dogId) {
        DogFound dogFound = dogFoundService.findById(dogId);
        DogDto map = modelMapper.map(dogFound, DogDto.class);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downFoundPath)
                .toUriString();

        map.setMemberAndFileProperties(baseUri + map.getId());

        return ResponseEntity.ok()
                .body(new CommonResponse<>(map));
    }

    @GetMapping("/dog-founds")
    public ResponseEntity<Page> getAllDogFound(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DogFound> all = dogFoundService.findAll(pageable);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downFoundPath)
                .toUriString();

        return ResponseEntity.ok()
                .body(all.map(d -> modelMapper.map(d, DogDto.class)
                        .setMemberAndFileProperties(baseUri + d.getId())));
    }
}
