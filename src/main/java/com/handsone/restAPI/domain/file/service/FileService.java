package com.handsone.restAPI.domain.file.service;

import com.handsone.restAPI.domain.dogFound.domain.DogFound;
import com.handsone.restAPI.domain.dogLost.domain.DogLost;
import com.handsone.restAPI.domain.file.domain.File;
import com.handsone.restAPI.domain.file.dto.FileDto;
import com.handsone.restAPI.domain.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public File saveFile(File file) {
        return fileRepository.save(file);
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
