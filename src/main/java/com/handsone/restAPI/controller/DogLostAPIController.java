package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.service.DogLostService;
import com.handsone.restAPI.global.request.DogDto;
import com.handsone.restAPI.global.response.CommonResponse;
import com.handsone.restAPI.global.response.ErrorResponse;
import com.handsone.restAPI.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.handsone.restAPI.global.request.DogDto.toDogDto;

@RestController
@RequiredArgsConstructor
public class DogLostAPIController {

    private final DogLostService dogLostService;

    @PostMapping("dog-lost")
    public ResponseEntity<?extends Response> postDogLost(
            @ModelAttribute DogDto dogDto,
            @RequestParam("files") List<MultipartFile> files) throws Exception{
        DogLost upload = dogLostService.upload(dogDto, files);
        return ResponseEntity.ok().body(new CommonResponse<DogDto>(new DogDto(upload)));
    }

    @GetMapping("dog-lost/{dogId}")
    public ResponseEntity<? extends Response> getDogLost(@PathVariable Long dogId) {
        Optional<DogLost> byId = dogLostService.findById(dogId);
        if (byId.isPresent()) return ResponseEntity.ok().body(new CommonResponse<DogDto>(toDogDto(byId.get())));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("없는 글입니다"));
    }

    @PostMapping("dog-lost/slices")
    public ResponseEntity<? extends Response> getSlices() {
        return null;
    }
}
