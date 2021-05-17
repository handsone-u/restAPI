package com.handsone.restAPI.domain.file.repository;

import com.handsone.restAPI.domain.dogFound.domain.DogFound;
import com.handsone.restAPI.domain.dogLost.domain.DogLost;
import com.handsone.restAPI.domain.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    public List<File> findAllByDogLost(DogLost dogLost);
    public List<File> findAllByDogLostId(Long dogLostId);

    public List<File> findAllByDogFound(DogFound dogFound);
    public List<File> findAllByDogFoundId(Long dogFoundId);
}
