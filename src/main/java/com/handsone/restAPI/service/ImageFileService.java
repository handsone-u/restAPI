package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.domain.ImageFile;
import com.handsone.restAPI.exception.FileDownloadException;
import com.handsone.restAPI.property.FileUploadProperties;
import com.handsone.restAPI.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.handsone.restAPI.domain.ImageFile.createFile;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;
    private final FileUploadProperties fileUploadProperties;
    private Path lostSavePath;
    private Path foundSavePath;
    private Path fileLocation;

    @PostConstruct
    public void init() throws IOException {
        log.info("ImageFileService::postConstruct INIT. *MUST PASS!*");
        this.fileLocation = Paths.get(fileUploadProperties.getUploadDir()).toAbsolutePath().normalize();
        log.debug(fileLocation.toUri().toURL().toString());
        log.debug(fileLocation.toUri().toString());
        log.debug(fileLocation.toString());
        this.lostSavePath = this.fileLocation.resolve("lost");
        this.foundSavePath = this.fileLocation.resolve("found");
        try {
            Files.createDirectories(this.lostSavePath);
            Files.createDirectories(this.foundSavePath);
        } catch (IOException e) {
            log.error("ImageFileService:: ERROR in postConstructor");
            log.error("Need to create Directories again.");
            throw new FileUploadException("Couldn't create directory.", e);
        }
        log.info("ImageFileService::postConstruct DONE. *SUCCESS!*");
    }

    public ImageFile findById(Long id) {
        return imageFileRepository.findById(id).get();
    }

    @Transactional(readOnly = false)
    public void lostUpload(DogLost dog, List<MultipartFile> files) throws IOException {
        Path savePath = lostSavePath.resolve(dog.getId().toString());
        Files.createDirectories(savePath);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile multiFile = files.get(i);
            ImageFile imageFile = createFile(dog, multiFile, Integer.toString(i));
            setPath(savePath, multiFile, imageFile);
            imageFileRepository.save(imageFile);
        }
    }

    @Transactional(readOnly = false)
    public void foundUpload(DogFound dog, List<MultipartFile> files) throws IOException {
        Path savePath = foundSavePath.resolve(dog.getId().toString());
        Files.createDirectories(savePath);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile multiFile = files.get(i);
            ImageFile imageFile = createFile(dog, multiFile, Integer.toString(i));
            setPath(savePath, multiFile, imageFile);
            imageFileRepository.save(imageFile);
        }
    }
    public Resource loadFileLost(Long dogId, String fileName) {
        try {
            Path filePath = lostSavePath
                    .resolve(dogId.toString())
                    .resolve(fileName).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) return resource;
            else throw new FileDownloadException("Cannot find file.");
        } catch (MalformedURLException e) {
            throw new FileDownloadException("Cannot find file.", e);
        }
    }

    public Resource loadFileFound(Long dogId, String fileName) {
        try {
            Path filePath = foundSavePath
                    .resolve(dogId.toString())
                    .resolve(fileName).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) return resource;
            else throw new FileDownloadException("Cannot find file.");
        } catch (MalformedURLException e) {
            throw new FileDownloadException("Cannot find file.", e);
        }
    }

    private void setPath(Path savePath, MultipartFile multiFile, ImageFile imageFile) throws IOException {
        Path filePath = savePath.resolve(imageFile.getFileName());

        multiFile.transferTo(filePath);
        imageFile.setFilePath(filePath.toString());
    }
}
