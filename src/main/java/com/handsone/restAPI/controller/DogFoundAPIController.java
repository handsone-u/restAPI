package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.service.DogFoundService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dog-found")
@Slf4j
public class DogFoundAPIController {

    private final DogFoundService dogFoundService;
    private final ImageFileService imageFileService;
    private final ModelMapper modelMapper;
    private final String downFoundPath = "/download-image-file/found/";

    @PostMapping("")
    public ResponseEntity<? extends Response> postDogFound(
            @ModelAttribute DogDto dogDto,
            @RequestParam("files")List<MultipartFile> files) throws Exception{
        DogFound upload = dogFoundService.upload(dogDto);
        imageFileService.foundUpload(upload, files);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downFoundPath)
                .path(upload.getId().toString())
                .toUriString();

        return ResponseEntity.ok()
                .body(new CommonResponse<>(modelMapper.map(upload, DogDto.class)
                        .setMemberAndFileProperties(baseUri)));
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<? extends Response> getDogFound(@PathVariable Long dogId) {
        DogFound dogFound = dogFoundService.findById(dogId);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(modelMapper.map(dogFound, DogDto.class)));
    }

    @GetMapping("/dog-founds")
    public ResponseEntity<Page> getAllDogFound(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        log.debug("currentRequestUri = [{}]", ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        log.debug("pageable.PageNumber[{}]", pageable.getPageNumber());
        log.debug("pageable.PageSize[{}]", pageable.getPageSize());
        log.debug("pageable.PageOffset[{}]", pageable.getOffset());
        Page<DogFound> all = dogFoundService.findAll(pageable);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downFoundPath)
                .toUriString();

        return ResponseEntity.ok()
                .body(all.map(d -> modelMapper.map(d, DogDto.class)
                        .setMemberAndFileProperties(baseUri + d.getId())));
    }
}
