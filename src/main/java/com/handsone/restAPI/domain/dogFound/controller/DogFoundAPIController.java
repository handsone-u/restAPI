package com.handsone.restAPI.domain.dogFound.controller;

import com.handsone.restAPI.domain.dogFound.domain.DogFound;
import com.handsone.restAPI.domain.dogFound.service.DogFoundService;
import com.handsone.restAPI.global.request.DogDto;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DogFoundAPIController {

    private final DogFoundService dogFoundService;

    @PostMapping("dog-found")
    public ResponseEntity<? extends Response> postDogFound(@ModelAttribute DogDto dogDto,
                                                           @RequestParam("file1")MultipartFile file) throws Exception{
        return ResponseEntity.ok().body(new CommonResponse<Long>(0L));
    }

    @PostMapping("testing")
    public ResponseEntity<Resource> testing(@ModelAttribute DogDto dogDto,
                                            @RequestParam("files") List<MultipartFile> files) {
        return null;
    }
}
