package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.domain.File;
import com.handsone.restAPI.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.handsone.restAPI.domain.File.createFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final String lostSavePath = System.getProperty("user.dir") + "/lost";
    private final String foundSavePath = System.getProperty("user.dir") + "/found";

    public void lostUpload(DogLost dogLost, List<MultipartFile> files) throws IOException {
        for (int i = 0; i < files.size(); i++) {
            MultipartFile multiFile = files.get(i);
            File file = createFile(dogLost, multiFile, Integer.toString(i));
            String savePath = lostSavePath + "/" + dogLost.getId();
            String filePath = savePath + "/" + file.getFileName();

            createDir(lostSavePath);
            createDir(savePath);
            multiFile.transferTo(new java.io.File(filePath));
            file.setFilePath(filePath);

            fileRepository.save(file);
        }
    }

    public void foundUpload(DogFound dogFound, List<MultipartFile> files) throws IOException {
        for (int i = 0; i < files.size(); i++) {
            MultipartFile multiFile = files.get(i);
            File file = createFile(dogFound, multiFile, Integer.toString(i));
            String savePath = foundSavePath + "/" + dogFound.getId();
            String filePath = savePath + "/" + file.getFileName();

            createDir(foundSavePath);
            createDir(savePath);
            multiFile.transferTo(new java.io.File(filePath));
            file.setFilePath(filePath);

            fileRepository.save(file);
        }
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

    public File getFileById(Long id) {
        return fileRepository.findById(id).get();
    }

    public List<File> getAllByDogLost(DogLost dogLost) {
        return fileRepository.findAllByDogLost(dogLost);
    }
    public List<File> getAllByDogLostId(Long dogLostId) {
        return fileRepository.findAllByDogLostId(dogLostId);
    }

    public List<File> getAllByDogFound(DogFound dogFound) {
        return fileRepository.findAllByDogFound(dogFound);
    }
    public List<File> getAllByDogFoundId(Long dogFoundId) {
        return fileRepository.findAllByDogFoundId(dogFoundId);
    }
}
