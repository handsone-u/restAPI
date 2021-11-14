package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.dto.RequestAI;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dog-lost")
@Slf4j
public class DogLostAPIController {

    private final DogLostService dogLostService;
    private final ImageFileService imageFileService;
    private final ModelMapper modelMapper;
    private final String downLostPath = "/download-image-file/lost/";

    private final String aiModelHost = "localhost";
    private final int aiModelPort = 8080;
    private final String aiModelPath = "/ai-model";

    @PostMapping("")
    public ResponseEntity<? extends Response> postDogLost(
            @ModelAttribute DogDto dogDto,
            @RequestParam("files") List<MultipartFile> files) throws Exception {
        DogLost upload = dogLostService.upload(dogDto);
        imageFileService.lostUpload(upload, files);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downLostPath)
                .path(upload.getId().toString())
                .toUriString();

        DogDto dto = modelMapper.map(upload, DogDto.class)
                .setMemberAndFileProperties(baseUri);

        RequestAI requestAI = new RequestAI(dto.getImageFileList().get(0).getFilePath(),
                dto.getId(), dto.getDogBreed(),"lost");
        getDogFoundByDogBreed(requestAI);

        log.info("response={}", dto.getId());
        return ResponseEntity.ok().body(
                new CommonResponse<>(dto));
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<? extends Response> getDogLost(@PathVariable Long dogId) {
        DogLost dogLost = dogLostService.findById(dogId);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(modelMapper.map(dogLost, DogDto.class)));
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
                .body(all.map(d -> modelMapper.map(d, DogDto.class).setMemberAndFileProperties(baseUri + d.getId())));
    }

    private void getDogFoundByDogBreed(RequestAI requestAI) {
        log.info("call AI Model, original Param:[{}]",requestAI);

        UriComponents baseUri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(aiModelHost)
                .port(aiModelPort)
                .build();
        log.info("requestBaseUri={}", baseUri.toUriString());

        WebClient webClient = WebClient.create(baseUri.toUriString());
        webClient.post()
                .uri(aiModelPath)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestAI)
                .retrieve()
                .bodyToMono(RequestAI.class)
                .subscribe(r -> log.info("result=[{}]", r));
    }
}
