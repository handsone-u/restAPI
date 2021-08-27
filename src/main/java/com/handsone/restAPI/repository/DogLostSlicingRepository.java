package com.handsone.restAPI.repository;

import com.handsone.restAPI.domain.DogLost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

public interface DogLostSlicingRepository extends CrudRepository<DogLost, Long> {
    Slice<DogLost> findAll(Pageable pageable);
}
