package com.handsone.restAPI.service;

import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.error.ErrorCode;
import com.handsone.restAPI.exception.ClientException;
import com.handsone.restAPI.repository.DogLostRepository;
import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.handsone.restAPI.domain.DogLost.createDogLost;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DogLostService {

    private final DogLostRepository dogLostRepository;
    private final MemberRepository memberRepository;
    private final ImageFileService imageFileService;

    /**
     * 1. find Member, from dogDto.memberId
     * 2. Save Board(Lost), get Board's PK
     * 3. Save Image, with Board's PK
     * @param dogDto : DTO of Board, containing Member's ID.
     * @param files : images to be Uploaded.
     * @return Entity of DogLost
     */
    @Transactional(readOnly = false)
    public DogLost upload(DogDto dogDto, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findById(dogDto.getMemberId())
                .orElseThrow(() -> new ClientException("Cannot find Member's info.", ErrorCode.NOTFOUND_MEMBER));
        DogLost dogLost = dogLostRepository.save(createDogLost(member, dogDto));
        imageFileService.lostUpload(dogLost, files);

        return dogLost;
    }

    public DogLost findById(Long id) {
        return dogLostRepository.findById(id)
                .orElseThrow(() -> new ClientException("Cannot find Dog's info.", ErrorCode.NOTFOUND_DOG));
    }

//    public Slice<DogLost> findAllByBoardStatusNormal(Pageable pageRequest) {
//        return dogLostRepository.findAllByBoardStatus(BoardStatus.NORMAL, pageRequest);
//    }
//
//    public Long count(){
//        return dogLostRepository.count();
//    }
//
//    public List<DogLost> findAll() {
//        return dogLostRepository.findAll();
//    }
//
//    public List<DogLost> findAllByMemberId(Long memberId) {
//        return dogLostRepository.findAllByMemberId(memberId);
//    }

}
