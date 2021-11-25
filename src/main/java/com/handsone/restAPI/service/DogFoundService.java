package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.error.ErrorCode;
import com.handsone.restAPI.exception.ClientException;
import com.handsone.restAPI.repository.DogFoundRepository;
import com.handsone.restAPI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DogFoundService {

    private final DogFoundRepository dogFoundRepository;
    private final MemberRepository memberRepository;

    /**w
     * 1. find Member, from dogDto.memberId
     * 2. Save Board(Found), get Board's PK
     * 3. Save Image, with Board's PK
     * @param dogDto : DTO of Board, containing Member's ID.
     * @return Entity of DogFound
     */
    @Transactional(readOnly = false)
    public DogFound upload(DogDto dogDto) {
        Member member = memberRepository.findById(dogDto.getMemberId())
                .orElseThrow(() -> new ClientException("Cannot find Member's info.", ErrorCode.NOTFOUND_MEMBER));

        DogFound dogFound = dogDto.toEntityFound().createDogFound(member);
        return dogFoundRepository.save(dogFound);
    }

    @Transactional(readOnly = false)
    public DogFound update(Long id, String dogBreed) {
        DogFound dogFound = dogFoundRepository.findById(id).orElseThrow(() -> new ClientException(ErrorCode.NOTFOUND_DOG));
        dogFound.setDogBreed(dogBreed);
        return dogFound;
    }

    public DogFound findById(Long id) {
        return dogFoundRepository.findById(id)
                .orElseThrow(() -> new ClientException("Cannot find Dog's info.", ErrorCode.NOTFOUND_DOG));
    }

    public List<DogFound> findAllByDogBreed(String dogBreed) {
        return dogFoundRepository.findAllByDogBreed(dogBreed);
    }

    public Page<DogFound> findAll(Pageable pageable) {
        return dogFoundRepository.findAll(pageable);
    }

    public long count() {
        return dogFoundRepository.count();
    }

    public Page<DogFound> findAllByStatus(BoardStatus status, Pageable pageable) {
        return dogFoundRepository.findAllByBoardStatus(status, pageable);
    }
}
