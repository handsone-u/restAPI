package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.service.DogLostService;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.Response;
import com.handsone.restAPI.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

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
                        .setMemberAndFileProperties(baseUri)));
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<? extends Response> getDogLost(@PathVariable Long dogId) {
        DogLost dogLost = dogLostService.findById(dogId);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(modelMapper.map(dogLost, DogDto.class)));
    }

    // TODO: 2021/10/12
    //  Impl slicing
    @GetMapping("/dog-losts")
    public ResponseEntity<Page> getAllDogLost(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<DogLost> all = dogLostService.findAll(pageable);
        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downLostPath)
                .toUriString();
        Page<DogDto> dtos = all.map(d -> modelMapper.map(d, DogDto.class)
                .setMemberAndFileProperties(baseUri + d.getId()));

        return ResponseEntity.ok()
                .body(dtos);
    }
}
