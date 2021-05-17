package com.handsone.restAPI.domain.dogFound.service;

import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.dogFound.domain.DogFound;
import com.handsone.restAPI.domain.dogFound.repository.DogFoundRepository;
import com.handsone.restAPI.domain.dogLost.domain.DogLost;
import com.handsone.restAPI.domain.member.domain.Member;
import com.handsone.restAPI.domain.member.repository.MemberRepository;
import com.handsone.restAPI.global.request.DogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.handsone.restAPI.domain.dogFound.domain.DogFound.createDogFound;
import static com.handsone.restAPI.domain.dogLost.domain.DogLost.createDogLost;

@Service
@Transactional
@RequiredArgsConstructor
public class DogFoundService {

    private final DogFoundRepository dogFoundRepository;
    private final MemberRepository memberRepository;

    public DogFound upload(DogDto dogDto) {
        Member member = memberRepository.findById(dogDto.getMemberId()).get();
        DogFound dogFound = createDogFound(member, dogDto);
        return dogFoundRepository.save(dogFound);
    }

    @Transactional(readOnly = true)
    public Slice<DogFound> findAllByBoardStatusNormal(PageRequest pageRequest) {
        return dogFoundRepository.findAllByBoardStatus(BoardStatus.NORMAL, pageRequest);
    }

    @Transactional(readOnly = true)
    public List<DogFound> findAll() {
        return dogFoundRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<DogFound> findAllByMemberId(Long memberId) {
        return dogFoundRepository.findAllByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public Optional<DogFound> findById(Long id) {
        return dogFoundRepository.findById(id);
    }
}
