package com.handsone.restAPI.repository;

import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogLostRepository extends JpaRepository<DogLost, Long> {
    public List<DogLost> findAllByMemberId(Long memberId);
    public List<DogLost> findAllByMember(Member member);

    public Slice<DogLost> findAllByBoardStatus(BoardStatus boardStatus, Pageable pageable);

    public Slice<DogLost> findAllBy(Pageable pageable);
}
