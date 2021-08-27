package com.handsone.restAPI.service;

import com.handsone.restAPI.exception.FileUploadException;
import com.handsone.restAPI.property.FileUploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadDownloadService {
    private final Path fileDir;

    @Autowired
    public FileUploadDownloadService(FileUploadProperties properties) {
        fileDir = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileDir);
        } catch (IOException e) {
            throw new FileUploadException("Couldn't create upload Directory.");
        }
    }
//
//    public String storeFile(MultipartFile multipartFile) {
//
//    }
}
