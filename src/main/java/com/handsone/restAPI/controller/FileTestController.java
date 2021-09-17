package com.handsone.restAPI.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * without DB connection
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class FileTestController {

    @PostMapping("test-file-up1")
    public String testUpload(@RequestParam(required = true, value = "imageList") List<MultipartFile> imageList,
                             @RequestParam(value = "name") String name) {
        return getString(imageList, name);
    }

    @PostMapping("test-file-up2")
    public String testUpload(@RequestParam String name) {
        return getString(new ArrayList<>(), name);
    }

    @PostMapping("test-file-up/save")
    public String testSave(@RequestParam(required = true, value = "imageList") List<MultipartFile> imageList,
                             @RequestParam(value = "name") String name) {
        return getString(imageList, name);
    }

    private String getString(List<MultipartFile> imageList, String name) {
        log.info(name);
        if(imageList.isEmpty()) return "empty list";
        for (MultipartFile image : imageList) {
            log.info("original name = {}", image.getOriginalFilename());
            log.info("name = {}", image.getName());
            log.info("type = {}", image.getContentType());
            log.info("size = {}", image.getSize());
        }
        return "ok";
    }
}
