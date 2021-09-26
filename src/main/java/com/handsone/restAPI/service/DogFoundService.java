package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.repository.DogFoundRepository;
import com.handsone.restAPI.domain.File;
import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.repository.MemberRepository;
import com.handsone.restAPI.dto.DogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.handsone.restAPI.domain.DogFound.createDogFound;
import static com.handsone.restAPI.domain.File.createFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DogFoundService {

    private final DogFoundRepository dogFoundRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Transactional(readOnly = false)
    public DogFound upload(DogDto dogDto, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findById(dogDto.getMemberId()).get();
        DogFound dogFound = createDogFound(member, dogDto);
        dogFound = dogFoundRepository.save(dogFound);
        fileService.foundUpload(dogFound, files);

        return dogFound;
    }

    public Slice<DogFound> findAllByBoardStatusNormal(PageRequest pageRequest) {
        return dogFoundRepository.findAllByBoardStatus(BoardStatus.NORMAL, pageRequest);
    }

    public List<DogFound> findAll() {
        return dogFoundRepository.findAll();
    }

    public List<DogFound> findAllByMemberId(Long memberId) {
        return dogFoundRepository.findAllByMemberId(memberId);
    }

    public Optional<DogFound> findById(Long id) {
        return dogFoundRepository.findById(id);
    }
}
