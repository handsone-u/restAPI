package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.service.DogLostService;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.Response;
import com.handsone.restAPI.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dog-lost")
public class DogLostAPIController {

    private final DogLostService dogLostService;
    private final ImageFileService imageFileService;
    private final ModelMapper modelMapper;
    private final String downLostPath = "/download-image-file/lost/";

    @PostMapping("")
    public ResponseEntity<?extends Response> postDogLost(
            @ModelAttribute DogDto dogDto,
            @RequestParam("files") List<MultipartFile> files) throws Exception{
        DogLost upload = dogLostService.upload(dogDto, files);
        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downLostPath)
                .path(upload.getId().toString())
                .toUriString();

        return ResponseEntity.ok()
                .body(new CommonResponse<>(modelMapper.map(upload, DogDto.class)
                        .setMemberProperties().setFileProperties(baseUri)));
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<? extends Response> getDogLost(@PathVariable Long dogId) {
        DogLost dogLost = dogLostService.findById(dogId);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(modelMapper.map(dogLost, DogDto.class)));
    }

    // TODO: 2021/10/12
    //  Impl slicing
}
