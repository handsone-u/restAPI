package com.handsone.restAPI.controller;

import com.handsone.restAPI.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class ImageFileAPIController {
    private final ImageFileService imageFileService;

//    @GetMapping("/{fileId}")
}
