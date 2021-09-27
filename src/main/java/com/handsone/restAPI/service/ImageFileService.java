package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.domain.ImageFile;
import com.handsone.restAPI.exception.ClientException;
import com.handsone.restAPI.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.handsone.restAPI.domain.ImageFile.createFile;
import static com.handsone.restAPI.error.ErrorCode.NOTFOUND_FILE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;
    private final String lostSavePath = System.getProperty("user.dir") + "/lost";
    private final String foundSavePath = System.getProperty("user.dir") + "/found";

    @PostConstruct
    public void init() {
        log.debug("ImageFileService::postConstruct DONE");
        createDir(lostSavePath);
        createDir(foundSavePath);
    }

    public void lostUpload(DogLost dogLost, List<MultipartFile> files) throws IOException {
        String savePath = lostSavePath + "/" + dogLost.getId();
        createDir(savePath);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile multiFile = files.get(i);
            ImageFile imageFile = createFile(dogLost, multiFile, Integer.toString(i));
            String filePath = savePath + "/" + imageFile.getFileName();

            multiFile.transferTo(new java.io.File(filePath));
            imageFile.setFilePath(filePath);

            imageFileRepository.save(imageFile);
        }
    }

    public void foundUpload(DogFound dogFound, List<MultipartFile> files) throws IOException {
        String savePath = foundSavePath + "/" + dogFound.getId();
        createDir(savePath);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile multiFile = files.get(i);
            ImageFile imageFile = createFile(dogFound, multiFile, Integer.toString(i));
            String filePath = savePath + "/" + imageFile.getFileName();

            multiFile.transferTo(new java.io.File(filePath));
            imageFile.setFilePath(filePath);

            imageFileRepository.save(imageFile);
        }
    }

    public Resource getImage(Long id) {
        ImageFile imageFile = imageFileRepository.findById(id).orElseThrow(() -> new ClientException("Cannot find File.", NOTFOUND_FILE));
        return null;
    }

    private void createDir(String savePath) {
        if (!new java.io.File(savePath).exists()) {
            try {
                new java.io.File(savePath).mkdir();
            } catch (Exception e) {
                log.error("createDir():: {}", e.getMessage());
                Arrays.stream(e.getStackTrace()).forEach(System.out::println);
            }
        }
    }
}
