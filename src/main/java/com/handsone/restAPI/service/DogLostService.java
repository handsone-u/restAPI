package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.error.ErrorCode;
import com.handsone.restAPI.exception.ClientException;
import com.handsone.restAPI.repository.DogLostRepository;
import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor @Slf4j
public class DogLostService {

    private final DogLostRepository dogLostRepository;
    private final MemberRepository memberRepository;

    /**
     * 1. find Member, from dogDto.memberId
     * 2. Save Board(Lost), get Board's PK
     * 3. Save Image, with Board's PK
     *
     * @param dogDto : DTO of Board, containing Member's ID.
     * @return Entity of DogLost
     */
    @Transactional(readOnly = false, rollbackFor = {ClientException.class, RuntimeException.class})
    public DogLost upload(DogDto dogDto) {
        Member member = memberRepository.findById(dogDto.getMemberId())
                .orElseThrow(() -> new ClientException("Cannot find Member's info.", ErrorCode.NOTFOUND_MEMBER));

        DogLost dogLost = dogDto.toEntityLost().createDogLost(member);
        return dogLostRepository.save(dogLost);
    }

    @Transactional(readOnly = false)
    public DogLost update(Long id, String dogBreed) {
        DogLost dogLost = dogLostRepository.findById(id).orElseThrow(() -> new ClientException(ErrorCode.NOTFOUND_DOG));
        dogLost.setDogBreed(dogBreed);
        return dogLost;
    }

    public DogLost findById(Long id) {
        return dogLostRepository.findById(id)
                .orElseThrow(() -> new ClientException("Cannot find Dog's info.", ErrorCode.NOTFOUND_DOG));
    }

    public Long count(){
        return dogLostRepository.count();
    }

    public List<DogLost> findAllByDogBreed(String dogBreed) {
        return dogLostRepository.findAllByDogBreed(dogBreed);
    }

    public Page<DogLost> findAll(Pageable pageable) {
        return dogLostRepository.findAll(pageable);
    }

    public List<DogLost> findAllByMemberId(Long memberId) {
        return dogLostRepository.findAllByMemberId(memberId);
    }

    public Page<DogLost> findAllByStatus(BoardStatus status, Pageable pageable) {
        return dogLostRepository.findAllByBoardStatus(status, pageable);
    }

}
