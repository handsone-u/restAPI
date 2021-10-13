package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.error.ErrorCode;
import com.handsone.restAPI.exception.ClientException;
import com.handsone.restAPI.repository.DogFoundRepository;
import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.repository.MemberRepository;
import com.handsone.restAPI.dto.DogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.handsone.restAPI.domain.DogFound.createDogFound;
import static com.handsone.restAPI.domain.ImageFile.createFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DogFoundService {

    private final DogFoundRepository dogFoundRepository;
    private final MemberRepository memberRepository;
    private final ImageFileService imageFileService;

    /**
     * 1. find Member, from dogDto.memberId
     * 2. Save Board(Found), get Board's PK
     * 3. Save Image, with Board's PK
     * @param dogDto : DTO of Board, containing Member's ID.
     * @param files : images to be Uploaded.
     * @return Entity of DogFound
     */
    @Transactional(readOnly = false)
    public DogFound upload(DogDto dogDto, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findById(dogDto.getMemberId())
                .orElseThrow(() -> new ClientException("Cannot find Member's info.", ErrorCode.NOTFOUND_MEMBER));
        DogFound dogFound = dogFoundRepository.save(createDogFound(member, dogDto));
        imageFileService.foundUpload(dogFound, files);

        return dogFound;
    }

    public DogFound findById(Long id) {
        return dogFoundRepository.findById(id)
                .orElseThrow(() -> new ClientException("Cannot find Dog's info.", ErrorCode.NOTFOUND_DOG));
    }

    public Page<DogFound> findAll(Pageable pageable) {
        return dogFoundRepository.findAll(pageable);
    }

    public long count() {
        return dogFoundRepository.count();
    }
//
//    public Slice<DogFound> findAllByBoardStatusNormal(PageRequest pageRequest) {
//        return dogFoundRepository.findAllByBoardStatus(BoardStatus.NORMAL, pageRequest);
//    }
//
//    public List<DogFound> findAll() {
//        return dogFoundRepository.findAll();
//    }
//
//    public List<DogFound> findAllByMemberId(Long memberId) {
//        return dogFoundRepository.findAllByMemberId(memberId);
//    }
}
