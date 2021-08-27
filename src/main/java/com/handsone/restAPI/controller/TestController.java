package com.handsone.restAPI.controller;

import com.handsone.restAPI.global.response.Response;
import com.handsone.restAPI.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final FileService fileService;

    @GetMapping(value ="/test-api/files/{id}")
    public ResponseEntity<? extends Response> getFileFromResource(@PathVariable Long id) {
        return null;
    }

    @PostMapping(value = "/images/lost")
    public String weGot(Long pk, double accuracy) {
        log.info(pk.toString());
        System.out.println("accuracy = " + accuracy);
        return "sexy You";
    }
}
