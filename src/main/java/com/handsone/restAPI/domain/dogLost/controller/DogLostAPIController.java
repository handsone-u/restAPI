package com.handsone.restAPI.domain.dogLost.controller;

import com.handsone.restAPI.domain.dogLost.service.DogLostService;
import com.handsone.restAPI.global.request.DogDto;
import com.handsone.restAPI.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DogLostAPIController {

    private final DogLostService dogLostService;

    @PostMapping("dog-lost")
    public ResponseEntity<?extends Response> postDogLost(@ModelAttribute DogDto dogDto,
                               @RequestParam("files") List<MultipartFile> files) throws Exception{

        dogLostService.upload(dogDto, files);
        return null;
    }
}
