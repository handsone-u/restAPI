package com.handsone.restAPI.repository;

import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.domain.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    public List<ImageFile> findAllByDogLost(DogLost dogLost);
    public List<ImageFile> findAllByDogLostId(Long dogLostId);

    public List<ImageFile> findAllByDogFound(DogFound dogFound);
    public List<ImageFile> findAllByDogFoundId(Long dogFoundId);
}
