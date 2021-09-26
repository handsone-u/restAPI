package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.service.DogFoundService;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DogFoundAPIController {

    private final DogFoundService dogFoundService;

    @PostMapping("dog-found")
    public ResponseEntity<? extends Response> postDogFound(@ModelAttribute DogDto dogDto,
                                                           @RequestParam("files")List<MultipartFile> files) throws Exception{
        DogFound dogFound = dogFoundService.upload(dogDto, files);
        return ResponseEntity.ok().body(new CommonResponse<Long>(0L));
    }
}
