package com.handsone.restAPI.domain.dogLost.repository;

import com.handsone.restAPI.domain.dogLost.domain.DogLost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

public interface DogLostSlicingRepository extends CrudRepository<DogLost, Long> {
    Slice<DogLost> findAll(Pageable pageable);
}
