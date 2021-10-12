package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.domain.ImageFile;
import com.handsone.restAPI.service.DogFoundService;
import com.handsone.restAPI.service.DogLostService;
import com.handsone.restAPI.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageFileAPIController {
    private final ImageFileService imageFileService;
    private final DogLostService dogLostService;
    private final DogFoundService dogFoundService;

    @GetMapping("/download-image-file/lost/{dogId}/{fileName}")
    public ResponseEntity<Resource> downloadLost(@PathVariable Long dogId,
                                                 @PathVariable String fileName,
                                                 RequestEntity requestEntity) {

        Resource resource = imageFileService.loadFileLost(dogId, fileName);
        DogLost dogLost = dogLostService.findById(dogId);
        int index = Integer.parseInt(fileName.split("\\.")[0]);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dogLost.getImageFileList().get(index).getFileContentType()))
                .body(resource);
    }

    @GetMapping("/download-image-file/found/{dogId}/{fileName}")
    public ResponseEntity<Resource> downloadFound(@PathVariable Long dogId,
                                                 @PathVariable String fileName,
                                                 RequestEntity requestEntity) {

        Resource resource = imageFileService.loadFileFound(dogId, fileName);
        DogFound dogFound = dogFoundService.findById(dogId);
        int index = Integer.parseInt(fileName.split("\\.")[0]);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dogFound.getImageFileList().get(index).getFileContentType()))
                .body(resource);
    }
}
