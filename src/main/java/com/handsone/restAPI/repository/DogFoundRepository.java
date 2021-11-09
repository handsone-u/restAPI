package com.handsone.restAPI.repository;

import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogFoundRepository extends JpaRepository<DogFound, Long> {
    public List<DogFound> findAllByMemberId(Long memberId);

    public List<DogFound> findAllByMember(Member member);

    public List<DogFound> findAllByDogBreed(String dogBreed);

    public Slice<DogFound> findAllByBoardStatus(BoardStatus boardStatus, Pageable pageable);

    public Slice<DogFound> findAllBy(Pageable pageable);
}
